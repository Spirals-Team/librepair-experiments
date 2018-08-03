package com.cmpl.web.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity(name = "memberships")
@Table(name = "memberships", indexes = {@Index(name = "IDX_ENTITY", columnList = "entity_id"),
    @Index(name = "IDX_GROUP", columnList = "group_id")})
public class Membership extends BaseEntity {

  @Column(name = "entity_id")
  private Long entityId;
  @Column(name = "group_id")
  private Long groupId;

  public Long getEntityId() {
    return entityId;
  }

  public void setEntityId(Long entityId) {
    this.entityId = entityId;
  }

  public Long getGroupId() {
    return groupId;
  }

  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }
}
