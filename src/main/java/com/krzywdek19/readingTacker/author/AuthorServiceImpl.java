package com.krzywdek19.readingTacker.author;

import com.krzywdek19.readingTacker.author.dto.AuthorDto;
import com.krzywdek19.readingTacker.author.dto.CreateAuthorDto;
import com.krzywdek19.readingTacker.author.exception.AuthorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorDto createAuthor(CreateAuthorDto authorDto) {
        var author = Author.builder()
                .name(authorDto.getName())
                .lastName(authorDto.getLastName())
                .birthDate(authorDto.getBirthDate())
                .description(authorDto.getDescription())
                .writtenBooks(null)
                .build();

        return authorMapper.authorToAuthorDto(authorRepository.save(author));
    }

    @Override
    public AuthorDto updateAuthor(Long authorId, AuthorDto authorDto) {
        var existingAuthor = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id %d not found".formatted(authorId)));

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
    public void deleteAuthor(Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new AuthorNotFoundException("Author with id %d not found".formatted(authorId));
        }
        authorRepository.deleteById(authorId);
    }

    @Override
    public AuthorDto getAuthorById(Long authorId) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id %d not found".formatted(authorId)));

        return authorMapper.authorToAuthorDto(author);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::authorToAuthorDto)
                .toList();
    }
}