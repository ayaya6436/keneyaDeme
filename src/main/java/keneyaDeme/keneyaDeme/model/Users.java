package keneyaDeme.keneyaDeme.model;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
@JsonIgnoreProperties({
    "maladies",
    "annonces",
    "epidemies",
    "zones",
    "cas",
})
public class Users {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length=50, nullable = false)
    @NotBlank(message = "Le nom ne doit pas être vide !")
    @NotNull(message = "Le nom ne doit pas être null")
    @Size(min = 3 ,message = "Le nom est trop court", max = 225)
    private String nom;

    @Column(length=50, nullable = false)
    @NotBlank(message = "Le prenom ne doit pas être vide !")
    @NotNull(message = "Le prenom ne doit pas être null")
    @Size(min = 3 ,message = "Le prenom est trop court", max = 225)
    private String prenom;

    @Column(length = 50)
    @Email(message = "L'adresse email doit être valide")
    @NotBlank(message = "L'adresse email ne doit pas être vide")
    private String email;


    @Column(length=250)
    private String password;
 
// un user peut ajouter un ou plusieurs maladies, annonces,accepter un cas,epidemies et zones
    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private List<Maladies> maladies;

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private List<Annonces> annonces;

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private List<Cas> cas;

  
}



