package elisaraeli.U5_W2_D3.services;

import elisaraeli.U5_W2_D3.entities.Autore;
import elisaraeli.U5_W2_D3.exceptions.BadRequestException;
import elisaraeli.U5_W2_D3.exceptions.NotFoundException;
import elisaraeli.U5_W2_D3.payloads.NewAuthorPayload;
import elisaraeli.U5_W2_D3.repositories.AutoreRepository;
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
public class AuthorService {

    private final AutoreRepository autoreRepository;

    @Autowired
    public AuthorService(AutoreRepository autoreRepository) {
        this.autoreRepository = autoreRepository;
    }

    // Creazione autore
    public Autore save(NewAuthorPayload payload) {

        // Controllo che il nome / email non siano vuote
        if (payload.getNome() == null || payload.getNome().isBlank()) {
            throw new BadRequestException("Attenzione! Il nome non può essere vuoto");
        }
        if (payload.getEmail() == null || payload.getEmail().isBlank()) {
            throw new BadRequestException("Attenzione! L'email non può essere vuota");
        }

        //  Controllo che l'email non sia già stata utilizzata
        autoreRepository.findByEmail(payload.getEmail()).ifPresent(a -> {
            throw new BadRequestException("Attenzione! L'email " + a.getEmail() + " è già stata usata.");
        });

        //  Creo l'autore
        Autore newAuthor = new Autore(
                payload.getNome(),
                payload.getCognome(),
                payload.getEmail(),
                payload.getDataDiNascita()
        );

        newAuthor.setAvatar("https://ui-avatars.com/api/?name=" +
                payload.getNome() + "+" + payload.getCognome());

        // Lo salvo
        Autore autoreSalvato = autoreRepository.save(newAuthor);

        log.info("L'autore con id: " + autoreSalvato.getId() + " è stato salvato correttamente!");

        return autoreSalvato;
    }

    // PAGINAZIONE
    public Page<Autore> findAll(int page, int size, String orderBy, String sort) {

        if (size > 100) size = 10;
        if (size < 1) size = 1;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort.equalsIgnoreCase("desc")
                        ? Sort.by(orderBy).descending()
                        : Sort.by(orderBy).ascending()
        );

        return autoreRepository.findAll(pageable);
    }

    //  Trovo l'autore per ID
    public Autore findById(UUID authorId) {
        return autoreRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException(authorId));
    }

    // Aggiorno l'autore per ID
    public Autore findByIdAndUpdate(UUID authorId, NewAuthorPayload payload) {

        Autore found = this.findById(authorId);

        // Se l'email cambia, controllo che non sia già in uso
        if (!found.getEmail().equals(payload.getEmail())) {
            autoreRepository.findByEmail(payload.getEmail()).ifPresent(a -> {
                throw new BadRequestException("L'email " + a.getEmail() + " è già in uso!");
            });
        }

        found.setNome(payload.getNome());
        found.setCognome(payload.getCognome());
        found.setEmail(payload.getEmail());
        found.setDataDiNascita(payload.getDataDiNascita());
        found.setAvatar("https://ui-avatars.com/api/?name=" +
                payload.getNome() + "+" + payload.getCognome());

        Autore modified = autoreRepository.save(found);

        log.info("L'autore con id: " + modified.getId() + " è stato modificato correttamente!");

        return modified;
    }

    // Elimino l'autore per ID
    public void findByIdAndDelete(UUID authorId) {
        Autore found = this.findById(authorId);
        autoreRepository.delete(found);
        log.info("L'autore è stato eliminato correttamente!");
    }
}

