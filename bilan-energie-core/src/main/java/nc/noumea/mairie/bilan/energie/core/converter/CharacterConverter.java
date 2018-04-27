package nc.noumea.mairie.bilan.energie.core.converter;

import org.dozer.DozerConverter;

/**
 * Convertit un caractère depuis/vers une chaine
 * 
 * @author Greg Dujardin
 */
public class CharacterConverter extends DozerConverter<Character, String> {

	/**
	 * Constructeur CharacterConverter
	 * 
	 */
	public CharacterConverter() {

		super(Character.class, String.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dozer.DozerConverter#convertTo(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public String convertTo(final Character source, final String destination) {

		return source == null ? null : source.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dozer.DozerConverter#convertFrom(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public Character convertFrom(final String source,
			final Character destination) {

		return source == null || source.length() == 0 ? null : source.charAt(0);
	}

}
