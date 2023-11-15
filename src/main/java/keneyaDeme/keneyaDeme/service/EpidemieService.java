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

import keneyaDeme.keneyaDeme.model.Epidemies;
import keneyaDeme.keneyaDeme.repository.EpidemieRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EpidemieService {
    private final EpidemieRepository epidemieRepository;

    
     public Epidemies createEpidemie(Epidemies epidemie, MultipartFile audioFile) throws Exception {
        if (epidemieRepository.findByNom(epidemie.getNom()) == null) {
    
            // Traitement du fichier audio
            if (audioFile != null) {
                String audioLocation = "C:var/www/html/devoir/audios";
                try {
                    Path audioRootLocation = Paths.get(audioLocation);
                    if (!Files.exists(audioRootLocation)) {
                        Files.createDirectories(audioRootLocation);
                    }
    
                    String audioName = UUID.randomUUID().toString() + "_" + audioFile.getOriginalFilename();
                    Path audioPath = audioRootLocation.resolve(audioName);
                    Files.copy(audioFile.getInputStream(), audioPath, StandardCopyOption.REPLACE_EXISTING);
                    epidemie.setAudio("http://localhost/devoir/audios/" + audioName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement du fichier audio : " + e.getMessage());
                }
            }
    
            return epidemieRepository.save(epidemie);
        } else {
            throw new IllegalArgumentException("L'epidemie " + epidemie.getNom() + " existe déjà");
        }
    }


    public long getNumberOfEpidemie() {
        try {
            return epidemieRepository.count(); 
        } catch (Exception ex) {
            throw new RuntimeException("Une erreur s'est produite lors de la récupération du nombre d'epidemie", ex);
        }
    }
    
    public List<Epidemies> readAll() {
        try {
            List<Epidemies> epidemiList = epidemieRepository.findAll();
    
            if (epidemiList.isEmpty()) {
                throw new NoSuchElementException("La liste des epidemies est vide");
            }
    
            return epidemiList;
        } catch (NoSuchElementException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Une erreur s'est produite lors de la lecture de la liste des epidemies", ex);
        }
    } 
    
    
  public Epidemies readById(Long id) {
    return epidemieRepository.findById(id).orElseThrow(() -> new RuntimeException("Epidemie non trouvé !"));
}


public Epidemies updateEpidemie(Long id, Epidemies epidemie, MultipartFile audioFile) throws Exception {
    try {
        Epidemies epidemieExistante = epidemieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Maladie non trouvée avec l'ID : " + id));

        // Mettre à jour les champs
        epidemieExistante.setNom(epidemie.getNom());
        epidemieExistante.setStatus(epidemie.getStatus());
        epidemieExistante.setVictimes(epidemie.getVictimes());
        epidemieExistante.setGravite(epidemie.getGravite());


    
        // Mettre à jour l'audio si fourni
        if (audioFile != null) {
            String emplacementAudio = "/var/www/html/devoir/audios";
            String nomAudio = UUID.randomUUID().toString() + "_" + audioFile.getOriginalFilename();
            Path cheminAudio = Paths.get(emplacementAudio).resolve(nomAudio);

            Files.copy(audioFile.getInputStream(), cheminAudio, StandardCopyOption.REPLACE_EXISTING);
            epidemieExistante.setAudio("http://localhost/devoir/audios/" + nomAudio);
        }

        // Enregistrer la maladie mise à jour
        return epidemieRepository.save(epidemieExistante);
    } catch (NoSuchElementException ex) {
       throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour de l'epidemie avec l'ID : " + id);

    } 
}

 public String supprimer(Long id) {
       Optional <Epidemies> epidemie = epidemieRepository.findById(id);
        if (epidemie.isPresent()) {
            epidemieRepository.deleteById(id);
            return "Epidemie supprimé avec succès.";
        } else {
          
            return "Epidemie non trouvé.";
        }
    }
}
