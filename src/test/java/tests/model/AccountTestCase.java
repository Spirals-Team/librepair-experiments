package tests.model;

import static org.junit.Assert.*;
import org.junit.Test;
import model.Account;
import model.exceptions.NoCreditException;

public class AccountTestCase {

	@Test
	public void shouldDiscountCreditWhenItCan() {
		Account account= new Account();
		account.addCredit(20);
		account.debitCredit(10);
		assertEquals(10, account.getCredit(),0);
	}
	
	@Test(expected=NoCreditException.class)
	public void shouldNotDiscountCreditWhenDebitCreditIsGreaterThanAccountCredit(){
		Account account= new Account();
		account.debitCredit(20);
	}
	
	@Test
	public void shouldCreditThrows40WhenDebit20To60(){
		Account account= new Account();
		account.addCredit(60);
		assertEquals(account.debitCredit(20),40,0);
	} 
	

}
