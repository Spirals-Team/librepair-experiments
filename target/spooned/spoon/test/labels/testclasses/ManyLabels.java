package spoon.test.labels.testclasses;


public class ManyLabels {
    public static void main(java.lang.String[] args) {
        labelBlock : {
            java.lang.System.out.println("block");
            if (((args.length) != 0) && (args[0].equals("test"))) {
                java.lang.System.out.println("block after break3");
                break labelBlock;
            }
            labelIf : if (((args.length) > 1) && (args[0].equals("test"))) {
                if ((args.length) == 2) {
                    break labelIf;
                }
                java.lang.System.out.println("block after break1");
            }
            java.lang.System.out.println("block after break");
        }
        sw : switch ("f") {
            case "f" :
                label : do {
                    java.lang.System.out.println("do");
                    lWhile : while (true) {
                        forloop : for (int i = 0; i < 10; i++) {
                            java.lang.System.out.println("for");
                            if ((i % 2) == 0) {
                                continue forloop;
                            }
                            if (i == 9) {
                                continue lWhile;
                            }
                            if (i < 8) {
                                continue label;
                            }
                            if (i == 8) {
                                break label;
                            }
                            java.lang.System.out.println("for 1");
                            labelbreak : break labelbreak;
                        }
                        break sw;
                    } 
                } while (true );
                break;
        }
    }
}

