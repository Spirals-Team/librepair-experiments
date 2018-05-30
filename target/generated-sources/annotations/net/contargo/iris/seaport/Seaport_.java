package net.contargo.iris.seaport;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Seaport.class)
public abstract class Seaport_ extends net.contargo.iris.GeoLocation_ {

	public static volatile SingularAttribute<Seaport, String> name;
	public static volatile SingularAttribute<Seaport, Long> id;
	public static volatile SingularAttribute<Seaport, Boolean> enabled;
	public static volatile SingularAttribute<Seaport, BigInteger> uniqueId;

}

