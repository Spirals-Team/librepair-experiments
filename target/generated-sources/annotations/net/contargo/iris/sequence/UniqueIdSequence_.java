package net.contargo.iris.sequence;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UniqueIdSequence.class)
public abstract class UniqueIdSequence_ {

	public static volatile SingularAttribute<UniqueIdSequence, BigInteger> nextId;
	public static volatile SingularAttribute<UniqueIdSequence, String> entityName;
	public static volatile SingularAttribute<UniqueIdSequence, Long> id;

}

