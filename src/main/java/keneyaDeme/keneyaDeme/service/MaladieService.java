package keneyaDeme.keneyaDeme.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.stereotype.Service;


import keneyaDeme.keneyaDeme.model.Maladies;
import keneyaDeme.keneyaDeme.repository.MaladieRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MaladieService {
    private final MaladieRepository maladieRepository;
   

    public Maladies createMaladie(Maladies maladie, MultipartFile imageFile, MultipartFile audioFile) throws Exception {
        if (maladieRepository.findByNom(maladie.getNom()) == null) {
    
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
                    maladie.setImage("keneyaDeme/images/" + imageName);
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
                    maladie.setAudio("keneyaDeme/audios/" + audioName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement du fichier audio : " + e.getMessage());
                }
            }
    

            return maladieRepository.save(maladie);
        } else {
            throw new IllegalArgumentException("La maladie " + maladie.getNom() + " existe déjà");
        }
    }
    

 
    public List<Maladies> readAll() {
        try {
            List<Maladies> maladieList = maladieRepository.findAll();
    
            if (maladieList.isEmpty()) {
                throw new NoSuchElementException("La liste de maladies est vide");
            }
    
            return maladieList;
        } catch (NoSuchElementException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Une erreur s'est produite lors de la lecture de la liste des maladies", ex);
        }
    } 

    
     public long getNumberOfMaladies() {
        try {
            return maladieRepository.count(); 
        } catch (Exception ex) {
            throw new RuntimeException("Une erreur s'est produite lors de la récupération du nombre de maladies", ex);
        }
    }
    
  public Maladies readById(Long id) {
    return maladieRepository.findById(id).orElseThrow(() -> new RuntimeException("Maladie non trouvé !"));
}

  


    public Maladies updateMaladie(Long id, Maladies maladie, MultipartFile imageFile, MultipartFile audioFile) throws Exception {
        try {
            Maladies maladieExistante = maladieRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Maladie non trouvée avec l'ID : " + id));

            // Mettre à jour les champs
            maladieExistante.setNom(maladie.getNom());
            maladieExistante.setDescription(maladie.getDescription());
           

            // Mettre à jour l'image si fournie
            if (imageFile != null) {
                String emplacementImage = "C:\\xampp\\htdocs\\keneyaDeme\\images";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                maladieExistante.setImage("keneyaDeme/images/" + nomImage);
            }

            // Mettre à jour l'audio si fourni
            if (audioFile != null) {
                String emplacementAudio = "C:\\xampp\\htdocs\\keneyaDeme\\audios";
                String nomAudio = UUID.randomUUID().toString() + "_" + audioFile.getOriginalFilename();
                Path cheminAudio = Paths.get(emplacementAudio).resolve(nomAudio);

                Files.copy(audioFile.getInputStream(), cheminAudio, StandardCopyOption.REPLACE_EXISTING);
                maladieExistante.setAudio("keneyaDeme/audios/" + nomAudio);
            }

            // Enregistrer la maladie mise à jour
            return maladieRepository.save(maladieExistante);
        } catch (NoSuchElementException ex) {
           throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour de la maladie avec l'ID : " + id);

        } 
    }


     public String supprimer(Long id) {
       Optional <Maladies> maladie = maladieRepository.findById(id);
        if (maladie.isPresent()) {
            maladieRepository.deleteById(id);
            return "Maladie supprimé avec succès.";
        } else {
          
            return "Maladie non trouvé.";
        }
    }


   
    
}
