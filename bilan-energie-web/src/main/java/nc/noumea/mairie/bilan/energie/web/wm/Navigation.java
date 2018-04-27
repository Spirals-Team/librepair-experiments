package nc.noumea.mairie.bilan.energie.web.wm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.iterators.ArrayListIterator;

/**
 * Class utilitaire de navigation
 * 
 * @author David ALEXIS
 * 
 * @param <T> Type d'objet à gérer
 */
public class Navigation<T> {

	/**
	 * Index courant dans la nivgation
	 */
	private Integer currentIndex = null;

	/**
	 * Liste des items de navigation
	 */
	private List<T> navList = new ArrayList<T>();

	/**
	 * Récuérer l'item précedent à l'item en cour
	 * 
	 * @return null si la listede navigation est vide. Item courant s'il n'est
	 *         plus possible de nagiuer
	 */
	public T last() {

		if (currentIndex == null)
			return null;

		if (currentIndex != 0)
			currentIndex--;

		return navList.get(currentIndex);
	}

	/**
	 * Récuérer l'item suivant à l'item en cour
	 * 
	 * @return null si la listede navigation est vide. Item courant s'il n'est
	 *         plus possible de nagiuer
	 */
	public T next() {

		if (currentIndex == null)
			return null;

		if (currentIndex < navList.size() - 1)
			currentIndex++;

		return navList.get(currentIndex);
	}

	/**
	 * Ajouter un objet dans la liste de navigation
	 * 
	 * @param obj
	 *            Objet à ajouter
	 */
	public void add(T obj) {

		// Retier de la liste tou ce qui est au dessus de l'index courant
		if (currentIndex != null) {
			List<Integer> remove = new ArrayList<Integer>();
			for (int i = navList.size() - 1; i > currentIndex; i--) {
				remove.add(i);
			}
			
			for (Integer index : remove)
				navList.remove(index.intValue());

			currentIndex = navList.size();
		} else
			currentIndex = 0;

		navList.add(obj);
	}

	/**
	 * @return Objet courant dans la navigation
	 */
	public T getCurrent() {

		if (currentIndex == null)
			return null;

		return navList.get(currentIndex);
	}

	/**
	 * Supprimer un objet dans la liste de navigation
	 * 
	 * @param obj
	 *            Objet à supprimer
	 * @return Objet courant. Si c'est l'objet supprimé, la méthode retourne
	 *         l'objet précédent
	 */
	public T removeAndGetBefore(T obj) {
		return remove(obj, false);
	}

	/**
	 * Supprimer un objet dans la liste de navigation
	 * 
	 * @param obj
	 *            Objet à supprimer
	 * @return Objet courant. Si c'est l'objet supprimé, la méthode retourne
	 *         l'objet suivant
	 */
	public T removeAndGetAfter(T obj) {
		return remove(obj, true);
	}

	/**
	 * Supprimer un objet dans la liste de navigation
	 * 
	 * @param obj
	 *            Objet à supprimer
	 * @param after
	 *            Si true et que obj est l'objet supprimé, la méthode retourne
	 *            l'objet précédent.
	 * @return Nouvelle objet
	 */
	private T remove(T obj, boolean after) {

		// On supprime l'élément à null
		if (currentIndex == null)
			return null;

		// Retrouver les élément de même instance que "obj"
		List<T> newNavList = new ArrayList<T>();
		Integer newCurrentIndex = null;
		boolean exact = false;
		for (int i = 0; i < navList.size(); i++) {

			T item = navList.get(i);

			// Egalité sur l'instance
			if (item == obj){				
				if (currentIndex != null && currentIndex.intValue() == i)
					exact = true;
			}
			else{
				newNavList.add(item);
				
				if (currentIndex != null && currentIndex.intValue() >= i){
					if (newCurrentIndex == null)
						newCurrentIndex = 0;
					else
						newCurrentIndex++;
				}
			}
		}
		
		if (exact == true && newCurrentIndex != null)
			newCurrentIndex++;
		
		currentIndex = newCurrentIndex;
		navList = newNavList;
		
		// Nétoyer la liste pour ne pas avoir des éléments de même type consécutif
		T lastItem = null;
		List<Integer> lstRemove = new ArrayList<>();
		for (int i = 0; i < navList.size(); i++) {

			T item = navList.get(i);

			if (i == 0){
				lastItem = item;
				continue;
			}
			
			if (item == lastItem){
				lstRemove.add(i);
				
				if (currentIndex != null && i < currentIndex)
					currentIndex--;
			}
			
			lastItem = item;
		}
		// Suppression
		for (int i = lstRemove.size() - 1; i >= 0; i--)
			navList.remove(lstRemove.get(i).intValue());
		
		
		if (currentIndex.intValue() >= navList.size())
			currentIndex = navList.size() - 1;
		
		// S'il n'y a plus rien dans la liste
		if (navList.size() == 0) {
			currentIndex = null;
			return null;
		}


		return navList.get(currentIndex);
	}
}
