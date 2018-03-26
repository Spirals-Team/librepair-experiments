package com.walkertribe.ian.protocol.core.eng;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.walkertribe.ian.enums.Origin;
import com.walkertribe.ian.protocol.AbstractPacketTester;

public class EngRequestGridUpdatePacketTest extends AbstractPacketTester<EngRequestGridUpdatePacket> {
	@Test
	public void testParse() {
		execute("core/eng/EngRequestGridUpdatePacket.txt", Origin.CLIENT, 1);
	}

	@Test
	public void testConstruct() {
		new EngRequestGridUpdatePacket();
	}

	@Override
	protected void testPackets(List<EngRequestGridUpdatePacket> packets) {
		Assert.assertNotNull(packets.get(0));
	}
}
