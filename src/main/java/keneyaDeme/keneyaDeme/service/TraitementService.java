package keneyaDeme.keneyaDeme.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import keneyaDeme.keneyaDeme.model.Traitements;
import keneyaDeme.keneyaDeme.repository.TraitementRepository;
import lombok.AllArgsConstructor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TraitementService {
    private final TraitementRepository traitementRepository;

    public Traitements createTraitement(Traitements traitement, MultipartFile imageFile, MultipartFile audioFile)
            throws Exception {
        if (traitementRepository.findByNom(traitement.getNom()) == null) {
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
                    traitement.setImage("http://localhost/devoir/images/" + imageName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement du fichier image : " + e.getMessage());
                }
            }

            // Traitement du fichier audio
            if (audioFile != null) {
                String audioLocation = "var/www/html/devoir/audios";
                try {
                    Path audioRootLocation = Paths.get(audioLocation);
                    if (!Files.exists(audioRootLocation)) {
                        Files.createDirectories(audioRootLocation);
                    }

                    String audioName = UUID.randomUUID().toString() + "_" + audioFile.getOriginalFilename();
                    Path audioPath = audioRootLocation.resolve(audioName);
                    Files.copy(audioFile.getInputStream(), audioPath, StandardCopyOption.REPLACE_EXISTING);
                    traitement.setAudio("http://localhost/devoir/audios/" + audioName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement du fichier audio : " + e.getMessage());
                }
            }

            return traitementRepository.save(traitement);
        } else {
            throw new IllegalArgumentException("La methode de prevention " + traitement.getNom() + " existe déjà");
        }
    }

    
   public List<Traitements> readAll() {
        try {
            List<Traitements> traitementList = traitementRepository.findAll();
    
            if (traitementList.isEmpty()) {
                throw new NoSuchElementException("La liste des traitements est vide");
            }
    
            return traitementList;
        } catch (NoSuchElementException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Une erreur s'est produite lors de la lecture de la liste des traitements", ex);
        }
    } 

    
    public Traitements readById(Long id) {
        return traitementRepository.findById(id).orElseThrow(() -> new RuntimeException("Traitements non trouvé !"));
    }


    public Traitements updateTraitement(Long id, Traitements traitement, MultipartFile imageFile, MultipartFile audioFile) throws Exception {
        try {
            Traitements traitementExistante = traitementRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(" Methode de traitement non trouvée avec l'ID : " + id));

            // Mettre à jour les champs
            traitementExistante.setNom(traitement.getNom());
            traitementExistante.setDescription(traitement.getDescription());
           

            // Mettre à jour l'image si fournie
            if (imageFile != null) {
                String emplacementImage = "C:\\xampp\\htdocs\\keneyaDeme\\images";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                traitementExistante.setImage("http://localhost/keneyaDeme/images/" + nomImage);
            }

            // Mettre à jour l'audio si fourni
            if (audioFile != null) {
                String emplacementAudio = "C:\\xampp\\htdocs\\keneyaDeme\\audios";
                String nomAudio = UUID.randomUUID().toString() + "_" + audioFile.getOriginalFilename();
                Path cheminAudio = Paths.get(emplacementAudio).resolve(nomAudio);

                Files.copy(audioFile.getInputStream(), cheminAudio, StandardCopyOption.REPLACE_EXISTING);
                traitementExistante.setAudio("http://localhost/keneyaDeme/audios/" + nomAudio);
            }

            // Enregistrer la maladie mise à jour
            return traitementRepository.save(traitementExistante);
        } catch (NoSuchElementException ex) {
           throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour de la methode de prevention avec l'ID : " + id);

        } 
    }

    public String supprimer(Long id) {
        Optional <Traitements> traitement = traitementRepository.findById(id);
         if (traitement.isPresent()) {
             traitementRepository.deleteById(id);
             return "Methode de Traitement supprimé avec succès.";
         } else {
           
             return "Methode de prrevention non trouvé.";
         }
     }

}

