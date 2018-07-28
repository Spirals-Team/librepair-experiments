package ru.job4j.crud.store;

/**
 * @author Yury Matskevich
 */
public class StoreCollectionTest extends StoreTest {
	@Override
	protected Store getStore() {
		return MemoryStore.getInstance();
	}
}
