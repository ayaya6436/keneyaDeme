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

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import keneyaDeme.keneyaDeme.model.Annonces;
import keneyaDeme.keneyaDeme.model.Cas;
import keneyaDeme.keneyaDeme.model.Users;
import keneyaDeme.keneyaDeme.repository.CasRepository;
import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor
public class CasService {
    private final CasRepository casRepository;
        private final JavaMailSender javaMailSender;
        private final UserService userService;


     public Cas createCas(Cas cas, MultipartFile imageFile) throws Exception {
       
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
                    cas.setImage("cas/" + imageName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement du fichier image : " + e.getMessage());
                }
            }
    
            sendEmailToUsers("Alerte!!!!!!!!!!!!!!!", "Un nouveau cas a été declaree. Consultez-le sur notre plateforme.");

            return casRepository.save(cas);
        
    }

     private void sendEmailToUsers(String subject, String text) {
        List<Users> usersList = userService.readAll(); // Remplacez par votre méthode pour obtenir la liste des utilisateurs

        for (Users user : usersList) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject(subject);
            message.setText(text);

            javaMailSender.send(message);
        }
    }

    public long getNumberOfCas() {
        try {
            return casRepository.count(); 
        } catch (Exception ex) {
            throw new RuntimeException("Une erreur s'est produite lors de la récupération du nombre de cas", ex);
        }
    }
    
    public List<Cas> readAll() {
        try {
            List<Cas> casList = casRepository.findAll();
    
            if (casList.isEmpty()) {
                throw new NoSuchElementException("La liste des cas est vide");
            }
    
            return casList;
        } catch (NoSuchElementException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Une erreur s'est produite lors de la lecture de la liste des cas", ex);
        }
    } 
    
    public Cas readById(Long id) {
        return casRepository.findById(id).orElseThrow(() -> new RuntimeException("Cas non trouvé !"));
    }


      public Cas updateCas(Long id, Cas cas, MultipartFile imageFile) throws Exception {
        try {
            Cas casExistante = casRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Cas non trouvée avec l'ID : " + id));

            // // Mettre à jour les champs
            // casExistante.setTitre(cas.getTitre());
            // casExistante.setDescription(cas.getDescription());
           

            // Mettre à jour l'image si fournie
            if (imageFile != null) {
                String emplacementImage = "C:\\xampp\\htdocs\\keneyaDeme\\images";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                casExistante.setImage("http://10.175.48.17/keneyaDeme/images/" + nomImage);
            }

            // Enregistrer la maladie mise à jour
            return casRepository.save(casExistante);
        } catch (NoSuchElementException ex) {
           throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour du cas avec l'ID : " + id);

        } 
    }


    
 public String supprimer(Long id) {
       Optional <Cas> cas = casRepository.findById(id);
        if (cas.isPresent()) {
            casRepository.deleteById(id);
            return "Cas supprimé avec succès.";
        } else {
          
            return "Cas non trouvé.";
        }
    }
}
