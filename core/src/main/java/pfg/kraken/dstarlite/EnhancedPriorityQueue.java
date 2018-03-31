/*
 * Copyright (C) 2013-2018 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

package pfg.kraken.dstarlite;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Une priority queue qui permet de faire percoler
 * À la racine il y a le nœud de plus petite clé
 * Exceptionnellement, les indices commencent à 1 pour simplifier les calculs.
 * 
 * @author pf
 *
 */

public class EnhancedPriorityQueue
{
	private DStarLiteNode[] tab;
	private int firstAvailable = 1;
//	private DStarLiteNode lastInserted = null;

	public boolean check()
	{
		// Tout nœud doit avoir une clé plus grande ou égale que celle de son père
		for(int i = 2; i < firstAvailable; i++)			
			if(tab[i].cle.lesserThan(tab[pere(i)].cle))
				return false;
		return true;
	}
	
	public EnhancedPriorityQueue(int size)
	{
		tab = new DStarLiteNode[size+1]; // +1 because index starts at 1 
	}
	
	/**
	 * Renvoie la racine et la supprime
	 * 
	 * @return
	 */
	public DStarLiteNode poll()
	{
/*		if(tab[1] == null || (lastInserted != null && tab[1].cle.greaterThan(lastInserted.cle)))
		{
			DStarLiteNode tmp = lastInserted;
			lastInserted = null;
			return tmp;
		}
		else*/
		assert firstAvailable > 1;
		{
			DStarLiteNode out = tab[1];
			tab[1] = tab[--firstAvailable];
			tab[1].indexPriorityQueue = 1;
			percolateDown(tab[1]);
			assert check();
			return out;
		}
	}

	/**
	 * Renvoie la racine
	 * 
	 * @return
	 */
	public DStarLiteNode peek()
	{
		assert firstAvailable > 1;
//		if(tab[1] == null || (lastInserted != null && tab[1].cle.greaterThan(lastInserted.cle)))
//			return lastInserted;
		return tab[1];
	}

	/**
	 * Vide la file
	 */
	public void clear()
	{
		firstAvailable = 1;
//		lastInserted = null;
	}

	/**
	 * Échange deux nœuds
	 * 
	 * @param index1
	 * @param index2
	 */
	private final void swap(int index1, int index2)
	{
		DStarLiteNode tmp = tab[index1];
		tab[index1] = tab[index2];
		tab[index2] = tmp;
		tab[index1].indexPriorityQueue = index1;
		tab[index2].indexPriorityQueue = index2;
	}

	/**
	 * Récupère l'indice du fils gauche
	 * 
	 * @param index
	 * @return
	 */
	private final int filsGauche(int index)
	{
		return 2 * index;
	}

	/**
	 * Récupère l'indice du fils droit
	 * 
	 * @param index
	 * @return
	 */
	private final int filsDroit(int index)
	{
		return 2 * index + 1;
	}

	/**
	 * Récupère l'indice du père
	 * 
	 * @param index
	 * @return
	 */
	private final int pere(int index)
	{
		return index >> 1;
	}
	
	/**
	 * Ajoute un nœuds au tas
	 * 
	 * @param node
	 */
	public void add(DStarLiteNode node)
	{
//		DStarLiteNode tmp = lastInserted;
//		lastInserted = node;
		DStarLiteNode tmp = node;
//		if(tmp != null)
		{
			tab[firstAvailable] = tmp;
			tmp.indexPriorityQueue = firstAvailable;
			firstAvailable++;
			percolateUp(tmp);			
			assert check();
		}
	}

	/**
	 * Percolate-down ce nœud
	 * 
	 * @param node
	 */
	public void percolateDown(DStarLiteNode node)
	{
//		if(lastInserted == node)
//			add(null); // pour ajouter lastInserted dans le tas (fait automatique le percolateUp)
//		else
		{
			DStarLiteNode n = node;
			int fg, diff;
			// diff < 0 si aucun enfant, diff = 0 si un enfant (le gauche), diff > 0
			// si deux enfants
			while((diff = (firstAvailable - 1 - (fg = 2 * n.indexPriorityQueue))) >= 0)
			{
				if(diff > 0 && tab[fg].cle.greaterThan(tab[fg + 1].cle))
					fg++;
				if(n.cle.greaterThan(tab[fg].cle))
					swap(fg, n.indexPriorityQueue);
				else
					return;
			}
		}
	}

	/**
	 * Supprime un nœud qui n'est pas forcément la racine
	 * 
	 * @param node
	 */
	public void remove(DStarLiteNode node)
	{
		assert firstAvailable > 1;
//		if(lastInserted == node)
//			lastInserted = null;
//		else
		{
			DStarLiteNode last = tab[--firstAvailable];
			tab[node.indexPriorityQueue] = last;
			last.indexPriorityQueue = node.indexPriorityQueue;
	
			if(node.cle.lesserThan(last.cle)) // ce qui a remplacé node est plus
												// grand : on descend
				percolateDown(last);
			else
				percolateUp(last);
			assert check();
		}
	}

	/**
	 * Percolate-up ce nœud
	 * 
	 * @param node
	 */
	public void percolateUp(DStarLiteNode node)
	{
//		if(lastInserted == node)
//			add(null); // pour ajouter lastInserted dans le tas (fait automatique le percolateUp)
//		else
		{
			int p;
			// si ce nœud n'est pas la racine et que son père est plus grand : on
			// inverse
			while(node.indexPriorityQueue > 1 && tab[p = pere(node.indexPriorityQueue)].cle.greaterThan(tab[node.indexPriorityQueue].cle))
				swap(node.indexPriorityQueue, p);
		}
	}

	public boolean contains(DStarLiteNode node)
	{
//		if(lastInserted == node)
//			return true;
		for(int i = 1; i < firstAvailable; i++)
			if(tab[i] == node)
				return true;
		return false;
	}
	
	/**
	 * Renvoie "true" si la file est vide
	 * 
	 * @return
	 */
	public boolean isEmpty()
	{
		return firstAvailable == 1;// && lastInserted == null;
	}

	/**
	 * Sauvegarde dans un fichier la priority queue sous format graphique
	 */
	public void print(int nb)
	{
		FileWriter fw;
		try
		{
			fw = new FileWriter(new File("priority-queue-" + nb + ".dot"));
			try
			{
				fw.write("digraph priorityqueue {\n");

				for(int i = 0; i < firstAvailable; i++)
				{
					fw.write(i + "[label=\"" + tab[i].cle + "\"];\n");
					if(filsGauche(i) < firstAvailable)
						fw.write(i + " -> " + filsGauche(i) + " [label=\"g\"];\n");
					if(filsDroit(i) < firstAvailable)
						fw.write(i + " -> " + filsDroit(i) + " [label=\"g\"];\n");
				}
				fw.write("}\n");
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				fw.close();
			}
		}
		catch(IOException e1)
		{
			e1.printStackTrace();
		}

	}
}
