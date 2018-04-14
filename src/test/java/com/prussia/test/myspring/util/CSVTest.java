package com.prussia.test.myspring.util;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

public class CSVTest {

	@Test
	public void testApacheCommonCSV() throws Exception {
		String file = ResourceUtils.getFile(this.getClass().getResource("/test.csv")).getAbsolutePath();
		
		Reader in = new FileReader(file);
		List<CSVRecord> records = CSVFormat.DEFAULT
											.withFirstRecordAsHeader()
											.withAllowMissingColumnNames().parse(in)
											.getRecords();
		for (CSVRecord record : records) {
			String columnOne = record.get(0);
			String columnTwo = record.get(1);
		}
		Assert.assertSame(5, records.size());

	}
}
