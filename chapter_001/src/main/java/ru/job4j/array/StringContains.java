package ru.job4j.array;

/**
 * @author Alexander Kaleganov
 * @since 10.02.2018
 * рограмма проверяет что слово есть в друом слове
 */
public class StringContains {

    public boolean stringContainsValid(String origin, String sub) {
        boolean result = false;
        char[]origin1 = origin.toCharArray();
        System.out.println(origin1);
        char[]sub1 = sub.toCharArray();
        System.out.println(sub1);
        for (int i = 0; i < (origin1.length - sub1.length); i++) {
            if (sub1[0] == origin1[i]) {
                for (int j = 0; j < sub1.length; j++) {
                    if (sub1[j] == origin1[i + j]) {
                        result =  true;
                        System.out.println(result);
                        break;
                    } else {
                        result = false;
                    }
                } break;
            }
        }
        return result;
    }
}
