package keneyaDeme.keneyaDeme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import keneyaDeme.keneyaDeme.model.Zones;


public interface ZoneRepository extends JpaRepository<Zones,Long> {
    public Zones findByNom(String nom);
}
