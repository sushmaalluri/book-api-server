package com.bookapi.bookserver.controller;

import com.bookapi.bookserver.model.Book;
import com.bookapi.bookserver.repository.BookRepository; // Import the repository
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List; // For findAll()
import java.util.Optional; // For findById()

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = {"http://localhost:5173", "https://cozy-pothos-54424c.netlify.app"} ) // Allow requests from your local React app
//@CrossOrigin(origins = 
//@CrossOrigin(origins = "https://cozy-pothos-54424c.netlify.app")// Allows all origins, good for development
public class BookController {

    private final BookRepository bookRepository; // Declare BookRepository

    // Constructor injection for BookRepository
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Endpoint to create a new book
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        if (book == null || book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        // Check if book with this ISBN already exists
        if (bookRepository.existsById(book.getIsbn())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Or custom error
        }
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    // Endpoint to get a book by its ISBN
    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        Optional<Book> bookOptional = bookRepository.findById(isbn);
        // if (bookOptional.isPresent()) {
        //     return ResponseEntity.ok(bookOptional.get());
        // } else {
        //     return ResponseEntity.notFound().build();
        // }
        // A more concise way using map:
        return bookOptional.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> allBooks = bookRepository.findAll();
        if (allBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allBooks);
    }

    // Endpoint to update an existing book
    @PutMapping("/{isbn}")
    public ResponseEntity<Book> updateBook(@PathVariable String isbn, @RequestBody Book updatedBookDetails) {
        if (!bookRepository.existsById(isbn)) {
            return ResponseEntity.notFound().build();
        }
        // Ensure the ISBN in the path is set on the book object to be saved
        updatedBookDetails.setIsbn(isbn);
        Book savedBook = bookRepository.save(updatedBookDetails); // save() also handles updates if ID exists
        return ResponseEntity.ok(savedBook);
    }

    // Endpoint to delete a book by its ISBN
    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {
        if (bookRepository.existsById(isbn)) {
            bookRepository.deleteById(isbn);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}