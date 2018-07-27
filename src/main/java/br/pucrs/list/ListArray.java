package br.pucrs.list;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
/****************8888
 * 
 * @author mcmor
 *
 * Comentário.
 */
public class ListArray <D extends Comparable<D>> implements ListTAD<D>, Serializable {
	private D [] vect;
	private int qtdElem = 0;
	private int corrente = -1;
	
	private class Iterador implements Iterator<D>{
        private int position = 0;

        public boolean hasNext() { return (position != qtdElem); }
        
        public D next() {
            if(position == qtdElem)
                throw new NoSuchElementException();
            D item = vect[position];
            position++;
            return item;
        }

        public void remove() { throw new UnsupportedOperationException();  }
    }

	public Iterator<D> iterator() {
		return new Iterador();
	}
	private void increaseVet()
	{
		D[] newVet = (D[]) new Comparable[(int)(vect.length * 1.5)];
		
		System.arraycopy(vect, 0, newVet, 0, vect.length);
		
		vect = newVet;
	}
	
	public ListArray(int n)
	{
		int tam = n;
		if (n <= 0)
			tam = 100;
		
		vect = (D[]) new Comparable[tam];
	}
	
	public boolean set(int index, D elem)
	{
		boolean res = true;
		
		if ((index >= 0) && (index < this.size()))
			{
				vect[index] = elem;
				corrente = index;
			}
		else
			res = false;
		
		return res;
	}
	
	public D get(int index)
	{
		D res = null; 
		
		if ((index >= 0) && (index < this.size()))
			{
				res = vect[index];
				corrente = index;
			}
		else
			throw new IndexOutOfBoundsException();
		
		return res;
	}
	
	public boolean add(int index, D elem)
	{
		int i;
		boolean res = true;
		
		if (this.size() == vect.length)
			increaseVet();
		
		if ((index >= 0) && (index <= this.size()))
		{
			for (i = this.size(); i > index; i--)
				vect[i] = vect[i-1];
			
			vect[index] = elem;
			corrente = index;
			qtdElem++;
		}
		else
			res = false;
		
		return res;
	}
	
	public boolean add(D elem)
	{
		return this.add(this.size(), elem);
	}
	
	public D remove(int index)
	{
		int i;
		D res;
		
		if ((index >= 0) && (index < this.size()))
		{
			res = vect[index];
			
			for (i = index; i < this.size()-1; i++)
				vect[i] = vect[i+1];
			
			corrente = -1;
			qtdElem--;
		}
		else
			throw new IndexOutOfBoundsException();
	
		return res;
	}
	
	public boolean isEmpty()
	{
		boolean res = false;
		
		if (this.size() == 0)
			res = true;
		
		return res;
	}
	
	public D getNext()
	{
		D res = null;  
		
		if ((corrente >= 0) && (corrente < this.size()-1))
			{
				corrente = corrente + 1;
				res = vect[corrente];
			}

		return res;
	}
	
	public int size()
	{
		return qtdElem;
	}

	public D remove(D element) {
		// TODO Auto-generated method stub
		return null;
	}

	public D search(D ob) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean addFirst(D d) {
		return this.add(0, d);
	}
	
	public boolean addLast(D d) {
		return this.add(d);
	}
	
	public void clean() {
		qtdElem = 0;
	}
	
	public int count(D element) {
		int res = 0;
		
		for (int i = 0; i < this.size(); i++)
			if (element.equals(vect[i]))
				res++;
		return res;
	}
	
	public D getFirst() {
		D res = null;
		
		if (this.size() > 0)
			res = vect[0];
		
		return res;
	}
	
	public D getLast() {
		D res = null;
		
		if (this.size() > 0)
			res = vect[this.size()-1];
		
		return res;
	}
	
	public D removeFirst() {
		D res = null;
		
		if (this.size() > 0) {
			res = vect[0];
			
			for (int i = 0; i < this.size()-1; i++)
				vect[i] = vect[i+1];
			
			qtdElem--;
		}
			
		return res;
	}
	
	public D removeLast() {
		D res = null;
		
		if (this.size() > 0) {
			res = vect[this.size() - 1];
			qtdElem--;
		}
		
		return res;
	}
	
	public String toString() {
		String res = "";
		
		for(int i = 0; i < this.size(); i++)
			res = res + vect[i].toString();
		
		return res;
	}
}
