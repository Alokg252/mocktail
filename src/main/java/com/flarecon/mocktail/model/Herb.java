package com.flarecon.mocktail.model;

import com.flarecon.mocktail.dto.herb.HerbCreateRequest;
import com.flarecon.mocktail.model.enums.HerbType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "herbs")
public class Herb implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false)
    String usages;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    HerbType type;

    // constructors
    public Herb(HerbCreateRequest request) {
        this.name = request.getName();
        this.usages = request.getUsages();
        this.type = request.getHerbType();
    }

    // overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (!(o instanceof Herb)) return false;
        else return id != null && id.equals(((Herb) o).id);
    }

    @Override
    public int hashCode() {return getClass().hashCode();}
}
