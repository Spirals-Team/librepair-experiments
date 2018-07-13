package ru.job4j.loop;

public class Board {
    public String paint(int width, int height){
        StringBuilder builder = new StringBuilder();
        for (int i=1;(height>0)& (i<=height);i++){
            for(int j=1; (width>0)&(j<=width);j++) {
                if ((i + j) % 2 == 0) {
                    builder.append("X");
                }
                else {
                    builder.append(" ");
                }
            }
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }
}
