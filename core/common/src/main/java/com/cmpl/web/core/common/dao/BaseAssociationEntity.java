package com.cmpl.web.core.common.dao;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseAssociationEntity<A extends BaseEntity, B extends BaseEntity> extends BaseEntity {

  public abstract A getA();

  public abstract void setA(A a);

  public abstract B getB();

  public abstract void setB(B b);

}
