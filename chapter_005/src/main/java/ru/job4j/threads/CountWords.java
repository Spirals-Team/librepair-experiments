package ru.job4j.threads;

public class CountWords implements Runnable {
    String str;

    public CountWords(String str) {
        this.str = str;
    }

    @Override
    public void run() {
        int countWords = 0;
        for(int e = 0; e < str.length(); e++){
            if(str.charAt(e) != ' '){
                countWords++;
                while(str.charAt(e) != ' ' && e < str.length()-1){
                    e++;
                }
            }
        }
        System.out.println(countWords);
    }
}
