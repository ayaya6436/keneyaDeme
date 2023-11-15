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
@Table(name ="traitements")
public class Traitements {
     @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length=50, nullable = false)
    @NotBlank(message = "Le nom ne doit pas être vide !")
    @NotNull(message = "Le nom ne doit pas être null")
    @Size(min = 3 ,message = "Le nom est trop court", max = 225)
    private String nom;
    
    private String description;
    private String image;
    private String audio;

     //une methode de traitements  ne peut etre lie qu'a une et une seule maladie
    //  @JsonIgnore
   @ManyToOne()
   @JoinColumn(name = "id_maladies",nullable = false)
  
   private Maladies maladies;
}
    