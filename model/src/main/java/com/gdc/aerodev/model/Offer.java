package com.gdc.aerodev.model;

/**
 * This is offer between {@code User} and {@code Cr}. Offer initiator can be only {@code Cr}. {@code Offer} has
 * links: {@param offeredUserId} to {@code User} and {@param offeredCrId} to {@code Cr}.
 *
 * @author Yusupov Danil
 */
public class Offer {

    private Long offerId;
    private Long offeredUserId;
    private Long offeredCrId;
    private String offerDescription;

    public Offer() {
    }

    public Offer(Long offerId, Long offeredUserId, Long offeredCrId, String offerDescription) {
        this.offerId = offerId;
        this.offeredUserId = offeredUserId;
        this.offeredCrId = offeredCrId;
        this.offerDescription = offerDescription;
    }

    public Long getOfferId() {
        return offerId;
    }

    public Long getOfferedUserId() {
        return offeredUserId;
    }

    public Long getOfferedCrId() {
        return offeredCrId;
    }

    public String getOfferDescription() {
        return offerDescription;
    }
}
