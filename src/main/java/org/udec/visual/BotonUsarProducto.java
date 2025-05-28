package org.udec.visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BotonUsarProducto extends JPanel implements MouseListener {

    private Shape figura;

    public BotonUsarProducto(int ancho, int alto){
        this.setLayout(null);
        Dimension dim = new Dimension(ancho, alto);
        this.setPreferredSize(dim);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);
        this.setVisible(true);
        this.setOpaque(true);
        this.setFocusable(false);
        this.setBorder(null);

        figura = new Rectangle(0,0,ancho,alto);

        this.addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(255, 199, 39));
        g.fill(figura);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
