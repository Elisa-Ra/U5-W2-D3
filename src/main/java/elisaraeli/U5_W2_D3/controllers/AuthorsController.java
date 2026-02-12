package elisaraeli.U5_W2_D3.controllers;

import elisaraeli.U5_W2_D3.entities.Autore;
import elisaraeli.U5_W2_D3.exceptions.ValidationException;
import elisaraeli.U5_W2_D3.payloads.AuthorDTO;
import elisaraeli.U5_W2_D3.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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
    public Autore create(@RequestBody @Validated AuthorDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {

            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.authorService.save(payload);
        }
    }

    // GET by ID
    @GetMapping("/{authorId}")
    public Autore getById(@PathVariable UUID authorId) {
        return this.authorService.findById(authorId);
    }

    // PUT
    @PutMapping("/{authorId}")
    public Autore update(
            @PathVariable UUID authorId,
            @RequestBody @Validated AuthorDTO payload,
            BindingResult validationResult
    ) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        }

        return this.authorService.findByIdAndUpdate(authorId, payload);
    }


    // DELETE
    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID authorId) {
        this.authorService.findByIdAndDelete(authorId);
    }

    @PatchMapping("/{authorId}/avatar")
    public String uploadImage(@RequestParam("avatar") MultipartFile file, @PathVariable UUID authorId) {
        // avatar deve corrispondere ESATTAMENTE al campo del Form Data dove viene inserito il file
        // se così non è il file non verrà trovato
        // per controllare se il file c'è
        //return file.getOriginalFilename();
        //System.out.println(file.getSize());
        String url = this.authorService.uploadAvatar(file, authorId);

        return url; // TODO: cambiare return da String ad un payload in JSON
    }
}
