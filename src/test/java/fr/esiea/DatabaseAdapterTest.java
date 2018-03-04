package fr.esiea;

import fr.esiea.models.*;
import fr.esiea.services.DatabaseAdapter;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.time.Clock;


public class DatabaseAdapterTest {
    @Test
    public void singleton_instance(){

        DatabaseAdapter databaseAdapter1 = DatabaseAdapter.getInstance();
        DatabaseAdapter databaseAdapter2 = DatabaseAdapter.getInstance();

        SoftAssertions solftly = new SoftAssertions();
        solftly.assertThat(databaseAdapter1)
                .as("object instanceOfDatabaseAdapter")
                .isEqualTo(databaseAdapter2);

        solftly.assertAll();
    }

    @Test
    public void item_unicity(){
        DatabaseAdapter databaseAdapter = DatabaseAdapter.getInstance();

        Item item = new Item("testName",1,2,3,4);

        int idItem = databaseAdapter.addItem(item);
        int itemCountBeforeOverride = databaseAdapter.getItems().size();

        databaseAdapter.addItem(item);
        int itemCountAfterOverride = databaseAdapter.getItems().size();

        SoftAssertions solftly = new SoftAssertions();
        solftly.assertThat(itemCountBeforeOverride)
                .as("int countItems")
                .isEqualTo(itemCountAfterOverride);

        databaseAdapter.removeItem(item);

        solftly.assertThat(databaseAdapter.getItem(idItem))
                .as("Item deletedItem")
                .isEqualTo(null);

        solftly.assertAll();
    }

    /*public void updateItem(Item item) {
        this.database.put(item.getId(), item);
    }*/

    @Test
    public void update_items(){
        DatabaseAdapter databaseAdapter = DatabaseAdapter.getInstance();

        Item item = new Item("testName1",1,2,3,4);

        int idItem = databaseAdapter.addItem(item);
        item.setQuality(3);

        databaseAdapter.updateItem(item);

        SoftAssertions solftly = new SoftAssertions();

        solftly.assertThat(databaseAdapter.getItem(idItem).getQuality())
                .as("int quality")
                .isEqualTo(3);

        Item item2 = new Item("testName2",1,2,3,4);
        Item item3 = new Item("testName3",5,6,7,8);
        Item item4 = new Item("testName4",9,10,11,12);
        Item[] items = new Item[]{item2, item3, item4};

        databaseAdapter.setItems(items);

        solftly.assertThat(databaseAdapter.getItems())
                .as("Item items")
                .isEqualTo(items);

        solftly.assertAll();
    }
}
