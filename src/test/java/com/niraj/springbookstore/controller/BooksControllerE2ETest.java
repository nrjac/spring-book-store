package com.niraj.springbookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niraj.springbookstore.model.Books;
import com.niraj.springbookstore.repository.BookJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BooksControllerE2ETest {

    private static final String BASE_URL_PATH = "/books";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Books mockBook = new Books();

    @Before
    public void setup(){
        mockBook.setId(6L);
        mockBook.setBookName("SpringBoot");
        mockBook.setAuthor("John Doe");
        mockBook.setPrice(50.75f);
        mockBook.setPublishDate("2018-02-22");
    }

    @Test
    public void checkContext(){
        assertNotNull(mockMvc);
    }

    @Test
    public void shouldGetListOfBooksWhenFindAllIsInvoked() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(BASE_URL_PATH + "/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseJson = mvcResult.getResponse().getContentAsString();
        List<Books> actualBookList = Arrays.asList(objectMapper.readValue(actualResponseJson, Books[].class));
        assertNotNull(actualBookList);
        assertEquals(5, actualBookList.size());
    }

    @Test
    public void shouldGetListOfBooksWhenfindByNameIsInvoked() throws Exception {
        String mockName = "ruby";
        MvcResult mvcResult = mockMvc.perform(get(BASE_URL_PATH + "/"+mockName)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andReturn();
        String actualResponseJson = mvcResult.getResponse().getContentAsString();
        Books actualBook = objectMapper.readValue(actualResponseJson,Books.class);
        assertEquals("ruby", actualBook.getBookName());
    }

    @Test
    public void shouldGetListOfBooksWhenLoadIsInvoked() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post(BASE_URL_PATH + "/load")
                .content(asJsonString(mockBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String actualResponseJson = mvcResult.getResponse().getContentAsString();
        Books actualBook =objectMapper.readValue(actualResponseJson, Books.class);
        assertNotNull(actualBook);
        assertEquals(mockBook.getId(), actualBook.getId());
    }
    @Test
    public void shouldGetListOfBooksWhenDeleteBooksIsInvoked() throws Exception {
        Long mockId=2L;
        MvcResult mvcResult = mockMvc.perform(delete(BASE_URL_PATH + "/delete/"+mockId.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andReturn();
        String actualResponseJson = mvcResult.getResponse().getContentAsString();
        Books actualBook = objectMapper.readValue(actualResponseJson,Books.class);
        assertEquals("2", actualBook.getId().toString());

    }

    @Test
    public void shouldGetListOfBooksWhenFilterBooksByDateIsInvoked() throws Exception {
        String mockDate = "2019";
        MvcResult mvcResult = mockMvc.perform(get(BASE_URL_PATH + "/filter/"+mockDate)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseJson = mvcResult.getResponse().getContentAsString();
        List<Books> actualBookList = Arrays.asList(objectMapper.readValue(actualResponseJson, Books[].class));
        assertNotNull(actualBookList);
        assertEquals(2, actualBookList.size());

    }

    @Test
    public void shouldGetListOfBooksWhenUpdateBookIsInvoked() throws Exception {
        mockBook.setId(5L);
        mockBook.setBookName("Getting Started with End To End Testing");
        mockBook.setAuthor("John Smith");
        mockBook.setPrice(250.75f);
        mockBook.setPublishDate("2020-01-22");
        MvcResult mvcResult = mockMvc.perform(put(BASE_URL_PATH + "/updateBook")
                .content(asJsonString(mockBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted ())
                .andReturn();
        String actualResponseJson = mvcResult.getResponse().getContentAsString();
        Books actualBook = objectMapper.readValue(actualResponseJson,Books.class);
        assertEquals("Getting Started with End To End Testing", actualBook.getBookName());
        assertEquals("John Smith", actualBook.getAuthor());
        assertEquals("250.75", actualBook.getPrice().toString());
        assertEquals("2020-01-22", actualBook.getPublishDate());
    }
    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
