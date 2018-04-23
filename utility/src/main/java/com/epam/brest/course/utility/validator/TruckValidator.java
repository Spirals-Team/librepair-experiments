package com.epam.brest.course.utility.validator;

import com.epam.brest.course.utility.data.TruckDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * validator from spring jsr303.
 */
public class TruckValidator implements Validator {

    /**
     * min truck code letters.
     */
    private static final int MIN_TRUCK_CODE = 5;
    /**
     * max .
     */
    private static final int MAX_TRUCK_CODE = 7;

    /**
     * @param clazz generic.
     * @return truck.
     */
    @Override
    public final boolean supports(final Class<?> clazz) {
        return TruckDto.class.equals(clazz);
    }

    /**
     * @param obj target.
     * @param e errors.
     */
    @Override
    public final void validate(final Object obj, final Errors e) {

        ValidationUtils.rejectIfEmptyOrWhitespace(e,
                "truckCode", "truckCode.empty");

        ValidationUtils.rejectIfEmptyOrWhitespace(e,
                "purchasedDate", "purchasedDate.empty");

        ValidationUtils.rejectIfEmptyOrWhitespace(e,
                "descriptions", "descriptions.empty");

        TruckDto truckDto = (TruckDto) obj;

        if (truckDto.getTruckCode().length() < MIN_TRUCK_CODE) {
            e.rejectValue("truckCode", "truckCode.short");
        } else if (truckDto.getTruckCode().length() > MAX_TRUCK_CODE) {
            e.rejectValue("truckCode", "truckCode.long");
        }

    }

}
