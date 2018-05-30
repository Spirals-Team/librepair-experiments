package net.contargo.iris.connection;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import net.contargo.iris.terminal.Terminal;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AbstractSubConnection.class)
public abstract class AbstractSubConnection_ {

	public static volatile SingularAttribute<AbstractSubConnection, BigDecimal> railElectricDistance;
	public static volatile SingularAttribute<AbstractSubConnection, BigDecimal> bargeDieselDistance;
	public static volatile SingularAttribute<AbstractSubConnection, BigDecimal> railDieselDistance;
	public static volatile SingularAttribute<AbstractSubConnection, Long> id;
	public static volatile SingularAttribute<AbstractSubConnection, Terminal> terminal;
	public static volatile SingularAttribute<AbstractSubConnection, MainRunConnection> parentConnection;

}

