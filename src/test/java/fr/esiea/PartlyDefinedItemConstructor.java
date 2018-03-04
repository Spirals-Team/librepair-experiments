package fr.esiea;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import fr.esiea.models.CheeseItem;
import fr.esiea.models.ConjuredItem;
import fr.esiea.models.Item;
import fr.esiea.models.LegendaryItem;
import fr.esiea.models.TicketItem;
 
@RunWith(Parameterized.class)
public class PartlyDefinedItemConstructor {
 
    @Parameters
    public static Object[] data() {
	    return new Object[] {
	    		new Object[] { new Item(), "", 0, 0 },
	    		new Object[] { new ConjuredItem(), "", 0, 0 },
	    		new Object[] { new LegendaryItem(), "", 0, 0 },
	    		new Object[] { new CheeseItem(), "", 0, 0 },
	    		new Object[] { new TicketItem(), "", 0, 0 },
	    		new Object[] { new Item("testName",1,2), "testName",1,2 },
	    		new Object[] { new LegendaryItem("testName",1,2), "testName",1,2 },
	    		new Object[] { new ConjuredItem("testName",1,2), "testName",1,2 },
	    		new Object[] { new CheeseItem("testName",1,2), "testName",1,2 },
	    		new Object[] { new TicketItem("testName",1,2), "testName",1,2 }
        };
    }
 
    private final Item item;
    private final String expectedName;
    private final int expectedSellIn;
    private final int expectedQuality;
 
    public PartlyDefinedItemConstructor(Item item, String expectedName, int expectedSellIn, int expectedQuality) {
        this.item = item;
        this.expectedName = expectedName;
        this.expectedSellIn = expectedSellIn;
        this.expectedQuality = expectedQuality;
    }
 
    @Test
    public void shouldBeCorrectlyInitialized() {
        SoftAssertions solftly = new SoftAssertions();
        
        solftly.assertThat(item.getName())
        	.as("foo name")
        	.isEqualTo(expectedName);
        
        solftly.assertThat(item.getSellIn())
	        .as("foo sellIn")
	        .isEqualTo(expectedSellIn);

        solftly.assertThat(item.getQuality())
	        .as("foo quality")
	        .isEqualTo(expectedQuality);
        
        solftly.assertAll();
    }
}