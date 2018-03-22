package guru.bonacci.oogway.sannyas.service.general;

/**
 * To avoid excessive web communication, store the max. page for each 'site +
 * tag' search request.
 * 
 * Cache or caching may refer to: Caching or hoarding (animal behavior), a food
 * storing behavior of animals A cache or hoard, a collection of artifacts
 * Treasure trove, a valuable cache which has been lost, or left unclaimed by
 * the owner, or a place where items are stored Geocaching, an outdoor
 * treasure-hunting game which involves looking for containers of varying sizes
 * called geocaches or caches Bear cache, a bear-safe storage facility.
 */
public interface PageCache {

	Integer getNrOfPages(String searchURL);
}
