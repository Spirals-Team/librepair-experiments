package pl.put.poznan.transformer.logic.transform;

public class NumberToWordTransform extends TransformDecorator {

    public NumberToWordTransform(Transform decorated) {
        super(decorated);
    }

    @Override
    public String apply(String text) {
        String [] splited = decorated.apply(text).split(" ");

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < splited.length; i++){
            if (splited[i].matches("^-?\\d+[\\.\\,\\!\\?]?$|^-?\\d+.\\d+[\\.\\,\\!\\?]?$")) // Jesli znaleziono wzorzec to przetworz
            {
                char ch = '\0';
                if (splited[i].endsWith(".") || splited[i].endsWith("!") || splited[i].endsWith("?") || splited[i].endsWith(",")) {
                    ch = splited[i].charAt(splited[i].length() - 1);
                    splited[i] = splited[i].substring(0, splited[i].length() - 1);
                }
                output.append(numberToWords(splited[i]));
                if (ch!='\0') output.append(ch);
            }
            else output.append(splited[i]); // jesli nie znaleziono wzorca to przepisz pierwotna wartosc

            if (i < splited.length-1) output.append(" "); // Dodaj skasowane spacje
        }
        return output.toString();
    }

    private static String numberToWords(String number){
        String convertedIntegerPart = "";
        String convertedFractionalPart = "";

        String sign = number.startsWith("-")?"minus ":"";

        if (!sign.isEmpty()) number = number.substring(1);

        try {
            if (number.contains(".")) {
                convertedIntegerPart = unitsToWords(Long.parseLong(number.substring(0, number.indexOf('.'))));
                convertedFractionalPart = fractionalPartToWords(number.substring(number.indexOf('.') + 1));
            }
            else convertedIntegerPart = unitsToWords(Long.parseLong(number));

            String output = sign;

            if (convertedIntegerPart.equals("zero") && convertedFractionalPart.length()>0)
                output+=convertedFractionalPart;
            else if (convertedFractionalPart.length()==0){
                if (convertedIntegerPart.equals("zero"))
                    output = "";
                output+=convertedIntegerPart;
            }
            else output+=convertedIntegerPart+" i "+convertedFractionalPart;

            return output;
        }
        catch (NumberFormatException e) // jeśli zlapie wyjatek zwroc podana wartosc
        {
            return number;
        }
    }
    private static String fractionalPartToWords(String number){
        if (number.matches("^0+$"))
            return "";
        if (number.length()>2)
            throw new NumberFormatException();

        while (number.endsWith("0"))
            number = number.substring(0,number.length()-1);

        StringBuilder output = new StringBuilder();
        if (number.length() == 1)
            output.append(unitsInflected[Character.getNumericValue(number.charAt(0))]);
        else
        {
            if (number.startsWith("1"))
                output.append(teens[Character.getNumericValue(number.charAt(1))]);
            else if (number.startsWith("0") || number.endsWith("2"))
            {
                output.append(tens[Character.getNumericValue(number.charAt(0))]);
                output.append(unitsInflected[Character.getNumericValue(number.charAt(1))]);
            }
            else{
                output.append(tens[Character.getNumericValue(number.charAt(0))]);
                output.append(units[Character.getNumericValue(number.charAt(1))]);
            }
        }

        if (number.equals("1") || number.equals("01"))
            output.append(fraction[number.length()-1][0]);
        else if ((number.endsWith("2")|| number.endsWith("3")|| number.endsWith("4")) && ! number.startsWith("1"))
            output.append(fraction[number.length()-1][1]);
        else
            output.append(fraction[number.length()-1][2]);
        return output.toString().substring(0,output.length()-1);
    }


    public static String unitsToWords(long number) {
        long j = 0/* jedności */, n = 0/* nastki */, d = 0/* dziesiątki */, s = 0/* setki */, g = 0/* grupy */, k = 0/* końcówwki */;

        String output = "";

        if (number == 0)
            return "zero";

        while (number != 0) {
            s = number % 1000 / 100;
            d = number % 100 / 10;
            j = number % 10;

            if (d == 1 & j > 0)
            {
                n = j;
                d = 0;
                j = 0;
            } else {
                n = 0;
            }

            if (j == 1 & s + d + n == 0) {
                k = 0;
                if (s + d == 0 && g > 0)
                {
                    j = 0;
                    output = groups[(int) g][(int) k] + output;
                }
            } else if (j >1 && j < 5) {
                k = 1;
            }
            else {
                k = 2;
            }


            if (s+d+n+j > 0) {
                output = hundreds[(int) s] + tens[(int) d] + teens[(int) n]
                        + units[(int) j] + groups[(int) g][(int) k] + output;
            }

            number = number / 1000;
            g = g + 1;
        }
        return output.substring(0,output.length()-1);
    }

    private static String[][] fraction = { { "dziesiąta ", "dziesiąte ","dziesiątych "},
            { "setna ", "setne ","setnych "}};
    private static String[] unitsInflected= { "", "jedna ", "dwie ", "trzy ", "cztery ",
            "pięć ", "sześć ", "siedem ", "osiem ", "dziewięć ", };

    private static String[] units = { "", "jeden ", "dwa ", "trzy ", "cztery ",
            "pięć ", "sześć ", "siedem ", "osiem ", "dziewięć ", };

    private static String[] teens = { "", "jedenaście ", "dwanaście ", "trzynaście ",
            "czternaście ", "piętnaście ", "szesnaście ", "siedemnaście ",
            "osiemnaście ", "dziewiętnaście ", };

    private static String[] tens = { "", "dziesięć ", "dwadzieścia ",
            "trzydzieści ", "czterdzieści ", "pięćdziesiąt ",
            "sześćdziesiąt ", "siedemdziesiąt ", "osiemdziesiąt ",
            "dziewięćdziesiąt ", };

    private static String[] hundreds = { "", "sto ", "dwieście ", "trzysta ", "czterysta ",
            "pięćset ", "sześćset ", "siedemset ", "osiemset ",
            "dziewięćset ", };

    private static String[][] groups = { { "", "", "" },
            { "tysiąc ", "tysiące ", "tysięcy " },
            { "milion ", "miliony ", "milionów " },
            { "miliard ", "miliardy ", "miliardów " },
            { "bilion ", "biliony ", "bilionów " },
            { "biliard ", "biliardy ", "biliardów " },
            { "trylion ", "tryliony ", "trylionów " }, };
}
