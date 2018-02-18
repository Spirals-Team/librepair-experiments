package io.xrates.backend.rateprovider.impl;

import io.xrates.backend.datamodel.util.XratesDBUtil;

import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DBSRateProviderImplTest {
	
	@InjectMocks
	private DBSRateProviderImpl dbsRateProviderImpl = null;
	
	@Mock
	private XratesDBUtil xratesDBUtil;
	
	private Currency sgd = Currency.getInstance("SGD");
	
	public DBSRateProviderImplTest() {
		dbsRateProviderImpl = new DBSRateProviderImpl();
	}
	
	@Test
	public void testUSD() {
		Currency usd = Currency.getInstance("USD");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, usd);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testEUR() {
		Currency eur = Currency.getInstance("EUR");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, eur);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGBP() {
		Currency gbp = Currency.getInstance("GBP");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, gbp);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAUD() {
		Currency aud = Currency.getInstance("AUD");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, aud);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCAD() {
		Currency cad = Currency.getInstance("CAD");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, cad);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNZD() {
		Currency nzd = Currency.getInstance("NZD");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, nzd);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDKK() {
		Currency dkk = Currency.getInstance("DKK");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, dkk);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHDK() {
		Currency hkd = Currency.getInstance("HKD");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, hkd);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNOK() {
		Currency nok = Currency.getInstance("NOK");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, nok);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSEK() {
		Currency sek = Currency.getInstance("SEK");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, sek);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCHF() {
		Currency chf = Currency.getInstance("CHF");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, chf);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCNY() {
		Currency cny = Currency.getInstance("CNY");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, cny);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testJPY() {
		Currency jpy = Currency.getInstance("JPY");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, jpy);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTHB() {
		Currency thb = Currency.getInstance("THB");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, thb);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testIDR() {
		Currency idr = Currency.getInstance("IDR");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, idr);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBND() {
		Currency bnd = Currency.getInstance("BND");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, bnd);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testZAR() {
		Currency zar = Currency.getInstance("ZAR");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, zar);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testINR() {
		Currency inr = Currency.getInstance("INR");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, inr);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testKRW() {
		Currency krw = Currency.getInstance("KRW");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, krw);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLKR() {
		Currency lkr = Currency.getInstance("LKR");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, lkr);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPHP() {
		Currency php = Currency.getInstance("PHP");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, php);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSAR() {
		Currency sar = Currency.getInstance("SAR");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, sar);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTWD() {
		Currency twd = Currency.getInstance("TWD");
		try {
			double rate = dbsRateProviderImpl.convert(sgd, twd);
			Assert.assertNotEquals(-1.0, rate);
		} catch (Exception e) {
			Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
}
