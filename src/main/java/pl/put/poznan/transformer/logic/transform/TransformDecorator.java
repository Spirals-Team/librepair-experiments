package pl.put.poznan.transformer.logic.transform;

public abstract class TransformDecorator implements Transform {
    protected Transform decorated;

    public TransformDecorator(Transform decorated) {
        this.decorated = decorated;
    }

    @Override
    public String apply(String text) {
        return decorated.apply(text);
    }
}
