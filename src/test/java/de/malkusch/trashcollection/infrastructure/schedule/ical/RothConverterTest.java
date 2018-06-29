package de.malkusch.trashcollection.infrastructure.schedule.ical;

import static de.malkusch.trashcollection.model.TrashCan.Type.ORGANIC;
import static de.malkusch.trashcollection.model.TrashCan.Type.PAPER;
import static de.malkusch.trashcollection.model.TrashCan.Type.PLASTIC;
import static de.malkusch.trashcollection.model.TrashCan.Type.RESIDUAL;
import static org.junit.Assert.assertEquals;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import de.malkusch.trashcollection.model.TrashCan;
import de.malkusch.trashcollection.model.TrashCan.Type;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;

@RunWith(Theories.class)
public class RothConverterTest {
	
	@DataPoints("shouldConvert")
	public static Object[][] shouldConvert = {
			{PAPER, "Abfuhr: Altpapier"},
			{PLASTIC, "Abfuhr: Gelber Sack"},
			{ORGANIC, "Abfuhr: Biomüll"},
			{RESIDUAL, "Abfuhr: Restmüll"},
	};
	
	@Theory
	public void shouldConvert(@FromDataPoints("shouldConvert") Object[] test) {
		TrashCan expected = new TrashCan((Type) test[0]);
		String summary = (String) test[1];
		VEvent event = new VEvent(new Date(), summary);
		VEventToTrashCanConverter converter = new RothConverter();
		
		TrashCan actual = converter.toTrashCan(event);
		
		assertEquals(expected, actual);
	}

}
