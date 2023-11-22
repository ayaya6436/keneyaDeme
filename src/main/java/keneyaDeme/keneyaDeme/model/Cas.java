package keneyaDeme.keneyaDeme.model;

import org.hibernate.annotations.CreationTimestamp; // Importez l'annotation correcte

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.PrePersist; // Importez l'annotation correcte
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name ="cas")
public class Cas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    @CreationTimestamp 
    private Date date;

   //  @ManyToOne()
   //  @JoinColumn(name = "id_users", nullable = false)
   //  private Users users;

    // Méthode pour définir le timestamp actuel avant la persistance de l'entité
    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = new Date();
        }
    }
}
