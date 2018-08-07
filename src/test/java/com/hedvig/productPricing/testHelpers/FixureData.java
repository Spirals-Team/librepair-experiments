package com.hedvig.productPricing.testHelpers;

import com.hedvig.productPricing.service.web.dto.PerilDTO;

import java.util.ArrayList;

public class FixureData {
    public static ArrayList<PerilDTO> createTwoPerils() {
        return new ArrayList<PerilDTO>() {{
            add(new PerilDTO(
                "ME.LEGAL",
                "Title1",
                "COVERED",
                "",
                "Short desc",
                "Long desc",
                false));
            add(new PerilDTO(
                    "ME.ASSAULT",
                    "Assault",
                    "NOT_COVERED",
                    "",
                    "Short assault desc",
                    "Long assult desc",
                    true));
        }};
    }
}
