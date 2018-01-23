package pl.put.poznan.transformer.logic.transform;

public class UpperTransform extends TransformDecorator {

    public UpperTransform(Transform decorated) {
        super(decorated);
    }

    @Override
    public String apply(String text) {
        return decorated.apply(text).toUpperCase();
    }
}
