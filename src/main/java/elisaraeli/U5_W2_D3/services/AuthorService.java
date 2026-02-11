package elisaraeli.U5_W2_D3.services;

import elisaraeli.U5_W2_D3.entities.Autore;
import elisaraeli.U5_W2_D3.exceptions.BadRequestException;
import elisaraeli.U5_W2_D3.exceptions.NotFoundException;
import elisaraeli.U5_W2_D3.payloads.NewAuthorPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AuthorService {

    private List<Autore> authorsDB = new ArrayList<>();

    public List<Autore> findAll() {
        return this.authorsDB;
    }

    // Salvo un autore
    public Autore save(NewAuthorPayload payload) {

        if (payload.getNome() == null || payload.getNome().isBlank()) {
            throw new BadRequestException("Il nome non può essere vuoto");
        }
        if (payload.getEmail() == null || payload.getEmail().isBlank()) {
            throw new BadRequestException("L'email non può essere vuota");
        }

        Autore newAuthor = new Autore(
                payload.getNome(),
                payload.getCognome(),
                payload.getEmail(),
                payload.getDataDiNascita()
        );

        newAuthor.setAvatar("https://ui-avatars.com/api/?name=" +
                payload.getNome() + "+" + payload.getCognome());

        this.authorsDB.add(newAuthor);
        log.info("L'autore con id {} è stato creato correttamente!", newAuthor.getId());

        return newAuthor;
    }

    // Cerco autore per id
    public Autore findById(UUID authorId) {
        Autore found = null;

        for (Autore autore : this.authorsDB) {
            if (autore.getId().equals(authorId)) {
                found = autore;
            }
        }

        if (found == null) throw new NotFoundException(authorId);

        return found;
    }

    // Cerco autore per id e lo aggiorno
    public Autore findByIdAndUpdate(UUID authorId, NewAuthorPayload payload) {
        Autore found = null;

        for (Autore autore : this.authorsDB) {
            if (autore.getId().equals(authorId)) {
                found = autore;

                if (payload.getNome() == null || payload.getNome().isBlank()) {
                    throw new BadRequestException("Il nome non può essere vuoto");
                }

                found.setNome(payload.getNome());
                found.setCognome(payload.getCognome());
                found.setEmail(payload.getEmail());
                found.setDataDiNascita(payload.getDataDiNascita());
            }
        }

        if (found == null) throw new NotFoundException(authorId);

        log.info("L'autore con id {} è stato aggiornato correttamente!", authorId);

        return found;
    }

    // Cerco autore per id e lo elimino
    public void findByIdAndDelete(UUID authorId) {
        Autore found = null;

        for (Autore autore : this.authorsDB) {
            if (autore.getId().equals(authorId)) {
                found = autore;
            }
        }

        if (found == null) throw new NotFoundException(authorId);

        this.authorsDB.remove(found);

        log.info("L'autore con id {} è stato eliminato correttamente", authorId);
    }
}
