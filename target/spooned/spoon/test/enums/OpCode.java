package spoon.test.enums;


public enum OpCode implements spoon.test.enums.Performable {
    PUSH(1) {
        public void perform(java.util.Stack<java.lang.Integer> s, int[] op) {
            s.push(op[0]);
        }
    }, ADD(0) {
        public void perform(java.util.Stack<java.lang.Integer> s, int[] op) {
            s.push(((s.pop()) + (s.pop())));
        }
    };
    OpCode(int numOp) {
        this.numOp = numOp;
    }

    private int numOp;

    private spoon.test.enums.OpCode OP;

    public int dummy() {
        return (OP.numOp) + (numOp);
    }
}

