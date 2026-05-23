package com.flarecon.mocktail.tool;

import com.flarecon.mocktail.dto.herb.HerbCreateRequest;
import com.flarecon.mocktail.model.Herb;
import com.flarecon.mocktail.service.HerbService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HerbTool {

    private final HerbService herbService;

    @Tool(description = "Create a new Herb")
    public Herb createHerb(HerbCreateRequest herb) {
        return herbService.createHerb(herb);
    }

    @Tool(description = "list all Herbs")
    public List<Herb> listAllHerbs() {
        return herbService.listAllHerbs();
    }

    @Tool(description = "search herbs by any single usage")
    public List<Herb> searchHerbsByUsage(String usage) {
        return herbService.searchHerbsByUsage(usage);
    }

}
