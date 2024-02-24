package com.example.book.bookbackend;

import com.example.book.bookbackend.model.Book;
import com.example.book.bookbackend.repository.BookRepository;
import com.example.book.bookbackend.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;


@SpringBootTest
@ActiveProfiles("integrationtest")
public class BookServiceTests {
    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    @Test
    public void testGivenBook_whenCreateBook_thenReturnSavedBook(){

        //given
        Book newBook = Book.builder()
                .title("Head first java")
                .author("Justin")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();
        given(bookRepository.save(any(Book.class))).willAnswer(invocation -> invocation.getArgument(0));

        //when
        Book createdBook = bookService.create(newBook);

        //then
        assertThat(createdBook.getAuthor()).isEqualTo(newBook.getAuthor());



    }

    @Test
    public void testFindBookById(){
        //given
        String bookId = "99999";
        Book newBook = Book.builder()
                .id(bookId)
                .title("Head first java")
                .author("Justin")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();

        given(bookRepository.findById(bookId)).willReturn(Optional.of(newBook));

        //when call bookService.findById
        Book searchedBook = bookService.findById(bookId);

        //then
        assertThat(searchedBook).isNotNull();
        assertThat(searchedBook.getAuthor()).isEqualTo(newBook.getAuthor());


    }


    @Test
    public void testFindBookAll(){
        //given
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

        List<Book> books = new ArrayList<>();
        books.add(createdBook);
        books.add(createdBook2);

        given(bookRepository.findAll()).willReturn(books);


        // when call bookService.findAll
        List<Book> searchedBooks =  bookService.findAll();


        //then
        assertThat(searchedBooks).isNotEmpty();
        assertThat(searchedBooks.size()).isEqualTo(2);

    }

    @Test
    public void testUpdateBook(){
        //given
        Book toUpdateBook = Book.builder()
                .id(UUID.randomUUID().toString())
                .title("Head first java")
                .author("Justin")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();

        given(bookRepository.save(any(Book.class))).willAnswer(invocation -> invocation.getArgument(0));

        //when call bookService.update
        Book updatedBook = bookService.update(toUpdateBook);

        //then
        assertThat(updatedBook).isNotNull();
        assertThat(updatedBook.getAuthor()).isEqualTo(toUpdateBook.getAuthor());

    }

    @Test
    public void testDeleteBook(){

        // given bookId is 123456
        String bookId = "123456";
        // given bookService.delete method will do nothing
        willDoNothing().given(bookRepository).deleteById(bookId);

        // when trigger bookService.delete call
        try{

            bookService.delete(bookId);

        }catch (Exception e){
            e.printStackTrace();
            fail("delete failed");
        }
    }
}
