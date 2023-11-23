package keneyaDeme.keneyaDeme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import keneyaDeme.keneyaDeme.model.Cas;

public interface CasRepository extends JpaRepository<Cas,Long>{
    
}
