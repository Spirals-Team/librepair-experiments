package nc.noumea.mairie.bilan.energie.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Quelques utilitaires sur les objets
 * 
 * @author alexisd
 */
public final class ObjectUtils {

	/**
	 * Constructeur privé (pas d'instanciation)
	 */
	private ObjectUtils() {

		super();
	}

	/**
	 * Clone un objet soit via la méthode clone, soit par sérialisation et
	 * désérialisation
	 * 
	 * @param src Objet à cloner
	 * @return Objet cloné
	 * @throws IOException Exception IO
	 */
	public final static Object clone(final Object src) throws IOException {

		// Récupération du résultat
		Object result = null;

		// Copie des paramétres si l'interface Cloneable est instancié
		if (src instanceof Cloneable) {

			Method m = null;
			try {
				m = src.getClass().getDeclaredMethod("clone");
				boolean access = m.isAccessible();
				m.setAccessible(true);
				result = m.invoke(src);
				m.setAccessible(access);
			} catch (final Exception e) {
				// on ne fait rien
			}
		}

		// Copie des paramétres si l'interface Cloneable n'est pas instancié
		if (result == null) {

			if (!(src instanceof Serializable)) {
				throw new IOException(
						"Pour clonner un objet, cet objet doit intancier soit Cloneable, soit Serializable");
			}

			// Sérialisation de l'objet
			final ByteArrayOutputStream bArrayOut = new ByteArrayOutputStream();
			final ObjectOutputStream out = new ObjectOutputStream(bArrayOut);

			try {
				out.writeObject(src);
				out.flush();
			} finally {
				out.close();
				bArrayOut.close();
			}

			// Désérialisation de l'objet
			final ByteArrayInputStream bArrayIn = new ByteArrayInputStream(
					bArrayOut.toByteArray());
			final ObjectInputStream in = new ObjectInputStream(bArrayIn);

			try {
				result = in.readObject();
			} catch (final ClassNotFoundException e) {

				// Ce cas ne doit jamais se produire puisqu'oon désérialise une
				// classe que l'on vient de sérialiser
				return null;

			} finally {
				try {
					in.close();
				} finally {
					bArrayIn.close();
				}
			}

		}

		return result;
	}

	/**
	 * Cette méthode retourne true si une classe (Integer, Float, etc...) est de
	 * type "Primitif" ou que la classe est un primitif (int, double, float,
	 * etc...)
	 * 
	 * @param primitiveClass 
	 *            Classe "primitif"
	 * @return boolean
	 */
	public final static boolean isPrimitiveClass(final Class<?> primitiveClass) {

		boolean primitiveType = primitiveClass.isPrimitive();
		if (!primitiveType) {
			try {
				primitiveType = ObjectUtils.getPrimitiveType(primitiveClass)
						.isPrimitive();
			} catch (final Exception e) {
				primitiveType = false;
			}
		}

		return primitiveType;
	}

	/**
	 * Cette méhode retourne la classe primitive associée à un objet de type
	 * Integer, Double, etc...
	 * 
	 * @param primitiveClass Classe "primitif"
	 * @return class primitiv associée
	 */
	public final static Class<?> getPrimitiveType(final Class<?> primitiveClass) {

		try {

			if (primitiveClass.isPrimitive()) {
				return primitiveClass;
			}

			final Field field = primitiveClass.getField("TYPE");
			final Class<?> clazz = (Class<?>) field.get(null);

			return clazz;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}

