package keneyaDeme.keneyaDeme.controller;


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
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import keneyaDeme.keneyaDeme.model.Preventions;
import keneyaDeme.keneyaDeme.model.Traitements;
import keneyaDeme.keneyaDeme.service.TraitementService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")

@RequestMapping("/keneya")

@Valid
@Validated
public class TraitementController {

        private final TraitementService traitementService;

     @PostMapping("/traitement")
    @Operation(summary = "Création d'une methode de traitement")
    public ResponseEntity<Traitements> createPrevention(
            @Valid @RequestParam("traitements") String traitementString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @RequestParam(value = "audio", required = false) MultipartFile audioFile)
            throws Exception {

        Traitements traitement = new Traitements();
        try {
            traitement = new JsonMapper().readValue(traitementString, Traitements.class);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }

        Traitements savedTraitements = traitementService.createTraitement(traitement, imageFile, audioFile);

        return new ResponseEntity<>(savedTraitements, HttpStatus.CREATED);
    }


    

    @GetMapping("/traitements/maladie/{id}")
    @Operation(summary = "Récupérer toutes les traitemennts pour une maladie spécifique par son ID")
    public List<Traitements> getPreventionsByMaladieId(@Valid @PathVariable Long id) {
        return traitementService.getPreventionsByMaladiesId(id);
    }

     @GetMapping("/traitements")
    @Operation(summary = "Afficher la liste des methodes de traitements")
    public List<Traitements> readAll() {
        return traitementService.readAll();
    }

    @GetMapping("/traitement/{id}")
    @Operation(summary = "Récupère une methode de traitement par ID")
    public Traitements read(@Valid @PathVariable Long id) {
        return traitementService.readById(id);
    }


    
    @PutMapping("/traitement/{id}")
    @Operation(summary = "Mise à jour d'une methode de traitement par son Id ")
    public ResponseEntity<Traitements> updateMaladie(
            @PathVariable Long id,
            @Valid @RequestParam("traitements") String traitementString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @RequestParam(value = "audio", required = false) MultipartFile audioFile) {
            Traitements traitementMiseAJour = new Traitements();
        try {
            traitementMiseAJour = new JsonMapper().readValue(traitementString, Traitements.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Traitements traitementMiseAJournee = traitementService.updateTraitement(id, traitementMiseAJour, imageFile, audioFile);
            return new ResponseEntity<>(traitementMiseAJournee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     @DeleteMapping("/traitement/{id}")
    @Operation(summary = "Supprimer une methode de traitement par son ID")
    public String delete(@Valid @PathVariable Long id) {
        return traitementService.supprimer(id);
    }
}
