package com.omer.iyzico.checkers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.iyzipay.Options;
import com.iyzipay.model.BinNumber;
import com.omer.iyzico.model.Log;
import com.omer.iyzico.model.Request;
import com.omer.iyzico.model.Sale;
import com.omer.iyzico.service.LogService;
import com.omer.iyzico.service.SaleService;

@Component
public class PaymentCheck {
	private static final String DEBIT_CARD = "DEBIT_CARD";
	private static final Long HALK_BANK_CODE = 12L;
	private static final List<Long> BANK_CODE_LIST = Arrays.asList(46L, 62L, 64L, 111L);
	private static final String SUCCESS = "success";
	Options options;
	Gson gson = new Gson();
	final static Logger logger = Logger.getLogger(PaymentCheck.class);

	@Autowired
	private LogService logService;
	@Autowired
	private SaleService saleService;

	public PaymentCheck() {
		options = new Options();
		options.setApiKey("sandbox-WJQ1HcphuA9cesK1quRqOko6tBneuy46");
		options.setSecretKey("sandbox-O5HjgLsjdf9iUSOj9FGI03TLVzmni9sA");
		options.setBaseUrl("https://sandbox-api.iyzipay.com");

	}

	public String payment(Request request) {
		String result = "";
		String success = "FAIL";
		if (request.getCardNumber() != null && request.getCardNumber().length() >= 6) {
			String binNumberString = request.getCardNumber().substring(0, 6);
			BinNumber binNumber = new BinNumberCheck().checkBinNumber(binNumberString);
			result = debitCardChecker(binNumber);
		} else {
			result = "Card numaranız boş olamaz";
		}
		try {
			if (result.equals(SUCCESS)) {

				priceChecker(request);

				boolean checkPromotionCode = true;
				String promotionCode = request.getPromotionCode();
				if (promotionCode != null && promotionCode.length() > 0) {
					checkPromotionCode = checkPromotionCode(request);
				}
				if (checkPromotionCode) {
					// Payment payment = Payment.create(request, options);
					result = "Ödeme Alındı";
					success = "SUCCESS";
				} else {
					result = "Promotion code hatalı.";
				}

			}
			saveSale(request, success);
			saveLog(request);

		} catch (ParseException e) {
			return "İşleminiz sırasnda bir hata oluştu";
		}
		return result;

	}

	private String debitCardChecker(BinNumber binNumber) {
		String result = "";
		if (binNumber.getStatus().equalsIgnoreCase("success")) {
			if (binNumber.getCardType().equalsIgnoreCase(DEBIT_CARD)) {
				if (binNumber.getBankCode() == HALK_BANK_CODE) {
					result = SUCCESS;
				} else {
					result = "Halk Bank dışında debit Kartlara izin verilmemektedir";
				}
			} else {
				if (BANK_CODE_LIST.contains(binNumber.getBankCode())) {
					result = SUCCESS;
				} else {
					result = "Bu bankaya izin verilmemektedir: " + binNumber.getBankName();
				}
			}
		} else {
			result = binNumber.getErrorMessage();
		}

		return result;
	}

	private void priceChecker(Request request) throws ParseException {

		Date date = request.getCreateDate();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (date.after(sdf.parse("01/07/2017")) && date.before(sdf.parse("15/01/2018"))) {
			request.setPrice(new BigDecimal("250").setScale(1, BigDecimal.ROUND_UNNECESSARY));
		} else if (date.after(sdf.parse("16/01/2018")) && date.before(sdf.parse("28/02/2018"))) {
			request.setPrice(new BigDecimal("500").setScale(1, BigDecimal.ROUND_UNNECESSARY));
		} else if (date.after(sdf.parse("01/03/2018")) && date.before(sdf.parse("30/04/2018"))) {
			request.setPrice(new BigDecimal("750").setScale(1, BigDecimal.ROUND_UNNECESSARY));
		} else if (date.after(sdf.parse("01/05/2018")) && date.before(sdf.parse("27/05/2018"))) {
			request.setPrice(new BigDecimal("1000").setScale(1, BigDecimal.ROUND_UNNECESSARY));
		} else {
			request.setPrice(new BigDecimal("1000").setScale(1, BigDecimal.ROUND_UNNECESSARY));
		}
	}

	private boolean checkPromotionCode(Request request) {
		Date date = request.getCreateDate();
		BigDecimal price = request.getPrice();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		switch (request.getPromotionCode()) {
		case "GAMMA":
			if (localDate.getMonthValue() == 3 && localDate.getDayOfMonth() == 13) {
				price = price.subtract(price.multiply(new BigDecimal("0.1"))).setScale(1, BigDecimal.ROUND_UNNECESSARY);
				request.setPrice(price);
				return true;
			} else {
				return false;
			}

		case "BECK":
			if (localDate.getMonthValue() == 3 && localDate.getDayOfMonth() == 31) {
				price = price.subtract(price.multiply(new BigDecimal("0.15"))).setScale(1,
						BigDecimal.ROUND_UNNECESSARY);
				request.setPrice(price);
				return true;
			} else {
				return false;
			}
		case "CUNNINGHAM":
			if (localDate.getMonthValue() == 5 && localDate.getDayOfMonth() == 26) {
				price = price.subtract(price.multiply(new BigDecimal("0.05"))).setScale(1,
						BigDecimal.ROUND_UNNECESSARY);
				request.setPrice(price);
				return true;
			} else {
				return false;
			}
		case "AGILE":
			if (localDate.getMonthValue() == 2 && localDate.getDayOfMonth() <= 13 && localDate.getDayOfMonth() >= 13) {
				price = price.subtract(price.multiply(new BigDecimal("0.2"))).setScale(1, BigDecimal.ROUND_UNNECESSARY);
				return true;
			} else {
				return false;
			}
		default:
			return false;

		}

	}

	private void saveLog(Request request) throws ParseException {

		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ssZ");
		Log log = new Log();
		log.setCardNumber(request.getCardNumber());
		log.setCreateDate(date);
		log.setProcessId(request.getProcessId());
		logger.info(sdf.format(log.getCreateDate()) + "," + log.getProcessId() + "," + log.getCardNumber());
		logService.save(log);
	}

	private void saveSale(Request request, String success) {
		Sale sale = new Sale();
		if (success.equals("SUCCESS")) {
			sale.setPrice(request.getPrice());
		}
		sale.setProcessId(request.getProcessId());
		sale.setResult(success);
		saleService.save(sale);
	}

}
