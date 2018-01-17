package pl.put.poznan.transformer.logic.transform;

public class InverseTransform extends TransformDecorator {
    public InverseTransform(Transform decorated) {
        super(decorated);
    }

    @Override
    public String apply(String text) {
        return inverse(decorated.apply(text));
    }

    private String inverse(String text){
        char[] inReverse = new StringBuilder(text).reverse().toString().toLowerCase().toCharArray();
        for (int i = 0; i < text.length(); i++){
            if (Character.isUpperCase(text.charAt(i))){
                inReverse[i] = Character.toUpperCase(inReverse[i]);
            }
        }
        return String.valueOf(inReverse);
    }
}
