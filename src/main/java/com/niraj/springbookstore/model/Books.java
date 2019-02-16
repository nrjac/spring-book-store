package com.niraj.springbookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Books {
    @Id
    private Long id;
    @Column(name="book_name")
    private String bookName;
    private  String author;
    private Float price;
    @Column(name="publish_date")
    private String publishDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String name) {
        this.bookName = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String date) {
        this.publishDate = date;
    }
}
