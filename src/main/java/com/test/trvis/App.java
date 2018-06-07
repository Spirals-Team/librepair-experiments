package com.test.trvis;

public class App {
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        MyClass testClass = new MyClass();
        
        int testV = testClass.multiply (2,2);
        System.out.println(testV);
    }
}
