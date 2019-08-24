package com.MobyDickens.service;

import com.MobyDickens.model.Book;

import java.util.List;

/**
 * Created by Brad on 11/20/2016.
 */
public interface BookService {
    //Integer getNextID();
    Iterable<Book> findAll();
    Iterable<Book> findByName(String name);
    Iterable<Book> findByAuthor(String author);
    Iterable<Book> findByNameAndAuthor(String name, String author);
    Iterable<Book> emptyBookList();
    Book findByID(Integer id);
    void createBook(Book book);
    void editBook(Book book);
    void deleteBook(int id);
}
