package ru.job4j.crud.store;

/**
 * @author Yury Matskevich
 */
public class DbStoreTest extends StoreTest {
	@Override
	protected Store getStore() {
		return DbStore.getInstance();
	}
}
