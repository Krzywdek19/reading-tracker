package com.krzywdek19.readingTacker.book;

import com.krzywdek19.readingTacker.author.AuthorRepository;
import com.krzywdek19.readingTacker.author.AuthorService;
import com.krzywdek19.readingTacker.book.dto.BookCreateDto;
import com.krzywdek19.readingTacker.book.dto.BookDto;
import com.krzywdek19.readingTacker.book.dto.BookUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.NotActiveException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) throws NotActiveException {
        var author =  authorRepository.findById(bookCreateDto.getAuthorId()).orElseThrow(NotActiveException::new);
        var book = Book.builder()
                .title(bookCreateDto.getTitle())
                .author(author)
                .pages(bookCreateDto.getPages())
                .description(bookCreateDto.getDescription())
                .build();
        return bookRepository.save(book);
    }

    @Override
    public BookDto updateBook(Long id, BookUpdateDto bookUpdateDto) {
        return null;
    }

    @Override
    public void deleteBook(Long id) {

    }

    @Override
    public BookDto getBookById(Long id) {
        return null;
    }

    @Override
    public List<BookDto> getAllBooks() {
        return List.of();
    }

    @Override
    public List<BookDto> getBooksByAuthor(Long authorId) {
        return List.of();
    }
}
