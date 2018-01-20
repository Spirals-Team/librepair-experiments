package hanfak.shopofhan.domain.stock;

import hanfak.shopofhan.domain.crosscutting.SingleValueType;

public class StockDescription extends SingleValueType<String> {
    private StockDescription(String value) {
        super(value);
    }

    public static StockDescription stockDescription(String amount) {
        return new StockDescription(amount);
    }
}
