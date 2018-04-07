package net.runelite.client.plugins.groundmarkers;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;

import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;
import javax.inject.Inject;
import java.util.Iterator ;

@Slf4j
public class GroundMarkerOverlay extends Overlay
{
	@Inject
	private Client client;

	@Inject
	private GroundMarkerConfig config;

	@Inject
	private GroundMarkerPlugin plugin;

	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private java.util.List<GroundMarkerPoint> lstPoints = new ArrayList<GroundMarkerPoint>();

	@Inject
	GroundMarkerOverlay(Client client, GroundMarkerConfig config)
	{
		this.client = client;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.HIGH);
		setLayer(OverlayLayer.UNDER_WIDGETS);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		Iterator<GroundMarkerPoint> it = this.lstPoints.iterator() ;

		while (it.hasNext())
		{
			drawTile( graphics, it.next(), it ) ;
		}

		return null;
	}

	private void drawTile(Graphics2D graphics, GroundMarkerPoint pt, Iterator<GroundMarkerPoint> it )
	{
		if ( pt.getPoint().distanceTo( client.getLocalPlayer( ).getWorldLocation( ) ) >= 32 )
		{
			return;
		}

		Polygon poly = Perspective.getCanvasTilePoly( client, pt.toLocal( client ) ) ;

		if ( poly == null )
		{
			return;
		}

		OverlayUtil.renderPolygon( graphics, poly, config.markerColor( ) ) ;

		return ;
	}
}