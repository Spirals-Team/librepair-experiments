package ru.job4j.last.dao;

import java.util.List;

/**
 * @author Yury Matskevich
 */
public interface IOfferDao {
	/**
	 * Adds a current offer to a store.
	 * @param offers an {@link List} of offer for adding.
	 */
	void add(List<Offer> offers);

	/**
	 * Deletes offer from the store.
	 * @param id offer's id which will be deleted.
	 */
	void delete(String id);

	/**
	 * Returns all the offers which are in the store.
	 * @return {@link List} of {@link Offer}.
	 */
	List<Offer> getOffers();

	/**
	 * Gets date of youngest date of creating offer
	 * which therer is in a db.
	 * @return a long number which represents the
	 * youngest date of creating offer in the db.
	 */
	long getTheNewestDate();
}
