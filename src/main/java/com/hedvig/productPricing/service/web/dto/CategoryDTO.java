package com.hedvig.productPricing.service.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.hedvig.productPricing.service.query.PerilEntity;

public class CategoryDTO {

    public String title;
    public String iconUrl;
    public ArrayList<PerilDTO> perils;
    public String description;

    public CategoryDTO(){
    	this.perils = new ArrayList<PerilDTO>();
    }

    public CategoryDTO(List<PerilEntity> pList){
    	ArrayList<PerilDTO> perils = new ArrayList<PerilDTO>();
    	for(PerilEntity p : pList){
    		perils.add(new PerilDTO(p));
    	}
    	this.perils = perils;
    	setSummary();
    }

    public void setSummary(){
    	/*
    	 * Adding a category description which is a summary of all perils, comma separated and ... truncated when longer than 50 characters
    	 * */
    	// Collections.sort(perils); // Make them appear in same order
    	// String categorySummary = "";
    	// for(PerilDTO p : perils)categorySummary+=(p.title + ", ");
    	// this.description = categorySummary.substring(0, Math.max(0,categorySummary.length() - 2));
		// if(this.description.length()>50)this.description = (this.description.substring(0, 50) + "...");
		this.description = "försäkras för";
    }


}
