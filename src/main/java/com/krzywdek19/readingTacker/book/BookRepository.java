package com.krzywdek19.readingTacker.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    List<Book> findByAuthorId(Long authorId);
    List<Book> findByUserId(Long userId);
    List<Book> findByUserIdAndAuthorId(Long userId, Long authorId);
}
