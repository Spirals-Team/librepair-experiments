package pl.put.poznan.transformer.logic.transform;

public class CapitalizeTransform extends TransformDecorator {
    public CapitalizeTransform(Transform decorated) {
        super(decorated);
    }

    @Override
    public String apply(String text) {
        return capitalize(decorated.apply(text));
    }

    private String capitalize(String text){
        char[] textArr = text.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < textArr.length; i++) {
            if (Character.isLetter(textArr[i])) {
                if(!found) {
                    textArr[i] = Character.toUpperCase(textArr[i]);
                    found = true;
                }
            } else found = false;
        }
        return String.valueOf(textArr);
    }
}
