package nc.noumea.mairie.bilan.energie.core.utils;

import java.lang.reflect.Method;

/**
 * Class utilitaire des énumérations
 * 
 * @author Greg Dujardin
 *
 */
public class EnumUtils {


		/**
		 * Constructeur privé conseillé par Sonar
		 */
		private EnumUtils() {

		}

		/**
		 * Cette méthode retourne la constante associée à l'objet retourné par la
		 * méthode "enumMethod" de l'énum. Exemple si l'énum E.TOTO.getCustumValue()
		 * retourne "CUSTUM_TOTO", et que les paramètre d'appel sont (E.class,
		 * "CUSTUM_TOTO", "getCustumValue"), alors cette méthode retourne l'énum
		 * E.TOTO
		 * 
		 * @param <T> Type
		 * @param classEnum
		 *            Classe de l'énum
		 * @param enumValue
		 *            valeur retourné par la méthode "enumMethod" de l'énum
		 * @param enumMethod
		 *            Nom de la méthode
		 * @return Enum ou null si aucun enum trouvé
		 */
		public static <T> T parseEnum(final Class<T> classEnum,
				final Object enumValue, String enumMethod) {

			final T[] names = classEnum.getEnumConstants();
			for (final T item : names) {
				try {
					final Method m = classEnum.getMethod(enumMethod);
					final Object val = m.invoke(item);

					if (val.equals(enumValue)) {
						return item;
					}

				} catch (final Exception e) {
					throw new RuntimeException(e);
				}
			}

			return null;
		}

		/**
		 * Cette méthode retourne la constante associée à la chaine de caractère
		 * retournée par l'énum.name(). Exemple si l'énum E.TOTO.name() retourne
		 * "TOTO", alors cette méthode retourne l'énum E.TOTO
		 * 
		 * @param <T> Type
		 * @param classEnum
		 *            Classe de l'énum
		 * @param enumValue
		 *            valeur de type String de l'énum
		 * @return Enum ou null si aucun enum trouvé
		 */
		public static <T> T parseEnum(final Class<T> classEnum,
				final String enumValue) {
			return parseEnum(classEnum, enumValue, "name");
		}

		/**
		 * Cette méthode retourne la constante associée à la chaine de caractère
		 * retournée par l'énum.ordinal() == ordinal passé en paramètre.
		 * 
		 * @param <T> Type
		 * @param classEnum
		 *            Classe de l'énum
		 * @param ordinal
		 *            Ordinal
		 * @return Enum
		 */
		public static <T> T parseEnum(final Class<T> classEnum, final int ordinal) {

			final T[] names = classEnum.getEnumConstants();

			return names[ordinal];
		}
	}
