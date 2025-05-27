package org.udec;

import javax.swing.*;
import java.awt.*;

public class PanelPrincipal extends JPanel {
    private PanelComprador com;
    private PanelExpendedor exp;
    private BorderLayout layout = new BorderLayout();

    public PanelPrincipal(){
        this.setLayout(layout);
        exp = new PanelExpendedor();
        com = new PanelComprador();

        this.add(exp, BorderLayout.WEST);
        this.add(com, BorderLayout.EAST);




        this.setBackground(Color.black);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        exp.paintComponent(g);
        com.paintComponent(g);
    }
}
