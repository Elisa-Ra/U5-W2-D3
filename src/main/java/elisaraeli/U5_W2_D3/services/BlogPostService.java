package elisaraeli.U5_W2_D3.services;

import elisaraeli.U5_W2_D3.entities.BlogPost;
import elisaraeli.U5_W2_D3.exceptions.BadRequestException;
import elisaraeli.U5_W2_D3.exceptions.NotFoundException;
import elisaraeli.U5_W2_D3.payloads.NewBlogPostPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BlogPostService {

    private List<BlogPost> blogPostsDB = new ArrayList<>();

    public List<BlogPost> findAll() {
        return this.blogPostsDB;
    }

    // Salvo un post
    public BlogPost save(NewBlogPostPayload payload) {

        // Validazioni minime
        if (payload.getTitolo() == null || payload.getTitolo().isBlank()) {
            throw new BadRequestException("Il titolo non può essere vuoto");
        }
        if (payload.getCategoria() == null || payload.getCategoria().isBlank()) {
            throw new BadRequestException("La categoria non può essere vuota");
        }

        BlogPost newPost = new BlogPost(
                payload.getCategoria(),
                payload.getTitolo(),
                payload.getContenuto(),
                payload.getTempoDiLettura()
        );

        newPost.setCover("https://picsum.photos/200/300");

        this.blogPostsDB.add(newPost);
        log.info("Il post con id {} è stato creato correttamente!", newPost.getId());

        return newPost;
    }

    // Cerco un post per id
    public BlogPost findById(UUID postId) {
        BlogPost found = null;

        for (BlogPost post : this.blogPostsDB) {
            if (post.getId().equals(postId)) {
                found = post;
            }
        }

        if (found == null) throw new NotFoundException(postId);

        return found;
    }

    // Cerco un post per id e lo aggiorno
    public BlogPost findByIdAndUpdate(UUID postId, NewBlogPostPayload payload) {
        BlogPost found = null;

        for (BlogPost post : this.blogPostsDB) {
            if (post.getId().equals(postId)) {
                found = post;

                // Validazioni
                if (payload.getTitolo() == null || payload.getTitolo().isBlank()) {
                    throw new BadRequestException("Il titolo non può essere vuoto");
                }

                found.setCategoria(payload.getCategoria());
                found.setTitolo(payload.getTitolo());
                found.setContenuto(payload.getContenuto());
                found.setTempoDiLettura(payload.getTempoDiLettura());
            }
        }

        if (found == null) throw new NotFoundException(postId);

        log.info("Il post con id {} è stato aggiornato correttamente!", postId);

        return found;
    }

    // Cerco un post per id e lo elimino
    public void findByIdAndDelete(UUID postId) {
        BlogPost found = null;

        for (BlogPost post : this.blogPostsDB) {
            if (post.getId().equals(postId)) {
                found = post;
            }
        }

        if (found == null) throw new NotFoundException(postId);

        this.blogPostsDB.remove(found);

        log.info("Il post con id {} è stato eliminato correttamente", postId);
    }
}

