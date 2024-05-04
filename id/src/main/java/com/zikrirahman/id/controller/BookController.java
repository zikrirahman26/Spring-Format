package com.zikrirahman.id.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zikrirahman.id.model.Book;
import com.zikrirahman.id.repository.BookRepository;

@RestController
@RequestMapping("/books")
public class BookController {


    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/addBook")
    public Book addBook(@RequestBody Book request){
        Book result = bookRepository.save(request);
        return result;
    }

    @GetMapping("/getBook/{id}")
    public Optional<Book> getBook(@PathVariable Long id){
        Optional<Book> result = bookRepository.findById(id);
        return result;
    }
}
