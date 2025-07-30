package com.krzywdek19.readingTacker.author;

import com.krzywdek19.readingTacker.author.dto.AuthorDto;
import com.krzywdek19.readingTacker.author.dto.CreateAuthorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody CreateAuthorDto authorDto) {
        var createdAuthor = authorService.createAuthor(authorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(
            @PathVariable("id") Long authorId,
            @RequestBody AuthorDto authorDto
    ) throws AccessDeniedException {
        var updatedAuthor = authorService.updateAuthor(authorId, authorDto);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long authorId) throws AccessDeniedException {
        authorService.deleteAuthor(authorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long authorId) throws AccessDeniedException {
        var author = authorService.getAuthorById(authorId);
        return ResponseEntity.ok(author);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        var authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }
}