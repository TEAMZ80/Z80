/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macroe;

import java.util.ArrayList;

/**
 *
 * @author alacr
 */
public class MacroE {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Z80 x = new Z80();
        x.read("programa");

        // TODO code application logic here

        String bin = "01rs";
        ArrayList <String> variables = new ArrayList <> ();
        variables.add("111");
        variables.add("00000100");
        
        
    }
    
}
