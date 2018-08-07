package com.hedvig.productPricing.pricing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.hedvig.productPricing.pricing.PricingResult.ResultTypes;

/**
 * Implementation of Hedvig's base line pricing model
 * */

public class PricingEngineBaseLine implements PricingEngine, InitializingBean {

    @Value("${hedvig.product-pricing.files.location:src/main/resources/}")
    private String fileLocation;

	private static Logger log = LoggerFactory.getLogger(PricingEngineBaseLine.class);
	private static ArrayList<Tariff> tariffs = new ArrayList<Tariff>();
	private static HashMap<String, Integer> zipzones = new HashMap<String, Integer>();

	static enum TravelDestination {EUROPE, NORTH_AMERICA, OTHER};
	static enum ObjectType {MUSIC_INSTRUMENT, WATCH, JEWELRY, CAMERA, ART, OTHER};

	private static Boolean fileDataRead = false;
	private static Boolean startupComplete = false;

	private static double bas_grundskydd = 430;
	private static double grund_grundskydd = 250;

	private static double bas_brf = 150;
	private static double grund_brf = 100;

	private static double bas_allrisk = 265;
	private static double grund_allrisk = 125;

	private static double bas_resa = 883;
	private static double grund_resa = 415;

	/*private static double bas_grundskydd = 1;
	private static double grund_grundskydd = 1;

	private static double bas_brf = 1;
	private static double grund_brf = 1;

	private static double bas_allrisk = 1;
	private static double grund_allrisk = 1;

	private static double bas_resa = 1;
	private static double grund_resa = 1;*/

	public static void main(String[] args){
		PricingEngineBaseLine p = new PricingEngineBaseLine();
	}

	public PricingEngineBaseLine(){
		log.info("Instantiating PriceEngineBaseLine ...");
		System.out.println(roundToClosestHundred(135.2));
		System.out.println(roundToClosestHundred(389));

		/*PricingQueryBase pq = new PricingQueryBase();
		pq.setAlder(39);
		pq.setAntper(4);
		pq.setFbelopp(1000000);
		pq.setBetalningsanmarkning(false);
		pq.setBoyta(130);
		pq.setBRF(true);
		pq.setLarm(false);
		pq.setSakerhetsdorr(true);
		pq.setNyteckning(true);
		pq.setGeografi("12130");
		pq.setVaning(0);
		pq.setDuration(0);
		pq.setSjrisk(1500);
		pq.setHyrdeg(false);
		PricingResult pr = getPrice(pq);
		*/
		/*this.Alder = 39;
		this.Antper = 4;
		this.Fbelopp = 1000000;
		this.Geografi = "12130";
		this.Sjrisk = 1500;
		this.Vaning = 0;
		this.Duration = 0;
		getGrundskydd();*/

		//getResa(55,TravelDestination.EUROPE, 2, 254);
		/*
		System.out.println("Riskpremie:" + pr.getRiskpremie()
		+ "\nBruttopremie exakt:" + pr.getBruttopremie_exakt()
		+ "\nBruttopremie kund:" + pr.getBruttopremie_kund()
		+ "\nPremie ink nyteckning:" + pr.getPremie_inc_nyteckning()
		+ "\nPremie ink rabatter:" + pr.getPremie_inc_rabatt()
		);
		*/
		log.info("... done");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		readData();
	}

	public PricingResult getPrice(PricingQuote pQuote){


		PricingQuery pq = pQuote.getPricingQuery();
		// Base product
		if(pq instanceof PricingQueryBase){
			log.info("Query for base product recieved:" + (PricingQueryBase)pq);

			PricingResult priceTotal = new PricingResult();
			priceTotal.type=ResultTypes.TOTAL;

			PricingResult priceBase = getGrundskydd((PricingQueryBase)pq); // Grundskydd
			priceBase.type=ResultTypes.BASE;
			priceTotal.add(priceBase);

			PricingResult priceAllrisk = getAllrisk((PricingQueryBase)pq); // Allrisk
			priceAllrisk.type=ResultTypes.ALLRISK;
			priceTotal.add(priceAllrisk);

			if(((PricingQueryBase) pq).getBRF()){
				PricingResult priceBrf= getBrf((PricingQueryBase)pq); // BRF tillägg
				priceBrf.type=ResultTypes.BRF;
				priceTotal.add(priceBrf);
			}

			return priceTotal;
		}
		return null;
	}

	// --------------------------------------------------------

	private PricingResult calculateResult(Double factor_total, Double bas_price, Double grund_price, Boolean nyteckningsrabatt, Boolean larm, Boolean sakerhetsdorr){
		PricingResult result = new PricingResult();

		Double riskpremie = factor_total * bas_price;
		// TODO: Validate the correctness of the Excel model and change this accordingly
		Double bruttopremie_exakt = ((riskpremie + grund_price)*getPayment(false, ""))/getRabatt(true, true, true);
		Double bruttopremie_kund = roundToClosestHundred(bruttopremie_exakt);
		Double premie_inc_nyteckning = bruttopremie_kund*getRabatt(nyteckningsrabatt, false, false);
		Double premie_inc_rabatt = premie_inc_nyteckning*getRabatt(false, larm, sakerhetsdorr);

		result.setRiskpremie(riskpremie);
		result.setBruttopremie_exakt(bruttopremie_exakt);
		result.setBruttopremie_kund(bruttopremie_kund);
		result.setPremie_inc_nyteckning(premie_inc_nyteckning);
		result.setPremie_inc_rabatt(premie_inc_rabatt);

		System.out.println("riskpremie:" + riskpremie +
				"\nbruttopremie_exakt:"+ bruttopremie_exakt +
				"\nbruttopremie_kund:" + bruttopremie_kund +
				"\npremie_inc_nyteckning:" + premie_inc_nyteckning +
				"\npremie_inc_rabatt:" + premie_inc_rabatt);

		return result;
	}

	private PricingResult getGrundskydd(PricingQueryBase pq){
		Integer zipzone = getZipZone(pq.getGeografi());
		String key = "Grundskydd";

		Double factor_Alder = getValue(key, "Alder", pq.getAlder());
		Double factor_Boyta = getValue(key, "Boyta", pq.getBoyta());
		Double factor_Fbelopp = getValue(key, "Fbelopp", pq.getFbelopp());
		Double factor_Antper = getValue(key, "Antper", pq.getAntper());
		Double factor_Geografi = getValue(key, "Geografi", zipzone);
		Double factor_Vaning = getValue(key, "Vaning", pq.getVaning());
		Double factor_Duration = getValue(key, "Duration", pq.getDuration());
		Double factor_Sjrisk = getValue(key, "Sjrisk", pq.getSjrisk());

		Double factor_total = 1 * factor_Alder *
				factor_Boyta *
				factor_Fbelopp *
				factor_Antper *
				factor_Geografi *
				factor_Vaning *
				factor_Duration *
				factor_Sjrisk;

		System.out.println("Grundskydd:" + factor_Alder + ":" +
				factor_Boyta + ":" +
				factor_Fbelopp + ":" +
				factor_Antper + ":" +
				factor_Geografi + ":" +
				factor_Vaning + ":" +
				factor_Duration + ":" +
				factor_Sjrisk + " --> " + factor_total);

		return calculateResult(factor_total, bas_grundskydd, grund_grundskydd, pq.getNyteckning(), pq.getLarm(), pq.getSakerhetsdorr());

	}

	public PricingResult getBrf(PricingQueryBase pq){
		Integer zipzone = getZipZone(pq.getGeografi());
		String key = "Bostadsrattstillagg";

		Double factor_Alder = getValue(key, "Alder", pq.getAlder());
		Double factor_Boyta = getValue(key, "Boyta", pq.getBoyta());
		Double factor_Fbelopp = getValue(key, "Fbelopp", pq.getFbelopp());
		Double factor_Antper = getValue(key, "Antper", pq.getAntper());
		Double factor_Geografi = getValue(key, "Geografi", zipzone);
		Double factor_Vaning = getValue(key, "Vaning", pq.getVaning());
		Double factor_Duration = getValue(key, "Duration", pq.getDuration());
		Double factor_Sjrisk = getValue(key, "Sjrisk", pq.getSjrisk());

		Double factor_total = 1 * factor_Alder *
				factor_Boyta *
				factor_Fbelopp *
				factor_Antper *
				factor_Geografi *
				factor_Vaning *
				factor_Duration *
				factor_Sjrisk;

		System.out.println("Brftillagg:" + factor_Alder + ":" +
				factor_Boyta + ":" +
				factor_Fbelopp + ":" +
				factor_Antper + ":" +
				factor_Geografi + ":" +
				factor_Vaning + ":" +
				factor_Duration + ":" +
				factor_Sjrisk + " --> " + factor_total);

		return calculateResult(factor_total, bas_brf, grund_brf, pq.getNyteckning(), pq.getLarm(), pq.getSakerhetsdorr());

	}

	public PricingResult getAllrisk(PricingQueryBase pq){
		Integer zipzone = getZipZone(pq.getGeografi());
		String key = "Allrisk";

		Double factor_Alder = getValue(key, "Alder", pq.getAlder());
		Double factor_Boyta = getValue(key, "Boyta", pq.getBoyta());
		Double factor_Fbelopp = getValue(key, "Fbelopp", pq.getFbelopp());
		Double factor_Antper = getValue(key, "Antper", pq.getAntper());
		Double factor_Geografi = getValue(key, "Geografi", zipzone);
		Double factor_Vaning = getValue(key, "Vaning", pq.getVaning());
		Double factor_Duration = getValue(key, "Duration", pq.getDuration());
		Double factor_Sjrisk = getValue(key, "Sjrisk", pq.getSjrisk());
		Double factor_Hyrdeg = getValue(key, "Hyrdeg", pq.getHyrdeg()?1:0); // Bara om man hyr i andra hand

		Double factor_total = 1 * factor_Alder *
				factor_Boyta *
				factor_Fbelopp *
				factor_Antper *
				factor_Geografi *
				factor_Vaning *
				factor_Duration *
				factor_Sjrisk *
				factor_Hyrdeg;

		System.out.println("Allrisk:" + factor_Alder + ":" +
				factor_Boyta + ":" +
				factor_Fbelopp + ":" +
				factor_Antper + ":" +
				factor_Geografi + ":" +
				factor_Vaning + ":" +
				factor_Duration + ":" +
				factor_Sjrisk + ":" +
				factor_Hyrdeg + " --> " + factor_total);

		return calculateResult(factor_total, bas_allrisk, grund_allrisk, pq.getNyteckning(), pq.getLarm(), pq.getSakerhetsdorr());
	}

	public Integer getZipZone(String geografi){
		Integer zipzone = zipzones.get(geografi);
		if(zipzone==null){
			zipzone=1;
		}
		return zipzone;
	}

	public PricingResult getObjectInsurance(PricingQueryObject pq){
		PricingResult result = new PricingResult();

		Double factor_total = 0.02d;
		switch(pq.getObjectType()){
			case MUSIC_INSTRUMENT: factor_total = 0.02d; break;
			case WATCH: factor_total = 0.025d; break;
			case JEWELRY: factor_total = 0.025d; break;
			case CAMERA: factor_total = 0.03d; break;
			case ART: factor_total = 0.015d; break;
		}

		Double bruttopremie_exakt = factor_total * pq.getObjvarde()*getPayment(false, "");
		Double bruttopremie_kund = roundToClosestHundred(bruttopremie_exakt);
		Double premie_inc_nyteckning = bruttopremie_kund*getRabatt(pq.getNyteckning(), false, false);
		Double premie_inc_rabatt = premie_inc_nyteckning*getRabatt(false, pq.getLarm(), pq.getSakerhetsdorr());

		result.setBruttopremie_exakt(bruttopremie_exakt);
		result.setBruttopremie_kund(bruttopremie_kund);
		result.setPremie_inc_nyteckning(premie_inc_nyteckning);
		result.setPremie_inc_rabatt(premie_inc_rabatt);

		System.out.println("bruttopremie_exakt:"+ bruttopremie_exakt +
				"\nbruttopremie_kund:" + bruttopremie_kund +
				"\npremie_inc_nyteckning:" + premie_inc_nyteckning +
				"\npremie_inc_rabatt:" + premie_inc_rabatt);

		return result;
	}

	public PricingResult getResa(PricingQueryTravel pq){

		int[][] factors = {{4,6,10,18},{11,16,27,43},{7,8,15,25}};

		int age_bracket = 3; // 61+
		if(pq.getAlder()<=25){
			age_bracket=0;
		}else if(pq.getAlder()<=30){
			age_bracket=21;
		}else if(pq.getAlder()<=60){
			age_bracket=2;
		}

		int destination_factor = 1;

		switch(pq.getResmal()){
			case EUROPE: destination_factor = factors[0][age_bracket]; break;
			case NORTH_AMERICA: destination_factor = factors[1][age_bracket]; break;
			case OTHER: destination_factor = factors[2][age_bracket]; break;
		}

		Double factor_total = destination_factor * ((double)pq.getAntaldagar()/365d);
		if(pq.getAntper()>1)factor_total *=2;

		return calculateResult(factor_total, bas_resa, grund_resa, pq.getNyteckning(), false, false);
	}


	// Rabatter
	public Double getRabatt(Boolean Nyteckning, Boolean Larm, Boolean Sakerhetsdorr){
		double factor = 1d;
		if(Nyteckning)factor*=0.9d;
		if(Larm)factor*=0.95d;
		if(Sakerhetsdorr)factor*=0.95d;
		return factor;
	}

	// Betalningsterming + betalningsanmärkning
	public Double getPayment(Boolean Betalningsanmarkning, String PaymentType){
		double factor = 1d;
		if(Betalningsanmarkning)factor*=1.5d;

		switch(PaymentType){
		case "PG_6m" : factor*=1.03d; break;
		case "PG_3m" : factor*=1.06d; break;
		}

		return factor;
	}

	// Rounds to closest 100 and subtract 1
	public Double roundToClosestHundred(double input){
		//System.out.println(input + " " + input%100 + " " + (Math.floor(input/100)));
		if(input%100>50)return (Math.ceil(input/100)*100)-1d;
		return (Math.floor(input/100)*100)-1d;
	}

	public double getValue(String product, String variable, int value){
		for(Tariff t : tariffs){
			if(t.product.equals(product) && t.variable.equals(variable) && t.lowValue<=value && t.highValue>=value)return t.effect;
		}
		return 1;
	}

	private void readData(){

        String csvFile = fileLocation + "tariff.csv";
        String geoFile = fileLocation + "geografi.csv";
        String line = "";
        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	br.readLine(); // Header line
            while ((line = br.readLine()) != null) {

                String[] fields = line.split(cvsSplitBy);
                Tariff t = new Tariff(fields[1], fields[2],new Double(fields[4].replace(",", ".")),new Integer(fields[5]), new Integer(fields[6]));
                tariffs.add(t);
                //System.out.println(t);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(geoFile))) {
        	br.readLine(); // Header line
            while ((line = br.readLine()) != null) {

                String[] fields = line.split(cvsSplitBy);
                zipzones.put(fields[0], new Integer(fields[1]));
                //System.out.println(fields[0] + ":" + fields[1]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

	@Override
	public Boolean isStartupComplete() {
		return this.startupComplete;
	}

	@Override
	public Boolean isFileDataRead() {
		return this.fileDataRead;
	}

}
