package com.hedvig.productPricing.service.aggregates;

import com.hedvig.productPricing.service.web.dto.SafetyIncreaserType;
import lombok.Value;

@Value
public class SafetyIncreaser {
    String name;

    private static final String SAFETY_DOOR = "Säkerhetsdörr";
    private static final String BURGLAR_ALARM = "Inbrottslarm";
    private static final String FIRE_EXTINGUISHER = "Brandsläckare";
    private static final String GATE = "Gallergrind";
    private static final String SMOKE_ALARM = "Brandvarnare";
    private static final String NONE = "";
    private static final String INGENTING = "Ingenting";

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

    public static SafetyIncreaserType createFrom(String safetyIncreaser) {
        switch(safetyIncreaser) {
            case SAFETY_DOOR:
                return SafetyIncreaserType.SAFETY_DOOR;
            case BURGLAR_ALARM:
                return SafetyIncreaserType.BURGLAR_ALARM;
            case FIRE_EXTINGUISHER:
                return SafetyIncreaserType.FIRE_EXTINGUISHER;
            case GATE:
                return SafetyIncreaserType.GATE;
            case SMOKE_ALARM:
                 return SafetyIncreaserType.SMOKE_ALARM;
            case INGENTING:
            case NONE:
                return SafetyIncreaserType.NONE;
            default:
                throw new RuntimeException(String.format("Missing enum hanlder %s:%s", SafetyIncreaserType.class.getName(), safetyIncreaser));
        }
    }
}
