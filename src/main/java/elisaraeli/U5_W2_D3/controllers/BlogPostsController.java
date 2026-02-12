package elisaraeli.U5_W2_D3.controllers;

import elisaraeli.U5_W2_D3.entities.BlogPost;
import elisaraeli.U5_W2_D3.exceptions.ValidationException;
import elisaraeli.U5_W2_D3.payloads.BlogPostDTO;
import elisaraeli.U5_W2_D3.payloads.BlogPostPutDTO;
import elisaraeli.U5_W2_D3.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/blogPosts")
public class BlogPostsController {

    private final BlogPostService blogPostService;

    @Autowired
    public BlogPostsController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    // GET
    @GetMapping
    public Page<BlogPost> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "titolo") String orderBy,
            @RequestParam(defaultValue = "asc") String sort
    ) {
        return this.blogPostService.findAll(page, size, orderBy, sort);
    }

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost create(@RequestBody @Validated BlogPostDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.blogPostService.save(payload);
        }
    }

    // GET by ID
    @GetMapping("/{postId}")
    public BlogPost getById(@PathVariable UUID postId) {
        return this.blogPostService.findById(postId);
    }

    // PUT
    @PutMapping("/{postId}")
    public BlogPost update(@PathVariable UUID postId, @RequestBody @Validated BlogPostPutDTO payload, BindingResult validationResult
    ) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.blogPostService.findByIdAndUpdate(postId, payload);
        }


    }

    // DELETE
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID postId) {
        this.blogPostService.findByIdAndDelete(postId);
    }
}

