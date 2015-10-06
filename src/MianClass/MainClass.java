/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MianClass;

import GUI.Main_GUI;
import GUI.PartFile_GUI;

/**
 *
 * @author Administrator
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main_GUI().setVisible(true);
            }
        });
    }
}
