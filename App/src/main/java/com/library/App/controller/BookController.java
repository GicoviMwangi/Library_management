package com.library.App.controller;

import com.library.App.model.Book;
import com.library.App.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks(){
        return ResponseEntity.ok().body(bookService.getBooks());
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId){
        return ResponseEntity.ok().body(bookService.getBookById(bookId));
    }

//    @PostMapping("/book")
//    public ResponseEntity<Book> addBook(@RequestPart Book book, @RequestPart MultipartFile bookImage) throws Exception {
//        Book book1 = bookService.addBook(book,bookImage);
//        return new ResponseEntity<>(book, HttpStatus.OK);
//    }

    @PostMapping("/book")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("/book/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId,@RequestPart Book book) {

        return new ResponseEntity<>(bookService.updateBook(bookId,book),HttpStatus.OK);
    }

    @DeleteMapping("/book/{bookId}")
    public void deleteBookById(@PathVariable Long bookId){
        bookService.deleteBookById(bookId);
    }
}
