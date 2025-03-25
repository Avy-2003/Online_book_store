package com.example.demo.service;

import com.example.demo.Book;
import com.example.demo.dao.BookRepository;
import org.springframework.stereotype.Service;
import com.example.demo.exception.BookNotFoundException;

import java.util.List;

@Service // Marks this as a service class in Spring
public class BookService {

    private final BookRepository bookRepository;

    // Constructor to inject the BookRepository dependency
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Adds a new book to the database
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // Retrieves all books from the database
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Retrieves a book by ID, throws exception if not found
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found."));
    }

    // Updates an existing book, throws exception if not found
    public Book updateBook(Long id, Book updatedBook) {
        // Find the book first
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found."));
        
        // Update the book details
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPrice(updatedBook.getPrice());
        existingBook.setPublishedDate(updatedBook.getPublishedDate());
        
        // Save the updated book and return it
        return bookRepository.save(existingBook);
    }

    // Deletes a book by ID, throws exception if not found
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
        bookRepository.deleteById(id);
    }
}
