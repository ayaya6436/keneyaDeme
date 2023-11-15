package keneyaDeme.keneyaDeme.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name ="annonces")
public class Annonces {
@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    
    private Long id;
    @Column(length=50, nullable = false, unique = true)
    @NotBlank(message = "Le titre ne doit pas être vide !")
    @NotNull(message = "Le titre ne doit pas être null")
    @Size(min = 3 ,message = "Le titre est trop court", max = 225)
    private String titre;
    private String description;
    private String image;

   //une annonce ne peut etre lie qu'a un et un seul user
   @ManyToOne()
   @JoinColumn(name = "id_users",nullable = false)
   
   private Users users;
    
}