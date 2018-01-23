package pl.put.poznan.transformer.logic.transform;

public class LowerTransform extends TransformDecorator {
    public LowerTransform(Transform decorated) {
        super(decorated);
    }

    @Override
    public String apply(String text) {
        return decorated.apply(text).toLowerCase();
    }
}
