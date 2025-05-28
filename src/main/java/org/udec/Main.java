
package org.udec;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame test = new JFrame("pruebaaa");
        test.setSize(1600, 900);
        test.setResizable(false);
        test.setLocationRelativeTo(null);
        test.setLayout(null);

        PanelPrincipal pp = new PanelPrincipal();
        pp.setBounds(0,0,1600,900);
        test.add(pp);
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.setVisible(true);
    }
}


