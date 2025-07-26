package com.krzywdek19.readingTacker.book;

import com.krzywdek19.readingTacker.book.dto.BookCreateDto;
import com.krzywdek19.readingTacker.book.dto.BookDto;
import com.krzywdek19.readingTacker.book.dto.BookUpdateDto;

import java.io.NotActiveException;
import java.nio.file.AccessDeniedException;
import java.util.List;

public interface BookService {
    BookDto createBook(BookCreateDto bookCreateDto) throws NotActiveException;
    BookDto updateBook(Long id, BookUpdateDto bookUpdateDto) throws AccessDeniedException;
    void deleteBook(Long id) throws AccessDeniedException;
    BookDto getBookById(Long id);
    List<BookDto> getAllBooks();
    List<BookDto> getBooksByAuthor(Long authorId);
}
