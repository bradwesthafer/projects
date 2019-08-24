package com.MobyDickens.repository;

import com.MobyDickens.model.Book;

import java.util.List;

/**
 * Created by Brad on 11/20/2016.
 */
public interface BookRepository {
    //Integer getNextID();
    Iterable<Book> findAll();
    List<Book> findByName(String name);
    List<Book> findByAuthor(String author);
    List<Book> findByNameAndAuthor(String name, String author);
    List<Book> emptyBookList();
    Book findByID(Integer id);
    void createBook(Book book);
    void editBook(Book book);
    void deleteBook(int id);
}
