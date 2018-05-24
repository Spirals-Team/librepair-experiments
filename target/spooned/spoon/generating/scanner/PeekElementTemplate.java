package spoon.generating.scanner;


class PeekElementTemplate {
    java.util.Deque<spoon.reflect.declaration.CtElement> stack;

    public void statement() {
        spoon.reflect.declaration.CtElement other = stack.peek();
    }
}

