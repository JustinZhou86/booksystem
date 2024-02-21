package com.example.book.bookbackend.controller;

import com.example.book.bookbackend.model.Book;
import com.example.book.bookbackend.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;


    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.findAll();
    }

    @GetMapping(value="/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable("bookId") String bookId) {
        logger.debug("Entering the getBook() method for the bookId: {}",bookId);
        return ResponseEntity.ok(bookService.findById(bookId));
    }



    @PutMapping(value="/{bookId}")
    public ResponseEntity<Book>  updateBook( @PathVariable("bookId") String id, @RequestBody Book book) {
        return ResponseEntity.ok(bookService.update(book));
    }

    @PostMapping
    public ResponseEntity<Book>  saveBook(@RequestBody Book book) {
        Book newBook = bookService.create(book);
        return ResponseEntity.created(URI.create("/books/"+newBook.getId())).body(newBook);
    }

    @DeleteMapping(value="/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook( @PathVariable("bookId") String BookId) {
        bookService.delete( BookId );

    }
}
