package keneyaDeme.keneyaDeme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import keneyaDeme.keneyaDeme.model.Epidemies;


public interface EpidemieRepository extends JpaRepository<Epidemies,Long> {
        public Epidemies findByNom(String nom);

}
