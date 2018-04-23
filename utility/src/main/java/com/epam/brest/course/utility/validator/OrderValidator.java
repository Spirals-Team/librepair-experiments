package com.epam.brest.course.utility.validator;

import com.epam.brest.course.utility.data.OrderDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * order validator.
 */
public class OrderValidator implements Validator {
    /**
     * max petrol qty for one order.
     */
    private static final double FIVE_HUNDS = 500.0;
    /**
     * min petrol for one order.
     */
    private static final double TWELVE = 12.0;

    /**
     *
     * @param clazz param.
     * @return true.
     */
    @Override
    public final boolean supports(final Class<?> clazz) {
        return OrderDto.class.equals(clazz);
    }

    /**
     *
     * @param obj param.
     * @param e error.
     */
    @Override
    public final void validate(final Object obj, final Errors e) {

        ValidationUtils.rejectIfEmptyOrWhitespace(e,
                "petrolQty", "petrolQty.empty", "enter petrol qty");

        ValidationUtils.rejectIfEmptyOrWhitespace(e,
                "orderDate", "orderDate.empty", "enter the order date");

        ValidationUtils.rejectIfEmptyOrWhitespace(e,
                "truckId", "truckId.empty", "choose truck");

        OrderDto orderDto = (OrderDto) obj;

        if (orderDto.getPetrolQty() == null || orderDto.getPetrolQty() < TWELVE) {
            e.rejectValue("petrolQty", "petrol.small", "petrol qty must be above 249");
        } else if (orderDto.getPetrolQty() > FIVE_HUNDS) {
            e.rejectValue("salary", "petrol.large",
                    "salary must be above 249");
        }

    }
}

