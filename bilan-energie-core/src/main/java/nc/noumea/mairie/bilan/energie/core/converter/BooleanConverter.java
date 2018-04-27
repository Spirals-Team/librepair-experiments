package nc.noumea.mairie.bilan.energie.core.converter;

import org.dozer.DozerConverter;

/**
 * Convertit un booléen depuis/vers un string
 * 
 * @author Greg Dujardin
 */
public class BooleanConverter extends DozerConverter<Boolean, String> {

	/**
	 * Constructeur BooleanConverter
	 * 
	 */
	public BooleanConverter() {

		super(Boolean.class, String.class);
	}

	/**
	 * @see org.dozer.DozerConverter#convertTo(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public String convertTo(final Boolean source, final String destination) {

		return source == null ? null : source.toString();
	}

	/**
	 * @see org.dozer.DozerConverter#convertFrom(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public Boolean convertFrom(final String source, final Boolean destination) {

		return source == null ? null : new Boolean(source);
	}
}
