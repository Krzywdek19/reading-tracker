package com.krzywdek19.readingTacker.author;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAllBYAddedById(Long id);
}
