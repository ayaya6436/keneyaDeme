package keneyaDeme.keneyaDeme.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;


// import io.swagger.v3.oas.annotations.Operation;

import keneyaDeme.keneyaDeme.model.Users;
import keneyaDeme.keneyaDeme.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/keneya")

@Valid
@Validated
public class UserController {
    // Injection de userService
    private final UserService userService;

    @PostMapping("/user")
    @Operation(summary = "Creer un nouveau user")
    public Users create(@Valid @RequestBody Users user) {
       return userService.createUser(user);

    }

    @GetMapping("/users")
    @Operation(summary = "Afficher la liste des users")
    public List<Users> read() {
        return userService.readAll();
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Récupère une user grâce à son ID ")
    public Users read(@Valid @PathVariable Long id) {
        return userService.readById(id);
    }

    @PutMapping("/user/{id}")
    @Operation(summary = "Récupère un user grâce à son ID  et le modifie")
    public Users update(@Valid @PathVariable Long id, @RequestBody Users user) {
        return userService.modifier(id, user);
    }

    @DeleteMapping("/user/{id}")
    @Operation(summary = "Supprimer une depense par son ID")

    public String delete(@Valid @PathVariable Long id) {
        return userService.supprimer(id);
    }

   /* @PostMapping("/login")
    @Operation(summary = "Connexion d'un utilisateur")
    public Object connexion(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");
        return userService.connexion(email, password);
    }
    */
}
