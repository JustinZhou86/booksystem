package com.example.book.bookbackend;

import com.example.book.bookbackend.model.Book;
import com.example.book.bookbackend.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ActiveProfiles("integrationtest")
public class BookControllerTests {
    @MockBean
    BookService bookService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testGivenBook_whenCreateBook_thenReturnSavedBook() throws Exception {
        //given a new book
        Book newBook = Book.builder()
                .title("Head first java")
                .author("Justin")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();

        Book savedBook = Book.builder()
                .id("123456")
                .title("Head first java")
                .author("Justin")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();


        //given the bookService's create method will accept any book object,and return it.
        given(bookService.create(any(Book.class))).willReturn(savedBook);


        //when we post to /books with  the given book's json string
        ResultActions response = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook))
        );

        //then we can see the status and the json return by "POST /books"

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(savedBook.getId())))
                .andExpect(jsonPath("$.title", is(savedBook.getTitle())))
                .andExpect(jsonPath("$.author", is(savedBook.getAuthor())))
                .andExpect(jsonPath("$.isbn", is(savedBook.getIsbn())))
                .andExpect(jsonPath("$.publicationYear", is(savedBook.getPublicationYear())));


    }

    @Test
    public void testGivenSavedBook_whenUpdateBook_thenReturnUpdatedBook() throws Exception {
        //give the bookId that the book we need to update
        String bookId = "123456";
        //given a savedBook
        Book savedBook = Book.builder()
                .id("123456")
                .title("Head first java")
                .author("Justin")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();


        Book updatedBook = Book.builder()
                .id("123456")
                .title("Head first java")
                .author("Kim")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();

        given(bookService.findById(bookId)).willReturn(savedBook);
        given(bookService.update(any(Book.class))).willAnswer(invocation -> invocation.getArgument(0));


        //when we put to /books/{id} with  the given book's json string
        ResultActions response = mockMvc.perform(put("/books/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook))
        );

        //then we can see the status and the json return by "PUT /books/{id}"

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedBook.getId())))
                .andExpect(jsonPath("$.title", is(updatedBook.getTitle())))
                .andExpect(jsonPath("$.author", is(updatedBook.getAuthor())))
                .andExpect(jsonPath("$.isbn", is(updatedBook.getIsbn())))
                .andExpect(jsonPath("$.publicationYear", is(updatedBook.getPublicationYear())));
    }


    @Test
    public void testGivenNonExistedBookId_whenUpdateBook_thenReturn404() throws Exception {
        //give the bookId that the book we need to update is not existed
        String bookId = "99999";
        //given a savedBook

        Book updatedBook = Book.builder()
                .id("123456")
                .title("Head first java")
                .author("Kim")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();

        given(bookService.findById(bookId)).willReturn(null);
        given(bookService.update(any(Book.class))).willAnswer(invocation -> invocation.getArgument(0));


        //when we put to /books/{id} with  the given book's json string
        ResultActions response = mockMvc.perform(put("/books/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook))
        );

        //then we can see the status and the json return by "PUT /books/{id}"

        response.andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void testGivenBookId_whenDeleteBook_thenReturn204() throws Exception {
        // given bookId is 123456
        String bookId = "123456";
        // given bookService.delete method will do nothing
        willDoNothing().given(bookService).delete(bookId);

        // when trigger a DELETE /books/{id}
        ResultActions response = mockMvc.perform(delete("/books/{id}", bookId));

        // then verify the status
        response.andExpect(status().isNoContent()).andDo(print());


    }

    @Test
    public void testListOfBooks_whenGetAllBooks_thenReturnAllBooks() throws Exception {
        //given a list of books
        List<Book> bookList = new ArrayList<>();
        bookList.add(Book.builder().id("22222").title("Design Pattern").author("Justin").isbn("8888-NHTY-XYXY-ZWKY").publicationYear("2015").build());
        bookList.add(Book.builder().id("11111").title("Thinking in Java").author("Jim").isbn("1253-NHTY-OUIL-BVSW").publicationYear("1998").build());

        //given bookService.findAll will return bookList
        given(bookService.findAll()).willReturn(bookList);

        //when GET /books
        ResultActions response = mockMvc.perform(get("/books"));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(bookList.size())));
    }

    @Test
    public void testGivenBookId_whenGetBookbyId_thenReturnSavedBook() throws Exception {
        // given bookId is 123456
        String bookId = "123456";
        Book savedBook = Book.builder()
                .title("Head first java")
                .author("kim")
                .isbn("1253-NHTY-OUIL-BVSW")
                .publicationYear("2014").build();

        // given bookService.findById method will return savedBook
        given(bookService.findById(bookId)).willReturn(savedBook);

        // when trigger a GET /books/{id}
        ResultActions response = mockMvc.perform(get("/books/{id}", bookId));

        // then verify the status and json
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedBook.getId())))
                .andExpect(jsonPath("$.title", is(savedBook.getTitle())))
                .andExpect(jsonPath("$.author", is(savedBook.getAuthor())))
                .andExpect(jsonPath("$.isbn", is(savedBook.getIsbn())))
                .andExpect(jsonPath("$.publicationYear", is(savedBook.getPublicationYear())));
    }


    @Test
    public void testGivenNonExistedBookId_whenGetBookbyId_thenReturn404() throws Exception {
        // given bookId is 66666, but not existed
        String bookId = "66666";

        // given bookService.findById method will return empty
        given(bookService.findById(bookId)).willReturn(null);

        // when trigger a GET /books/{id}
        ResultActions response = mockMvc.perform(get("/books/{id}", bookId));

        // then verify the status and json
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

}
