package keneyaDeme.keneyaDeme.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import keneyaDeme.keneyaDeme.model.Preventions;
import keneyaDeme.keneyaDeme.repository.PreventionRepository;
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
public class PreventionService {
    private final PreventionRepository preventionRepository;


    public List<Preventions> getPreventionsByMaladiesId(Long maladiesId) {
        return preventionRepository.findAllByMaladiesId(maladiesId);
    }

    
     public Preventions createPrevention(Preventions prevention, MultipartFile imageFile, MultipartFile audioFile) throws Exception {
        if (preventionRepository.findByNom(prevention.getNom()) == null) {
    
            // Traitement du fichier image
            if (imageFile != null) {
                String imageLocation = "C:\\xampp\\htdocs\\keneyaDeme\\images";
                try {
                    Path imageRootLocation = Paths.get(imageLocation);
                    if (!Files.exists(imageRootLocation)) {
                        Files.createDirectories(imageRootLocation);
                    }
    
                    String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                    Path imagePath = imageRootLocation.resolve(imageName);
                    Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                    prevention.setImage("http://10.175.48.17/keneyaDeme/images/" + imageName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement du fichier image : " + e.getMessage());
                }
            }
    
            // Traitement du fichier audio
            if (audioFile != null) {
                String audioLocation = "C:\\xampp\\htdocs\\keneyaDeme\\audios";
                try {
                    Path audioRootLocation = Paths.get(audioLocation);
                    if (!Files.exists(audioRootLocation)) {
                        Files.createDirectories(audioRootLocation);
                    }
    
                    String audioName = UUID.randomUUID().toString() + "_" + audioFile.getOriginalFilename();
                    Path audioPath = audioRootLocation.resolve(audioName);
                    Files.copy(audioFile.getInputStream(), audioPath, StandardCopyOption.REPLACE_EXISTING);
                    prevention.setAudio("http://10.175.48.17/keneyaDeme/audios/" + audioName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement du fichier audio : " + e.getMessage());
                }
            }
    
            return preventionRepository.save(prevention);
        } else {
            throw new IllegalArgumentException("La maladie " + prevention.getNom() + " existe déjà");
        }
    }


    public List<Preventions> readAll() {
        try {
            List<Preventions> prevntionList = preventionRepository.findAll();
    
            if (prevntionList.isEmpty()) {
                throw new NoSuchElementException("La liste des prevntions est vide");
            }
    
            return prevntionList;
        } catch (NoSuchElementException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Une erreur s'est produite lors de la lecture de la liste des prevntions", ex);
        }
    } 


    public Preventions readById(Long id) {
        return preventionRepository.findById(id).orElseThrow(() -> new RuntimeException("Prevention non trouvé !"));
    }


    public Preventions updatePrevention(Long id, Preventions prevention, MultipartFile imageFile, MultipartFile audioFile) throws Exception {
        try {
            Preventions preventionExistante = preventionRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Prevention non trouvée avec l'ID : " + id));

            // Mettre à jour les champs
            preventionExistante.setNom(prevention.getNom());
            preventionExistante.setDescription(prevention.getDescription());
           

            // Mettre à jour l'image si fournie
            if (imageFile != null) {
                String emplacementImage = "C:\\xampp\\htdocs\\keneyaDeme\\images";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                preventionExistante.setImage("http://10.175.48.17/keneyaDeme/images/" + nomImage);
            }

            // Mettre à jour l'audio si fourni
            if (audioFile != null) {
                String emplacementAudio = "C:\\xampp\\htdocs\\keneyaDeme\\audios";
                String nomAudio = UUID.randomUUID().toString() + "_" + audioFile.getOriginalFilename();
                Path cheminAudio = Paths.get(emplacementAudio).resolve(nomAudio);

                Files.copy(audioFile.getInputStream(), cheminAudio, StandardCopyOption.REPLACE_EXISTING);
                preventionExistante.setAudio("http://10.175.48.17/keneyaDeme/audios/" + nomAudio);
            }

            // Enregistrer la maladie mise à jour
            return preventionRepository.save(preventionExistante);
        } catch (NoSuchElementException ex) {
           throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour de la prevention avec l'ID : " + id);

        } 
    }


    public String supprimer(Long id) {
        Optional <Preventions> prevention = preventionRepository.findById(id);
         if (prevention.isPresent()) {
             preventionRepository.deleteById(id);
             return "Methode de Prevention supprimé avec succès.";
         } else {
           
             return "Methode de prrevention non trouvé.";
         }
     }
}
