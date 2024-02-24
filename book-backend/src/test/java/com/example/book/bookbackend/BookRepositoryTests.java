package com.example.book.bookbackend;

import com.example.book.bookbackend.model.Book;
import com.example.book.bookbackend.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integrationtest")
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testCreateBook(){
        Book newBook = Book.builder()
                .id(UUID.randomUUID().toString())
                .title("Head first java")
                .author("Justin")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();
        Book createdBook  = bookRepository.save(newBook);
        assertThat(createdBook.getId()).isNotEmpty();


    }

    @Test
    public void testFindBookById(){
        Book newBook = Book.builder()
                .id(UUID.randomUUID().toString())
                .title("Head first java")
                .author("Justin")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();
        Book createdBook  = bookRepository.save(newBook);

        Optional<Book> searchBook = bookRepository.findById(createdBook.getId());
        assertThat(searchBook).isNotEmpty();
        assertThat(searchBook.get().getAuthor()).isEqualTo(createdBook.getAuthor());


    }


    @Test
    public void testFindBookAll(){
        Book newBook = Book.builder()
                .id(UUID.randomUUID().toString())
                .title("Head first java")
                .author("Justin")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();
        Book newBook2 = Book.builder()
                .id(UUID.randomUUID().toString())
                .title("Head first java")
                .author("Justin")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();
        Book createdBook  = bookRepository.save(newBook);
        Book createdBook2  = bookRepository.save(newBook2);

        List<Book> books = bookRepository.findAll();
        assertThat(books).isNotEmpty();
        assertThat(books.size()).isEqualTo(2);


    }

    @Test
    public void testUpdateBook(){
        Book newBook = Book.builder()
                .id(UUID.randomUUID().toString())
                .title("Head first java")
                .author("Justin")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();
        Book createdBook  = bookRepository.save(newBook);

        Optional<Book> searchedBook = bookRepository.findById(createdBook.getId());

        assertThat(searchedBook).isNotEmpty();

        searchedBook.get().setAuthor("Felix");
        Book updatedBook = bookRepository.save(searchedBook.get());


        assertThat(updatedBook.getAuthor()).isEqualTo("Felix");


    }

    @Test
    public void testDeleteBook(){
        Book newBook = Book.builder()
                .id(UUID.randomUUID().toString())
                .title("Head first java")
                .author("Justin")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();
        Book createdBook  = bookRepository.save(newBook);

        Optional<Book> returnedBook = bookRepository.findById(createdBook.getId());
        assertThat(returnedBook).isNotEmpty();

        bookRepository.deleteById(createdBook.getId());


         Optional<Book> returnedBook2 = bookRepository.findById(createdBook.getId());

        assertThat(returnedBook2).isEmpty();

    }
}
