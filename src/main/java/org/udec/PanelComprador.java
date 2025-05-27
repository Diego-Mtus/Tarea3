package org.udec;

import javax.swing.*;
import java.awt.*;

public class PanelComprador extends JPanel {

    public PanelComprador(){

        this.setPreferredSize(new Dimension(300, 500));
        this.setBackground(new Color(255, 229, 44));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
