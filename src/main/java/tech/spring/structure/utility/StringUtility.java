package tech.spring.structure.utility;

public class StringUtility {

    public static String formalize(String propertyName) {
        StringBuilder formalNameBuilder = new StringBuilder();
        char[] characters = propertyName.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            Character character = characters[i];
            if (i == 0) {
                character = Character.toUpperCase(character);
            } else {
                if (Character.isUpperCase(character)) {
                    formalNameBuilder.append(" ");
                }
            }
            formalNameBuilder.append(character);
        }
        return formalNameBuilder.toString();
    }

}
