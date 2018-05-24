package spoon.test.fieldaccesses;


public class TargetedAccessPosition {
    public spoon.test.fieldaccesses.TargetedAccessPosition ta;

    public void foo() {
        spoon.test.fieldaccesses.TargetedAccessPosition t = new spoon.test.fieldaccesses.TargetedAccessPosition();
        t.ta.ta = t;
    }
}

