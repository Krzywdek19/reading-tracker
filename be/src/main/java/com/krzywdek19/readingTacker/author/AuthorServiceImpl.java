package com.krzywdek19.readingTacker.author;

import com.krzywdek19.readingTacker.auth.user.CurrentUserProvider;
import com.krzywdek19.readingTacker.author.dto.AuthorDto;
import com.krzywdek19.readingTacker.author.dto.CreateAuthorDto;
import com.krzywdek19.readingTacker.author.exception.AuthorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final CurrentUserProvider currentUserProvider;

    @Override
    public AuthorDto createAuthor(CreateAuthorDto authorDto) {
        var currentuser = currentUserProvider.getCurrentUser();

        var author = Author.builder()
                .name(authorDto.getName())
                .lastName(authorDto.getLastName())
                .birthDate(authorDto.getBirthDate())
                .description(authorDto.getDescription())
                .addedBy(currentuser)
                .writtenBooks(null)
                .build();

        return authorMapper.authorToAuthorDto(authorRepository.save(author));
    }

    @Override
    public AuthorDto updateAuthor(Long authorId, AuthorDto authorDto) throws AccessDeniedException {
        var existingAuthor = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id %d not found".formatted(authorId)));
        checkAuthorCreator(existingAuthor);
        if (authorDto.getName() != null) {
            existingAuthor.setName(authorDto.getName());
        }
        if (authorDto.getLastName() != null) {
            existingAuthor.setLastName(authorDto.getLastName());
        }
        if (authorDto.getBirthDate() != null) {
            existingAuthor.setBirthDate(authorDto.getBirthDate());
        }
        if (authorDto.getDescription() != null) {
            existingAuthor.setDescription(authorDto.getDescription());
        }

        return authorMapper.authorToAuthorDto(authorRepository.save(existingAuthor));
    }

    @Override
    public void deleteAuthor(Long authorId) throws AccessDeniedException {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id %d not found".formatted(authorId)));
        checkAuthorCreator(author);
        authorRepository.deleteById(authorId);
    }

    @Override
    public AuthorDto getAuthorById(Long authorId) throws AccessDeniedException {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id %d not found".formatted(authorId)));
        if(!checkAuthorCreator(author)) {
            throw new AccessDeniedException("Author with id %d doesn't belong to current user".formatted(authorId));
        }
        return authorMapper.authorToAuthorDto(author);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        var user = currentUserProvider.getCurrentUser();
        return authorRepository.findAll().stream()
                .filter(this::checkAuthorCreator)
                .map(authorMapper::authorToAuthorDto)
                .toList();
    }

    private boolean checkAuthorCreator(Author author) {
        var currentUser = currentUserProvider.getCurrentUser();
        return author.getAddedBy().getId().equals(currentUser.getId());
    }
}