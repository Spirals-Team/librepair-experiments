/*
 * Copyright (C) 2013-2018 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

package pfg.kraken.robot;

import java.awt.Graphics;
import pfg.graphic.GraphicPanel;
import pfg.graphic.printable.Printable;

/**
 * A point of the itinerary computed by Kraken
 * @author pf
 *
 */

public class ItineraryPoint implements Printable
{
	private static final long serialVersionUID = 1L;

	/**
	 * The robot should stop at this point
	 */
	public final boolean stop;
	
	/**
	 * The desired orientation
	 */
	public final double orientation;
	
	/**
	 * The desired position (x)
	 */
	public final double x;
	
	/**
	 * The desired position (y)
	 */
	public final double y;
	
	/**
	 * If the robot need to go forward
	 */
	public final boolean goingForward;
	
	/**
	 * The desired curvature
	 */
	public final double curvature;
	
	/**
	 * The maximal speed
	 */
	public final double maxSpeed;
	
	/**
	 * The recommended speed
	 */
	public final double possibleSpeed;

	public ItineraryPoint(CinematiqueObs c, boolean stop)
	{
		goingForward = c.enMarcheAvant;
		maxSpeed = c.maxSpeed;
		possibleSpeed = c.possibleSpeed;
		x = c.getPosition().getX();
		y = c.getPosition().getY();
		this.stop = stop;
		if(c.enMarcheAvant)
		{
			orientation = c.orientationGeometrique;
			curvature = c.courbureGeometrique;
		}
		else
		{
			orientation = c.orientationGeometrique + Math.PI;
			curvature = -c.courbureGeometrique;
		}
	}
	
	@Override
	public String toString()
	{
		return "("+x+","+y+"), orientation = "+orientation+", curvature = "+curvature+" going "+(goingForward ? "forward" : "backward")+", max speed = "+maxSpeed+", possible speed = "+possibleSpeed+(stop ? ", ending with a stop":"");
	}

	@Override
	public void print(Graphics g, GraphicPanel f)
	{
		int taille = 5;
		g.fillOval(f.XtoWindow(x)-taille/2, f.YtoWindow(y)-taille/2, taille, taille);
		double directionLigne = orientation + Math.PI / 2;
		double longueur = curvature * 10;
		int deltaX = (int) (longueur * Math.cos(directionLigne));
		int deltaY = (int) (longueur * Math.sin(directionLigne));
		g.drawLine(f.XtoWindow(x), f.YtoWindow(y), f.XtoWindow(x)+deltaX, f.YtoWindow(y)-deltaY);
	}

}
