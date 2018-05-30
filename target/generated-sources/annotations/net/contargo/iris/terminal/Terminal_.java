package net.contargo.iris.terminal;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Terminal.class)
public abstract class Terminal_ extends net.contargo.iris.GeoLocation_ {

	public static volatile SingularAttribute<Terminal, String> name;
	public static volatile SingularAttribute<Terminal, Long> id;
	public static volatile SingularAttribute<Terminal, Region> region;
	public static volatile SingularAttribute<Terminal, Boolean> enabled;
	public static volatile SingularAttribute<Terminal, BigInteger> uniqueId;

}

