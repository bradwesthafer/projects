package com.MobyDickens.service.impl;

import com.MobyDickens.model.Book;
import com.MobyDickens.repository.BookRepository;
import com.MobyDickens.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Brad on 11/20/2016.
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Integer getNextID() { return bookRepository.getNextID(); }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findByName(String name) {
        return bookRepository.findByName(name);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> findByNameAndAuthor(String name, String author) { return bookRepository.findByNameAndAuthor(name, author); }

    @Override
    public List<Book> emptyBookList() { return bookRepository.emptyBookList(); }

    @Override
    public Book findByID(Integer id) {
        return bookRepository.findByID(id);
    }

    @Override
    public void createBook(Book book) {
        bookRepository.createBook(book);
    }

    @Override
    public void editBook(Book book) {
        bookRepository.editBook(book);
    }

    @Override
    public void deleteBook(Book book) {
        bookRepository.deleteBook(book);
    }
}
