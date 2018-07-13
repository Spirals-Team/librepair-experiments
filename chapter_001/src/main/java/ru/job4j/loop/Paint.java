package ru.job4j.loop;

public class Paint {

    /**
     * Метод формирует строку из Х в середине и пробелов
     * @param aster количество Х
     * @param maxaster длинна строки
     * @return строку из Х в середине и пробелов
     */
    private String PrintLine(int aster,int maxaster){
        StringBuilder builder = new StringBuilder();
        int spaces=(maxaster-aster)/2;
        for (int i=1; (spaces>=0)&(i<=aster);i++){
           builder.append("X");
        }
        for(int j=1;(spaces>0)&(j<=spaces);j++){
            builder.insert(0," ");
            builder.append(" ");
        }
        return builder.toString();
    }


    public String piramid(int h){
        int max=(h-1)*2+1;
        StringBuilder builder = new StringBuilder();
        for(int i=1;(h>0)&(i<=h);i++){
            builder.append(this.PrintLine((i-1)*2+1,max));
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }
}
