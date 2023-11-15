package keneyaDeme.keneyaDeme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import keneyaDeme.keneyaDeme.model.Users;

public interface UserRepository extends JpaRepository<Users, Long>{
    public Users findByEmail(String email);

    public Users findByEmailAndPassword(String email, String password);
}
