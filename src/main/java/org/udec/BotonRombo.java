package org.udec;

// Base de la implementación obtenido de
// https://stackoverflow.com/questions/24034747/how-to-implement-mouselistener-on-a-particular-shape

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;
import javax.swing.*;




public class BotonRombo extends JPanel implements MouseListener, MouseMotionListener {

    // Variables de datos de figura.
    private final Shape figura;
    private boolean mouseEnFigura;
    private Area rectanguloArea;
    int[][] esquinasRect;
    int diametroCirculo = 6;

    // Variables manejo imagen
    private Image imagen;
    private PanelExpendedor panelExpendedor;


    // Variables del texto
    private final String texto;
    private Font fuentePersonalizada;
    private ProductosEnum producto;

    // Variables para sonido
    private boolean sonidoHoverYaReproducido = false;
    private Clip clipHover;
    private Clip clipClick;

    // Variables de colores
    private final Color colorNormal = new Color(25, 12, 6); // Color cuando el mouse no está sobre la figura
    private final Color colorHover = new Color(255, 199, 39); // Color cuando el mouse está sobre la figura
    private final Color colorClick = new Color(255,251,163);
    private Color colorActual = colorHover;
    private Color colorTexto;

    // Variables para animacion al clickear.
    private Timer timer;
    private Timer timerVuelta;
    private int pasos = 10; // Modificar para la velocidad de animación
    private int pasoActual = 0;

    // Valores que nos gustaron: ancho 280 y alto 60.

    public BotonRombo(int x, int y, int ancho, int alto, Image imagen, PanelExpendedor panelExpendedor, ProductosEnum producto) {
        this.panelExpendedor = panelExpendedor;
        this.imagen = imagen;
        this.producto = producto;

        this.setOpaque(false);
        // Crear un rectangulo rotado
        Shape cuadrado_base = new Rectangle(x, y, ancho, alto);

        // rotación
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(1.5),  x + ancho / 2.0, y + alto / 2.0); // Rotar 1 grado alrededor del centro del cuadrado

        this.figura = transform.createTransformedShape(cuadrado_base);
        this.rectanguloArea = new Area(figura);
        this.mouseEnFigura = false;
        this.colorTexto = colorHover;


        // Esquinas son para hacer circulos en las esquinas como si fueran perforaciones
        // Ya que el rectangulo está rotado 1.5 grados, hay que corregir las coordenadas de los bordes de acuerdo a eso.
        esquinasRect = new int[][]{
                {x + diametroCirculo, y + diametroCirculo}, // Esq superior izq
                {x + ancho - diametroCirculo, y + diametroCirculo}, // Esq superior derecha
                {x + diametroCirculo, y + alto - diametroCirculo}, // Esq inferior izq
                {x + ancho - diametroCirculo, y + alto - diametroCirculo} // Esq inferior derecha
        };

        for (int[] esquina : esquinasRect) {
            Point transformPunto = transformCoord(transform, esquina[0], esquina[1]);
            esquina[0] = transformPunto.x;
            esquina[1] = transformPunto.y;
        }

        // Restar el área de cada círculo en el rectángulo
        // Esquina sup izq
        rectanguloArea.subtract(new Area(new Ellipse2D.Double(
                esquinasRect[0][0], // Centrar el círculo
                esquinasRect[0][1],
                diametroCirculo,
                diametroCirculo)));

        // Esquina sup der
        rectanguloArea.subtract(new Area(new Ellipse2D.Double(
                esquinasRect[1][0] - diametroCirculo,
                esquinasRect[1][1],
                diametroCirculo,
                diametroCirculo)));

        // Esquina inf izq
        rectanguloArea.subtract(new Area(new Ellipse2D.Double(
                esquinasRect[2][0],
                esquinasRect[2][1] - diametroCirculo,
                diametroCirculo,
                diametroCirculo)));

        // Esquina inf der
        rectanguloArea.subtract(new Area(new Ellipse2D.Double(
                esquinasRect[3][0] - diametroCirculo,
                esquinasRect[3][1] - diametroCirculo,
                diametroCirculo,
                diametroCirculo
        )));

        // Ajusta tamaño preferido de acuerdo a dimensiones nuevas causadas por la rotación

        Dimension ajusteDimension = new Dimension(esquinasRect[1][0] - esquinasRect[0][0] + diametroCirculo * 4, esquinasRect[3][1] - esquinasRect[0][1] + diametroCirculo * 4);
        this.setPreferredSize(ajusteDimension);
        this.setMinimumSize(ajusteDimension);

        this.texto = producto.getNombre();
        // Cargar la fuente personalizada
        try {
            // Ruta hacia el archivo .otf (en la carpeta resources)

            InputStream fuenteStream = getClass().getResourceAsStream("/Skip.otf");

            if(fuenteStream == null) {
                throw new IOException("No se pudo cargar la fuente");
            }

            Font fuenteBase = Font.createFont(Font.TRUETYPE_FONT, fuenteStream);
            fuentePersonalizada = fuenteBase.deriveFont(28f); // Tamaño 30

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            fuentePersonalizada = new Font("Serif", Font.BOLD, 24); // Fuente alternativa si falla
        }

        // Cargar sonido
        try{
            // Sonido de pasar mouse por encima
            InputStream sonidoStreamHover = getClass().getResourceAsStream("/hover.wav");
            if (sonidoStreamHover == null) {
                throw new IllegalArgumentException("Recurso no encontrado: /hover.wav");
            }
            AudioInputStream audioInputStreamHover = AudioSystem.getAudioInputStream(new BufferedInputStream(sonidoStreamHover));
            clipHover = AudioSystem.getClip();
            clipHover.open(audioInputStreamHover);

            // Sonido de clickear boton
            InputStream sonidoStreamClick = getClass().getResourceAsStream("/click.wav");
            if (sonidoStreamClick == null) {
                throw new IllegalArgumentException("Recurso no encontrado: /click.wav");
            }
            AudioInputStream audioInputStreamClick = AudioSystem.getAudioInputStream(new BufferedInputStream(sonidoStreamClick));
            clipClick = AudioSystem.getClip();
            clipClick.open(audioInputStreamClick);

            // Volumen
            FloatControl controlVolumenHover = (FloatControl) clipHover.getControl(FloatControl.Type.MASTER_GAIN);
            controlVolumenHover.setValue(-15.0f);
            FloatControl controlVolumenClick = (FloatControl) clipClick.getControl(FloatControl.Type.MASTER_GAIN);
            controlVolumenClick.setValue(-15.0f);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        addMouseListener(this);
        addMouseMotionListener(this);

    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Para que bordes no se vean borrosos


        // Dibujado de rectangulo
        if(mouseEnFigura) {
            g.setColor(colorActual);
            g.fill(rectanguloArea);

        }

        // Dibujado de texto
        g.setColor(colorTexto);
        g.setFont(fuentePersonalizada);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(texto, esquinasRect[0][0] + (esquinasRect[1][0] - esquinasRect[0][0]) / 2 - fm.stringWidth(texto) / 2, esquinasRect[0][1] + (esquinasRect[3][1] - esquinasRect[0][1]) / 2 + fm.getAscent() / 2);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (figura.contains(e.getPoint())) {
            System.out.println("Figura clickeada");
            inicioTransicionColor();
            reproducirSonido(clipClick);

        }
    }


    private Point transformCoord(AffineTransform transform, int x, int y) {
        double[] coords = {x, y};
        transform.transform(coords, 0, coords, 0, 1);
        return new Point((int) coords[0], (int) coords[1]);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Verificar si el mouse está dentro de la figura
        if (figura.contains(e.getPoint())) {
            colorTexto = colorNormal;
            if (!mouseEnFigura) {
                mouseEnFigura = true;
                repaint(getVisibleRect().x, getVisibleRect().y, getVisibleRect().width, getVisibleRect().height);
            }

        } else {
            colorTexto = colorHover;
            if (mouseEnFigura) {
                mouseEnFigura = false;
                repaint(getVisibleRect().x, getVisibleRect().y, getVisibleRect().width, getVisibleRect().height);
            }
        }
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

    private void inicioTransicionColor(){
        pasoActual = 0;

        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        timer = new Timer(100/pasos, e -> {
            float t = (float) pasoActual / (pasos - 1);

            // Transición de colores entre colorHover y colorClick
            int r = (int) (colorHover.getRed() + t * (colorClick.getRed() - colorHover.getRed()));
            int g = (int) (colorHover.getGreen() + t * (colorClick.getGreen() - colorHover.getGreen()));
            int b = (int) (colorHover.getBlue() + t * (colorClick.getBlue() - colorHover.getBlue()));

            colorActual = new Color(r, g, b);

            repaint(getVisibleRect().x, getVisibleRect().y, getVisibleRect().width, getVisibleRect().height);

            pasoActual++;
            if (pasoActual >= pasos) {
                timer.stop();
                colorActual = colorClick;
                repaint(getVisibleRect().x, getVisibleRect().y, getVisibleRect().width, getVisibleRect().height);
                inicioTransicionColorRegreso();

            }

        });

        timer.start();
    }

    private void inicioTransicionColorRegreso(){
        pasoActual = 0;

        if (timerVuelta != null && timerVuelta.isRunning()) {
            timer.stop();
        }

        timerVuelta = new Timer(100/pasos, e -> {
            float t = (float) pasoActual / (pasos - 1);
            int r = (int) (colorClick.getRed() + t * (colorHover.getRed() - colorClick.getRed()));
            int g = (int) (colorClick.getGreen() + t * (colorHover.getGreen() - colorClick.getGreen()));
            int b = (int) (colorClick.getBlue() + t * (colorHover.getBlue() - colorClick.getBlue()));

            colorActual = new Color(r, g, b);
            repaint(getVisibleRect().x, getVisibleRect().y, getVisibleRect().width, getVisibleRect().height);

            pasoActual++;
            if(pasoActual >= pasos) {
                timerVuelta.stop();
                colorActual = colorHover;
                repaint(getVisibleRect().x, getVisibleRect().y, getVisibleRect().width, getVisibleRect().height);
            }

        });

        timerVuelta.start();
    }


    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (panelExpendedor != null) {
            panelExpendedor.setStock(true, "Stock disponible: 10 unidades", producto.getPrecio()); // Cambia el texto según el contexto de este botón
            panelExpendedor.setImagenActual(imagen);
        }
        if(!sonidoHoverYaReproducido){
            reproducirSonido(clipHover);
            sonidoHoverYaReproducido = true;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (panelExpendedor != null) {
            panelExpendedor.setStock(false, "", 0); // Deja de mostrar la barra
            panelExpendedor.setImagenActual(null);
        }
        sonidoHoverYaReproducido = false;
    }

}