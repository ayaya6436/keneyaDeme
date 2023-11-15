package keneyaDeme.keneyaDeme.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import keneyaDeme.keneyaDeme.model.Annonces;
import keneyaDeme.keneyaDeme.repository.AnnonceRepository;
import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor
public class AnnonceService {
    private final AnnonceRepository annonceRepository;

     public Annonces createAnnonce(Annonces annonce, MultipartFile imageFile) throws Exception {
        if (annonceRepository.findByTitre(annonce.getTitre()) == null) {
    
            // Traitement du fichier image
            if (imageFile != null) {
                String imageLocation = "var/www/html/devoir/images";
                try {
                    Path imageRootLocation = Paths.get(imageLocation);
                    if (!Files.exists(imageRootLocation)) {
                        Files.createDirectories(imageRootLocation);
                    }
    
                    String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                    Path imagePath = imageRootLocation.resolve(imageName);
                    Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                    annonce.setImage("http://localhost/devoir/images/" + imageName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement du fichier image : " + e.getMessage());
                }
            }
    
    
            return annonceRepository.save(annonce);
        } else {
            throw new IllegalArgumentException("L'annonce " + annonce.getTitre() + " existe déjà");
        }
    }


    
    public List<Annonces> readAll() {
        try {
            List<Annonces> annonceList = annonceRepository.findAll();
    
            if (annonceList.isEmpty()) {
                throw new NoSuchElementException("La liste des annonce est vide");
            }
    
            return annonceList;
        } catch (NoSuchElementException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Une erreur s'est produite lors de la lecture de la liste des annonces", ex);
        }
    } 
    
    public Annonces readById(Long id) {
        return annonceRepository.findById(id).orElseThrow(() -> new RuntimeException("Annonce non trouvé !"));
    }


    public Annonces updateAnnonce(Long id, Annonces annonce, MultipartFile imageFile) throws Exception {
        try {
            Annonces annonceExistante = annonceRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Annonce non trouvée avec l'ID : " + id));

            // Mettre à jour les champs
            annonceExistante.setTitre(annonce.getTitre());
            annonceExistante.setDescription(annonce.getDescription());
           

            // Mettre à jour l'image si fournie
            if (imageFile != null) {
                String emplacementImage = "C:\\xampp\\htdocs\\keneyaDeme\\images";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                annonceExistante.setImage("http://localhost/keneyaDeme/images/" + nomImage);
            }

            // Enregistrer la maladie mise à jour
            return annonceRepository.save(annonceExistante);
        } catch (NoSuchElementException ex) {
           throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour de l'annonce avec l'ID : " + id);

        } 
    }


    
 public String supprimer(Long id) {
       Optional <Annonces> annonce = annonceRepository.findById(id);
        if (annonce.isPresent()) {
            annonceRepository.deleteById(id);
            return "Annonce supprimé avec succès.";
        } else {
          
            return "Annonce non trouvé.";
        }
    }
    
    
}
