package elisaraeli.U5_W2_D3.services;

import elisaraeli.U5_W2_D3.entities.Autore;
import elisaraeli.U5_W2_D3.exceptions.BadRequestException;
import elisaraeli.U5_W2_D3.exceptions.NotFoundException;
import elisaraeli.U5_W2_D3.payloads.AuthorDTO;
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
    public Autore save(AuthorDTO payload) {


        //  Controllo che l'email non sia già stata utilizzata
        autoreRepository.findByEmail(payload.email()).ifPresent(a -> {
            throw new BadRequestException("Attenzione! L'email " + a.getEmail() + " è già stata usata.");
        });

        //  Creo l'autore
        Autore newAuthor = new Autore(
                payload.nome(),
                payload.cognome(),
                payload.email(),
                payload.dataDiNascita()
        );

        newAuthor.setAvatar("https://ui-avatars.com/api/?name=" +
                payload.nome() + "+" + payload.cognome());

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
    public Autore findByIdAndUpdate(UUID authorId, AuthorDTO payload) {

        Autore found = this.findById(authorId);

        // Se l'email cambia, controllo che non sia già in uso
        if (!found.getEmail().equals(payload.email())) {
            autoreRepository.findByEmail(payload.email()).ifPresent(a -> {
                throw new BadRequestException("L'email " + a.getEmail() + " è già in uso!");
            });
        }

        found.setNome(payload.nome());
        found.setCognome(payload.cognome());
        found.setEmail(payload.email());
        found.setDataDiNascita(payload.dataDiNascita());
        found.setAvatar("https://ui-avatars.com/api/?name=" +
                payload.nome() + "+" + payload.cognome());

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

