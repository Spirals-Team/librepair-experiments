package nc.noumea.mairie.bilan.energie.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;

/**
 * Classe devant être implémenter lors des tests unitaire. Elle apporte un
 * certain nombre d'utilitaire de test
 * 
 * @author David ALEXIS
 * 
 */
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BilanTestNG extends AbstractTestNGSpringContextTests {

	/**
	 * Map des valeurs de test pour les type primitifs
	 */
	private static Map<Class<?>, Object> mapType = new Hashtable<Class<?>, Object>();
	static {

		mapType.put(int.class, 10);
		mapType.put(Integer.class, 10);
		mapType.put(long.class, 11L);
		mapType.put(Long.class, 11L);
		mapType.put(boolean.class, true);
		mapType.put(Boolean.class, true);
		mapType.put(String.class, "str");
		mapType.put(BigDecimal.class, new BigDecimal("12.4"));
		mapType.put(Date.class, new Date());
	}

	/**
	 * Possibilité d'appeler une méthode private ou protected d'une class
	 * 
	 * @param instance
	 *            instance de la classe propriétaire de la méthode
	 * @param method
	 *            Méthode à exécuter
	 * @param params
	 *            Paramètres de la méthode
	 * @return Objet retournée par la méthode privée
	 * @throws InvocationTargetException
	 *             Exception levée à l'appel de la méthode
	 */
	protected Object callPrivateMethod(Object instance, Method method,
			Object... params) throws Throwable {

		boolean access = method.isAccessible();
		method.setAccessible(true);
		Object value;

		try {
			value = method.invoke(instance, params);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		} finally {
			method.setAccessible(access);
		}

		return value;
	}

	/**
	 * Test les méthodes getter / setter d'une propriété d'une instance d'objet
	 * 
	 * @param instance instance de la classe propriétaire de la méthode
	 * @param propName propriété dont les getter/setter sont à tester
	 * @param value Valeur passé en paramètre du test
	 */
	protected void testGetterSetter(Object instance, String propName,
			Object value) {

		// Récupération de getteur "get"
		Method mGetter;
		try {
			mGetter = getDeclaredMethod(instance.getClass(),
					getMethodName("get", propName));
		} catch (NoSuchMethodException e1) {

			// Récupération de getteur "is"
			try {
				mGetter = getDeclaredMethod(instance.getClass(),
						getMethodName("is", propName));
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e1);
			}
		} catch (SecurityException e1) {
			throw new RuntimeException(e1);
		}

		// Paramettre du setter
		Class<?> propClass = mGetter.getReturnType();

		// Récupération du setter
		Method mSetter;
		try {
			mSetter = getDeclaredMethod(instance.getClass(),
					getMethodName("set", propName), propClass);
		} catch (NoSuchMethodException | SecurityException e1) {
			throw new RuntimeException(e1);
		}

		boolean accessSetter = mSetter.isAccessible();
		boolean accessGetter = mGetter.isAccessible();
		mSetter.setAccessible(true);
		mGetter.setAccessible(true);

		try {
			mSetter.invoke(instance, value);

			// Si c'est un primtif on test avec le Equals (car par exemple
			// boolean != Boolean)
			if (propClass.isPrimitive()) {
				String msg = instance.getClass().getName() + "." + propName
						+ " valeur attendue : " + value;
				Assert.assertEquals(value, mGetter.invoke(instance), msg);
			} else {
				String msg = instance.getClass().getName() + "." + propName
						+ " valeur attendue : " + value;
				Assert.assertTrue(value == mGetter.invoke(instance), msg);
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new RuntimeException(e);
		} finally {
			mSetter.setAccessible(accessSetter);
			mGetter.setAccessible(accessGetter);
		}
	}

	protected Method getDeclaredMethod(Class<?> clazz, String methodName,
			Class<?> propClass) throws NoSuchMethodException {

		Method method;

		try {
			method = clazz.getDeclaredMethod(methodName, propClass);
		} catch (NoSuchMethodException e1) {
			if (clazz.getSuperclass() != null)
				return getDeclaredMethod(clazz.getSuperclass(), methodName,
						propClass);
			else
				throw e1;
		}
		return method;

	}

	protected Method getDeclaredMethod(Class<?> clazz, String methodName)
			throws NoSuchMethodException {

		Method method;

		try {
			method = clazz.getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e1) {
			if (clazz.getSuperclass() != null)
				return getDeclaredMethod(clazz.getSuperclass(), methodName);
			else
				throw e1;
		}
		return method;

	}

	/**
	 * Retourne le nom d'une méthode
	 * 
	 * @param prefix
	 *            prefixe de la méthode (is, get, set)
	 * @param property
	 *            Nom de la propriété (du getter ou du setter)
	 * @return Nom du getteur ou du Setteur
	 */
	protected String getMethodName(String prefix, String property) {

		StringBuilder str = new StringBuilder();
		str.append(prefix);
		str.append(property.substring(0, 1).toUpperCase());
		str.append(property.substring(1));

		return str.toString();
	}

	/**
	 * Test l'ensemble des getters / setters d'une class
	 * 
	 * @param clazz Class à tester
	 * @throws Throwable Exception
	 */
	protected void testClassGetterSetter(Class<?> clazz) throws Throwable {
		testClassGetterSetter(clazz, null);
	}

	/**
	 * Test l'ensemble des getters / setters d'une class
	 * 
	 * @param clazz Class à tester
	 * @param mapTypeCustom Map des types particuliers
	 * @throws Throwable Exception
	 */
	protected void testClassGetterSetter(Class<?> clazz,
			Map<Class<?>, Object> mapTypeCustom) throws Throwable {

		Object inst = clazz.newInstance();

		while (clazz != null && !clazz.getName().equals(Object.class.getName())) {
			for (Field item : clazz.getDeclaredFields()) {

				if (!Modifier.isFinal(item.getModifiers())) {

					Object value = null;

					if (mapTypeCustom != null)
						value = mapTypeCustom.get(item.getType());

					if (value == null)
						value = mapType.get(item.getType());

					if (value == null)
						value = item.getType().newInstance();

					if (value == null)
						throw new RuntimeException("Valeur du Bean introuvable");

					testGetterSetter(inst, item.getName(), value);
				}

			}

			clazz = clazz.getSuperclass();
		}
	}
}
