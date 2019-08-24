package com.MobyDickens.bootstrap;

import com.MobyDickens.model.Book;
import com.MobyDickens.model.DateTime;
import com.MobyDickens.model.Genre;
import com.MobyDickens.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by Brad on 11/20/2016.
 */
@Component
public class DatabaseLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Book b1 = new Book(1, "Moby Dick", 9780553213119L, "Herman Melville", new DateTime("November", 1851), Genre.CLASSICS, 3.60);
        Book b2 = new Book(2, "A Christmas Carol", 9780486268651L, "Charles Dickens", new DateTime("December", 1843), Genre.CLASSICS, 3.00);
        Book b3 = new Book(3, "50 Shades of Grey", 9780345803481L, "E.L. James", new DateTime("April", 2012), Genre.ROMANCE, 8.99);
        Book b4 = new Book(4, "Trump: The Art of the Deal", 9780399594496L, "President of the United States Donald J. Trump", new DateTime("November", 1987), Genre.NON_FICTION, 16.99);
        bookRepository.createBook(b1);
        bookRepository.createBook(b2);
        bookRepository.createBook(b3);
        bookRepository.createBook(b4);
    }
}
