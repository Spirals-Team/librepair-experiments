package com.cmpl.web.core.carousel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

@Entity(name = "carousel")
@Table(name = "carousel")
public class Carousel extends BaseEntity {

  @Column(name = "name", unique = true)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
