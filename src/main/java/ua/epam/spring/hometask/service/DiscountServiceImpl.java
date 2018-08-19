package ua.epam.spring.hometask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.strategy.DiscountStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    private List<DiscountStrategy> strategies;

    @Autowired
    public void setStrategies(List<DiscountStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {

        byte maxDiscount = 0;

        for (DiscountStrategy strategy : strategies) {
            byte discount = strategy.getPercentDiscount(user, event, airDateTime, numberOfTickets);
            if (discount > maxDiscount) {
                maxDiscount = discount;
            }
        }

        return maxDiscount;
    }
}
