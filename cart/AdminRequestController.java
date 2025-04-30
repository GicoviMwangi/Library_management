package com.library.App.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminRequestController {
    private final BookRequestRepository bookRequestRepository;

    @GetMapping("/all-requests")
    public ResponseEntity<?> getAllRequests(){
        return ResponseEntity.ok(bookRequestRepository.findAll());
    }

    @GetMapping("/by-status")
    public ResponseEntity<?> getRequestByStatus(@RequestParam RequestStatus status){
        return ResponseEntity.ok(bookRequestRepository.findByStatus(status));
    }
}
