package elisaraeli.U5_W2_D3.services;

import elisaraeli.U5_W2_D3.entities.Autore;
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

    // salvo un autore
    public Autore save(NewAuthorPayload payload) {
        Autore newAuthor = new Autore(
                payload.getNome(),
                payload.getCognome(),
                payload.getEmail(),
                payload.getDataDiNascita()
        );

        newAuthor.setAvatar("https://ui-avatars.com/api/?name=" +
                payload.getNome() + "+" + payload.getCognome());

        this.authorsDB.add(newAuthor);
        log.info("L'autore con id: " + newAuthor.getId() + " è stato aggiunto correttamente");

        return newAuthor;
    }

    // cerco un autore per id
    public Autore findById(UUID authorId) {
        Autore found = null;

        for (Autore autore : this.authorsDB) {
            if (autore.getId() == authorId) found = autore;
        }

        if (found == null) throw new NotFoundException(authorId);

        return found;
    }

    // cerco un autore per id e lo aggiorno
    public Autore findByIdAndUpdate(UUID authorId, NewAuthorPayload payload) {
        Autore found = null;

        for (Autore autore : this.authorsDB) {
            if (autore.getId() == authorId) {
                found = autore;
                found.setNome(payload.getNome());
                found.setCognome(payload.getCognome());
                found.setEmail(payload.getEmail());
                found.setDataDiNascita(payload.getDataDiNascita());
            }
        }

        if (found == null) throw new NotFoundException(authorId);

        log.info("L'autore con id: " + authorId + " è stato aggiornato correttamente");

        return found;
    }

    // cerco un autore per id e lo elimino
    public void findByIdAndDelete(UUID authorId) {
        Autore found = null;

        for (Autore autore : this.authorsDB) {
            if (autore.getId() == authorId) found = autore;
        }

        if (found == null) throw new NotFoundException(authorId);

        this.authorsDB.remove(found);

        log.info("L'autore con id: " + authorId + " è stato eliminato correttamente");
    }
}