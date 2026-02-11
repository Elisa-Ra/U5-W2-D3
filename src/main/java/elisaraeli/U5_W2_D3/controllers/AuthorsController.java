package elisaraeli.U5_W2_D3.controllers;

import elisaraeli.U5_W2_D3.entities.Autore;
import elisaraeli.U5_W2_D3.payloads.NewAuthorPayload;
import elisaraeli.U5_W2_D3.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    private final AuthorService authorService;

    @Autowired
    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // GET
    @GetMapping
    public Page<Autore> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String orderBy,
            @RequestParam(defaultValue = "asc") String sort
    ) {
        return this.authorService.findAll(page, size, orderBy, sort);
    }

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Autore create(@RequestBody NewAuthorPayload payload) {
        return this.authorService.save(payload);
    }

    // GET by ID
    @GetMapping("/{authorId}")
    public Autore getById(@PathVariable UUID authorId) {
        return this.authorService.findById(authorId);
    }

    // PUT
    @PutMapping("/{authorId}")
    public Autore update(@PathVariable UUID authorId, @RequestBody NewAuthorPayload payload) {
        return this.authorService.findByIdAndUpdate(authorId, payload);
    }

    // DELETE
    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID authorId) {
        this.authorService.findByIdAndDelete(authorId);
    }
}
