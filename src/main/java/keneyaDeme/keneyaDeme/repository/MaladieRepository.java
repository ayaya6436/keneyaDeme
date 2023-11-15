package keneyaDeme.keneyaDeme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import keneyaDeme.keneyaDeme.model.Maladies;


public interface MaladieRepository extends JpaRepository<Maladies,Long> {
    public Maladies findByNom(String nom);
    
} 
