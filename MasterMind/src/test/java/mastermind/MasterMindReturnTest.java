package mastermind;

import static org.junit.Assert.*;

import org.junit.Test;

public class MasterMindReturnTest {

	@Test
	public void test() {
		final MasterMindReturn masterMindReturn = new MasterMindReturn(5, 6);
		assertEquals(masterMindReturn.getWellPlaced(), 5);
		assertEquals(masterMindReturn.getBadlyPlaced(), 6);

		masterMindReturn.setWellPlaced(8);
		assertEquals(masterMindReturn.getWellPlaced(), 8);

		masterMindReturn.setBadlyPlaced(9);
		assertEquals(masterMindReturn.getBadlyPlaced(), 9);
		
	}

}
