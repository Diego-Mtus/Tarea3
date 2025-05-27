package org.udec;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame test = new JFrame("pruebaaa");
        //test.setSize(500,600);
        test.setResizable(false);

        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        test.add(new PanelPrincipal());
        test.pack();
        test.setVisible(true);
    }
}