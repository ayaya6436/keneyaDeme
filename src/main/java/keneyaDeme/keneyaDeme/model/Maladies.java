package keneyaDeme.keneyaDeme.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="maladies")
@JsonIgnoreProperties({
   
    "preventions",
    "epidemies",
    "traitements",
   
})
public class Maladies {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length=50, nullable = false, unique = true)
    @NotBlank(message = "Le nom ne doit pas être vide !")
    @NotNull(message = "Le nom ne doit pas être null")
    @Size(min = 3 ,message = "Le nom est trop court", max = 225)
    private String nom;

    private String image;
    private String description;

    private String audio;

    //une maladie ne peut etre lie qu'a un et un seul user
    // @JsonIgnore
    
    @ManyToOne()
    @JoinColumn(name = "id_users",nullable = false)
  
    private Users users;

    
    
    public Maladies(Long id) {
        this.id = id;
    }
    //une maladie ne peut etre lie qu'a une et un seule epidemie
 
    @OneToMany(mappedBy = "maladies",cascade = CascadeType.ALL)
      @JsonManagedReference
    private List<Epidemies> epidemies;

    //une maladie ne peut etre lie qu'a une et un seule methode de traitement
    @OneToMany(mappedBy = "maladies",cascade = CascadeType.ALL)
    private List<Traitements> traitements;

    //une maladie ne peut etre lie qu'a une et un seule methode de preven tion
    @OneToMany(mappedBy = "maladies",cascade = CascadeType.ALL)
    private List<Preventions> preventions;

    @ManyToMany(mappedBy = "maladies",cascade = CascadeType.ALL)
    
    private List<Zones> zones;

    
}
