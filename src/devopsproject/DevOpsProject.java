/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;

import java.util.Arrays;

public class DevOpsProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String line = "5,2,,6,,7," ;
        String [] split = line.split(",") ;
        System.out.println(Arrays.toString(split));
        for (String string : split) {
            System.out.println(string);
        }
        System.out.println(split[2].equals(""));
    }
    
}
