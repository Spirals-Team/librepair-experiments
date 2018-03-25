package city.pwd.mycity.waterreserve.bean;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="waterReserveData")
public class WaterReserveInfo {

	@XmlElement(name="waterReserveOn")
	public Map<String,List<Map>> waterReserveDataMap = null;
	public String lastUpdatedDate=null;
	public List<String> failureList = null;
	
	public WaterReserveInfo() {		
	}

}
