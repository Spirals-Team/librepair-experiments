
package coaching.solid;

/**
 * A class to Demonstrate the Dependency Inversion Principle (DIP).
 * 
 * Intent: High-level modules should remain independent of the implementation details of low-level module.
 * - High-level modules should not depend on low-level modules, and both should depend on abstractions.
 * - Abstractions should not depend on details, details should depend on abstractions.
 * 
 *  @author      martin.spamer.
 *  @version     0.1 - first release.
 *	Created      13-Jan-2005 - 12:07:53
 */
public class DependencyInversionPrinciple {

    /**
     * The Interface Abstraction.
     */
    public interface Abstraction {
        
        /**
         * Do something.
         */
        void doSomething();
    }

    /**
     * The Module class.
     */
    public abstract class Module implements Abstraction {
    }
    
    /**
     * The LowLevelModule.
     */
    public class LowLevelModule extends Module {
        
        /* (non-Javadoc)
         * @see coaching.solid.DependencyInversionPrinciple.Abstraction#doSomething()
         */
        @Override
        public void doSomething() {
        }
        
    }
    
    /**
     * The HighLevelModule.
     */
    public class HighLevelModule extends Module {
        
        /** The module. */
        public Abstraction module = new LowLevelModule();

        /* (non-Javadoc)
         * @see coaching.solid.DependencyInversionPrinciple.Abstraction#doSomething()
         */
        @Override
        public void doSomething() {
            module.doSomething();
        }
    }    
}
