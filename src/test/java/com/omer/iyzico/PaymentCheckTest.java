package com.omer.iyzico;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.omer.iyzico.checkers.PaymentCheck;
import com.omer.iyzico.model.Request;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PaymentCheckTest {

	@Autowired
	private PaymentCheck paymentCheck;

	@Test
	public void successPayment() throws ParseException {
		Request request = new Request();
		request.setCardNumber("5526080000000006");
		request.setProcessId("-1");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCreateDate(sdf.parse("02/12/2018"));
		String result = paymentCheck.payment(request);
		assertEquals("Ödeme Alındı", result);
	}

	@Test
	public void halkBankPayment() throws ParseException {
		Request request = new Request();
		request.setCardNumber("4475050000000003");
		request.setProcessId("-1");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCreateDate(sdf.parse("02/12/2018"));
		String result = paymentCheck.payment(request);
		assertEquals("Ödeme Alındı", result);
	}

	@Test
	public void debitCardPayment() throws ParseException {
		Request request = new Request();
		request.setCardNumber("5170410000000004");
		request.setProcessId("-1");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCreateDate(sdf.parse("02/12/2018"));
		String result = paymentCheck.payment(request);
		assertEquals("Halk Bank dışında debit Kartlara izin verilmemektedir", result);
	}

	@Test
	public void notAllowedBankPayment() throws ParseException {
		Request request = new Request();
		request.setCardNumber("4603450000000000");
		request.setProcessId("-1");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCreateDate(sdf.parse("02/12/2018"));
		String result = paymentCheck.payment(request);
		assertEquals("Bu bankaya izin verilmemektedir", result.split(":")[0]);
	}

	@Test
	public void emptyCardNumber() {
		Request request = new Request();
		request.setProcessId("-1");
		request.setCreateDate(Calendar.getInstance().getTime());
		request.setPrice(new BigDecimal("100"));
		String result = paymentCheck.payment(request);
		assertEquals("Card numaranız boş olamaz", result);
	}

	@Test
	public void wrongCardNumber() {

		Request request = new Request();
		request.setProcessId("-1");
		request.setCardNumber("1111111");
		request.setCreateDate(Calendar.getInstance().getTime());
		request.setPrice(new BigDecimal("100"));
		String result = paymentCheck.payment(request);
		assertEquals("Bin bulunamadı", result);
	}

	@Test
	public void dateBlindBird() throws ParseException {

		Request request = new Request();
		request.setProcessId("-1");
		request.setCardNumber("5526080000000006");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCreateDate(sdf.parse("02/07/2017"));
		paymentCheck.payment(request);
		assertEquals(new BigDecimal("250.0"), request.getPrice());
	}

	@Test
	public void dateEarlyBird() throws ParseException {

		Request request = new Request();
		request.setProcessId("-1");
		request.setCardNumber("5526080000000006");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCreateDate(sdf.parse("17/01/2018"));
		paymentCheck.payment(request);
		assertEquals(new BigDecimal("500.0"), request.getPrice());
	}

	@Test
	public void dateRegular() throws ParseException {

		Request request = new Request();
		request.setProcessId("-1");
		request.setCardNumber("5526080000000006");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCreateDate(sdf.parse("03/03/2018"));
		paymentCheck.payment(request);
		assertEquals(new BigDecimal("750.0"), request.getPrice());
	}

	@Test
	public void dateLaggard() throws ParseException {

		Request request = new Request();
		request.setProcessId("-1");
		request.setCardNumber("5526080000000006");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCreateDate(sdf.parse("02/05/2018"));
		paymentCheck.payment(request);
		assertEquals(new BigDecimal("1000.0"), request.getPrice());
	}
	
	@Test
	public void wrongPromotionCode() throws ParseException {
		Request request = new Request();
		request.setProcessId("-1");
		request.setCardNumber("4475050000000003");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCreateDate(sdf.parse("13/03/2018"));
		request.setPromotionCode("WRONGCODE");
		String result = paymentCheck.payment(request);
		assertEquals("Promotion code hatalı.", result);
	}
	@Test
	public void gammaPromotionCode() throws ParseException {
		Request request = new Request();
		request.setProcessId("-1");
		request.setCardNumber("4475050000000003");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCreateDate(sdf.parse("13/03/2018"));
		request.setPromotionCode("GAMMA");
		paymentCheck.payment(request);
		assertEquals(new BigDecimal("675.0"), request.getPrice());
	}
	@Test
	public void beckPromotionCode() throws ParseException {
		Request request = new Request();
		request.setProcessId("-1");
		request.setCardNumber("4475050000000003");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCreateDate(sdf.parse("31/03/2018"));
		request.setPromotionCode("BECK");
		paymentCheck.payment(request);
		assertEquals(new BigDecimal("637.5"), request.getPrice());
	}
	@Test
	public void cunninghamPromotionCode() throws ParseException {
		Request request = new Request();
		request.setProcessId("-1");
		request.setCardNumber("4475050000000003");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCreateDate(sdf.parse("26/05/2018"));
		request.setPromotionCode("CUNNINGHAM");
		paymentCheck.payment(request);
		assertEquals(new BigDecimal("950.0"), request.getPrice());
	}
	@Test
	public void agilePromotionCode() throws ParseException {
		Request request = new Request();
		request.setProcessId("-1");
		request.setCardNumber("4475050000000003");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCreateDate(sdf.parse("12/02/2018"));
		request.setPromotionCode("AGILE");
		paymentCheck.payment(request);
		assertEquals(new BigDecimal("500.0"), request.getPrice());
	}

}
