package nc.noumea.mairie.radi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Helper {

	private static Logger logger = LoggerFactory.getLogger(Helper.class);

	public static Date fromActiveDirectoryLongDateTime(String timestamp) {

		if (timestamp == null || timestamp.equals(""))
			return null;

		try {
			long longTimestamp = Long.parseLong(timestamp);
			long javaTime = (longTimestamp - 0x19db1ded53e8000L) / 10000;
			return new Date(javaTime);
		} catch (NumberFormatException e) {
			logger.warn("Could not parse AD date [{}]", timestamp);
			return null;
		}
	}

	public static Date fromActiveDirectoryStringDateTime(String dateTime) {

		if (dateTime == null || dateTime.equals(""))
			return null;

		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddssmmHH");
			return sf.parse(dateTime);

		} catch (ParseException e) {
			logger.warn("Could not parse AD date [{}]", dateTime);
			return null;
		}
	}

	public static String fromBinaryArrayToString(Object binaryObj) {
		
		byte[] byteArray = (byte[]) binaryObj;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++)
		{
			byte asByte = byteArray[i];
			sb.append(Integer.toString(asByte, 16));
		}
		
		return sb.toString();
	}
}
