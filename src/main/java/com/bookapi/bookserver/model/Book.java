package com.bookapi.bookserver.model;

import jakarta.persistence.Entity; // From jakarta.persistence
import jakarta.persistence.Id;   // From jakarta.persistence
import java.util.Objects;

@Entity // Marks this class as a JPA entity
public class Book {

    @Id // Marks 'isbn' as the primary key
    private String isbn;
    private String title;
    private String author;

    // No-argument constructor (required by JPA)
    public Book() {
    }

    // All-arguments constructor
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    // Getters
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    // Setters
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // equals() and hashCode() - good practice, especially with entities
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn) &&
               Objects.equals(title, book.title) &&
               Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, author);
    }

    // toString() - useful for logging/debugging
    @Override
    public String toString() {
        return "Book{" +
               "isbn='" + isbn + '\'' +
               ", title='" + title + '\'' +
               ", author='" + author + '\'' +
               '}';
    }
}
