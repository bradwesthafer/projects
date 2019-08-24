package com.MobyDickens.jpa;

import com.MobyDickens.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Brad on 12/11/2016.
 */
public interface BookCrudRepository extends CrudRepository<Book, Integer> {
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
}
