package com.hedvig.productPricing.pricing;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("base")
public class PricingQueryBase extends PricingQuery{

	private Integer Alder;
	private Integer Boyta;
	private Integer Fbelopp;
	private Integer Antper;
	private String Geografi;
	private Integer Vaning;
	private Integer Duration;
	private Integer Sjrisk;
	private Boolean Hyrdeg;
	private Boolean Nyteckning;
	private Boolean Larm;
	private Boolean Sakerhetsdorr;
	private Boolean Betalningsanmarkning;
	private String PaymentType;
	private Boolean BRF = true;
    private Boolean Student = false;
    private Boolean studentPolicy = false;

	public String toString(){
		return 	"Alder" + this.Alder
		+ "\nFbelopp:" + this.Fbelopp
		+ "\nAntper:" + this.Antper
		+ "\nGeografi:" + this.Geografi
		+ "\nVaning:" + this.Vaning
		+ "\nDuration:" + this.Duration
		+ "\nSjrisk:" + this.Sjrisk
		+ "\nHyrdeg:" + this.Hyrdeg
		+ "\nNyteckning:" + this.Nyteckning
		+ "\nLarm:" + this.Larm
		+ "\nSakerhetsdorr:" + this.Sakerhetsdorr
		+ "\nBetalningsanmarkning:" + this.Betalningsanmarkning
		+ "\nPaymentType:" + this.PaymentType
		+ "\nBRF:" + this.BRF
		+ "\nStudent:" + this.Student;
	}

	public PricingQueryBase(){
		super();
	}

	public Boolean getBRF() {
		return BRF;
	}
	public void setBRF(Boolean bRF) {
		BRF = bRF;
	}
	public Integer getAlder() {
		return Alder;
	}
	public Boolean getStudent() {
		return Student;
	}

	public void setStudent(Boolean student) {
		Student = student;
	}

	public void setAlder(Integer alder) {
		Alder = alder;
	}
	public Integer getBoyta() {
		return Boyta;
	}
	public void setBoyta(Integer boyta) {
		Boyta = boyta;
	}
	public Integer getFbelopp() {
		return Fbelopp;
	}
	public void setFbelopp(Integer fbelopp) {
		Fbelopp = fbelopp;
	}
	public Integer getAntper() {
		return Antper;
	}
	public void setAntper(Integer antper) {
		Antper = antper;
	}
	public String getGeografi() {
		return Geografi;
	}
	public void setGeografi(String geografi) {
		Geografi = geografi;
	}
	public Integer getVaning() {
		return Vaning;
	}
	public void setVaning(Integer vaning) {
		if(vaning < 0) {
			vaning = 0;
		}else if(vaning > 20) {
			vaning = 20;
		}
		Vaning = vaning;
	}
	public Integer getDuration() {
		return Duration;
	}
	public void setDuration(Integer duration) {
		Duration = duration;
	}
	public Integer getSjrisk() {
		return Sjrisk;
	}
	public void setSjrisk(Integer sjrisk) {
		Sjrisk = sjrisk;
	}
	public Boolean getHyrdeg() {
		return Hyrdeg;
	}
	public void setHyrdeg(Boolean hyrdeg) {
		Hyrdeg = hyrdeg;
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
	public Boolean getBetalningsanmarkning() {
		return Betalningsanmarkning;
	}
	public void setBetalningsanmarkning(Boolean betalningsanmarkning) {
		Betalningsanmarkning = betalningsanmarkning;
	}
	public String getPaymentType() {
		return PaymentType;
	}
	public void setPaymentType(String paymentType) {
		PaymentType = paymentType;
    }

    public void setStudentPolicy(Boolean studentPolicy) {
        this.studentPolicy = studentPolicy;
    }

    public Boolean getStudentPolicy() {
        return studentPolicy;
    }
}
