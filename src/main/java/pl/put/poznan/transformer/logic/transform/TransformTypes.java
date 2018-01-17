package pl.put.poznan.transformer.logic.transform;

public enum TransformTypes {
    upper(UpperTransform.class),
    lower(LowerTransform.class),
    inverse(InverseTransform.class),
    capitalize(CapitalizeTransform.class),
    numberToWord(NumberToWordTransform.class),
    removeRepetitions(RemoveRepetitionsTransform.class);

    Class transform;

    TransformTypes(Class<? extends Transform> transform) {
        this.transform = transform;
    }

    public Class getClazz() {
        return transform;
    }
}
