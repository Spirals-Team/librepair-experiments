package model;

import java.util.ArrayList;

import model.device.roles.Sniffer;
import org.pcap4j.util.MacAddress;

public class CapturasService {
	
	ArrayList<Captura> capturas;	//esto está mal pero ehm
	
	public CapturasService(ArrayList<Captura> capturas){
		
		this.capturas=capturas;
	}
	
	public ArrayList<MacAddress> getMacAddressesDetectedBy(Sniffer sniffer){
		
		ArrayList<MacAddress> macAddresses = new ArrayList<MacAddress>();
		
		for (Captura c : capturas){
			if(c.getSniffer().equals(sniffer)){	
				for(Package p : c.getPaquetes()){
//					macAddresses.add(p.getMacAddress());   falta mergear el objeto package y MacAddress a development
				}
			}
		}
		
		return macAddresses;
		
	}
	
	
	public ArrayList<Sniffer> getAPsQueDetectaron( MacAddress macAddress){
		
		ArrayList<Sniffer> aps = new ArrayList<Sniffer>();
			
			for (Captura c : capturas){
				
				for (Package p : c.getPaquetes()){
					
//					if (p.getMacAddress.equals(macAddress)){
						
//						aps.add(c.getSniffer());
						
//					}
				}
			}
		return aps;
		
	}
}
