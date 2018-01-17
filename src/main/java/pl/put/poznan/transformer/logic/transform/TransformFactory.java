package pl.put.poznan.transformer.logic.transform;

public final class TransformFactory {
    /**
     * This class creates new TransformTypes based on it's name and link it with rest of the chain.
     *
     * @param transformName name of transformation to be created
     * @param parentTransform prevous transformation in transformation chain. First transformation from the chain
     *                        have to be FinalTransform.
     * @return chain of transformations with new one on the top
     */
    public static Transform getTransform(String transformName, Transform parentTransform) {
        try {
            TransformTypes transformType = TransformTypes.valueOf(transformName);
            return (Transform) transformType.getClazz().getConstructor(Transform.class).newInstance(parentTransform);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid transformation name: " + transformName);
        }
    }
}
