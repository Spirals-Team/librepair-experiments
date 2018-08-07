package com.hedvig.productPricing.pricing;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PricingResult {

	public static enum ResultTypes {TOTAL, ALLRISK, BRF, BASE, OBJECT, TRAVEL};
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	
	Double riskpremie;
	Double bruttopremie_exakt;
	Double bruttopremie_kund;
	Double premie_inc_nyteckning;
	Double premie_inc_rabatt;
	ResultTypes type;

	public void add(PricingResult pr){
		this.riskpremie+=pr.riskpremie;
		this.bruttopremie_exakt+=pr.bruttopremie_exakt;
		this.bruttopremie_kund+=pr.bruttopremie_kund;
		this.premie_inc_nyteckning+=pr.premie_inc_nyteckning;
		this.premie_inc_rabatt+=pr.premie_inc_rabatt;
	}
	
	public static void main(String args[]){
		System.out.println(roundToClosestTen(135.2));
		System.out.println(roundToClosestTen(-1));
		System.out.println(roundToClosestTen(0));
		System.out.println(roundToClosestTen(32));
		System.out.println(roundToClosestTen(157.1239132));
		System.out.println(roundToClosestTen(199));
		System.out.println(roundToClosestTen(200));
		System.out.println("---------------");
		System.out.println(roundToClosestTwentyFive(135.2));
		System.out.println(roundToClosestTwentyFive(-1));
		System.out.println(roundToClosestTwentyFive(0));
		System.out.println(roundToClosestTwentyFive(32));
		System.out.println(roundToClosestTwentyFive(38));
		System.out.println(roundToClosestTwentyFive(157.1239132));
		System.out.println(roundToClosestTwentyFive(199));
		System.out.println(roundToClosestTwentyFive(200));
	}
	
	public String toString(){
		return "riskpremie" + riskpremie
			+ "\nbruttopremie_exakt" + bruttopremie_exakt
			+ "\nbruttopremie_kund" + bruttopremie_kund
			+ "\npremie_inc_nyteckning" + premie_inc_nyteckning
			+ "\npremie_inc_rabatt" + premie_inc_rabatt
			+ "\n --> price per month:" + getTotalPerMonth();
	}

	public PricingResult(){
		riskpremie=0d;
		bruttopremie_exakt=0d;
		bruttopremie_kund=0d;
		premie_inc_nyteckning=0d;
		premie_inc_rabatt=0d;	
	}
	
	// Rounds to closest 10 and subtract 1
	public static Double roundToClosestTen(double input){
		//System.out.println(input + " " + input%100 + " " + (Math.floor(input/100)));
		if(input%10>5)return (Math.ceil(input/10)*10)-1d;
		return (Math.floor(input/10)*10)-1d;
	}
	
	// Rounds to closest 25
	public static Double roundToClosestTwentyFive(double input){
		//System.out.println(input + " " + input%100 + " " + (Math.floor(input/100)));
		if(input%25>12)return (Math.ceil(input/25)*25);
		return (Math.floor(input/25)*25);
	}
	
	public Double getTotalPerMonth(){
		// All calculations are done on monthly basis
		return roundToClosestTen(this.getPremie_inc_rabatt()/12d);
	}
	
	protected Double getRiskpremie() {
		return riskpremie;
	}
	protected void setRiskpremie(Double riskpremie) {
		this.riskpremie = riskpremie;
	}
	protected Double getBruttopremie_exakt() {
		return bruttopremie_exakt;
	}
	protected void setBruttopremie_exakt(Double bruttopremie_exakt) {
		this.bruttopremie_exakt = bruttopremie_exakt;
	}
	protected Double getBruttopremie_kund() {
		return bruttopremie_kund;
	}
	protected void setBruttopremie_kund(Double bruttopremie_kund) {
		this.bruttopremie_kund = bruttopremie_kund;
	}
	protected Double getPremie_inc_nyteckning() {
		return premie_inc_nyteckning;
	}
	protected void setPremie_inc_nyteckning(Double premie_inc_nyteckning) {
		this.premie_inc_nyteckning = premie_inc_nyteckning;
	}
	protected Double getPremie_inc_rabatt() {
		return premie_inc_rabatt;
	}
	protected void setPremie_inc_rabatt(Double premie_inc_rabatt) {
		this.premie_inc_rabatt = premie_inc_rabatt;
	}
	
}
