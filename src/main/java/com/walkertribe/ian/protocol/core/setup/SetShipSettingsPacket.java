package com.walkertribe.ian.protocol.core.setup;

import java.util.Arrays;

import com.walkertribe.ian.enums.Origin;
import com.walkertribe.ian.enums.DriveType;
import com.walkertribe.ian.iface.PacketReader;
import com.walkertribe.ian.iface.PacketWriter;
import com.walkertribe.ian.protocol.BaseArtemisPacket;
import com.walkertribe.ian.protocol.Packet;
import com.walkertribe.ian.protocol.core.CorePacketType;
import com.walkertribe.ian.protocol.core.ValueIntPacket.SubType;
import com.walkertribe.ian.vesseldata.Vessel;
import com.walkertribe.ian.vesseldata.VesselAttribute;

/**
 * Set the name, type and drive of ship your console has selected. This is only
 * sent by the helm console.
 * @author dhleong
 */
@Packet(origin = Origin.CLIENT, type = CorePacketType.VALUE_INT, subtype = SubType.SHIP_SETUP)
public class SetShipSettingsPacket extends BaseArtemisPacket {
	private static final byte[] UNKNOWN = new byte[] { (byte) 1, 0, 0, 0 };

	private Ship mShip;
	private byte[] mUnknown;

	/**
	 * Use this constructor if you wish to use a Vessel instance from the
	 * VesselData class.
	 */
	public SetShipSettingsPacket(DriveType drive, Vessel vessel, float color, String name) {
        if (vessel == null) {
        	throw new IllegalArgumentException("You must specify a Vessel");
        }

        if (!vessel.is(VesselAttribute.PLAYER)) {
        	throw new IllegalArgumentException("Must select a player vessel");
        }

		mShip = new Ship(name, vessel.getId(), color, drive);
		mUnknown = Arrays.copyOf(UNKNOWN, 4);
	}

	/**
	 * Use this constructor if you wish to use a hull ID.
	 */
	public SetShipSettingsPacket(DriveType drive, int hullId, float color, String name) {
		mShip = new Ship(name, hullId, color, drive);
    }

	public SetShipSettingsPacket(PacketReader reader) {
        reader.skip(4); // subtype
		DriveType drive = DriveType.values()[reader.readInt()];
		int hullId = reader.readInt();
		float color = reader.readFloat();
		mUnknown = reader.readBytes(4);
		CharSequence name = reader.readString();
		mShip = new Ship(name, hullId, color, drive);
	}

	/**
	 * Returns the Ship object that contains the data described by this packet.
	 */
	public Ship getShip() {
		return mShip;
	}

	@Override
	protected void writePayload(PacketWriter writer) {
		writer	.writeInt(SubType.SHIP_SETUP)
				.writeInt(mShip.getDrive().ordinal())
				.writeInt(mShip.getShipType())
				.writeFloat(mShip.getAccentColor())
				.writeBytes(mUnknown)
				.writeString(mShip.getName());
	}

	@Override
	protected void appendPacketDetail(StringBuilder b) {
    	b.append(mShip);
	}
}