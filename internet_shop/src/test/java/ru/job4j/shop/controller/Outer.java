package ru.job4j.shop.controller;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 02.03.2018.
 * @version $Id$.
 * @since 0.1.
 */

    public class Outer {

        private class Inner {
            private String privateField = "AAA";
            private void print() {
                System.out.println(this.privateField);
            }
        }
    }

