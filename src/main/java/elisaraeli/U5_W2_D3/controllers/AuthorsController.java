package elisaraeli.U5_W2_D3.controllers;

import elisaraeli.U5_W2_D3.entities.Autore;
import elisaraeli.U5_W2_D3.payloads.NewAuthorPayload;
import elisaraeli.U5_W2_D3.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    private final AuthorService authorsService;

    @Autowired
    public AuthorsController(AuthorService authorsService) {
        this.authorsService = authorsService;
    }

    @GetMapping
    public List<Autore> findAll() {
        return this.authorsService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Autore create(@RequestBody NewAuthorPayload payload) {
        return this.authorsService.save(payload);
    }

    @GetMapping("/{id}")
    public Autore findById(@PathVariable UUID id) {
        return this.authorsService.findById(id);
    }

    @PutMapping("/{id}")
    public Autore update(@PathVariable UUID id, @RequestBody NewAuthorPayload payload) {
        return this.authorsService.findByIdAndUpdate(id, payload);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        this.authorsService.findByIdAndDelete(id);
    }
}