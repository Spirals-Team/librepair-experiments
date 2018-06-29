package com.hedvig.productPricing.pricing;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.hedvig.productPricing.pricing.PricingEngineBaseLine.TravelDestination;

@Entity
@DiscriminatorValue("travel")
public class PricingQueryTravel extends PricingQuery{
	private Integer Alder;
	private TravelDestination Resmal; 
	private Integer Antper;
	private Integer Antaldagar;
	private Boolean Nyteckning;
	private Boolean Student;
	
	public String toString(){
		return 	"Alder:" + this.Alder
		+ "\nResmal:" + this.Resmal
		+ "\nAntper:" + this.Antper
		+ "\nAntaldagar:" + this.Antaldagar
		+ "\nNyteckning:" + this.Nyteckning
		+ "\nStudent:" + this.Student;
	}
	
	public PricingQueryTravel() {
		super();
	}
	
	public Boolean getStudent() {
		return Student;
	}

	public void setStudent(Boolean student) {
		Student = student;
	}

	public Integer getAlder() {
		return Alder;
	}
	public void setAlder(Integer alder) {
		Alder = alder;
	}
	public TravelDestination getResmal() {
		return Resmal;
	}
	public void setResmal(TravelDestination resmal) {
		Resmal = resmal;
	}
	public Integer getAntper() {
		return Antper;
	}
	public void setAntper(Integer antper) {
		Antper = antper;
	}
	public Integer getAntaldagar() {
		return Antaldagar;
	}
	public void setAntaldagar(Integer antaldagar) {
		Antaldagar = antaldagar;
	}
	
	public Boolean getNyteckning() {
		return Nyteckning;
	}
	public void setNyteckning(Boolean nyteckning) {
		Nyteckning = nyteckning;
	}

}
