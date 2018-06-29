package com.hedvig.productPricing.service.aggregates;

import com.hedvig.productPricing.service.web.dto.SafetyIncreaserType;
import lombok.Value;

@Value
public class SafetyIncreaser {
    String name;

    public static SafetyIncreaser createFrom(SafetyIncreaserType safetyIncreaserType) {
        SafetyIncreaser si;
        switch(safetyIncreaserType) {
            case SAFETY_DOOR:
                si = new SafetyIncreaser("Säkerhetsdörr");
                break;
            case BURGLAR_ALARM:
                si = new SafetyIncreaser("Inbrottslarm");
                break;
            case FIRE_EXTINGUISHER:
                si = new SafetyIncreaser("Brandsläckare");
                break;
            case GATE:
                si = new SafetyIncreaser("Gallergrind");
                break;
            case SMOKE_ALARM:
                si = new SafetyIncreaser("Brandvarnare");
                break;
            case NONE:
                si = new SafetyIncreaser("");
                break;
            default:
                throw new RuntimeException(String.format("Missing enum hanlder %s:%s", SafetyIncreaserType.class.getName(), safetyIncreaserType.name()));
        }
        return si;
    }
}
