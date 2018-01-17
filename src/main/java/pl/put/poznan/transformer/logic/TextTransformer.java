package pl.put.poznan.transformer.logic;

import pl.put.poznan.transformer.logic.transform.FinalTransform;
import pl.put.poznan.transformer.logic.transform.Transform;
import pl.put.poznan.transformer.logic.transform.TransformFactory;

public class TextTransformer {

    private final Transform transform;

    public TextTransformer(String[] transforms) {
        Transform parentTransform = new FinalTransform();
        for (String transformName : transforms) {
            parentTransform = TransformFactory.getTransform(transformName, parentTransform);
        }
        this.transform = parentTransform;
    }

    public String transform(String text) {
        return transform.apply(text);
    }
}
