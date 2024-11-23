package keneyaDeme.keneyaDeme.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import keneyaDeme.keneyaDeme.model.Users;
import keneyaDeme.keneyaDeme.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public Users createUser(Users user) {
        if(userRepository.findByEmail(user.getEmail())==null){
            return userRepository.save(user);
        }else{
            throw new IllegalArgumentException("L'email" + user.getEmail() + "existe deja");
        }
    }

    public List<Users> readAll() {
    try {
        List<Users> usersList = userRepository.findAll();
        if (usersList.isEmpty()) {
            throw new NoSuchElementException("La liste d'utilisateurs est vide");
        }
        return usersList;
    } catch (NoSuchElementException ex) {
        throw ex; // Permettre à l'ApplicationControllerAdvice de gérer cette exception
    } catch (Exception ex) {
        throw new RuntimeException("Une erreur s'est produite lors de la lecture de la liste des utilisateurs", ex);
    }
}

    public Users readById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new RuntimeException("users non trouvé !"));

    }


    public Users modifier(Long id, Users user) {
        Optional<Users> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            Users updatedUser = existingUser.get();
            updatedUser.setNom(user.getNom());
            updatedUser.setPrenom(user.getPrenom());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            return userRepository.save(updatedUser);
        } else {

             throw new NoSuchElementException("Utilisateur non trouvé avec l'ID : " + id);
        }
    }


    public String supprimer(Long id) {
        Optional<Users> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return "Utilisateur supprimé avec succès.";
        } else {

            return "Utilisateur non trouvé.";
        }
    }


   /* public Object connexion(String email, String password) {
        Users user = userRepository.findByEmailAndPassword(email, password);
        if (user == null) {

            return "Connexion réussie.";
        } else {

            return user;
        }
    }*/

}
