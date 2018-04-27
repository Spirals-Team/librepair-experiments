package nc.noumea.mairie.bilan.energie.core.converter;

import java.util.Calendar;
import java.util.Date;

import org.dozer.DozerConverter;

/**
 * Convertit une date depuis/vers une date
 * 
 * @author Greg Dujardin
 */
public class DateLongConverter extends DozerConverter<Date, Long> {

	/**
	 * Constructeur de DateLongConverter
	 * 
	 */
	public DateLongConverter() {

		super(Date.class, Long.class);
	}

	/**
	 * @see org.dozer.DozerConverter#convertTo(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public Long convertTo(final Date source, final Long destination) {

		return source == null ? null : source.getTime();
	}

	/**
	 * @see org.dozer.DozerConverter#convertFrom(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public Date convertFrom(final Long source, final Date destination) {

		if (source == null) {
			return null;
		}

		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(source);

		return cal.getTime();
	}
}
