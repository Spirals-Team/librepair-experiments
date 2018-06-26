
package coaching.model;

public class Fuel implements FuelInterface {

    private int quantity = 0;
    
    public Fuel() {
        super();
    }

    @Override
    public void addFuel(int quantity) {
        this.quantity += quantity;
    }

    @Override
    public void useFuel(int quantity) {
        this.quantity += quantity;
    }

    @Override
    public String toString() {
        return String.format("%s [quantity=%s]", this.getClass().getSimpleName(), quantity);
    }
    
}
