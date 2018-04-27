package nc.noumea.mairie.bilan.energie.core.converter;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Un moyen rapide de récupérer un type supporté
 * 
 * @author Greg Dujardin
 */
public enum NumberType {

	/**
	 * Type Big Décimal
	 */
	BIGDECIMAL(BigDecimal.class) {

		@Override
		public Number convert(final BigDecimal value) {

			return value;
		}
	},

	/**
	 * Type Big Integer
	 */
	BIGINTEGER(BigInteger.class) {

		@Override
		public Number convert(final BigDecimal value) {

			return value.toBigInteger();
		}
	},

	/**
	 * Type Double
	 */
	DOUBLE(Double.class, double.class) {

		@Override
		public Number convert(final BigDecimal value) {

			return value.doubleValue();
		}
	},

	/**
	 * Type Float
	 */
	FLOAT(Float.class, float.class) {

		@Override
		public Number convert(final BigDecimal value) {

			return value.floatValue();
		}
	},

	/**
	 * Type Long
	 */
	LONG(Long.class, long.class) {

		@Override
		public Number convert(final BigDecimal value) {

			return value.longValue();
		}
	},

	/**
	 * Type Integer
	 */
	INTEGER(Integer.class, int.class) {

		@Override
		public Number convert(final BigDecimal value) {

			return value.intValue();
		}
	},

	/**
	 * Type Short
	 */
	SHORT(Short.class, short.class) {

		@Override
		public Number convert(final BigDecimal value) {

			return value.shortValue();
		}
	},

	/**
	 * Type Byte
	 */
	BYTE(Byte.class, byte.class) {

		@Override
		public Number convert(final BigDecimal value) {

			return value.byteValue();
		}
	};

	/**
	 * Type de la class
	 */
	private final Class<?>[] types;

	/**
	 * Constructeur par défaut
	 * 
	 * @param types Type du numérique
	 */
	private NumberType(final Class<?>... types) {

		this.types = types;
	}

	/**
	 * Récupère la valeur du champ type
	 * 
	 * @return type : Class
	 */
	public Class<?>[] types() {

		return this.types;
	}

	/**
	 * Méthode simple de conversion d'un Number
	 * 
	 * @param value Valeur à convertir
	 * @return valeur convertie
	 */
	public abstract Number convert(final BigDecimal value);
}
