package com.walkertribe.ian.world;

import org.junit.Assert;

import com.walkertribe.ian.enums.BeamFrequency;
import com.walkertribe.ian.util.TestUtil;

public class BaseArtemisShipTest {
	public static void assertShip(BaseArtemisShip ship, float velocity, float shieldsFrontMax, float shieldsRearMax,
			float[] shieldFreqs, float steering, float topSpeed, float turnRate, float impulse) {
		Assert.assertEquals(velocity, ship.getVelocity(), TestUtil.EPSILON);
		Assert.assertEquals(shieldsFrontMax, ship.getShieldsFrontMax(), TestUtil.EPSILON);
		Assert.assertEquals(shieldsRearMax, ship.getShieldsRearMax(), TestUtil.EPSILON);

		for (BeamFrequency freq : BeamFrequency.values()) {
			Assert.assertEquals(shieldFreqs[freq.ordinal()], ship.getShieldFreq(freq), TestUtil.EPSILON);
		}

		Assert.assertEquals(steering, ship.getSteering(), TestUtil.EPSILON);
		Assert.assertEquals(topSpeed, ship.getTopSpeed(), TestUtil.EPSILON);
		Assert.assertEquals(turnRate, ship.getTurnRate(), TestUtil.EPSILON);
		Assert.assertEquals(impulse, ship.getImpulse(), TestUtil.EPSILON);
	}
}
