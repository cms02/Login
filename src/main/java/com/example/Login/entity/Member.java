package com.example.Login.entity;

import javax.persistence.*;

@Entity
public class Member extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String password;

    private int age;

    @Enumerated(EnumType.STRING)
    private Authority authority;
}
