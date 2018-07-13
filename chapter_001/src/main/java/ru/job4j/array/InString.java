package ru.job4j.array;

public class InString {

    /**
     * Отвечает на вопрос: Входит ли вторая строка в первую
     * @param origin первая строка
     * @param sub вторая строка
     * @return Ответ.
     *
     */

    boolean contains(String origin, String sub){
        char[] ch_origin=origin.toCharArray();
        char[] ch_sub=sub.toCharArray();
        boolean subs=false;
        if(origin!=null&&sub!=null&&origin.length()>=sub.length()){
            for(int i=0; !subs && i<(origin.length()-sub.length());  i++){
                if (ch_sub[0]==ch_origin[i]){
                    subs=true;
                    for(int j=1; subs&&j<sub.length();j++)
                        subs=ch_sub[j]==ch_origin[i+j];
                }
            }
        }
        return subs;
    }
}
