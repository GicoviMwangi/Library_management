package com.library.App.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.App.model.Book;
import com.library.App.model.UserDTO;
import com.library.App.model.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToMany
    private Set<Book> bookSet = new HashSet<>();

    private LocalDateTime requestDate;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    private String serialNumber;
    private LocalDateTime approvalDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RequestStatus getStatus(){
        return this.status;
    }
    
}
