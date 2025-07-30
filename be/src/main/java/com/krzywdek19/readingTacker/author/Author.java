package com.krzywdek19.readingTacker.author;

import com.krzywdek19.readingTacker.auth.user.User;
import com.krzywdek19.readingTacker.book.Book;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "author")
    private List<Book> writtenBooks = new ArrayList<>();
    private String name;
    private String lastName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User addedBy;
    @Nullable
    private LocalDate birthDate;
    @Nullable
    private String description;
}
