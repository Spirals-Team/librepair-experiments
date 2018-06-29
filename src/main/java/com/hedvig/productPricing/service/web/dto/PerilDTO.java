package com.hedvig.productPricing.service.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hedvig.productPricing.service.query.PerilEntity;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PerilDTO implements Comparable<PerilDTO>{

    public String id;
    public String title;
    public String state;
    public String imageUrl;
    public String description;
    public String longText;
    public Boolean isRemovable;
    
    @JsonIgnore
    public String category;
    
    @JsonIgnore
    public int sortOrder;

    public PerilDTO(){}
    
    public PerilDTO(String id, String title, String state,String imageUrl,String shortText, String longText, Boolean isRemovable){
    	this.id = id;
        this.title = title;
    	this.state = state;
    	this.imageUrl = imageUrl;
    	this.description = shortText;
    	this.longText = longText;
    	this.isRemovable = isRemovable;
    	this.sortOrder = getSortOrder(id);
    }
    
    public PerilDTO(PerilEntity p){
    	this.id = p.id;
    	this.state = p.defaultState;
        this.title = p.title;
    	this.imageUrl = p.imageUrl;
    	this.description = p.shortText;
    	this.longText = p.longText;
    	this.category = p.category;
    	this.sortOrder = getSortOrder(p.id);
    	this.isRemovable = p.isRemovable;
    }
    
    private int getSortOrder(String id){
    	switch(id){
    	case "ME.LEGAL": return 1;
    	case "ME.ASSAULT": return 2;
    	case "ME.IDENTITY": return 3;
    	case "ME.TRAVEL.SICK": return 4;
    	case "ME.TRAVEL.ACCIDENT": return 5;
    	case "ME.TRAVEL.DELAY": return 6;
    	case "ME.TRAVEL.LUGGAGE.DELAY": return 7;
    	case "HOUSE.FIRE": return 8;
    	case "HOUSE.WATER": return 9;
    	case "HOUSE.WEATHER": return 10;
    	case "HOUSE.BREAK-IN": return 11;
    	case "HOUSE.DAMAGE": return 12;
    	case "HOUSE.APPLIANCES": return 13;
    	case "HOUSE.BUGS": return 14;
    	case "STUFF.CARELESS": return 15;
    	case "STUFF.THEFT": return 16;
    	case "STUFF.DAMAGE": return 17;
    	case "STUFF.FIRE": return 18;
    	case "STUFF.WATER": return 19;
    	case "STUFF.WEATHER": return 20;
    	default: return -1;
    	}
    }
    
    @Override
    public int compareTo(PerilDTO p) {
        return this.sortOrder-p.sortOrder;
    }

}
