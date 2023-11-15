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
import keneyaDeme.keneyaDeme.model.Annonces;
import keneyaDeme.keneyaDeme.service.AnnonceService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/keneya")

@Valid
@Validated
public class AnnonceController {
     private final AnnonceService annonceService;

    @PostMapping("/annonce")
    @Operation(summary = "Création d'une annonce")
    public ResponseEntity<Annonces> createAnnonce(
            @Valid @RequestParam("annonces") String annonceString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile)
            throws Exception {

        Annonces annonce = new Annonces();
        try {
            annonce = new JsonMapper().readValue(annonceString, Annonces.class);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }

        Annonces savedAnnonces = annonceService.createAnnonce(annonce, imageFile);

        return new ResponseEntity<>(savedAnnonces, HttpStatus.CREATED);
    }


     @GetMapping("/annonces")
    @Operation(summary = "Afficher la liste des annonces")
    public List<Annonces> readAll() {
        return annonceService.readAll();
    }

    @GetMapping("/annonce/{id}")
    @Operation(summary = "Récupère une annonce par ID")
    public Annonces read(@Valid @PathVariable Long id) {
        return annonceService.readById(id);
    }


    @PutMapping("/annonce/{id}")
    @Operation(summary = "Mise à jour d'une annonce par son Id ")
    public ResponseEntity<Annonces> updateMaladie(
            @PathVariable Long id,
            @Valid @RequestParam("annonces") String annonceString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        Annonces annonceMiseAJour = new Annonces();
        try {
            annonceMiseAJour = new JsonMapper().readValue(annonceString, Annonces.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Annonces annonceMiseAJournee = annonceService.updateAnnonce(id, annonceMiseAJour, imageFile);
            return new ResponseEntity<>(annonceMiseAJournee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/annonce/{id}")
    @Operation(summary = "Supprimer une annonce par son ID")
    public String delete(@Valid @PathVariable Long id) {
        return annonceService.supprimer(id);
    }

}
