package touk.parkingmeter.domain;


public class RateCreator {

    public static Rate createRate(DriverType driverType, Currency currency,
                                  double fhour, double shour, double nhour) {
        return Rate.builder()
                .driverType(driverType.toString())
                .currency(currency.toString())
                .firstHour(fhour)
                .secondHour(shour)
                .nextHour(nhour)
                .build();
    }
}
