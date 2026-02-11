package elisaraeli.U5_W2_D3.services;

import elisaraeli.U5_W2_D3.entities.BlogPost;
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

    // salvo un post
    public BlogPost save(NewBlogPostPayload payload) {
        BlogPost newPost = new BlogPost(
                payload.getCategoria(),
                payload.getTitolo(),
                payload.getContenuto(),
                payload.getTempoDiLettura()
        );

        newPost.setCover("https://picsum.photos/200/300");

        this.blogPostsDB.add(newPost);
        log.info("Il post con id: " + newPost.getId() + " è stato creato correttamente!");

        return newPost;
    }

    // cerco un post per id
    public BlogPost findById(UUID postId) {
        BlogPost found = null;

        for (BlogPost post : this.blogPostsDB) {
            if (post.getId() == postId) found = post;
        }

        if (found == null) throw new NotFoundException(postId);

        return found;
    }

    // cerco un post per id e lo aggiorno
    public BlogPost findByIdAndUpdate(UUID postId, NewBlogPostPayload payload) {
        BlogPost found = null;

        for (BlogPost post : this.blogPostsDB) {
            if (post.getId() == postId) {
                found = post;
                found.setCategoria(payload.getCategoria());
                found.setTitolo(payload.getTitolo());
                found.setContenuto(payload.getContenuto());
                found.setTempoDiLettura(payload.getTempoDiLettura());
            }
        }

        if (found == null) throw new NotFoundException(postId);

        log.info("Il post con id: " + postId + " è stato aggiornato correttamente!");

        return found;
    }

    // cerco un post per id e lo elimino
    public void findByIdAndDelete(UUID postId) {
        BlogPost found = null;

        for (BlogPost post : this.blogPostsDB) {
            if (post.getId() == postId) found = post;
        }

        if (found == null) throw new NotFoundException(postId);

        this.blogPostsDB.remove(found);

        log.info("Il post con id: " + postId + " è stato eliminato correttamente");
    }
}
