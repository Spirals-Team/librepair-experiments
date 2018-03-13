package ru.job4j.start;

import javax.jws.soap.SOAPBinding;

abstract public  class BaseAction implements UserAction {

    String name;
    int key;

    public BaseAction(String name, int key) {
        this.name = name;
        this.key = key;
    };

    @Override
    public abstract int key();

    @Override
    public abstract void execute(Input input, Tracker tracker);

    public String info() {

        return String.format("%s. %s", this.key, this.name);

    };

//    public static void main(String[] args) {
//
//        new BaseAction("name", 1) {
//            @Override
//            public int key() {
//                return 0;
//            }
//
//            @Override
//            public void execute(Input input, Tracker tracker) {
//
//            }
//
//            @Override
//            public String info() {
//                return null;
//            }
//        };
//
//    }
}
