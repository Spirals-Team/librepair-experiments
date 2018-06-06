package domain;

import lombok.Builder;
import lombok.Data;
import spi.OnlineBankingService;

import java.util.List;

/**
 * Created by alexg on 05.09.17.
 */
@Data
@Builder
public class LoadBookingsResponse {

    private OnlineBankingService onlineBankingService;
    private List<Booking> bookings;
    private List<StandingOrder> standingOrders;
    private BankAccountBalance bankAccountBalance;
}
