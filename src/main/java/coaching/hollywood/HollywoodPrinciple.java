package coaching.hollywood;

public class HollywoodPrinciple {

    public void dontCallUs() {
        try {
            throw new Exception();
        } catch (Exception e) {
            // increased coupling, two points of contact, two reasons to change.
            String msg = e.getLocalizedMessage();
            System.out.println(msg);
        }
    }
    
    public void wellCallYou() {
        try {
            throw new Exception();
        } catch (Exception e) {
            // decreased coupling, one point of contact, one reason to change.
            e.printStackTrace(System.out);
        }
    }    
}
