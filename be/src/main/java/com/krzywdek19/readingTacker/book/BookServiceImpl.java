package com.krzywdek19.readingTacker.book;

import com.krzywdek19.readingTacker.auth.user.CurrentUserProvider;
import com.krzywdek19.readingTacker.author.AuthorMapper;
import com.krzywdek19.readingTacker.author.AuthorRepository;
import com.krzywdek19.readingTacker.author.exception.AuthorNotFoundException;
import com.krzywdek19.readingTacker.book.dto.BookCreateDto;
import com.krzywdek19.readingTacker.book.dto.BookDto;
import com.krzywdek19.readingTacker.book.dto.BookUpdateDto;
import com.krzywdek19.readingTacker.book.exception.BookNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final CurrentUserProvider currentUserProvider;
    private final AuthorMapper authorMapper;

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        var author =  authorRepository.findById(bookCreateDto.getAuthorId()).orElseThrow(
                ()->new AuthorNotFoundException("Author with id %d not found".formatted(bookCreateDto.getAuthorId())));

        var currentUser = currentUserProvider.getCurrentUser();

        var book = Book.builder()
                .title(bookCreateDto.getTitle())
                .author(author)
                .pages(bookCreateDto.getPages())
                .readPages(0)
                .description(null)
                .user(currentUser)
                .isRead(false)
                .build();
        return bookMapper
                .toBookDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDto updateBook(Long id, BookUpdateDto bookUpdateDto) throws AccessDeniedException {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id %d not found".formatted(id)));
        checkBookOwnership(book);

        updateIfChanged(book::setTitle, book.getTitle(), bookUpdateDto.getTitle());
        updateIfChanged(book::setDescription, book.getDescription(), bookUpdateDto.getDescription());
        updateIfChanged(book::setPages, book.getPages(), bookUpdateDto.getPages());

        if (bookUpdateDto.getReadPages() != null &&
                !Objects.equals(bookUpdateDto.getReadPages(), book.getReadPages()) && bookUpdateDto.getReadPages() <= book.getPages()) {
            book.setReadPages(bookUpdateDto.getReadPages());
            markBookAsRead(book);
        }

        if (bookUpdateDto.getAuthorId() != null &&
                !Objects.equals(bookUpdateDto.getAuthorId(), book.getAuthor().getId())) {
            var author = authorRepository.findById(bookUpdateDto.getAuthorId())
                    .orElseThrow(() -> new AuthorNotFoundException("Author with id %d not found"
                            .formatted(bookUpdateDto.getAuthorId())));
            book.setAuthor(author);
        }

        return bookMapper.toBookDto(bookRepository.save(book));
    }

    private <T> void updateIfChanged(Consumer<T> setter, T currentValue, T newValue) {
        if(newValue != null && !Objects.equals(currentValue, newValue)) {
            setter.accept(newValue);
        }
    }

    @Override
    public void deleteBook(Long id) throws AccessDeniedException {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id %d not found".formatted(id)));
        checkBookOwnership(book);
        bookRepository.delete(book);
    }

    @Override
    public BookDto getBookById(Long id) {
        var user = currentUserProvider.getCurrentUser();
        var book = bookRepository.findById(id)
                .filter(b -> b.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new BookNotFoundException("Book with id %d not found".formatted(id)));
        var author = authorRepository.findById(book.getAuthor().getId()).orElseThrow(() -> new AuthorNotFoundException("Author with id %d not found".formatted(id)));
        var mappedBook = bookMapper.toBookDto(book);
        mappedBook.setAuthorDto(authorMapper.authorToAuthorDto(author));
        return mappedBook;
    }

    @Override
    public List<BookDto> getAllBooks() {
        var user = currentUserProvider.getCurrentUser();
        return bookRepository
                .findByUserId(user.getId())
                .stream()
                .map(book -> {
                    var bookDto = bookMapper.toBookDto(book);
                    if (book.getAuthor() != null) {
                        bookDto.setAuthorDto(authorMapper.authorToAuthorDto(book.getAuthor()));
                    }
                    return bookDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getBooksByAuthor(Long authorId) {
        var user = currentUserProvider.getCurrentUser();
        return bookRepository
                .findByUserIdAndAuthorId(user.getId(), authorId)
                .stream().map(bookMapper::toBookDto).collect(Collectors.toList());
    }

    private void markBookAsRead(Book book) {
        book.setRead(book.getReadPages() != null && book.getPages() != null
                && book.getReadPages().equals(book.getPages()));
    }

    private void checkBookOwnership(Book book) throws AccessDeniedException {
        var currentUser = currentUserProvider.getCurrentUser();
        if(!book.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You do not have access to this book");
        }
    }
}
