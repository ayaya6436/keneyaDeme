package keneyaDeme.keneyaDeme.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import keneyaDeme.keneyaDeme.model.Maladies;
import keneyaDeme.keneyaDeme.service.MaladieService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")

@RequestMapping("/keneya")

@Valid
@Validated
public class MaladieController {
    // Injection de maladieService
    private final MaladieService maladieService;

    @PostMapping("/maladie")
    @Operation(summary = "Création d'une maladie")
    public ResponseEntity<Maladies> createMaladie(
            @Valid @RequestParam("maladies") String maladieString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @RequestParam(value = "audio", required = false) MultipartFile audioFile
            )throws Exception {

        Maladies maladie = new Maladies();
        try {
            maladie = new JsonMapper().readValue(maladieString, Maladies.class);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }

        Maladies savedMaladies = maladieService.createMaladie(maladie, imageFile, audioFile);

        return new ResponseEntity<>(savedMaladies, HttpStatus.CREATED);
    }

    @GetMapping("/maladies/count") 
    @Operation(summary = "Afficher le nombre de maladie")
    public long getNumberOfMaladies() {
        return maladieService.getNumberOfMaladies();
    }

    @GetMapping("/maladies")
    @Operation(summary = "Afficher la liste des maladies")
    public List<Maladies> readAll() {
        return maladieService.readAll();
    }

    @GetMapping("/maladie/{id}")
    @Operation(summary = "Récupère une maladie par ID")
    public Maladies read(@Valid @PathVariable Long id) {
        return maladieService.readById(id);
    }

    @PutMapping("/maladie/{id}")
    @Operation(summary = "Mise à jour d'une maladie par son Id ")
    public ResponseEntity<Maladies> updateMaladie(
            @PathVariable Long id,
            @Valid @RequestParam("maladies") String maladieString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @RequestParam(value = "audio", required = false) MultipartFile audioFile) {
        Maladies maladieMiseAJour = new Maladies();
        try {
            maladieMiseAJour = new JsonMapper().readValue(maladieString, Maladies.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Maladies maladieMiseAJournee = maladieService.updateMaladie(id, maladieMiseAJour, imageFile, audioFile);
            return new ResponseEntity<>(maladieMiseAJournee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/maladie/{id}")
    @Operation(summary = "Supprimer une maladie par son ID")
    public String delete(@Valid @PathVariable Long id) {
        return maladieService.supprimer(id);
    }

}
