package nc.noumea.mairie.bilan.energie.core.converter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import org.dozer.CustomConverter;

/**
 * Convertit un nombre depuis/vers un nombre
 * 
 * @author Greg Dujardin
 */
public class NumberConverter implements CustomConverter {

	// cache des types natifs
	private final static Map<String, NumberType> CACHE = new TreeMap<String, NumberType>();

	static {
		for (final NumberType nt : NumberType.values()) {
			for (final Class<?> type : nt.types()) {
				NumberConverter.CACHE.put(type.getName(), nt);
			}
		}
	}

	/**
	 * @see org.dozer.CustomConverter#convert(java.lang.Object,
	 *      java.lang.Object, java.lang.Class, java.lang.Class)
	 */
	@Override
	public Object convert(final Object existing, final Object value,
			final Class<?> dst, final Class<?> src) {

		if (value == null) {
			return null;
		}

		final Object result;
		final String original;

		// cas particulier : source en chaine
		if (String.class.isAssignableFrom(src)) {
			original = (String) value;
		} else {
			// sinon number
			original = value.toString();
		}

		// cas second : destination en chaine
		if (String.class.isAssignableFrom(dst)) {
			result = original;
		} else {
			// sinon parse la chaine via un BigDecimal ...
			final BigDecimal temp = new BigDecimal(original);

			// ... puis convertit en type natif
			final NumberType type = NumberConverter.CACHE.get(dst.getName());
			if (type == null) {
				throw new RuntimeException(
						String.format(
								"Unsupported destination type : existing=%s, value=%s, dst=%s, src=%s",
								existing, value, dst, src));
			}

			result = NumberConverter.CACHE.get(dst.getName()).convert(temp);
		}

		return result;
	}
}
