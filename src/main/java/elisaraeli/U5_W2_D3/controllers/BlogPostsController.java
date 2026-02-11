package elisaraeli.U5_W2_D3.controllers;

import elisaraeli.U5_W2_D3.entities.BlogPost;
import elisaraeli.U5_W2_D3.payloads.NewBlogPostPayload;
import elisaraeli.U5_W2_D3.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/blogPosts")
public class BlogPostsController {

    private final BlogPostService blogPostsService;

    @Autowired
    public BlogPostsController(BlogPostService blogPostsService) {
        this.blogPostsService = blogPostsService;
    }

    @GetMapping
    public List<BlogPost> findAll() {
        return this.blogPostsService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost create(@RequestBody NewBlogPostPayload payload) {
        return this.blogPostsService.save(payload);
    }

    @GetMapping("/{id}")
    public BlogPost findById(@PathVariable UUID id) {
        return this.blogPostsService.findById(id);
    }

    @PutMapping("/{id}")
    public BlogPost update(@PathVariable UUID id, @RequestBody NewBlogPostPayload payload) {
        return this.blogPostsService.findByIdAndUpdate(id, payload);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        this.blogPostsService.findByIdAndDelete(id);
    }
}
