package keneyaDeme.keneyaDeme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import keneyaDeme.keneyaDeme.model.Traitements;

public interface TraitementRepository extends JpaRepository<Traitements, Long> {
        public Traitements findByNom(String nom);

}
