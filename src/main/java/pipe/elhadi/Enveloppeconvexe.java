package pipe.elhadi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




/**
 * Cette classe permet de créer l'enveloppe convexe d'un nuage de point en entrée (liste de point)
 * elle est baséé sur l'algorithme d'Andrew's monotone chain
 * @author Elhadi BOUCHAOUR
 * 
 *
 */
public class Enveloppeconvexe {


private List<Mypoint> liste;
	
	/**
	 * @param list
	 */
	public Enveloppeconvexe(List<Mypoint> list)
	{
		liste=new ArrayList<Mypoint>(list.size());
		
		for (Mypoint p: list)
		{
			liste.add(p);
		}
		
	}
	
	/**
	 * @return
	 */
	public List<Mypoint> getListe() {
		return liste;
	}
	
	/**
	 * @param liste
	 */
	public void setListe(List<Mypoint> liste) {
		this.liste = liste;
	}

	/**
	 * Cette methode test si un 3ème point P2 se situe à gauche/sur/droit de deux point P0 et P1
	 * >=0 P2 à gauche du  P0 et P1 (retourne true)
	 * =0 P2 sur la meme ligne P0P1  (retourne true)
	 * <0 P2 se situe à droit du deux points (retourne false);
	 *  
	 * @param P0 premier point
	 * @param P1 deuxième point
	 * @param P2 troisième point
	 * @return boolean 
	 */
	public static boolean positiong(Mypoint P0, Mypoint P1, Mypoint P2) {
        return ( (P1.getX()-P0.getX())*(P2.getY()-P0.getY()) - (P1.getY()-P0.getY())*(P2.getX()-P0.getX()) ) >= 0;
    }
	
	
	/**
	 * La methode getenveloppeconvexe permet de créer l'enveloppe convexe à partir d'un nuage de point
	 * en entrée (liste de point), comme citée au-déssus elle utilise l'algorithme d'Andrew's monotone 
	 * chain qui commence par le trie de la liste des points en ordre croissant selon leurs x
	 * (si deux points ont le memex elle compare leur y, elle fait appel à la méthode compareto de classe
	 * Mypoint),ensuite les diviser en deux sous ensembles (superieur et inférieur),à la fin du traitement
	 * un fusion des deux sous ensembles se fait pour obtenir l'enveloppe convexe   
	 * 
	 * @param liste c'est le nuage de point en entrée
	 * @return listeres c'est liste de points qui constitue l'eveloppe convexe
	 * 
	 */
	public List<Mypoint> getenveloppeconvexe(List<Mypoint> liste) {
	   Collections.sort(liste);
	   int n = liste.size();
        ArrayList<Mypoint> listeres = new ArrayList<Mypoint>();
        ArrayList<Mypoint> liste2 = new ArrayList<Mypoint>();
      
        listeres.add(liste.get(0));
        listeres.add(liste.get(1));

            for(int i=2 ; i<n ; i++) {
            	listeres.add(liste.get(i));

                while(listeres.size()>2 && positiong(listeres.get(listeres.size()-3), listeres.get(listeres.size()-2), listeres.get(listeres.size()-1))) {
                	listeres.remove(listeres.get(listeres.size()-2));
                }
            }
      
            liste2.add(liste.get(n-1));
            liste2.add(liste.get(n-2));

            for(int i=n-1 ; i>=0 ; i--) {
            	liste2.add(liste.get(i));

                while(liste2.size()>2 && positiong(liste2.get(liste2.size()-3), liste2.get(liste2.size()-2), liste2.get(liste2.size()-1))) {
                	liste2.remove(liste2.get(liste2.size()-2));
                }
            }

            liste2.remove(0);
            liste2.remove(liste2.size()-1);
                    
            listeres.addAll(liste2);

        return listeres;
    }

		
	

}
