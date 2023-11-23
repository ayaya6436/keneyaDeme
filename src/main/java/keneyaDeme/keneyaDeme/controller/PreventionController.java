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
import keneyaDeme.keneyaDeme.model.Preventions;
import keneyaDeme.keneyaDeme.service.PreventionService;
import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")

@RequestMapping("/keneya")

@Valid
@Validated
public class PreventionController {
      // Injection de prevntionService
    private final PreventionService preventionService;

    @PostMapping("/prevention")
    @Operation(summary = "Création d'une methode prevention")
    public ResponseEntity<Preventions> createPrevention(
            @Valid @RequestParam("preventions") String preventionString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @RequestParam(value = "audio", required = false) MultipartFile audioFile)
            throws Exception {

        Preventions prevention = new Preventions();
        try {
            prevention = new JsonMapper().readValue(preventionString, Preventions.class);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }

        Preventions savedPreventions = preventionService.createPrevention(prevention, imageFile, audioFile);

        return new ResponseEntity<>(savedPreventions, HttpStatus.CREATED);
    }

    @GetMapping("/preventions/maladie/{id}")
    @Operation(summary = "Récupérer toutes les préventions pour une maladie spécifique par son ID")
    public List<Preventions> getPreventionsByMaladieId(@Valid @PathVariable Long id) {
        return preventionService.getPreventionsByMaladiesId(id);
    }


     @GetMapping("/preventions")
    @Operation(summary = "Afficher la liste des methodes de preventions")
    public List<Preventions> readAll() {
        return preventionService.readAll();
    }

    @GetMapping("/prevention/{id}")
    @Operation(summary = "Récupère une methode de prevention par ID")
    public Preventions read(@Valid @PathVariable Long id) {
        return preventionService.readById(id);
    }

    
    @PutMapping("/prevention/{id}")
    @Operation(summary = "Mise à jour d'une methode prevention par son Id ")
    public ResponseEntity<Preventions> updateMaladie(
            @PathVariable Long id,
            @Valid @RequestParam("preventions") String preventionString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @RequestParam(value = "audio", required = false) MultipartFile audioFile) {
            Preventions preventionMiseAJour = new Preventions();
        try {
            preventionMiseAJour = new JsonMapper().readValue(preventionString, Preventions.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Preventions preventionMiseAJournee = preventionService.updatePrevention(id, preventionMiseAJour, imageFile, audioFile);
            return new ResponseEntity<>(preventionMiseAJournee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/prevention/{id}")
    @Operation(summary = "Supprimer une methode de prevention par son ID")
    public String delete(@Valid @PathVariable Long id) {
        return preventionService.supprimer(id);
    }
}
