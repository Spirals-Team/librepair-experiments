package com.omer.iyzico;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.iyzipay.model.BinNumber;
import com.omer.iyzico.checkers.BinNumberCheck;

public class BinNumberCheckTest {
	
	@Test
	public void binNumbercheck() {
		
		BinNumberCheck binNumberCheck=new BinNumberCheck();
		BinNumber binNumber = binNumberCheck.checkBinNumber("554960");
		
		assertEquals(new Long(62), binNumber.getBankCode());
		
	}

}
