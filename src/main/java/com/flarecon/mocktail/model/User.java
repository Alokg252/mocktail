package com.flarecon.mocktail.model;

import com.flarecon.mocktail.dto.user.UserCreateRequest;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String username;

    @Column(nullable = false)
    public String password;

    public String roles = "viewer";


    // constructors
    public User(UserCreateRequest request) {
        this.name = request.getName();
        this.username = request.getUsername();
        this.password = request.getPassword();
    }


    // overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (!(o instanceof User)) return false;
        else return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {return getClass().hashCode();}
}
