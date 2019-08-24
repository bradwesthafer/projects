package com.MobyDickens.controller;

import com.MobyDickens.model.Book;
import com.MobyDickens.model.DateTime;
import com.MobyDickens.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brad on 11/20/2016.
 */
@Controller
public class IndexController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "index";
    }

    @RequestMapping(value="/search", method=RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("books", bookService.emptyBookList());
        return "search";
    }

    @RequestMapping(value="/search", method=RequestMethod.POST)
    public String search(@RequestParam(value="title") String title, @RequestParam(value="author") String author, Model model) {
        if(title.equals("") && author.equals("")) {
            model.addAttribute("books", bookService.findAll());
        }
        else if(title.equals("")) {
            model.addAttribute("books", bookService.findByAuthor(author));
        }
        else if(author.equals("")) {
            model.addAttribute("books", bookService.findByName(title));
        }
        else {
            model.addAttribute("books", bookService.findByNameAndAuthor(title, author));
        }
        return "search";
    }

    @RequestMapping(value="/contact-us", method=RequestMethod.GET)
    public String contact() {
        return "contact";
    }

    @RequestMapping(value="/contact-us", method=RequestMethod.POST)
    public String contact(@RequestParam(value="name") String name, @RequestParam(value="email") String email, @RequestParam(value="message") String message, Model model) {
        // Insert Email Processing Code Here (Wasn't Covered in Class)
        model.addAttribute("name", name);
        return "contact-sent";
    }

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping("/logout-success")
    public String logout() { return "logout"; }

    @RequestMapping("/admin/books/view")
    public String view(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "view";
    }

    @RequestMapping(value="/admin/books/add", method= RequestMethod.GET)
    public String addBook() {
        return "add";
    }

    @RequestMapping(value="/admin/books/add", method= RequestMethod.POST)
    public String addBook(@RequestParam(value="bookTitle") String bookTitle,  @RequestParam(value="bookPrice") String bookPrice, @RequestParam(value="bookAuthor") String bookAuthor, @RequestParam(value="bookISBN") String bookISBN, @RequestParam(value="bookDateMonth") String bookDateMonth, @RequestParam(value="bookDateYear") String bookDateYear, @RequestParam(value="bookGenre") String bookGenre) {

        Book book = new Book(bookService.getNextID(), bookTitle, Long.valueOf(bookISBN), bookAuthor, new DateTime(bookDateMonth, Integer.valueOf(bookDateYear)), Book.findGenre(bookGenre), Double.valueOf(bookPrice));
        bookService.createBook(book);
        return "redirect:/";
    }

    @RequestMapping("/admin/books/edit/{bookID}")
    public String editBook(@PathVariable Integer bookID, Model model) {
        model.addAttribute("book", bookService.findByID(bookID));
        return "edit";
    }

    @RequestMapping(value="/admin/books/edit/{bookID}", method= RequestMethod.POST)
    public String editBook(@PathVariable Integer bookID, @RequestParam(value="bookTitle") String bookTitle,  @RequestParam(value="bookPrice") String bookPrice, @RequestParam(value="bookAuthor") String bookAuthor, @RequestParam(value="bookISBN") String bookISBN, @RequestParam(value="bookDateMonth") String bookDateMonth, @RequestParam(value="bookDateYear") String bookDateYear, @RequestParam(value="bookGenre") String bookGenre) {

        Book book = new Book(bookID, bookTitle, Long.valueOf(bookISBN), bookAuthor, new DateTime(bookDateMonth, Integer.valueOf(bookDateYear)), Book.findGenre(bookGenre), Double.valueOf(bookPrice));
        bookService.editBook(book);
        return "redirect:/";
    }

    @RequestMapping(value="/admin/books/delete/{bookID}")
    public String deleteBook(@PathVariable Integer bookID, Model model) {
        model.addAttribute("book", bookService.findByID(bookID));
        return "delete";
    }

    @RequestMapping(value="/admin/books/delete/{bookID}", method= RequestMethod.POST)
    public String deleteBook(@PathVariable Integer bookID, @RequestParam(value="answer") String answer) {
        if(answer.equals("Yes")) {
            bookService.deleteBook(bookService.findByID(bookID));
        }
        return "redirect:/";
    }
}
