package net.contargo.iris.routedatarevision;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import net.contargo.iris.terminal.Terminal;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RouteDataRevision.class)
public abstract class RouteDataRevision_ {

	public static volatile SingularAttribute<RouteDataRevision, String> country;
	public static volatile SingularAttribute<RouteDataRevision, BigDecimal> tollDistanceOneWayInKilometer;
	public static volatile SingularAttribute<RouteDataRevision, String> city;
	public static volatile SingularAttribute<RouteDataRevision, BigDecimal> truckDistanceOneWayInKilometer;
	public static volatile SingularAttribute<RouteDataRevision, BigDecimal> radiusInMeter;
	public static volatile SingularAttribute<RouteDataRevision, BigDecimal> latitude;
	public static volatile SingularAttribute<RouteDataRevision, String> postalCode;
	public static volatile SingularAttribute<RouteDataRevision, Terminal> terminal;
	public static volatile SingularAttribute<RouteDataRevision, Date> validFrom;
	public static volatile SingularAttribute<RouteDataRevision, String> comment;
	public static volatile SingularAttribute<RouteDataRevision, Long> id;
	public static volatile SingularAttribute<RouteDataRevision, BigDecimal> airlineDistanceInKilometer;
	public static volatile SingularAttribute<RouteDataRevision, BigDecimal> longitude;
	public static volatile SingularAttribute<RouteDataRevision, Date> validTo;
	public static volatile SingularAttribute<RouteDataRevision, String> cityNormalized;

}

