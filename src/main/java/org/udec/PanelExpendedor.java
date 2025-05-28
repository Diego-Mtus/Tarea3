package org.udec;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PanelExpendedor extends JPanel {

    private BotonRombo[] productos;
    private int[] precios;


    private Font fuentePersonalizada;
    private Image imagenActual;
    private BufferedImage fondo;


    private BufferedImage barraStock;
    private boolean mostrarBarraStock = false; // Inicio: barra oculta
    private String textoBarraStock = "";
    private Font fuenteStock;
    private String textoPrecio = "";


    public PanelExpendedor(){

        Dimension dim = new Dimension(1200, 900);
        this.setPreferredSize(dim);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);

        this.setBorder(new EmptyBorder(220,150,100,350));
        this.setLayout(new DiagonalLayout(0));
        int auxCantidad = ProductosEnum.values().length;
        productos = new BotonRombo[auxCantidad];
        precios = new int[auxCantidad];

        Image[] imagenesProductos = new Image[5];
        for (int i = 0; i < productos.length; i++) {
            String nombreImagen = "/test" + (i % 5 + 1 ) + ".png";
            imagenesProductos[i] = new ImageIcon(getClass().getResource(nombreImagen)).getImage();
        }
        for(int i = 0; i < productos.length; i++){
            productos[i] = new BotonRombo(10, 10, 280, 60,  imagenesProductos[i], this, ProductosEnum.values()[i]);
            this.add(productos[i]);
            precios[i] = ProductosEnum.values()[i].getPrecio();
        }

        try{
            barraStock = ImageIO.read(getClass().getResource("/barraStock.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setOpaque(false);

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
        fuenteStock = fuentePersonalizada.deriveFont(fuentePersonalizada.getStyle(), 18);


    }

    public void setImagenActual(Image imagen){
        this.imagenActual = imagen;
        repaint(600,20,600,600);
    }

    // Mét0do para que no se tenga que renderizar fondo a cada rato
    private void crearFondo(){
        fondo = new BufferedImage(1200, 900, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = fondo.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Dibujar un rectángulo de fondo
        g.setColor(new Color(0, 0, 0, 220)); // Color semi-transparente
        g.fillPolygon(new int[]{0, 0, 350, 1080,540}, new int[]{220, 0, 0, 900, 900}, 5);

        // Dibujar frase de compra
        g.setColor(Color.white);
        g.setFont(fuentePersonalizada);
        g.drawString("¿Qué desea comprar?", 40, 150);

        g.dispose();
    }

    public void setStock(boolean visible, String stock, int precio){
        this.mostrarBarraStock = visible;
        this.textoBarraStock = stock;
        if(precio > 0){
            this.textoPrecio = "$" + precio;
        }
        repaint(-20,500,400,280);
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);

        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Sección estética - - -
        // Sólo se renderiza el fondo si es que no lo estaba de antes
        if (fondo == null){
            crearFondo();
        }
        g.drawImage(fondo, 0, 0, this);

        // Dibujar imagen de producto
        if(imagenActual != null){
            g.drawImage(imagenActual, 600, 20, 600, 600, this);
        }
        if(mostrarBarraStock){
            g.drawImage(barraStock, -20, 500, 400, 280, this);
            g.setColor(new Color(255, 199, 39));

            g.setFont(fuentePersonalizada.deriveFont(Font.ITALIC, 48));
            g.drawString(textoPrecio, 100, 600);

            g.setFont(fuenteStock);
            // Rotar texto de acuerdo a barra
            FontMetrics fm = g.getFontMetrics();
            AffineTransform transformOriginal = g.getTransform();
            g.rotate(Math.toRadians(7.6), 50 + fm.stringWidth(textoBarraStock) / 2, 700 - fm.getHeight() / 2);
            g.drawString(textoBarraStock, 50, 700);
            g.setTransform(transformOriginal);


        }



    }
}
