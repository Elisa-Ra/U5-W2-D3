package elisaraeli.U5_W2_D3.services;

import elisaraeli.U5_W2_D3.entities.Autore;
import elisaraeli.U5_W2_D3.entities.BlogPost;
import elisaraeli.U5_W2_D3.exceptions.BadRequestException;
import elisaraeli.U5_W2_D3.exceptions.NotFoundException;
import elisaraeli.U5_W2_D3.payloads.NewBlogPostPayload;
import elisaraeli.U5_W2_D3.repositories.AutoreRepository;
import elisaraeli.U5_W2_D3.repositories.BlogPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final AutoreRepository autoreRepository;

    @Autowired
    public BlogPostService(BlogPostRepository blogPostRepository, AutoreRepository autoreRepository) {
        this.blogPostRepository = blogPostRepository;
        this.autoreRepository = autoreRepository;
    }

    // Salvo
    public BlogPost save(NewBlogPostPayload payload) {

        // Controllo se il titolo / categoria sono vuoti
        if (payload.getTitolo() == null || payload.getTitolo().isBlank()) {
            throw new BadRequestException("Attenzione! Il titolo non può essere vuoto");
        }
        if (payload.getCategoria() == null || payload.getCategoria().isBlank()) {
            throw new BadRequestException("Attenzione! La categoria non può essere vuota");
        }

        //  Cerco l'autore per ID
        Autore autore = autoreRepository.findById(payload.getAutoreId())
                .orElseThrow(() -> new NotFoundException(payload.getAutoreId()));

        // Creo il post
        BlogPost newPost = new BlogPost(
                payload.getCategoria(),
                payload.getTitolo(),
                payload.getContenuto(),
                payload.getTempoDiLettura()
        );

        newPost.setAutore(autore);
        newPost.setCover("https://picsum.photos/200/300");

        // Salvo il post
        BlogPost saved = blogPostRepository.save(newPost);

        log.info("Il post con id: " + saved.getId() + " è stato salvato correttamente!");

        return saved;
    }

    // Paginazione
    public Page<BlogPost> findAll(int page, int size, String orderBy, String sortCriteria) {

        if (size > 100) size = 10;
        if (size < 1) size = 1;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(
                page,
                size,
                sortCriteria.equalsIgnoreCase("desc")
                        ? Sort.by(orderBy).descending()
                        : Sort.by(orderBy).ascending()
        );

        return blogPostRepository.findAll(pageable);
    }

    // Cerco un post per ID
    public BlogPost findById(UUID postId) {
        return blogPostRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(postId));
    }

    // Aggiorno il post per ID
    public BlogPost findByIdAndUpdate(UUID postId, NewBlogPostPayload payload) {

        // Cerco il post
        BlogPost found = this.findById(postId);

        // Controllo che il titolo non sia vuoto
        if (payload.getTitolo() == null || payload.getTitolo().isBlank()) {
            throw new BadRequestException("Attenzione! Il titolo non può essere vuoto");
        }

        // Aggiorno i vari campi
        found.setCategoria(payload.getCategoria());
        found.setTitolo(payload.getTitolo());
        found.setContenuto(payload.getContenuto());
        found.setTempoDiLettura(payload.getTempoDiLettura());

        // Controllo se è cambiato l'autore del post e lo cambio
        if (payload.getAutoreId() != null && !payload.getAutoreId().equals(found.getAutore().getId())) {
            Autore autore = autoreRepository.findById(payload.getAutoreId())
                    .orElseThrow(() -> new NotFoundException(payload.getAutoreId()));
            found.setAutore(autore);
        }

        // Salvo il post modificato
        BlogPost modified = blogPostRepository.save(found);
        log.info("Il post con id: " + modified.getId() + " è stato modificato correttamente!");
        return modified;
    }

    // Cancello un post per ID
    public void findByIdAndDelete(UUID postId) {
        BlogPost found = this.findById(postId);
        blogPostRepository.delete(found);
        log.info("Il post è stato correttamente eliminato!");
    }
}


