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

import keneyaDeme.keneyaDeme.model.Cas;

import keneyaDeme.keneyaDeme.service.CasService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/keneya")

@Valid
@Validated
public class CasController {
    private final CasService casService;

    @PostMapping("/cas")
    @Operation(summary = "Création d'un cas")
    public ResponseEntity<Cas> createCas(
            @Valid @RequestParam("cas") String casString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile)
            throws Exception {

        Cas cas = new Cas();
        try {
            cas = new JsonMapper().readValue(casString, Cas.class);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }

        Cas savedCas = casService.createCas(cas, imageFile);

        return new ResponseEntity<>(savedCas, HttpStatus.CREATED);
    }

    @GetMapping("/cas/count") 
    @Operation(summary = "Afficher le nombre de cas")
    public long getNumberOfCas() {
        return casService.getNumberOfCas();
    }
    @GetMapping("/cas")
    @Operation(summary = "Afficher la liste des cas")
    public List<Cas> readAll() {
        return casService.readAll();
    }

    @GetMapping("/cas/{id}")
    @Operation(summary = "Récupère un cas par ID")
    public Cas read(@Valid @PathVariable Long id) {
        return casService.readById(id);
    }




    @PutMapping("/cas/{id}")
    @Operation(summary = "Mise à jour d'un cas par son Id ")
    public ResponseEntity<Cas> updateCas(
            @PathVariable Long id,
            @Valid @RequestParam("cas") String casString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        Cas casMiseAJour = new Cas();
        try {
            casMiseAJour = new JsonMapper().readValue(casString, Cas.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Cas casMiseAJournee = casService.updateCas(id, casMiseAJour, imageFile);
            return new ResponseEntity<>(casMiseAJournee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/cas/{id}")
    @Operation(summary = "Supprimer un cas par son ID")
    public String delete(@Valid @PathVariable Long id) {
        return casService.supprimer(id);
    }

}
