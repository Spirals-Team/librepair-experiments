package fr.esiea.services;

import fr.esiea.models.Item;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import fr.esiea.web.GildedRoseApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map.Entry;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DatabaseAdapter {

    private final BiMap<Integer, Item> database = HashBiMap.create();
    private final AtomicInteger sequenceGenerator = new AtomicInteger();
    private static final Logger LOGGER = LoggerFactory.getLogger(GildedRoseApplication.class);

    public DatabaseAdapter() {
    }

    public int addItem(Item item) {
        final int id;
        if (!database.containsValue(item)) {
            id = sequenceGenerator.incrementAndGet();
            item.setId(id);
            database.put(id, item);
            LOGGER.info(item + " created with ID: " + id);
        } else {
            id = database.inverse().get(item);
            LOGGER.info(item + " already exists with ID: " + id);
        }
        return id;
    }

    public Item getItem(int id){
        return database.getOrDefault(id, null);
    }

    public void updateItem(Item item){
        this.database.put(item.getId(), item);
    }

    public boolean removeItem(Item item) {
        if (database.containsValue(item)) {
            LOGGER.info(item + " removed");
            database.remove(item.getId(), item);
        }
        return true;
    }

    public List<Item> getItems(){
            return database.entrySet()
                    .stream()
                    .map(Entry::getValue)
                    .collect(Collectors.toList());
    }
}
