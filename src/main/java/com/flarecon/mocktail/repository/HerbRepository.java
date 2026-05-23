package com.flarecon.mocktail.repository;

import com.flarecon.mocktail.model.Herb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HerbRepository extends JpaRepository<Herb, Long> {
    List<Herb> findByUsagesLikeIgnoreCase(String usage);
}
