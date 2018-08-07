package com.hedvig.productPricing.pricing;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.hedvig.productPricing.pricing.PricingResult.ResultTypes;

/**
 * Version 1.0
 * Implementation of Hedvig's base line pricing model.
 * Diff from BaseLine model:
 * - No rounding to nearest hundred to calculate bruttopremie_exakt
 * - Price per month is rounded to closest 9SEK
 * */

public class PricingEngineHedvig_v1 implements PricingEngine, InitializingBean {

    //@Value("${hedvig.product-pricing.files.location:src/main/resources/}")
    private String fileLocation;

	private static Logger log = LoggerFactory.getLogger(PricingEngineHedvig_v1.class);
	private static ArrayList<Tariff> tariffs = new ArrayList<Tariff>();
	private static HashMap<String, Integer> zipzones = new HashMap<String, Integer>();
	private static Boolean fileDataRead = false;
	private Boolean startupComplete = false;

	static enum TravelDestination {EUROPE, NORTH_AMERICA, OTHER};
	static enum ObjectType {MUSIC_INSTRUMENT, WATCH, JEWELRY, CAMERA, ART, OTHER};

	private static double price_factor = 0.79d;

	private static double bas_grundskydd = 430*price_factor;
	private static double grund_grundskydd = 250*price_factor;

	private static double bas_brf = 150*price_factor;
	private static double grund_brf = 100*price_factor;

	private static double bas_allrisk = 265*price_factor;
	private static double grund_allrisk = 125*price_factor;

	private static double bas_resa = 883*price_factor;
	private static double grund_resa = 415*price_factor;

	/*private static double bas_grundskydd = 1;
	private static double grund_grundskydd = 1;

	private static double bas_brf = 1;
	private static double grund_brf = 1;

	private static double bas_allrisk = 1;
	private static double grund_allrisk = 1;

	private static double bas_resa = 1;
	private static double grund_resa = 1;*/

	public static void main(String[] args){
		PricingEngineHedvig_v1 p = new PricingEngineHedvig_v1("src/main/resources/");
	}

	public void TestPricingEngine(){

		if(!fileDataRead){
			throw new RuntimeException("Tariff data is not loaded. Not possible to calculate price");
		}
		log.info("Testing price enginge consitency...");
		PricingQueryBase pq = new PricingQueryBase();
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

		PricingQuote pQuote = new PricingQuote();
		pQuote.setPricingQuery(pq);
		getPrice(pQuote);
		PricingResult pr = pQuote.getPricingResult(PricingResult.ResultTypes.TOTAL);

		startupComplete = true;
	}

	public PricingEngineHedvig_v1(String fileLocation){
		log.info("Instantiating PriceEngineBaseLine ...");
		this.fileLocation = fileLocation;
		//System.out.println(roundToClosestHundred(135.2));
		//System.out.println(roundToClosestHundred(389));
		readData();
		TestPricingEngine();
		log.info("...startup complete:" + startupComplete);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		readData();
	}

	public PricingResult getPrice(PricingQuote pQuote){


		PricingQuery pq = pQuote.getPricingQuery();
        // Base product
		if(pq instanceof PricingQueryBase){
            PricingQueryBase pqb = ((PricingQueryBase) pq);
            if (pqb.getStudentPolicy()) {
                log.info("Query for student product received: " + pqb);
                return createStudentPrice(pqb, pQuote);
            }
			log.info("Query for base product recieved:" + pqb);

			PricingResult priceTotal = new PricingResult();
			priceTotal.type=ResultTypes.TOTAL;

			PricingResult priceBase = getGrundskydd(pqb); // Grundskydd
			priceBase.type=ResultTypes.BASE;
			priceTotal.add(priceBase);
			pQuote.addPricingResult(priceBase);

			PricingResult priceAllrisk = getAllrisk(pqb); // Allrisk
			priceAllrisk.type=ResultTypes.ALLRISK;
			priceTotal.add(priceAllrisk);
			pQuote.addPricingResult(priceAllrisk);

			if(((PricingQueryBase) pq).getBRF()){
				PricingResult priceBrf= getBrf(pqb); // BRF tillägg
				priceBrf.type=ResultTypes.BRF;
				priceTotal.add(priceBrf);
				pQuote.addPricingResult(priceBrf);
			}

			pQuote.addPricingResult(priceTotal);
			return priceTotal;
		}
		return null;
    }

    private PricingResult createStudentPrice(PricingQueryBase pqb, PricingQuote pq) {
        PricingResult priceTotal = new PricingResult();
        priceTotal.type = ResultTypes.TOTAL;
        if (pqb.getBRF()) {
            priceTotal.setPremie_inc_rabatt(1188d);
            priceTotal.setBruttopremie_exakt(1188d);
            priceTotal.setBruttopremie_kund(1188d);
            priceTotal.setPremie_inc_nyteckning(1188d);
            // priceTotal.setRiskpremie(???);
        } else {
            priceTotal.setPremie_inc_rabatt(948d);
            priceTotal.setBruttopremie_exakt(948d);
            priceTotal.setBruttopremie_kund(948d);
            priceTotal.setPremie_inc_nyteckning(948d);
            // priceTotal.setRiskpremie(???);
        }
        pq.addPricingResult(priceTotal);
        return priceTotal;
    }

	// --------------------------------------------------------

	private PricingResult calculateResult(Double factor_total, Double bas_price, Double grund_price, Boolean nyteckningsrabatt, Boolean larm, Boolean sakerhetsdorr){
		PricingResult result = new PricingResult();

		Double riskpremie = factor_total * bas_price;
		// TODO: Validate the correctness of the Excel model and change this accordingly
		Double bruttopremie_exakt = ((riskpremie + grund_price)*getPayment(false, ""))/getRabatt(true, true, true);
		Double bruttopremie_kund = bruttopremie_exakt; //roundToClosestHundred(bruttopremie_exakt);
		Double premie_inc_nyteckning = bruttopremie_kund*getRabatt(nyteckningsrabatt, false, false);
		Double premie_inc_rabatt = premie_inc_nyteckning*getRabatt(false, larm, sakerhetsdorr);

		result.setRiskpremie(riskpremie);
		result.setBruttopremie_exakt(bruttopremie_exakt);
		result.setBruttopremie_kund(bruttopremie_kund);
		result.setPremie_inc_nyteckning(premie_inc_nyteckning);
		result.setPremie_inc_rabatt(premie_inc_rabatt);

		log.info("riskpremie:" + riskpremie +
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

		log.info("Grundskydd:" + factor_Alder + ":" +
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

		log.info("Brftillagg:" + factor_Alder + ":" +
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

		log.info("Allrisk:" + factor_Alder + ":" +
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
		Double bruttopremie_kund = bruttopremie_exakt; //roundToClosestHundred(bruttopremie_exakt);
		Double premie_inc_nyteckning = bruttopremie_kund*getRabatt(pq.getNyteckning(), false, false);
		Double premie_inc_rabatt = premie_inc_nyteckning*getRabatt(false, pq.getLarm(), pq.getSakerhetsdorr());

		result.setBruttopremie_exakt(bruttopremie_exakt);
		result.setBruttopremie_kund(bruttopremie_kund);
		result.setPremie_inc_nyteckning(premie_inc_nyteckning);
		result.setPremie_inc_rabatt(premie_inc_rabatt);

		log.info("bruttopremie_exakt:"+ bruttopremie_exakt +
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

        val csvFile = Paths.get(fileLocation, "tariff.csv");
        val geoFile = Paths.get(fileLocation, "geografi.csv");
        String line = "";
        String cvsSplitBy = ";";

        log.info("Reading pricing data...");

        log.info("...tariffs from: " + csvFile);
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile.toFile()))) {
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
        log.info("...done!");

        log.info("Zip data from: " + geoFile);
        try (BufferedReader br = new BufferedReader(new FileReader(geoFile.toFile()))) {
        	br.readLine(); // Header line
            while ((line = br.readLine()) != null) {

                String[] fields = line.split(cvsSplitBy);
                zipzones.put(fields[0], new Integer(fields[1]));
                //System.out.println(fields[0] + ":" + fields[1]);

            }
            fileDataRead = true;
            log.info("...done!");
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
