
package coaching.solid;

/**
 * A class to Demonstrate the Interface Segregation Principle (ISP).
 * 
 * Intent: Clients should not be forced to depend upon interfaces that they don't use.
 * 
 *  @author			martin.spamer.
 *  @version		0.1 - first release.
 *	Created			13-Jan-2005 - 12:21:40
 */
public class InterfaceSegregationPrinciple {

    /**
     * Instead of one large interface;
     */
    public interface Abstraction {
        void work();
        void rest();
        void play();
        void learn();
    }

    /**
     * Use separate multiple simple interfaces.
     *
     */
    public interface Work {
        void work();
    }
    
    public interface Rest {
        void rest();
    }
    
    public interface Play {
        void play();
    }

    public interface Learn {
        void learn();
    }
    
    public abstract class Person implements Abstraction {
    }

    public class Teacher extends Person {
        @Override
        public void work() {
            // redundant
        }

        @Override
        public void rest() {
            // redundant
        }

        @Override
        public void play() {
            // redundant
        }

        @Override
        public void learn() {
            // do some learning            
        }
    }
    
    public class Boss extends Person {
        @Override
        public void learn() {
            // redundant
        }

        @Override
        public void work() {
            // do some work            
        }

        @Override
        public void rest() {
            // redundant
        }

        @Override
        public void play() {
            // redundant
        }

    }
   
}
