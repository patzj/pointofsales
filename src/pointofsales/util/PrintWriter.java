/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pointofsales.util;

/**
 *
 * @author patzj
 */
public class PrintWriter {
    /**
     * 
     * @param s String to be printed.
     */
    public static void print(String s) {
        System.out.print(s);
    }
    
    /**
     * 
     * @param s String to be printed.
     */
    public static void println(String s) {
        System.out.println(s);
    }
    
    /**
     * 
     * @param count number of horizontal lines to be drawn
     */
    public static void drawHorizontalBrokenLines(int count) {
        for(int i = 0; i < count; i++) {
            print("-");
        }
        println("");
    }
}
