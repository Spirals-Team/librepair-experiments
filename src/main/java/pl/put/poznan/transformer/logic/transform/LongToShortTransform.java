package pl.put.poznan.transformer.logic.transform;

public class LongToShortTransform extends TransformDecorator {

    public LongToShortTransform(Transform decorated) {
        super(decorated);
    }

    @Override
    public String apply(String text) {
        text = decorated.apply(text);
        text = text.replaceAll("\\bmiędzy innymi\\b","m.in.");
        text = text.replaceAll("\\bMiędzy innymi\\b","M.in.");
        text = text.replaceAll("\\bi tym podobne\\b","itp.");
        text = text.replaceAll("\\bI tym podobne\\b","Itp.");
        text = text.replaceAll("\\bna przykład\\b","np.");
        text = text.replaceAll("\\bNa przykład\\b","Np.");
        return text;
    }
}
