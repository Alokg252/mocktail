package com.flarecon.mocktail.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String username;

    @JsonIgnore
    @Column(nullable = false)
    public String password;

    public String roles = "viewer";
}
