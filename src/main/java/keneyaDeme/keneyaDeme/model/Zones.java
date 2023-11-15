package keneyaDeme.keneyaDeme.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="zones")
public class Zones {
     @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length=50, nullable = false, unique = true)
    @NotBlank(message = "Le nom ne doit pas être vide !")
    @NotNull(message = "Le nom ne doit pas être null")
    @Size(min = 3 ,message = "Le nom est trop court", max = 225)
    private String nom;

    private String longitude;
    private String latitude;
    

    // Une zone peut être liée à plusieurs maladies
    @ManyToMany()
    @JoinTable(
        name = "zones_maladies",
        joinColumns = @JoinColumn(name = "id_zones"),
        inverseJoinColumns = @JoinColumn(name = "id_maladies")
    )
    @JsonBackReference
    private List<Maladies> maladies;


   public Zones(Long id) {
      this.id = id;
   }


   
}
