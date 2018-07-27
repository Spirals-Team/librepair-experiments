package br.pucrs.list;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListSimpleLinked <D extends Comparable<D>> implements ListTAD<D>, Serializable, Iterable<D> {

	/**
	 *  Comentário
	 */
	private static final long serialVersionUID = 1L;

	public class Node implements Serializable, Comparable<Node> {		

	    private Node refNext;
	    private D it;
	    
	    public Node()
	    {
	    	int inutil;
	    	refNext = null;
	    	it = null;
	    }
	    
	    public Node(D elem, Node n)
	    {
	    	this.it = elem;
	    	refNext = n;
	    }

	    public void setNext(Node no) 
	    	{ refNext = no; }
	 
	    public Node getNext() 
	    	{ return refNext; }
	    
	    public void setElem(D o) 
	    	{ it = o; }
	    
	    public D getElem() 
	    	{ return it; }

		public int compareTo(Node n) {
			return this.getElem().compareTo(n.getElem());
		}
	}
	
    private Node refHead = null;
    private Node refTail = null;
    private int qtdElem = 0;
    
    private class IteradorLSL implements Iterator<D>{
    	private  Node proximo;
    	
    	public IteradorLSL(){
    		proximo = refHead;
    	}
    	
    	public boolean hasNext() {
    		boolean res = true;
    		
    		if (proximo == null)
    			res = false;
    		
    		return res;
    	}
    	
    	public D next() {
    		D res = null;
    		
    		if (proximo != null) {
    			res = proximo.getElem();
    			proximo = proximo.getNext();
    		}
    		
    		return res;
    	}
    	
    	public void remove() {
    		throw new UnsupportedOperationException();
    	}
    }
    
		
	public boolean add(D element)
	{
		Node novo = new Node(element, null);
		boolean res = true;
		
		if (refHead == null)
		{
			refHead = refTail = novo;
		}
		else
		{
			refTail.setNext(novo);
			refTail = novo;
		}
		
		qtdElem++;
		return res;
	}

	public boolean addFirst(D d) {
		Node novo = new Node(d, refHead);
		boolean res = true;
		
		if (refHead == null)
			refTail = novo;
		
		refHead = novo;
		
		qtdElem++;
		return res;
	}

	public boolean addLast(D d) { 
		Node novo = new Node(d, null);
		boolean res = true;
		
		if (refTail == null)
			refHead = refTail = novo;
		else {
			refTail.setNext(novo);
			refTail = novo;
		} 
			

			
		qtdElem++;
		return res;
	}


	
	public boolean add(int index, D element)
	{
		Node ant, novo, prox = null;
		int i;
		boolean res = true;
		
		if ((index >= 0) && (index <= qtdElem)) {
			novo = new Node();
			novo.setElem(element);
	
			if (index == 0) {
				novo.setNext(refHead);
				if (refTail == null)
					refTail = novo;
				refHead = novo;
			}
			else {
					ant = refHead;
					for (i = 1; i < index; i++)
						ant = ant.getNext();
					
					if (index == qtdElem)
						refTail = novo;
					prox = ant.getNext();
					ant.setNext(novo);
					novo.setNext(prox);
			}
			qtdElem++;
		}
		else
			res = false;
		
		return res;
	}

	public D removeFirst() {
		D auxD = null;
		
	    if(refHead != null)
	       {
	    	auxD = refHead.getElem();
	    	
	        refHead = refHead.getNext();
	        if (refHead == null)
	        	refTail = null;
	
	        qtdElem--;
	       }
	    
	    return auxD;
	}

	public D removeLast() {
	D auxD = null;
	Node aux;
	
	if(refHead != null) 
	   {
		if(refHead.getNext() == null)
		  {
			auxD = refHead.getElem();
	        refHead = null;    
	        refTail = null;
		  }
	
	    else 
	      {
	       aux = refHead;
	       while(aux.getNext().getNext() != null) 
	    	   aux = aux.getNext();
	       
	       auxD = aux.getNext().getElem();
	       aux.setNext(null);
	       refTail = aux;
	      }
	    qtdElem--;
	   }
	return auxD;
	}

	public D remove(int index) {
		Node aux, ant, prox;
		D auxD = null;
		int i;
		
		if ((index >=0) && (index < qtdElem))
		{
			if (index == 0)
			{
				auxD = refHead.getElem();
				refHead = refHead.getNext();
				
				if (refHead == null)
					refTail = null;
			}
			else
			{
				ant = aux = refHead;
				for (i = 1; i <= index; i++)
				{
					ant = aux;
	   				aux = aux.getNext();
				}
	
				auxD = aux.getElem();
				prox = aux.getNext();
				
				ant.setNext(prox);
				
				if (aux == refTail)
					refTail = ant;
			}
			
			qtdElem --;
		}    	
		else
			throw(new IndexOutOfBoundsException());
		
	return auxD;
	}

	public D remove(D element) {
		D res = null;
		Node aux, ant; 
		
		ant = aux = refHead;
		while(aux != null && !aux.getElem().equals(element)) {
			ant = aux;
			aux = aux.getNext();
		}
		
		if (aux != null) {
			res = aux.getElem();
			qtdElem--;
			
			if (qtdElem == 0)
				refHead = refTail = null;
			else {
				  if (aux == refHead) 
					  refHead = refHead.getNext();
				  else
					  ant.setNext(aux.getNext());
				
				  if (aux == refTail)
					  refTail = ant;
			}
		}
		
		return res;
	}
	
	public int removeAll(D dado) {
		int res = 0;
		Node aux, ant;
		if (refHead != null) {
			aux = ant = refHead;
			while(aux != null) {
				if (aux.getElem().equals(dado)){
					if (refHead == aux){
						refHead = aux.getNext();
						aux = ant = refHead; }
					else ant.setNext(aux.getNext());
					if (refTail == aux)
						refTail = ant;
					res++;
					qtdElem--;
				}
				else ant = aux;

				aux = aux.getNext();
			}
		}
		return res;
	}
	
	public boolean addAll(D[] ar){
		boolean res = true;
		
		if (ar == null || ar.length == 0)
			res = false;
		else
			for (D x:ar)
				add(x);
		
		return res;
	}

	public D getFirst() {
		D res = null;
		
		if (refHead != null)
			res = refHead.getElem();
		
		return res;
	}

	public D getLast() {
		D res = null;
		
		if (refTail != null)
			res = refTail.getElem();
		
		return res;
	}

	public D get(int pos){
		D res = null;
		Node aux;
		int i;
		
	    if ((pos >= 0) && (pos < qtdElem))
	       {
		    if(refHead != null) 
		       {
	            aux = refHead;
	            for(i = 1 ; i <= pos; i++)
	               aux = aux.getNext();
	        
	            res = aux.getElem();
	           }
		    }
		else 
			throw(new IndexOutOfBoundsException());
	    
	    return res;
	}

	public boolean set(int index, D element) {
		Node aux = refHead;
		boolean res = true;
		
		for (int i = 0; aux != null && i < index; i++)
			aux = aux.getNext();
		
		if (aux != null)
			aux.setElem(element);
		else
			res = false;
		
		return res;
	}

	public D search(D ob)
	{
		Node aux;
		D res = null;
		
		aux = refHead;
		while ((aux != null) && (aux.getElem().compareTo(ob) != 0))
			aux = aux.getNext();
		
		if (aux != null)
			res = aux.getElem();
		
		return res;
	}

/*	public Iterator<D> iterator() {
		return new Iterador();
	}
*/
	public Iterator<D> iterator() {
		return new IteradorLSL();
	}
	
    public int size() {
    	return qtdElem;
    }    
    
    public boolean isEmpty() {
    	boolean res;
    	
        if(refHead == null) 
        	res = true;
        else 
        	res = false;
        
        return res;
    }
    
    public int count(D dado)
    {
    	int cont = 0;
    	Node aux = refHead;
    	
    	while(aux != null)
    	{
    		if (dado.equals(aux.getElem()))
    			cont++;
    		aux = aux.getNext();
    	}
    	
    	return cont;
    }
    
    public void clean() {
		refHead = refTail = null;
		qtdElem = 0;
	}

	public void reverse()
	{
		Node novaLista = null, aux;
		
		while (!this.isEmpty())
		{
			 aux = refHead;
			 refHead = refHead.getNext();
			 aux.setNext(novaLista);
			 novaLista = aux;
		}
		
		refHead = novaLista;
	}

	public String toString() {
		Node aux = refHead;
		String res = "";
		int tot = size();
		
		for (int i = 0; i < tot; i++) {
			res = res + aux.getElem().toString();
			aux = aux.getNext();
		}
		
		return res;
	}
}