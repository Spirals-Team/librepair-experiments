package ru.job4j.json;

import net.jcip.annotations.ThreadSafe;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class stores user in the {@link Map}
 *
 * @author Yury Matskevich
 */
@ThreadSafe
public class MemoryStore {
	private static final Logger LOG = Logger.getLogger(MemoryStore.class);
	private static MemoryStore uniqueInstance = new MemoryStore();
	private final Map<Integer, User> store = new ConcurrentHashMap<>();

	private MemoryStore() {

	}

	public static MemoryStore getInstance() {
		return uniqueInstance;
	}

	public void add(User user) {
		store.put(user.getId(), user);
	}

	public List<User> getAllUsers() {
		return new ArrayList<>(store.values());
	}
}
