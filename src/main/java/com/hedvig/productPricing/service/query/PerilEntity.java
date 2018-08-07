package com.hedvig.productPricing.service.query;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hedvig.productPricing.service.web.dto.PerilDTO;

@Entity
public class PerilEntity {

	private static Logger log = LoggerFactory.getLogger(PerilEntity.class);
	
    @Id
    public String id;
    public String defaultState;
    public String title; // Name of peril
    
	@Column(length=10485760)
    public String shortText;
	
    public String longText;
    public String imageUrl;
    public Boolean isRemovable;

    public String category;

	public PerilEntity(PerilDTO p) {
	    this.id = p.id;
	    this.defaultState = p.state;
	    this.title = p.title;
	    this.imageUrl = p.imageUrl;
	    this.shortText = p.description;
	    this.longText = p.longText;
	}

	public PerilEntity() {
		// TODO Auto-generated constructor stub
	}
}
