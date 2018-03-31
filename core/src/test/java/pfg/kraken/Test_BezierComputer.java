/*
 * Copyright (C) 2013-2018 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

package pfg.kraken;

import java.awt.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pfg.graphic.printable.Layer;
import pfg.kraken.astar.tentacles.BezierComputer;
import pfg.kraken.astar.tentacles.DynamicTentacle;
import pfg.kraken.obstacles.CircularObstacle;
import pfg.kraken.robot.Cinematique;
import pfg.kraken.utils.XY;

/**
 * Test unitaire de BezierComputer
 * @author pf
 *
 */

public class Test_BezierComputer extends JUnit_Test
{
	protected BezierComputer bezier;
	
	@Before
	public void setUp() throws Exception
	{
		super.setUpWith(null, "default", "graphic", "empty");
		bezier = injector.getService(BezierComputer.class);
	}

	@Test
	public void test_xyoc_2_xy() throws Exception
	{
		double courbureInitiale = -3;
		Cinematique c = new Cinematique(0, 1000, Math.PI / 2, true, courbureInitiale, false);
		Cinematique arrivee = new Cinematique(1100, 1700, Math.PI / 2, false, 0, false);
		log.write("Initial : " + c, LogCategoryKraken.TEST);
		DynamicTentacle arc = bezier.quadraticInterpolationXYOC2XY(c, arrivee.getPosition(), 0);

		Assert.assertTrue(arc != null);
		
		display.addTemporaryPrintable(arc, Color.BLACK, Layer.FOREGROUND.layer);
		for(int i = 0; i < arc.getNbPoints(); i++)
			System.out.println(i + " " + arc.getPoint(i));
		
		int rayonCourbure = (int) Math.round(1000. / courbureInitiale);
		display.addPrintable(new CircularObstacle(new XY(-rayonCourbure, 1000), Math.abs(rayonCourbure)), Color.GRAY, Layer.FOREGROUND.layer);
	}
	
	@Test
	public void test_xyo_2_xyo() throws Exception
	{
		Cinematique c = new Cinematique(0, 1000, Math.PI / 2, true, 0, false);
		Cinematique arrivee = new Cinematique(400, 1500, 0, false, 0, false);
		log.write("Initial : " + c, LogCategoryKraken.TEST);
		DynamicTentacle arc = bezier.quadraticInterpolationXYO2XYO(c, arrivee, 0);

		Assert.assertTrue(arc != null);
		
		display.addTemporaryPrintable(arc, Color.BLACK, Layer.FOREGROUND.layer);
		for(int i = 0; i < arc.getNbPoints(); i++)
			System.out.println(i + " " + arc.getPoint(i));
	}

	@Test
	public void test_xyoc_2_xyo() throws Exception
	{
		double courbureInitiale = -1;
		Cinematique c = new Cinematique(0, 1000, Math.PI / 2, true, courbureInitiale, false);
		Cinematique arrivee = new Cinematique(1000, 1500, 0, false, 0, false);
		log.write("Initial : " + c, LogCategoryKraken.TEST);
		DynamicTentacle arc = bezier.cubicInterpolationXYOC2XYO(c, arrivee, 0);

		Assert.assertTrue(arc != null);
		
		display.addTemporaryPrintable(arc, Color.BLACK, Layer.FOREGROUND.layer);
		for(int i = 0; i < arc.getNbPoints(); i++)
			System.out.println(i + " " + arc.getPoint(i));

		int rayonCourbure = (int) Math.round(1000. / courbureInitiale);
		display.addPrintable(new CircularObstacle(new XY(-rayonCourbure, 1000), Math.abs(rayonCourbure)), Color.GRAY, Layer.FOREGROUND.layer);
	}
}
