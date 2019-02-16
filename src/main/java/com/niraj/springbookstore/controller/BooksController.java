package com.niraj.springbookstore.controller;


import com.niraj.springbookstore.model.Books;
import com.niraj.springbookstore.repository.BookJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    private BookJpaRepository bookJpaRepository;

    public BooksController(BookJpaRepository bookJpaRepository){
        this.bookJpaRepository = bookJpaRepository;
    }

    @GetMapping(value = "/all")
    public List<Books> findAll(){
        return bookJpaRepository.findAll();
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<Books> findByName(@PathVariable final String name) {
        Books book = bookJpaRepository.findByBookName(name);
        if(book != null){
            return new ResponseEntity<>(book, HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value ="/load")
    public ResponseEntity<Books> load(@RequestBody final Books book){
        bookJpaRepository.save(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }
    @DeleteMapping(value ="/delete/{id}")
    public ResponseEntity<Books> deleteBooks(@PathVariable String id)
    {
        Books book = bookJpaRepository.findOneById(Long.parseLong(id));
        if(book != null) {
            bookJpaRepository.delete(book);
            return new ResponseEntity<>(book, HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/filter/{date}")
    public List<Books> filterBooksByDate(@PathVariable String date){
        return bookJpaRepository.findByPublishDate(date);
    }

    @PutMapping(value = "/updateBook")
    public ResponseEntity<Books> updateBook(@RequestBody Books book){
        bookJpaRepository.save(book);
        return new ResponseEntity<>(book, HttpStatus.ACCEPTED);
    }



}
