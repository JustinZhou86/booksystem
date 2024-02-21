package com.example.book.bookbackend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_id", nullable = false)
    String id;

    @Column(name = "book_title", nullable = false)
    String title;

    @Column(name = "author", nullable = false)
    String author;

    @Column(name = "publication_year", nullable = false)
    String publicationYear;

    @Column(name = "ISBN", nullable = false)
    String isbn;

}
