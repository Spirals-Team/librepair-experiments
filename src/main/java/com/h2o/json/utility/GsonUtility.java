package com.h2o.json.utility;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import city.pwd.mycity.waterreserve.bean.WaterReserveInfo;

public class GsonUtility {
	private static final String fileName = "db.json";
	/**
	 * Utility API to convert java to json
	 */

	private static void object2Json(Object o,FileWriter writer) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();		
		gson.toJson(o,WaterReserveInfo.class,writer);
		
	}
	public static WaterReserveInfo jsonToObject() throws IOException {
		Gson gson = new Gson();
		WaterReserveInfo waterReserve = null;
		Reader reader = null;
		try {
			reader = new FileReader(getWaterReserveFileName());
			waterReserve = gson.fromJson(reader, WaterReserveInfo.class);
		} catch (IOException e) {
			throw e;
		}finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return waterReserve;	
	}
	private static String getWaterReserveFileName() throws IOException {
		File currentDirectory = new File(new File(".").getAbsolutePath());
		String storagePath = "output";
		File dataDirectory = new File(currentDirectory.getCanonicalPath()+File.separator+storagePath);
		if(!dataDirectory.exists()) {
			dataDirectory.mkdirs();
		}
		String filePath = dataDirectory.getCanonicalPath()+File.separator+fileName;
		return filePath;
	}
	
	public static void writeToFile(WaterReserveInfo waterReserveBean) throws IOException {
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(getWaterReserveFileName());
			object2Json(waterReserveBean,writer);
		} catch (IOException e) {
			throw e;
		}finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		try {
			System.out.println(getWaterReserveFileName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
