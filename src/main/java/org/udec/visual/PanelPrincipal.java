package org.udec.visual;

import javax.swing.*;
import java.awt.*;

public class PanelPrincipal extends JPanel {
    private PanelComprador com;
    private PanelExpendedor exp;
    private Image fondo;

    public PanelPrincipal(){
        this.setLayout(null);
        exp = new PanelExpendedor();
        com = new PanelComprador(exp);
        exp.setPanelComprador(com);
        exp.setBounds(0,0,1200,900);
        com.setBounds(1200,0,400,900);

        this.add(exp);
        this.add(com);

        // Para no cargarlo multiples veces
        fondo = new ImageIcon(getClass().getResource("/fondo.png")).getImage().getScaledInstance(1600,900,Image.SCALE_SMOOTH);
        JLabel fondoLabel = new JLabel(new ImageIcon(fondo));
        fondoLabel.setBounds(0,0,1600,900);
        this.add(fondoLabel);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }


    }
}
