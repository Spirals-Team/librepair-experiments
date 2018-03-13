package ru.job4j.templates;

public abstract class TempalteAction {

    public TempalteAction(String name) {

    }

    abstract void start();

    abstract void fininsh();

    public void work() {
        this.start();
        this.fininsh();
    }

    public static void main(String[] args) {
        //new TempalteAction();
//        new CopyAction();

        new TempalteAction("asdf") {

            @Override
            public void start() {

            }

            @Override
            public void fininsh() {

            }

            @Override
            public void work() {
                super.work();
            }
        };
    }
}
