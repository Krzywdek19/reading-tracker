package com.krzywdek19.readingTacker.book;

import com.krzywdek19.readingTacker.auth.user.CurrentUserProvider;
import com.krzywdek19.readingTacker.auth.user.User;
import com.krzywdek19.readingTacker.author.Author;
import com.krzywdek19.readingTacker.author.AuthorRepository;
import com.krzywdek19.readingTacker.book.dto.BookCreateDto;
import com.krzywdek19.readingTacker.book.dto.BookDto;
import com.krzywdek19.readingTacker.book.dto.BookUpdateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private CurrentUserProvider currentUserProvider;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void createdBookShouldBeMarkedAsUnread(){
        BookCreateDto dto = new BookCreateDto("title",1L, 700);
        Author author = new Author();
        User currentUser = new User();

        when(bookRepository.save(any())).thenReturn(new Book());
        when(bookMapper.toBookDto(any())).thenReturn(new BookDto());
        when(authorRepository.findById(any())).thenReturn(Optional.of(author));
        when(currentUserProvider.getCurrentUser()).thenReturn(currentUser);

        bookService.createBook(dto);

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        Book createdBook = captor.getValue();

        assertFalse(createdBook.isRead());
    }

    @Test
    public void shouldMarkBookAsReadAfterReachLastPage() throws AccessDeniedException {
        User currentUser = User.builder()
                .id(1L).build();
        Book book = Book
                .builder()
                .id(1L)
                .pages(700)
                .title("title")
                .user(currentUser)
                .build();
        BookUpdateDto bookUpdateDto = new BookUpdateDto(null,null,null,700, null);

        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        when(currentUserProvider.getCurrentUser()).thenReturn(currentUser);

        bookService.updateBook(1L,bookUpdateDto);

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        Book updatedBook = captor.getValue();

        assertTrue(updatedBook.isRead());
    }
}
