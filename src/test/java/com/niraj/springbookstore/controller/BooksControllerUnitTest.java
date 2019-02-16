package com.niraj.springbookstore.controller;


import com.niraj.springbookstore.model.Books;
import com.niraj.springbookstore.repository.BookJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class BooksControllerUnitTest {

    private BooksController controllerUnderTest;

    @MockBean
    private BookJpaRepository mockBookJpaRepository;

    private List<Books> mockBooksList = new ArrayList<>();
    private Books mockBook = new Books();

    @Before
    public void setup(){
        controllerUnderTest= new BooksController(mockBookJpaRepository);
        mockBook.setId(1L);
        mockBook.setBookName("SpringBoot");
        mockBook.setAuthor("John Doe");
        mockBook.setPrice(50.75f);
        mockBook.setPublishDate("2018-02-22");
        mockBooksList.add(mockBook);
     }

    @Test
    public void shouldDelegateToBookJpaRepositoryWhenFindAllIsCalled(){
        when(mockBookJpaRepository.findAll())
                .thenReturn(mockBooksList);
        List<Books> acutalResult = controllerUnderTest.findAll();
        verify(mockBookJpaRepository, times(1)).findAll();
        assertNotNull(acutalResult);
    }

    @Test
    public void shouldDelegateToBookJpaRepositoryWhenFindByNameIsCalled(){

        String mockBookName = "Getting Started with Java";
        when(mockBookJpaRepository.findByBookName(mockBookName))
                .thenReturn(mockBook);
        ResponseEntity<Books>  actualResponse = controllerUnderTest.findByName(mockBookName);
        verify(mockBookJpaRepository,times(1)).findByBookName(mockBookName);
        assertNotNull(actualResponse);
        assertEquals(HttpStatus.FOUND, actualResponse.getStatusCode());
    }

    @Test
    public void shouldReturnNotFoundHttpStatusWhenNoBookIsFoundWhileCallingFindByMethod(){
        String mockBookName = "Getting Started with Java";
        mockBook=null;
        when(mockBookJpaRepository.findByBookName(mockBookName))
                .thenReturn(mockBook);
        ResponseEntity<Books>  actualResponse = controllerUnderTest.findByName(mockBookName);
        verify(mockBookJpaRepository,times(1)).findByBookName(mockBookName);
        assertNull(actualResponse.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    public void shouldDelegateToBookJpaRepositoryWhenLoadIsCalled(){
        when(mockBookJpaRepository.save(mockBook)).thenReturn(mockBook);
        ResponseEntity<Books> actualResponse = controllerUnderTest.load(mockBook);
        verify(mockBookJpaRepository,times(1)).save(mockBook);
        assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
    }

    @Test
    public void shouldDelegateToBookJpaRepositoryWhenDeleteBooksIsCalled(){
        Long mockId =3L;
        when(mockBookJpaRepository.findOneById(mockId)).thenReturn(mockBook);
        ResponseEntity<Books>  actualResponse = controllerUnderTest.deleteBooks(mockId.toString());
        verify(mockBookJpaRepository,times(1)).findOneById(mockId);
        verify(mockBookJpaRepository,times(1)).delete(mockBook);
        assertEquals(HttpStatus.FOUND,actualResponse.getStatusCode());
    }

    @Test
    public void shouldDelegateToBookJpaRepositoryWhenfilterBooksByDateIsCalled(){
        String mockDate="2018-02-02";
        when(mockBookJpaRepository.findByPublishDate(mockDate)).thenReturn(mockBooksList);
        List<Books>  actualResponseList = controllerUnderTest.filterBooksByDate(mockDate);
        verify(mockBookJpaRepository,times(1)).findByPublishDate(mockDate);
        assertEquals(mockBooksList.size(), actualResponseList.size());

    }

    @Test
    public void shouldDelegateToBookJpaRepositoryWhenUpdateBookIsCalled(){
        when(mockBookJpaRepository.save(mockBook)).thenReturn(mockBook);
        ResponseEntity<Books>  actualResponse = controllerUnderTest.updateBook(mockBook);
        verify(mockBookJpaRepository,times(1)).save(mockBook);
        assertEquals(HttpStatus.ACCEPTED,actualResponse.getStatusCode());
    }
}
