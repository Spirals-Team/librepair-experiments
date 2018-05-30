package net.contargo.iris.connection;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import net.contargo.iris.route.RouteType;
import net.contargo.iris.seaport.Seaport;
import net.contargo.iris.terminal.Terminal;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MainRunConnection.class)
public abstract class MainRunConnection_ {

	public static volatile SingularAttribute<MainRunConnection, Seaport> seaport;
	public static volatile SingularAttribute<MainRunConnection, BigDecimal> railElectricDistance;
	public static volatile SingularAttribute<MainRunConnection, BigDecimal> bargeDieselDistance;
	public static volatile SingularAttribute<MainRunConnection, BigDecimal> roadDistance;
	public static volatile SingularAttribute<MainRunConnection, BigDecimal> railDieselDistance;
	public static volatile SingularAttribute<MainRunConnection, Long> id;
	public static volatile SingularAttribute<MainRunConnection, Terminal> terminal;
	public static volatile ListAttribute<MainRunConnection, AbstractSubConnection> subConnections;
	public static volatile SingularAttribute<MainRunConnection, RouteType> routeType;
	public static volatile SingularAttribute<MainRunConnection, Boolean> enabled;

}

