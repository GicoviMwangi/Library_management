package com.library.App.cart;

import com.library.App.model.Book;
import com.library.App.model.UserDTO;
import com.library.App.model.Users;
import com.library.App.repo.BooksRepository;
import com.library.App.repo.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookRequestService {
    private final LibraryUserRepository userRepository;
    private final BookRequestRepository bookRequestRepository;
    private final BooksRepository booksRepository;
    private final LibraryUserRepository libraryUserRepository;


    public BookRequest submitRequest(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));

        BookRequest request = bookRequestRepository.findByUserAndStatus(user, RequestStatus.PENDING)
                .orElseThrow(() -> new RuntimeException("No pending request found"));

        for (Book book : request.getBookSet()) {
            Book dbBook = booksRepository.findById(book.getBookId()).orElseThrow(() -> new UsernameNotFoundException("BOOK NOT AVAILABLE"));

            if (request.getBookSet().size() > 2) throw new RuntimeException("MAX AMOUNT OF BOOKS ISSUED TO USER " + user.getUsername());

            if (dbBook.getQuantity() <= 0) {
                request.setStatus(RequestStatus.REJECTED);
                bookRequestRepository.save(request);
                throw new UsernameNotFoundException("BOOK " + dbBook.getBookName() + " IS NOT AVAILABLE");
            }
        }

        String serialNumber = generateSerialNumber();

        request.getBookSet().forEach(book -> {
            Book dbBook = booksRepository.findById(book.getBookId()).get();
            dbBook.setQuantity(dbBook.getQuantity() -1);
            booksRepository.save(dbBook);
        });

        request.setStatus(RequestStatus.APPROVED);
        request.setSerialNumber(serialNumber);
        request.setApprovalDate(LocalDateTime.now());
        BookRequest savedRequest = bookRequestRepository.save(request);


        return savedRequest;
    }

    public List<BookRequest> getUserRequest (Long userId){
        return bookRequestRepository.findAll();
    }

    private String generateSerialNumber(){
        return "LIB-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))+
                "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public BookRequest addToCart(Long userId, Long bookId) {
        Users user = libraryUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("USER NOT FOUND"));
//        UserDTO userDTO = convertToDto(user);

        Book book = booksRepository.findById(bookId).orElseThrow(()-> new RuntimeException("404, BOOK NOT FOUND"));

        BookRequest request = bookRequestRepository.findByUserAndStatus(user,RequestStatus.PENDING).orElseGet(() -> {
            BookRequest newRequest = new BookRequest();
            newRequest.setUser(user);
            newRequest.setStatus(RequestStatus.PENDING);
            newRequest.setRequestDate(LocalDateTime.now());
            return bookRequestRepository.save(newRequest);
        });

        if (!request.getBookSet().contains(book)){
            request.getBookSet().add(book);
            return bookRequestRepository.save(request);
        }
        return request;
    }

    private UserDTO convertToDto(Users user) {
        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.isEnabled()
        );
    }
}


