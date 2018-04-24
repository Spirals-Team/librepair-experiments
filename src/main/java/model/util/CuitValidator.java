package model.util;

/**
 * Created by mariano on 09/04/18.
 */
public class CuitValidator {


    public static Boolean isValid(String cuit){
        Boolean valid = Boolean.FALSE;
        if(cuit != null && !cuit.isEmpty() && cuit.length() == 11) {
            String prefix = Character.toString(cuit.charAt(0)) + Character.toString(cuit.charAt(1));
            if( prefix.equals("20") || prefix.equals("23") || prefix.equals("24") || prefix.equals("27") ||
                    prefix.equals("30") || prefix.equals("33") || prefix.equals("34") ){
                //la secuencia de valores de factor es 5, 4, 3, 2, 7, 6, 5, 4, 3, 2
                Integer factor = 5;
                Integer[] arrayCuit = new Integer[11];
                Integer result = 0;

                for (int i = 0; i < 10; i++) {
                    arrayCuit[i] = Integer.valueOf(Character.toString(cuit.charAt(i))).intValue();
                    result += arrayCuit[i] * factor;
                    factor = (factor == 2) ? 7 : factor - 1;
                }
                arrayCuit[10] = Integer.valueOf(Character.toString(cuit.charAt(10))).intValue();
                Integer control = (11 - (result % 11)) % 11;
                if(control == arrayCuit[10]){
                    valid = Boolean.TRUE;
                }
            }
        }
        return valid;
    }



    public static void main(String[] args) {
        String miCuit = "20320231680";
        System.out.println(isValid(miCuit));
    }


}
