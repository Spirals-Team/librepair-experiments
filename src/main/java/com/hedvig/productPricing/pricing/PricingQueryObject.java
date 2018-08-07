package com.hedvig.productPricing.pricing;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.hedvig.productPricing.pricing.PricingEngineBaseLine.ObjectType;

@Entity
@DiscriminatorValue("object")
public class PricingQueryObject extends PricingQuery{

	private ObjectType objectType;
	private Double Objvarde;
	private Double Alder;
	private String Geografi;
	private Double Inkomst;
	private Boolean Student;
	
	public String toString(){
		return 	"objectType:" + this.objectType
				+ "\nObjvarde:" + this.Objvarde
				+ "\nAlder:" + this.Alder
				+ "\nGeografi:" + this.Geografi
				+ "\nInkomst:" + this.Inkomst
				+ "\nStudent:" + this.Student;
	}
	
	public PricingQueryObject() {
		super();
	}
	
	public Boolean getStudent() {
		return Student;
	}

	public void setStudent(Boolean student) {
		Student = student;
	}

	public Boolean getNyteckning() {
		return Nyteckning;
	}
	public void setNyteckning(Boolean nyteckning) {
		Nyteckning = nyteckning;
	}
	public Boolean getLarm() {
		return Larm;
	}
	public void setLarm(Boolean larm) {
		Larm = larm;
	}
	public Boolean getSakerhetsdorr() {
		return Sakerhetsdorr;
	}
	public void setSakerhetsdorr(Boolean sakerhetsdorr) {
		Sakerhetsdorr = sakerhetsdorr;
	}
	private Boolean Nyteckning;
	private Boolean Larm;
	private Boolean Sakerhetsdorr;
	
	public ObjectType getObjectType() {
		return objectType;
	}
	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}
	public Double getObjvarde() {
		return Objvarde;
	}
	public void setObjvarde(Double objvarde) {
		Objvarde = objvarde;
	}
	public Double getAlder() {
		return Alder;
	}
	public void setAlder(Double alder) {
		Alder = alder;
	}
	public String getGeografi() {
		return Geografi;
	}
	public void setGeografi(String geografi) {
		Geografi = geografi;
	}
	public Double getInkomst() {
		return Inkomst;
	}
	public void setInkomst(Double inkomst) {
		Inkomst = inkomst;
	}

}
