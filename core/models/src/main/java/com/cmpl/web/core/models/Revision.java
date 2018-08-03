package com.cmpl.web.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Entity(name = "Revinfo")
@Table(name = "REVINFO")
@RevisionEntity
public class Revision {

  @Id
  @GeneratedValue
  @Column(name = "REV")
  @RevisionNumber
  private int id;

  @Column(name = "REVTSTMP")
  @RevisionTimestamp
  private long timestamp;

  private String user;
}
