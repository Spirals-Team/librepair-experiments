
package coaching.solid;

/**
 * A class to Demonstrate the Interface Segregation Principle (ISP).
 *
 * Intent: Clients should not be forced to depend upon interfaces that they
 * don't use.
 *
 * @author martin.spamer.
 * @version 0.1 - first release.
 *          Created 13-Jan-2005 - 12:21:40
 */
public class InterfaceSegregationPrinciple {

    /**
     * Instead of one large interface;.
     */
    public interface Abstraction {
        
        /**
         * Work.
         */
        void work();

        /**
         * Rest.
         */
        void rest();

        /**
         * Play.
         */
        void play();

        /**
         * Learn.
         */
        void learn();
    }

    /**
     * Use separate multiple simple interfaces.
     *
     */
    public interface Work {
        
        /**
         * Work.
         */
        void work();
    }

    /**
     * The Interface Rest.
     */
    public interface Rest {
        
        /**
         * Rest.
         */
        void rest();
    }

    /**
     * The Interface Play.
     */
    public interface Play {
        
        /**
         * Play.
         */
        void play();
    }

    /**
     * The Interface Learn.
     */
    public interface Learn {
        
        /**
         * Learn.
         */
        void learn();
    }

    /**
     * The Class Person.
     */
    public abstract class Person implements Abstraction {
    }

    /**
     * The Class Teacher.
     */
    public class Teacher extends Person {
        
        /* (non-Javadoc)
         * @see coaching.solid.InterfaceSegregationPrinciple.Abstraction#work()
         */
        @Override
        public void work() {
            // redundant
        }

        /* (non-Javadoc)
         * @see coaching.solid.InterfaceSegregationPrinciple.Abstraction#rest()
         */
        @Override
        public void rest() {
            // redundant
        }

        /* (non-Javadoc)
         * @see coaching.solid.InterfaceSegregationPrinciple.Abstraction#play()
         */
        @Override
        public void play() {
            // redundant
        }

        /* (non-Javadoc)
         * @see coaching.solid.InterfaceSegregationPrinciple.Abstraction#learn()
         */
        @Override
        public void learn() {
            // do some learning
        }
    }

    /**
     * The Class Boss.
     */
    public class Boss extends Person {
        
        /* (non-Javadoc)
         * @see coaching.solid.InterfaceSegregationPrinciple.Abstraction#learn()
         */
        @Override
        public void learn() {
            // redundant
        }

        /* (non-Javadoc)
         * @see coaching.solid.InterfaceSegregationPrinciple.Abstraction#work()
         */
        @Override
        public void work() {
            // do some work
        }

        /* (non-Javadoc)
         * @see coaching.solid.InterfaceSegregationPrinciple.Abstraction#rest()
         */
        @Override
        public void rest() {
            // redundant
        }

        /* (non-Javadoc)
         * @see coaching.solid.InterfaceSegregationPrinciple.Abstraction#play()
         */
        @Override
        public void play() {
            // redundant
        }

    }

}
