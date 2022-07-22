package com.example.Login.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String password;

    private int age;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member(String username, String password, int age, Role role) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.role = role;
    }
}
