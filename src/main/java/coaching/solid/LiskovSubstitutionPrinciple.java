
package coaching.solid;

/**
 * A class to Demonstrate the Liskov Substitution Principle (LSP).
 * 
 * Intent:  Derived types must be completely substitutable for their base types.
 * 
 *  @author			martin.spamer.
 *  @version		0.1 - first release.
 *	Created			13-Jan-2005 - 12:08:34
 */
public class LiskovSubstitutionPrinciple {

    public interface Abstraction {
        void doSomething();
        }
    
    public abstract class BaseType implements Abstraction {
        @Override
        public abstract void doSomething();
    }

    public class TypeOne extends BaseType{
        @Override
        public void doSomething() {
            doOneThing();
        }
        private void doOneThing() {
            // do one thing;
        }
    }

    public class TypeType extends BaseType{
        @Override
        public void doSomething() {
            doSomethingElse();
        }

        private void doSomethingElse() {
            // do something else
        }
    }
}
