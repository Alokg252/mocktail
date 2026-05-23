package com.flarecon.mocktail.dto.herb;

import com.flarecon.mocktail.model.enums.HerbType;
import lombok.Data;

@Data
public class HerbCreateRequest {
    String name;
    String usages;
    HerbType herbType;
}
