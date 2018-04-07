package net.runelite.client.plugins.groundmarkers;

import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;

public class GroundMarkerPoint
{
	@Getter(AccessLevel.PACKAGE)
	private WorldPoint point ;

	@Getter(AccessLevel.PACKAGE)
	private boolean isInstanced = false ;

	GroundMarkerPoint(WorldPoint point, Client client)
	{
		this.isInstanced = client.isInInstancedRegion() ;
		this.point = point ;
	}

	GroundMarkerPoint(int[] intRaw)
	{
		fromRaw(intRaw) ;
	}

	public int[] toRaw()
	{
		return new int[] { point.getX(), point.getY(), point.getPlane() } ;
	}

	private void fromRaw(int[] intRaw)
	{
		this.point = new WorldPoint(intRaw[0], intRaw[1], intRaw[2]) ;
	}

	public LocalPoint toLocal(Client client)
	{
		return new LocalPoint(0, 0).fromWorld(client, this.point);
	}
}
