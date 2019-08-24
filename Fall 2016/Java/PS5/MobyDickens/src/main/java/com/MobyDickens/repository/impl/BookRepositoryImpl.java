package com.MobyDickens.repository.impl;

import com.MobyDickens.model.Book;
import com.MobyDickens.repository.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brad on 11/20/2016.
 */
@Repository
public class BookRepositoryImpl implements BookRepository {

    private List<Book> books = new ArrayList<>();
    private Integer nextID = 1;

    @Override
    public Integer getNextID() { return nextID; }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public List<Book> findByName(String name) {
        List<Book> booksCalledName = new ArrayList<>();
        for(Book b: books) {
            if(b.getTitle().toLowerCase().contains(name.toLowerCase())) {
                booksCalledName.add(b);
            }
        }
        return booksCalledName;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<Book> booksByAuthor = new ArrayList<>();
        for(Book b: books) {
            if(b.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                booksByAuthor.add(b);
            }
        }
        return booksByAuthor;
    }

    public List<Book> findByNameAndAuthor(String name, String author) {
        List<Book> booksByNameAndAuthor = new ArrayList<>();
        for(Book b: books) {
            if(b.getAuthor().toLowerCase().contains(author.toLowerCase()) && b.getTitle().toLowerCase().contains(name.toLowerCase())) {
                booksByNameAndAuthor.add(b);
            }
        }
        return booksByNameAndAuthor;
    }

    public List<Book> emptyBookList() {
        List<Book> emptyList = new ArrayList<>();
        return emptyList;
    }

    @Override
    public Book findByID(Integer id) {
        for(Book b: books) {
            if(b.getBookID() == id) {
                return b;
            }
        }
        return null;
    }

    @Override
    public void createBook(Book book) {
        books.add(book);
        nextID++;
    }

    @Override
    public void editBook(Book book) {
        for(Book b: books) {
            if(b.getBookID() == book.getBookID()) {
                b.setAuthor(book.getAuthor());
                b.setDatePublished(book.getDatePublished());
                b.setGenre(book.getGenre());
                b.setISBN(book.getISBN());
                b.setPrice(book.getPrice());
                b.setTitle(book.getTitle());
                break; // Since IDs are unique, there is no need to continue traversing the list of books.
            }
        }
    }

    @Override
    public void deleteBook(Book book) {
        books.remove(book);
    }
}
