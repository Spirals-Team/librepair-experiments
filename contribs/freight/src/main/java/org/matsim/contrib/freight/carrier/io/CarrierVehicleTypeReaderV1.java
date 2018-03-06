package org.matsim.contrib.freight.carrier.io;

import java.util.Stack;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.contrib.freight.carrier.CarrierVehicleType;
import org.matsim.contrib.freight.carrier.CarrierVehicleType.VehicleCostInformation;
import org.matsim.contrib.freight.carrier.CarrierVehicleTypes;
import org.matsim.core.utils.io.MatsimXmlParser;
import org.matsim.vehicles.EngineInformation;
import org.matsim.vehicles.EngineInformation.FuelType;
import org.matsim.vehicles.EngineInformationImpl;
import org.matsim.vehicles.VehicleType;
import org.xml.sax.Attributes;

/**
 * Reader reading carrierVehicleTypes from an xml-file.
 * 
 * This is the original version from sschroeder. Only moved to .io package and renamed to V1. kturner feb/18
 * 
 * @author sschroeder
 *
 */
public class CarrierVehicleTypeReaderV1 extends MatsimXmlParser {
	
	private static Logger logger = Logger.getLogger(CarrierVehicleTypeReaderV1.class);
	
	private CarrierVehicleTypes carrierVehicleTypes;

	private Id<VehicleType> currentTypeId;

	private String currentDescription;

	

//	private Integer currentCap;

	private VehicleCostInformation currentVehicleCosts;

	private EngineInformation currentEngineInfo;

	private String currentCapacity;
	
	private String maxVelo;

	public CarrierVehicleTypeReaderV1(CarrierVehicleTypes carrierVehicleTypes) {
		super();
		this.carrierVehicleTypes = carrierVehicleTypes;
	}
	

	@Override
	public void startTag(String name, Attributes atts, Stack<String> context) {
		if(name.equals("vehicleType")){
			this.currentTypeId = Id.create(atts.getValue("id"), VehicleType.class);
		}
		if(name.equals("allowableWeight")){
			String weight = atts.getValue("weight");
			parseDouble(weight);
		}
		if(name.equals("engineInformation")){
			String fuelType = atts.getValue("fuelType");
			String gasConsumption = atts.getValue("gasConsumption");
			EngineInformation engineInfo = new EngineInformationImpl(parseFuelType(fuelType), parseDouble(gasConsumption));
			this.currentEngineInfo = engineInfo;
		}
		
		if(name.equals("costInformation")){
			String fix = atts.getValue("fix");
			String perMeter = atts.getValue("perMeter");
			String perSecond = atts.getValue("perSecond");
			if(fix == null || perMeter == null || perSecond == null) throw new IllegalStateException("cannot read costInformation correctly. probably the paramName was written wrongly");
			VehicleCostInformation vehicleCosts = new VehicleCostInformation(parseDouble(fix), parseDouble(perMeter), parseDouble(perSecond));
			this.currentVehicleCosts = vehicleCosts;
		}
	}

	private FuelType parseFuelType(String fuelType) {
		if(fuelType.equals(FuelType.diesel.toString())){
			return FuelType.diesel;
		}
		else if(fuelType.equals(FuelType.electricity.toString())){
			return FuelType.electricity;
		}
		else if(fuelType.equals(FuelType.gasoline.toString())){
			return FuelType.gasoline;
		}
		throw new IllegalStateException("fuelType " + fuelType + " is not supported");
	}

	private double parseDouble(String weight) {
		return Double.parseDouble(weight);
	}

	@Override
	public void endTag(String name, String content, Stack<String> context) {
		if(name.equals("description")){
			this.currentDescription = content;
		}
		if(name.equals("capacity")){
			this.currentCapacity = content;
		}
		if(name.equals("maxVelocity")){
			this.maxVelo = content;
		}
		if(name.equals("vehicleType")){
			CarrierVehicleType.Builder typeBuilder = CarrierVehicleType.Builder.newInstance(currentTypeId);
			if(currentDescription != null) typeBuilder.setDescription(currentDescription);
//			if(currentWeight != null) vehType.setAllowableTotalWeight(currentWeight);
//			if(currentCap != null) vehType.setFreightCapacity(currentCap);
			if(currentVehicleCosts != null) typeBuilder.setVehicleCostInformation(currentVehicleCosts);
			if(currentEngineInfo != null) typeBuilder.setEngineInformation(currentEngineInfo);
			if(currentCapacity != null) typeBuilder.setCapacity(Integer.parseInt(currentCapacity));
			if(maxVelo != null) typeBuilder.setMaxVelocity(Double.parseDouble(maxVelo));
			CarrierVehicleType vehType = typeBuilder.build();
			carrierVehicleTypes.getVehicleTypes().put(vehType.getId(), vehType);
			reset();
		}
		
	}

	private void reset() {
		currentTypeId = null;
		currentDescription = null;
		currentVehicleCosts = null;
		currentEngineInfo = null;
	}

}
