package org.udec.visual;

import org.udec.logica.*;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PanelMonedas extends JPanel {

    private JButton botonMoneda100;
    private JButton botonMoneda500;
    private JButton botonMoneda1000;
    private JButton botonMoneda1500;
    private Moneda monedaSeleccionada;
    private int monedaTipo;
    private Font fuentePersonalizada;
    private Clip clipClick;

    public PanelMonedas(Clip clipClick){
        this.setLayout(new GridLayout(2, 2));
        this.setBorder(new LineBorder(new Color(88,4,4), 5));
        this.setPreferredSize(new Dimension(350, 350));
        this.setOpaque(false);
        this.setVisible(true);

        this.clipClick = clipClick;

        // Cargar fuente
        try {
            // Ruta hacia el archivo .otf (en la carpeta resources)
            InputStream fuenteStream = getClass().getResourceAsStream("/Skip.otf");

            if(fuenteStream == null) {
                throw new IOException("No se pudo cargar la fuente");
            }

            Font fuenteBase = Font.createFont(Font.TRUETYPE_FONT, fuenteStream);
            fuentePersonalizada = fuenteBase.deriveFont(30f); // Tamaño 30

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            fuentePersonalizada = new Font("Serif", Font.BOLD, 24); // Fuente alternativa si falla
        }


        botonMoneda100 = new JButton();
        botonMoneda100.setPreferredSize(new Dimension(50, 50));
        botonMoneda100.setBorder(new EmptyBorder(0,0,0,0));
        botonMoneda100.setOpaque(true);
        botonMoneda100.setVisible(true);
        botonMoneda100.setFocusable(false);
        botonMoneda100.setFont(fuentePersonalizada);
        botonMoneda100.setText("$100");
        this.add(botonMoneda100);

        botonMoneda500 = new JButton();
        botonMoneda500.setPreferredSize(new Dimension(50, 50));
        botonMoneda500.setBorder(new EmptyBorder(0,0,0,0));
        botonMoneda500.setOpaque(true);
        botonMoneda500.setVisible(true);
        botonMoneda500.setFocusable(false);
        botonMoneda500.setFont(fuentePersonalizada);
        botonMoneda500.setText("$500");
        this.add(botonMoneda500);

        botonMoneda1000 = new JButton();
        botonMoneda1000.setPreferredSize(new Dimension(50, 50));
        botonMoneda1000.setBorder(new EmptyBorder(0,0,0,0));
        botonMoneda1000.setOpaque(true);
        botonMoneda1000.setVisible(true);
        botonMoneda1000.setFocusable(false);
        botonMoneda1000.setFont(fuentePersonalizada);
        botonMoneda1000.setText("$1000");
        this.add(botonMoneda1000);

        botonMoneda1500 = new JButton();
        botonMoneda1500.setPreferredSize(new Dimension(50, 50));
        botonMoneda1500.setBorder(new EmptyBorder(0,0,0,0));
        botonMoneda1500.setOpaque(true);
        botonMoneda1500.setVisible(true);
        botonMoneda1500.setFocusable(false);
        botonMoneda1500.setFont(fuentePersonalizada);
        botonMoneda1500.setText("$1500");
        this.add(botonMoneda1500);

        botonMoneda100.addActionListener(e -> {
            reproducirSonido(clipClick);
            monedaSeleccionada = new Moneda100();
            monedaTipo = 100;
            repaint(1200,0,400,400);
        });
        botonMoneda500.addActionListener(e -> {
            reproducirSonido(clipClick);
            monedaSeleccionada = new Moneda500();
            monedaTipo = 500;
            repaint(1200,0,400,400);
        });
        botonMoneda1000.addActionListener(e -> {
            reproducirSonido(clipClick);
            monedaSeleccionada = new Moneda1000();
            monedaTipo = 1000;
            repaint(1200,0,400,400);
        });
        botonMoneda1500.addActionListener(e -> {
            reproducirSonido(clipClick);
            monedaSeleccionada = new Moneda1500();
            monedaTipo = 1500;
            repaint(1200,0,400,400);
        });

    }

    public Moneda getMonedaSeleccionada(){
        return monedaSeleccionada;
    }

    private void reproducirSonido(Clip clip){
        if(clip != null){
            if(clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch(monedaTipo){
            case 100:
                botonMoneda100.setBackground(new Color(255, 199, 39));
                botonMoneda500.setBackground(new Color(8, 8, 8));
                botonMoneda1000.setBackground(new Color(8, 8, 8));
                botonMoneda1500.setBackground(new Color(8, 8, 8));
                break;
            case 500:
                botonMoneda100.setBackground(new Color(8, 8, 8));
                botonMoneda500.setBackground(new Color(255, 199, 39));
                botonMoneda1000.setBackground(new Color(8, 8, 8));
                botonMoneda1500.setBackground(new Color(8, 8, 8));
                break;
            case 1000:
                botonMoneda100.setBackground(new Color(8, 8, 8));
                botonMoneda500.setBackground(new Color(8, 8, 8));
                botonMoneda1000.setBackground(new Color(255, 199, 39));
                botonMoneda1500.setBackground(new Color(8, 8, 8));
                break;
            case 1500:
                botonMoneda100.setBackground(new Color(8, 8, 8));
                botonMoneda500.setBackground(new Color(8, 8, 8));
                botonMoneda1000.setBackground(new Color(8, 8, 8));
                botonMoneda1500.setBackground(new Color(255, 199, 39));
                break;
            default:
                botonMoneda100.setBackground(new Color(8, 8, 8));
                botonMoneda500.setBackground(new Color(8, 8, 8));
                botonMoneda1000.setBackground(new Color(8, 8, 8));
                botonMoneda1500.setBackground(new Color(8, 8, 8));
                break;
        }
    }
}
