package pipe.elhadi;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class Enveloppeconvexetest extends TestCase {

	
	
	/**
	 * Cette methode permet de test si un troisième point pt3 se trouve à gauche ou aligné avec  
	 * des deux points pt1 et pt2, dans ce cas retourne true(resultat >=0) sinon retourne false
	 * notre test consiste à créer trois points pt1,pt2 et un troisieme point pt3 
	 */
	@Test
	public void testPositiong() {
	//pt3 se trouve à gauche des deux points	
	Mypoint pt1=new Mypoint(1.0,1.0);
	Mypoint pt2=new Mypoint(3.0,3.0);
	Mypoint pt3=new Mypoint(1.0,4.0);
	assertTrue(Enveloppeconvexe.positiong(pt1,pt2,pt3));
    
	//pt3 se trouve sur la meme ligne
	
	pt3.setX(2.0);
	pt3.setY(2.0);
	assertTrue(Enveloppeconvexe.positiong(pt1,pt2,pt3));
	 
	//pt3 se trouve à droit des deux points
	pt3.setX(3.0);
	pt3.setY(1.0);
	assertFalse(Enveloppeconvexe.positiong(pt1,pt2,pt3));
	
	}

	
	/**
	 * Le test de cette methode consiste à créer une liste de points
	 * considere comme le nuage de points en entrée, ensuite on 
	 * teste l'appartenance de deux points à notre enveloppe convexe 
	 * 
	 * 
	 */
	@Test
	public void testGetenveloppeconvexe() {
		
		List<Mypoint> points = new ArrayList<Mypoint>();
		Mypoint pt1= new Mypoint(1.0,1.0);
		Mypoint pt2= new Mypoint(-3.0,2.0);
		Mypoint pt3= new Mypoint(8.0,2.0);
		Mypoint pt4= new Mypoint(-4.0,3.0);
		Mypoint pt5= new Mypoint(3.0,3.0);
		Mypoint pt6= new Mypoint(6.0,6.0);
		Mypoint pt7= new Mypoint(-2.0,2.0);
		points.add(pt1);
		points.add(pt2);
		points.add(pt3);
		points.add(pt4);
		points.add(pt5);
		points.add(pt6);
		points.add(pt7);
		//notre enveloppe convexe attendue est construit à partir des points
		//pt4,pt6,pt3,pt1 et pt2
		 
		// On va tester maintenant si deux points du nuage appartient 
		// à notre enveloppe convexe 
				
		Enveloppeconvexe envelpcvx= new Enveloppeconvexe(points);
		assertTrue(envelpcvx.getenveloppeconvexe(points).contains(pt3));
		assertFalse(envelpcvx.getenveloppeconvexe(points).contains(pt5));
	}
    
   
}
