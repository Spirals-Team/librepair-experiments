package hanfak.shopofhan.domain.stock;

import hanfak.shopofhan.domain.crosscutting.SingleValueType;

public class StockAmount extends SingleValueType<Integer> {
    private StockAmount(Integer value) {
        super(value);
    }

    public static StockAmount stockAmount(Integer amount) {
        return new StockAmount(amount);
    }
}
