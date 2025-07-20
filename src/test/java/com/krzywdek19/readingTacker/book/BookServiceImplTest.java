package com.krzywdek19.readingTacker.book;

import com.krzywdek19.readingTacker.author.Author;
import com.krzywdek19.readingTacker.author.AuthorRepository;
import com.krzywdek19.readingTacker.author.dto.AuthorDto;
import com.krzywdek19.readingTacker.book.dto.BookCreateDto;
import com.krzywdek19.readingTacker.book.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void shouldCreateBookSuccessfully() {
        BookCreateDto bookCreateDto = new BookCreateDto("Test Book", 1L,300, "Description");
        Author author = new Author();
        author.setId(1L);
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);

        Book book = Book
                .builder()
                .title("Test Book")
                .author(author)
                .pages(300)
                .description("Description")
                .build();

        Book savedBook = Book
                .builder()
                .id(10L)
                .title("Test Book")
                .author(author)
                .pages(300)
                .description("Description")
                .build();

        BookDto bookDto = new BookDto(10L, "Test Book", authorDto, 300, "Descritpion");

        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(savedBook);
        Mockito.when(bookMapper.toBookDto(Mockito.any(Book.class))).thenReturn(bookDto);

        BookDto result = bookService.createBook(bookCreateDto);

        assertEquals(bookDto,result);
        Mockito.verify(authorRepository).findById(1L);
        Mockito.verify(bookRepository).save(Mockito.any(Book.class));
        Mockito.verify(bookMapper).toBookDto(savedBook);
    }
}
