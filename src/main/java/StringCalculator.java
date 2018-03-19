public class StringCalculator {

    private static final String EMPTY_STRING = "";

    public int Add(String numbers) {
        if (numbers.equals(EMPTY_STRING)) {
            return 0;
        } else {
            String list[];
            Integer newList[] = null;
            list = numbers.split(",");
            int summary = 0;
            for (String i : list) {
                summary += Integer.parseInt(i);
            }
            return summary;
        }
    }
}

