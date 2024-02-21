package com.example.book.bookbackend.repository;

import com.example.book.bookbackend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    public Optional<Book> findById(String bookId);

}
