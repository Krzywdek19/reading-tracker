package com.krzywdek19.readingTacker.book;

import com.krzywdek19.readingTacker.author.AuthorRepository;
import com.krzywdek19.readingTacker.author.exception.AuthorNotFoundException;
import com.krzywdek19.readingTacker.book.dto.BookCreateDto;
import com.krzywdek19.readingTacker.book.dto.BookDto;
import com.krzywdek19.readingTacker.book.dto.BookUpdateDto;
import com.krzywdek19.readingTacker.book.exception.BookNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        var author =  authorRepository.findById(bookCreateDto.getAuthorId()).orElseThrow(
                ()->new AuthorNotFoundException("Author with id %d not found".formatted(bookCreateDto.getAuthorId())));
        var book = Book.builder()
                .title(bookCreateDto.getTitle())
                .author(author)
                .pages(bookCreateDto.getPages())
                .description(bookCreateDto.getDescription())
                .build();
        return bookMapper
                .toBookDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDto updateBook(Long id, BookUpdateDto bookUpdateDto) {
        var book  = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format("Book with id %d not found", id)));
        if(!Objects.equals(bookUpdateDto.getTitle(), book.getTitle())) {
            book.setTitle(bookUpdateDto.getTitle());
        }
        if(!Objects.equals(bookUpdateDto.getDescription(), book.getDescription())) {
            book.setDescription(bookUpdateDto.getDescription());
        }
        if(!bookUpdateDto.getAuthorId().equals(book.getAuthor().getId())) {
            var author = authorRepository.findById(bookUpdateDto.getAuthorId())
                    .orElseThrow(() -> new AuthorNotFoundException("Author with id %d not found".formatted(bookUpdateDto.getAuthorId())));
            book.setAuthor(author);
        }
        return bookMapper
                .toBookDto(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookMapper.toBookDto(
                bookRepository
                        .findById(id)
                        .orElseThrow(()-> new BookNotFoundException(String.format("Book with id %d not found", id)))
        );
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository
                .findAll()
                .stream().map(bookMapper::toBookDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getBooksByAuthor(Long authorId) {
        return bookRepository
                .findByAuthorId(authorId)
                .stream().map(bookMapper::toBookDto).collect(Collectors.toList());
    }
}
