package elisaraeli.U5_W2_D3.repositories;

import elisaraeli.U5_W2_D3.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlogPostRepository extends JpaRepository<BlogPost, UUID> {
}

