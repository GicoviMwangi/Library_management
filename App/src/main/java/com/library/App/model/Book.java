package com.library.App.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "category")
    private String category;

    @Column(name = "level")
    private int level;

    @Column(name = "quantity")
    private int quantity;

//    private String bookImageSource;
//    private String bookImageType;
//    @Lob
//    private byte[] bookImage;


}
