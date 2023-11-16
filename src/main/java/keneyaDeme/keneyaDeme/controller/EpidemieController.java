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
import keneyaDeme.keneyaDeme.model.Epidemies;
import keneyaDeme.keneyaDeme.service.EpidemieService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")

@RequestMapping("/keneya")

@Valid
@Validated
public class EpidemieController {
     private final EpidemieService epidemieService;

    @PostMapping("/epidemie")
    @Operation(summary = "Création d'une epidemie")
    public ResponseEntity<Epidemies> createMaladie(
            @Valid @RequestParam("epidemies") String epidemieString,
            @RequestParam(value = "audio", required = false) MultipartFile audioFile)
            throws Exception {

        Epidemies epidemie = new Epidemies();
        try {
            epidemie = new JsonMapper().readValue(epidemieString, Epidemies.class);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }

        Epidemies savedEpidemies = epidemieService.createEpidemie(epidemie,  audioFile);

        return new ResponseEntity<>(savedEpidemies, HttpStatus.CREATED);
    }


    @GetMapping("/epidemies/count") 
    @Operation(summary = "Afficher le nombre d'epidemie")
    public long getNumberOfEpidemie() {
        return epidemieService.getNumberOfEpidemie();
    }
    @GetMapping("/epidemies")
    @Operation(summary = "Afficher la liste des epidemies")
    public List<Epidemies> readAll() {
        return epidemieService.readAll();
    }

    @GetMapping("/epidemie/{id}")
    @Operation(summary = "Récupère une epidemie par ID")
    public Epidemies read(@Valid @PathVariable Long id) {
        return epidemieService.readById(id);
    }
@PutMapping("/epidemie/{id}")
    @Operation(summary = "Mise à jour d'une epidemie par son Id ")
    public ResponseEntity<Epidemies> updateMaladie(
            @PathVariable Long id,
            @Valid @RequestParam("epidemies") String epidemieString,
            @RequestParam(value = "audio", required = false) MultipartFile audioFile) {
        Epidemies epidemieMiseAJour = new Epidemies();
        try {
            epidemieMiseAJour = new JsonMapper().readValue(epidemieString, Epidemies.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Epidemies epidemieMiseAJournee = epidemieService.updateEpidemie(id, epidemieMiseAJour, audioFile);
            return new ResponseEntity<>(epidemieMiseAJournee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/epidemie/{id}")
    @Operation(summary = "Supprimer une epidemie par son ID")
    public String delete(@Valid @PathVariable Long id) {
        return epidemieService.supprimer(id);
    }

}
