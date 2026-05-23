package com.flarecon.mocktail.service;

import com.flarecon.mocktail.dto.herb.HerbCreateRequest;
import com.flarecon.mocktail.model.Herb;
import com.flarecon.mocktail.repository.HerbRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HerbService {

    private final HerbRepository herbRepository;

    public Herb createHerb(HerbCreateRequest herb) {
        return herbRepository.saveAndFlush(new Herb(herb));
    }

    public List<Herb> listAllHerbs() {
        return herbRepository.findAll();
    }

    public List<Herb> searchHerbsByUsage(String usage) {
        return herbRepository.findByUsagesLikeIgnoreCase("%" + usage + "%");
    }
}
