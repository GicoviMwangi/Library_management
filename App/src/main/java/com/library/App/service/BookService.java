package com.library.App.service;

import com.library.App.model.Book;
import com.library.App.repo.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BooksRepository booksRepository;

    public List<Book> getBooks() {
        return booksRepository.findAll();
    }

    public Book getBookById(Long bookId) {
        return booksRepository.findById(bookId).orElse(null);
    }


//    public Book addBook(Book book, MultipartFile bookImage) throws Exception{
//        book.setBookName(bookImage.getOriginalFilename());
//        book.setBookImageType(bookImage.getContentType());
//        book.setBookImage(bookImage.getBytes());
//
//        return booksRepository.save(book);
//    }
    public Book addBook(Book book){
        return booksRepository.save(book);
    }

//    public Book updateBook(Long bookId,Book book,MultipartFile bookImage) throws Exception{
//        book.setBookName(bookImage.getOriginalFilename());
//        book.setBookImageType(bookImage.getContentType());
//        book.setBookImage(bookImage.getBytes());
//
//        return booksRepository.save(book);
//    }

    public Book updateBook(Long bookId,Book book){
        return booksRepository.save(book);
    }

    public void deleteBookById(Long bookId) {
        booksRepository.deleteById(bookId);
    }
}
