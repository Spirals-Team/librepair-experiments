package net.contargo.iris.address.staticsearch;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StaticAddress.class)
public abstract class StaticAddress_ extends net.contargo.iris.GeoLocation_ {

	public static volatile SingularAttribute<StaticAddress, String> country;
	public static volatile SingularAttribute<StaticAddress, String> hashKey;
	public static volatile SingularAttribute<StaticAddress, String> city;
	public static volatile SingularAttribute<StaticAddress, String> postalcode;
	public static volatile SingularAttribute<StaticAddress, String> suburb;
	public static volatile SingularAttribute<StaticAddress, Long> id;
	public static volatile SingularAttribute<StaticAddress, String> suburbNormalized;
	public static volatile SingularAttribute<StaticAddress, BigInteger> uniqueId;
	public static volatile SingularAttribute<StaticAddress, String> cityNormalized;

}

