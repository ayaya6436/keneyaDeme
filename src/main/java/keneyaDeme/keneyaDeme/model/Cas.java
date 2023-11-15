package keneyaDeme.keneyaDeme.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name ="cas")
public class Cas {
     @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String image;

    //un cas ne peut etre lie qu'a un et un seul user
   @ManyToOne()
   @JoinColumn(name = "id_users",nullable = false)
   
   private Users users;
}
