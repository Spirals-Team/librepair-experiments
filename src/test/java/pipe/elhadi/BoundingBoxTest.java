package pipe.elhadi;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import junit.framework.TestCase;

public class BoundingBoxTest extends TestCase {

	
	/** 
		 * Le test consite à créer deux listes, liste contenant le nuage de point en entrée
		 * et la deuxième liste c'est résultat attendu qui est le Bounding Box du nuage, 
		 * ensuite en compare cette dernière avec celle générée par la fonction getenveloppeboundbx
		 * du code, si les listes ont les meme éléments le test passe 
		 * 
		 **/
	
	@Test
	public void testGetenveloppeboundbx() {
		
		Boundingbox boundbox= new Boundingbox();
		List<Mypoint> liste=new ArrayList<Mypoint>();
		List<Mypoint> listboundingbx=new ArrayList<Mypoint>();
			
		Mypoint pt1= new Mypoint(1.0,1.0);
		Mypoint pt2= new Mypoint(3.0,2.0);
		Mypoint pt3= new Mypoint(8.0,2.0);
		Mypoint pt4= new Mypoint(4.0,3.0);
		Mypoint pt5= new Mypoint(3.0,3.0);
		Mypoint pt6= new Mypoint(6.0,6.0);
		Mypoint pt7= new Mypoint(-2.0,2.0);
		Mypoint pt8= new Mypoint(2.0,2.0);
		Mypoint pt9= new Mypoint(1.0,5.0);
				
		liste.add(pt1);liste.add(pt2);liste.add(pt3);liste.add(pt4);
		liste.add(pt5);liste.add(pt6);liste.add(pt7);liste.add(pt8);
		liste.add(pt9);
		
		 //liste des points attendue du Bounding Box
		  		 
		listboundingbx.add(new Mypoint(-2,1));
		listboundingbx.add(new Mypoint(8,1));
		listboundingbx.add(new Mypoint(8,6));
		listboundingbx.add(new Mypoint(-2,6));
		
		//comparaison des deux listes				
		int j=boundbox.getenveloppeboundbx(liste).size();
				for (int i=0;i<j;++i)
	    {
		assertTrue(false);			
	    	assertEquals(listboundingbx.get(i).getX(),boundbox.getenveloppeboundbx(liste).get(i).getX());
	    	assertEquals(listboundingbx.get(i).getY(),boundbox.getenveloppeboundbx(liste).get(i).getY());
	    }
		
		
		
		
	}
   
			
		
	}
