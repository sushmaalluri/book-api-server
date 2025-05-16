package com.bookapi.bookserver.repository;

import com.bookapi.bookserver.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Optional, but good practice

@Repository // Indicates that this is a Spring Data repository
public interface BookRepository extends JpaRepository<Book, String> {
    // Spring Data JPA will automatically provide implementations for methods like:
    // save(Book book), findById(String isbn), findAll(), deleteById(String isbn), etc.

    // You can also define custom query methods here if needed later.
    // For example: List<Book> findByAuthor(String author);
}