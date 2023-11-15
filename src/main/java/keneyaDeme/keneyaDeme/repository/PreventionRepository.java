package keneyaDeme.keneyaDeme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import keneyaDeme.keneyaDeme.model.Preventions;

public interface PreventionRepository extends JpaRepository<Preventions ,Long> {
    public Preventions findByNom(String nom);
}
