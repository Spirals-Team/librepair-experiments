package de.adorsys.onlinebanking.mock;

import domain.BankAccess;
import domain.BankAccount;
import domain.LoadBookingsResponse;
import org.junit.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Ignore
public class MockBankingTest {
	private String pin = "password";
	private BankAccess bankAccess;
	MockBanking mockBanking;
	
	@BeforeClass
	public static void beforeClass(){
		System.setProperty("mockConnectionUrl", "http://localhost:10010");
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Object> entity = restTemplate.getForEntity("http://localhost:10010/health", Object.class);
			Assume.assumeTrue(entity.getStatusCode().value()==200);
		} catch(Exception e){
			Assume.assumeTrue(false);
		}
	}
	
	@Before
	public void before(){
		bankAccess = new BankAccess();
		bankAccess.setBankLogin("login");
		mockBanking = new MockBanking();
	}
	
	@Test
	public void testLoadBankAccounts() {
		List<BankAccount> bankAccounts = mockBanking.loadBankAccounts(null, bankAccess, null, pin, true);
		Assert.assertNotNull(bankAccounts);
		Assert.assertFalse(bankAccounts.isEmpty());
	}

	@Test
	public void testLoadBookings() {
		List<BankAccount> bankAccounts = mockBanking.loadBankAccounts(null, bankAccess, null, pin, true);
		Assume.assumeNotNull(bankAccounts);
		Assume.assumeFalse(bankAccounts.isEmpty());
		BankAccount bankAccount = bankAccounts.iterator().next();
		LoadBookingsResponse response = mockBanking.loadBookings(null, bankAccess, null, bankAccount, pin);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getBookings());
		Assert.assertFalse(response.getBookings().isEmpty());
	}
}
