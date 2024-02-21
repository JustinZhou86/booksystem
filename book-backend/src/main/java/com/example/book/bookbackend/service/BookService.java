package com.example.book.bookbackend.service;

import com.example.book.bookbackend.model.Book;
import com.example.book.bookbackend.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    public Book findById(String bookId){
        Optional<Book> opt = null;
        try{
            opt = bookRepository.findById(bookId);
        }catch(Exception e){

        }
        if (!opt.isPresent()) {
            String message = String.format("Unable to find an organization with the Book id %s", bookId);
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
        logger.debug("Retrieving Book Info: " + opt.get().toString());
        return opt.get();
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book create(Book book){
        book.setId(UUID.randomUUID().toString());
        book = bookRepository.save(book);
        return book;
    }

    public Book update(Book book){
        return bookRepository.save(book);
    }

    public void delete(String bookId){
        bookRepository.deleteById(bookId);
    }

}
