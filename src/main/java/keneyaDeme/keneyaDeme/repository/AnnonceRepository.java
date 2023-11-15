package keneyaDeme.keneyaDeme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import keneyaDeme.keneyaDeme.model.Annonces;


public interface AnnonceRepository extends JpaRepository<Annonces,Long> {
  public Annonces findByTitre(String titre);

}
