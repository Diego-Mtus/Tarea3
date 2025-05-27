package org.udec;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PanelExpendedor extends JPanel {

    public PanelExpendedor(){

        this.setPreferredSize(new Dimension(600, 200));
        this.setBackground(new Color(250, 166, 34));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
