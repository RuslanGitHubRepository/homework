package com.homework.lusinda.homework23.sbsecurity.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Information")
@Getter
@Setter
public class Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long Id;

    @Column(name = "Name", length = 64)
    private String name;

    @Column(name = "Isbn", length = 64)
    private String isbn;

    @Column(name = "Author", length = 64)
    private String author;

    @Column(name = "Publishing_House", length = 64)
    private String publishingHouse;
}
