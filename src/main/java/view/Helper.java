package view;

public class Helper {

    public static void printHelp() {

        /**
         * формирует строку помощи.
         */
        String help = "\n" +
                "usage:\n\n" +
                "--h, print help\n" +
                "--char <arg>  print only char statistics\n" +
                "--word <arg>  print only word statistics\n" +
                "<arg> print full statistics\n\n";

        System.out.printf(help);
    }
}
