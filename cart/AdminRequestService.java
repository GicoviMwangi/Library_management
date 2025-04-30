package com.library.App.cart;

import com.library.App.model.Book;
import com.library.App.repo.BooksRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminRequestService {
    private final BookRequestRepository bookRequestRepository;
    private final BooksRepository booksRepository;

    public List<BookRequest> getRecentRequest(int days){
        return bookRequestRepository.findByApprovalDateAfter(LocalDateTime.now().minusDays(days));
    }

    @Transactional
    public void cancelRequest(Long requestId){
        BookRequest request = bookRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("REQUEST NOT FOUND"));

        if (request.getStatus() == RequestStatus.APPROVED){
            request.getBookSet().forEach(book -> {
                Book dbBook = booksRepository.findById(book.getBookId()).get();

                dbBook.setQuantity(dbBook.getQuantity() +1);
                booksRepository.save(dbBook);
            });
        }
        request.setStatus(RequestStatus.REJECTED);
        bookRequestRepository.save(request);
    }

}
