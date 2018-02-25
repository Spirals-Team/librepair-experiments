package pipe.elhadi;

import java.util.ArrayList;
import java.util.List;


/**
 * Cette classe permet de generer l'eveloppe Bounding Box d'un nuage de points en entrée
 * 
 * @author elhadi Bouchaour
 *
 */
public class Boundingbox{

	private double pointminx;
	private double pointmaxx;
	private double pointminy;
	private double pointmaxy;
	private List<Mypoint> boundboxlst;


	/**
	 * Constructeur de la classe
	 */
	public Boundingbox()
	{
		boundboxlst=new ArrayList<Mypoint>();   
	    pointminx=0;
	    pointmaxx=0;
	    pointminy=0;
	    pointmaxy=0;
	        
	}

	
	/**
	 * Cette methode genere l'enveloppe Bouding Box a partir d'une liste de points recus en parametre
	 * elle extrait le mininum des x ,ensuite elle extrait le maximun des x, elle fait la même chose 
	 * les minimun et maximun y, depuis les resultats elle creer 4 points qui forme le bounding Box
	 * @param liste liste des points du nuage
	 * @return listetrie liste des points constituent l'enveloppe Bounding Box
	 * @author elhadi Bouchaour 
	 */
	
	
	public List<Mypoint> getenveloppeboundbx(List<Mypoint> liste) {
		pointminx=liste.get(0).getX();
		pointmaxx=liste.get(0).getX();
		pointminy=liste.get(0).getY();
		pointmaxy=liste.get(0).getY();
		
		for (int i=0;i<liste.size();++i)
		{
			pointminx=Math.min(liste.get(i).getX(),pointminx);
			pointmaxx=Math.max(liste.get(i).getX(),pointmaxx);
			pointminy=Math.min(liste.get(i).getY(),pointminy);
			pointmaxy=Math.max(liste.get(i).getY(),pointmaxy);
		}
	      Mypoint p1=new Mypoint(pointminx,pointminy);
	      Mypoint p2=new Mypoint(pointmaxx,pointminy);
	      Mypoint p3=new Mypoint(pointmaxx,pointmaxy);
	      Mypoint p4=new Mypoint(pointminx,pointmaxy);
	      boundboxlst.add(p1);
	      boundboxlst.add(p2);
	      boundboxlst.add(p3);
	      boundboxlst.add(p4);
	    return boundboxlst;
	}
	 
   


}

