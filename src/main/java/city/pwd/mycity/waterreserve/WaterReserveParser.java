package city.pwd.mycity.waterreserve;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.h2o.http.utility.HttpServicesUtility;
import com.h2o.json.utility.GsonUtility;

import city.pwd.mycity.waterreserve.bean.WaterReserveInfo;

public class WaterReserveParser {

	private static final List<String> KEYS = new ArrayList<String>();
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
	public static class Result	{
		public List<String> FAILED_INFORMATION = new ArrayList<String>();
		public int totalNoOfDaysForWaterReserve = 0;
		public Map<String,List<Map>> WATER_RESERVE_INFO = new LinkedHashMap<String,List<Map>>();
 	}
	
	public WaterReserveParser() {
		KEYS.add("reservoir");
		KEYS.add("fullTankLevel");
		KEYS.add("fullCapacity");
		KEYS.add("level");
		KEYS.add("storage");
		KEYS.add("inflow");
		KEYS.add("outflow");
		KEYS.add("rainfall");
		KEYS.add("lastYearStorage");
	}

	/**
	 * fromDate and toDate format dd/MM/yyyy. Get the water reserve between the given dates inclusive of toDate.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public WaterReserveParser.Result waterReserveBetween(String fromDateString,String toDateString){
		
		Result result = new Result();
		Map<String,List<Map>> waterReserveMap = new LinkedHashMap<String,List<Map>>();
		WaterReserveInfo waterBean = null;
		try {
			waterBean = GsonUtility.jsonToObject();			
		} catch (IOException e) {
		}
		if(waterBean ==  null) {
			waterBean = new WaterReserveInfo();			
			waterBean.lastUpdatedDate=fromDateString;
			waterBean.waterReserveDataMap = waterReserveMap;
		}
		
		fromDateString = (waterBean.lastUpdatedDate!=null)?waterBean.lastUpdatedDate:fromDateString;
		LocalDate fromDate = LocalDate.parse(fromDateString, formatter);
		LocalDate toDate = LocalDate.parse(toDateString, formatter);
		
		LocalDate currentDate = fromDate.plusDays(1);
		int totalNoOfDaysForWaterReserve = 0;		
		String lastSuccessfulUpdatedDate = null;

		for(;currentDate.isBefore(toDate) || currentDate.isEqual(toDate) ;currentDate=currentDate.plusDays(1)) {
			String currentDateString = currentDate.format(formatter);
			totalNoOfDaysForWaterReserve++;
			List<Map> waterReserveData = null;
			try {
				waterReserveData = waterReserveOn(currentDateString);
				if(waterReserveData != null) {
					waterReserveMap.put(currentDateString, waterReserveData);
					lastSuccessfulUpdatedDate = currentDateString;
				}
				
			} catch (IOException e) {
				String failure = "Failed for Date " + currentDateString + " reason : "+e.getMessage(); 
				System.err.println(e.getMessage());
				result.FAILED_INFORMATION.add(failure);
			}			
		}
		
		if(waterReserveMap != null && waterReserveMap.size() >= 1 ) {
			try {
				waterBean.waterReserveDataMap.putAll(waterReserveMap);
				if(lastSuccessfulUpdatedDate !=null) {
					waterBean.lastUpdatedDate = lastSuccessfulUpdatedDate;
				}
				if(result.FAILED_INFORMATION.size() > 0) {
					List<String> failureList = waterBean.failureList;
					if(failureList !=null) {
						failureList.addAll(result.FAILED_INFORMATION);
					}else {
						waterBean.failureList = result.FAILED_INFORMATION;
					}
				}
				
				GsonUtility.writeToFile(waterBean);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		result.totalNoOfDaysForWaterReserve = totalNoOfDaysForWaterReserve;
		result.WATER_RESERVE_INFO= waterReserveMap;
		return result;
	}
	
	
	/**
	 * dateString should be of format dd/MM/yyyy
	 * 
	 * @param dateString
	 * @return
	 * @throws IOException 
	 */
	public List<Map> waterReserveOn(String dateString ) throws IOException{
				
		String htmlResponse = HttpServicesUtility.queryReserveForGivenDate(dateString);
		if(htmlResponse.contains("Lake Level is not available")) {
			return null;
		}
		List<Map> waterReserveData = parseHtml(htmlResponse);		
		return waterReserveData;
	}
	public List<Map> todayWaterReserve() throws IOException {
		String htmlResponse = HttpServicesUtility.browseTodayReserveInfo();
		List<Map> waterReserveData = parseHtml(htmlResponse);
		return waterReserveData;
	}

	private List<Map> parseHtml(String htmlContent) {
		Document doc = Jsoup.parse(htmlContent);
		Elements tables = doc.select("table");
		List<Map> rowList = null;
		if (tables.size() > 0) {
			Element table = tables.get(0);
			Elements rows = table.select("tr");
			int rowIndex = 0;
			rowList = new ArrayList<Map>();
			while (rowIndex < rows.size()) {
				if (rowIndex == 0) {
					rowIndex++;
					continue;
				}
				Element row = rows.get(rowIndex++);
				Elements tableDatas = row.select("td");
				Map<String, String> dataMap = new LinkedHashMap<String, String>();
				int dataIndex = 0;
				for (Element tableData : tableDatas) {
					dataMap.put(KEYS.get(dataIndex++), tableData.text().trim());
				}
				rowList.add(dataMap);
			}

		}

		return rowList;
	}
	public String todayDateString() {		
		SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		return simpleDateFormatter.format(new Date());
		
	}
	public static void main(String[] args) {
		WaterReserveParser parser = new WaterReserveParser();
		try {
			WaterReserveParser.Result result = parser.waterReserveBetween("01/12/2003",parser.todayDateString());			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			WaterReserveInfo waterReserve = GsonUtility.jsonToObject();
			System.out.println(waterReserve.lastUpdatedDate);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
