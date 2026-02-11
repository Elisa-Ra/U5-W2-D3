package elisaraeli.U5_W2_D3.repositories;

import elisaraeli.U5_W2_D3.entities.Autore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AutoreRepository extends JpaRepository<Autore, UUID> {
    Optional<Autore> findByEmail(String email);

}

