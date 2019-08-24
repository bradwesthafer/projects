package com.MobyDickens.bootstrap;

import com.MobyDickens.model.Book;
//import com.MobyDickens.model.DateTime;
import com.MobyDickens.model.Genre;
import com.MobyDickens.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import java.sql.Date;
//import java.time.LocalTime;

/**
 * Created by Brad on 11/20/2016.
 */
@Component
public class DatabaseLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Book b1 = new Book("Moby Dick", 9780553213119L, "Herman Melville", /*LocalDateTime.now()*/new Date(System.currentTimeMillis()), Book.genreToString(Genre.CLASSICS), 3.60);
        Book b2 = new Book("A Christmas Carol", 9780486268651L, "Charles Dickens", /*LocalDateTime.now()*/new Date(System.currentTimeMillis()), Book.genreToString(Genre.CLASSICS), 3.00);
        Book b3 = new Book("50 Shades of Grey", 9780345803481L, "E.L. James", /*LocalDateTime.now()*/new Date(System.currentTimeMillis()), Book.genreToString(Genre.ROMANCE), 8.99);
        Book b4 = new Book("Trump: The Art of the Deal", 9780399594496L, "President of the United States Donald J. Trump", /*LocalDateTime.now()*/new Date(System.currentTimeMillis()), Book.genreToString(Genre.NON_FICTION), 16.99);
        bookRepository.createBook(b1);
        bookRepository.createBook(b2);
        bookRepository.createBook(b3);
        bookRepository.createBook(b4);
    }
}
