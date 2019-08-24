package com.MobyDickens.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
//import java.util.Timer;

/**
 * Created by Brad on 11/20/2016.
 */

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_seq")
    @SequenceGenerator(name = "book_id_seq", sequenceName = "book_id_seq", allocationSize = 100)
    private Integer id;

    private String title;
    private Long ISBN;
    private String author;
    private Date datePublished;
    private String genre;
    private Double price;


    public Book() {}

    public Book(String title, Long ISBN, String author, Date datePublished, String genre, Double price) {
        //this.bookID = bookID;
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        this.datePublished = datePublished;
        this.genre = genre;
        this.price = price;
    }

    public Integer getBookID() {
        return id;
    }

    public void setBookID(Integer bookID) {
        this.id = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getISBN() {
        return ISBN;
    }

    public void setISBN(Long ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDatePublished() {
        return datePublished;
    }
    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getPrice() {
        return price;

    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public static Genre findGenre(String genre) {
        switch(genre) {
            case "NON_FICTION":
                return Genre.NON_FICTION;
            case "ROMANCE":
                return Genre.ROMANCE;
            case "HORROR":
                return Genre.HORROR;
            case "YOUNG_ADULT":
                return Genre.YOUNG_ADULT;
            case "SCIENCE_FICTION":
                return Genre.SCIENCE_FICTION;
            case "COMEDY":
                return Genre.COMEDY;
            case "DRAMA":
                return Genre.DRAMA;
            case "SATIRE":
                return Genre.SATIRE;
            case "TRAGEDY":
                return Genre.TRAGEDY;
            case "FANTASY":
                return Genre.FANTASY;
            case "FABLE":
                return Genre.FABLE;
            case "FAIRY_TALE":
                return Genre.FAIRY_TALE;
            case "CRIME_DETECTIVE":
                return Genre.CRIME_DETECTIVE;
            case "MYSTERY":
                return Genre.MYSTERY;
            case "FAN_FICTION":
                return Genre.FAN_FICTION;
            case "CLASSICS":
                return Genre.CLASSICS;
            case "REALISTIC_FICTION":
                return Genre.REALISTIC_FICTION;
            case "HISTORICAL_FICTION":
                return Genre.HISTORICAL_FICTION;
            case "WESTERN":
                return Genre.WESTERN;
            case "THRILLER":
                return Genre.THRILLER;
            case "MANGA":
                return Genre.MANGA;
            case "ADVENTURE":
                return Genre.ADVENTURE;
        }
        return null;
    }
    public static String genreToString(Genre genre) {
        switch (genre) {
            case NON_FICTION:
                return "NON_FICTION";
            case ROMANCE:
                return "ROMANCE";
            case HORROR:
                return "HORROR";
            case YOUNG_ADULT:
                return "YOUNG_ADULT";
            case SCIENCE_FICTION:
                return "SCIENCE_FICTION";
            case COMEDY:
                return "COMEDY";
            case DRAMA:
                return "DRAMA";
            case SATIRE:
                return "SATIRE";
            case TRAGEDY:
                return "TRAGEDY";
            case FANTASY:
                return "FANTASY";
            case FABLE:
                return "FABLE";
            case FAIRY_TALE:
                return "FAIRY_TALE";
            case CRIME_DETECTIVE:
                return "CRIME_DETECTIVE";
            case MYSTERY:
                return "MYSTERY";
            case FAN_FICTION:
                return "FAN_FICTION";
            case CLASSICS:
                return "CLASSICS";
            case REALISTIC_FICTION:
                return "REALISTIC_FICTION";
            case HISTORICAL_FICTION:
                return "HISTORICAL_FICTION";
            case WESTERN:
                return "WESTERN";
            case THRILLER:
                return "THRILLER";
            case MANGA:
                return "MANGA";
            case ADVENTURE:
                return "ADVENTURE";
        }
        return null;
    }
}
