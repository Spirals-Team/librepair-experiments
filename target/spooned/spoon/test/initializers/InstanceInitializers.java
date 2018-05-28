package spoon.test.initializers;


public class InstanceInitializers {
    {
        x = 3;
    }

    int x;

    int y = 3;

    java.lang.Integer z = 5;

    java.util.List<java.lang.Double> k = new java.util.ArrayList<java.lang.Double>();

    java.util.List<java.lang.Double> l = new java.util.ArrayList<java.lang.Double>() {
        private static final long serialVersionUID = 1L;

        static final double PI = 3.14;

        final double PI2 = 3.14;

        double PI3 = 3.14;

        {
            add(12.0);
            add(15.0);
            add(PI);
            add(PI2);
            add(PI3);
        }
    };
}

