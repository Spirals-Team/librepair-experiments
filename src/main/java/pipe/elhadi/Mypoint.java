package pipe.elhadi;

/**
 * Classe point
 * @author elhadibouchaour
 *
 */
public class Mypoint implements Comparable{

	private double x, y;
	/**
	 * constructeur de classe
	 */
	Mypoint()
	{
		this.x = Double.NaN;
		this.y = Double.NaN;
	}
	
	/**
	 * constructor de classe
	 * @param x
	 * @param y
	 */
	public Mypoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return this.x; }
    public double getY() { return this.y; }
       
    /**
     * Cette methode permet de comparer deux objets (points)
     * @param Object o : l'autre point à comparer
     *  retourne 3 valeurs (-1,0,1)
     */
    public int compareTo(Object o) {
    	Mypoint p = (Mypoint) o;
        double a = p.getX();
        if (this.x < a)  return -1;
        else if(this.x == a) return 0;
        else return 1;
    }

 
	/**
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}





}
