package org.udec.visual;

import org.udec.logica.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PanelMonedas extends JPanel {

    private JButton botonMoneda100;
    private JButton botonMoneda500;
    private JButton botonMoneda1000;
    private Moneda monedaSeleccionada;
    private int monedaTipo;

    public PanelMonedas(){
        this.setLayout(new GridLayout(2, 2));
        this.setBorder(new LineBorder(new Color(25, 12, 6), 10));
        this.setPreferredSize(new Dimension(350, 350));
        this.setOpaque(false);
        this.setVisible(true);


        botonMoneda100 = new JButton();
        botonMoneda100.setPreferredSize(new Dimension(50, 50));
        botonMoneda100.setOpaque(true);
        botonMoneda100.setVisible(true);
        botonMoneda100.setFocusable(false);
        botonMoneda100.setText("$100");
        this.add(botonMoneda100);

        botonMoneda500 = new JButton();
        botonMoneda500.setPreferredSize(new Dimension(50, 50));
        botonMoneda500.setOpaque(true);
        botonMoneda500.setVisible(true);
        botonMoneda500.setFocusable(false);
        botonMoneda500.setText("$500");
        this.add(botonMoneda500);

        botonMoneda1000 = new JButton();
        botonMoneda1000.setPreferredSize(new Dimension(50, 50));
        botonMoneda1000.setOpaque(true);
        botonMoneda1000.setVisible(true);
        botonMoneda1000.setFocusable(false);
        botonMoneda1000.setText("$1000");
        this.add(botonMoneda1000);

        botonMoneda100.addActionListener(e -> {
            monedaSeleccionada = new Moneda100();
            monedaTipo = 100;
            repaint();
        });
        botonMoneda500.addActionListener(e -> {
            monedaSeleccionada = new Moneda500();
            monedaTipo = 500;
            repaint();
        });
        botonMoneda1000.addActionListener(e -> {
            monedaSeleccionada = new Moneda1000();
            monedaTipo = 1000;
            repaint();
        });

    }

    public Moneda getMonedaSeleccionada(){
        return monedaSeleccionada;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch(monedaTipo){
            case 100:
                botonMoneda100.setBackground(new Color(255, 199, 39));
                botonMoneda500.setBackground(new Color(255, 255, 255));
                botonMoneda1000.setBackground(new Color(255, 255, 255));
                break;
            case 500:
                botonMoneda100.setBackground(new Color(255, 255, 255));
                botonMoneda500.setBackground(new Color(255, 199, 39));
                botonMoneda1000.setBackground(new Color(255, 255, 255));
                break;
            case 1000:
                botonMoneda100.setBackground(new Color(255, 255, 255));
                botonMoneda500.setBackground(new Color(255, 255, 255));
                botonMoneda1000.setBackground(new Color(255, 199, 39));
                break;
            default:
                botonMoneda100.setBackground(new Color(255, 255, 255));
                botonMoneda500.setBackground(new Color(255, 255, 255));
                botonMoneda1000.setBackground(new Color(255, 255, 255));
                break;
        }
    }
}
