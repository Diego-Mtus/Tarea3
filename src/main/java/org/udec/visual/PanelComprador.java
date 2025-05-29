package org.udec.visual;

import org.udec.logica.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

public class PanelComprador extends JPanel {

    private PanelExpendedor panelExpendedor;
    private JButton botonComprar;
    private PanelMonedas panelMonedas;
    private Producto ultimoProducctoComprado;
    private JButton botonSaldo;
    private Deposito<Moneda> depositoMonedasVuelto;
    private JButton botonRecargarStock;
    private JButton botonConseguirProducto;

    // Media
    private Font fuentePersonalizada;
    private Clip clipClick;
    private Clip clipError;
    private ImageIcon imagenMoneda;

    public PanelComprador(PanelExpendedor panelExpendedor) {
        this.setPreferredSize(new Dimension(400, 900));
        this.setOpaque(false);

        UIManager.put("Button.select", new Color(8, 8, 8));
        // Tiene sentido que el comprador necesite un expendedor para funcionar
        this.panelExpendedor = panelExpendedor;


        // Cargar fuente
        try {
            // Ruta hacia el archivo .otf (en la carpeta resources)
            InputStream fuenteStream = getClass().getResourceAsStream("/Skip.otf");

            if(fuenteStream == null) {
                throw new IOException("No se pudo cargar la fuente");
            }

            Font fuenteBase = Font.createFont(Font.TRUETYPE_FONT, fuenteStream);
            fuentePersonalizada = fuenteBase.deriveFont(14f);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            fuentePersonalizada = new Font("Serif", Font.BOLD, 14); // Fuente alternativa si falla
        }

        // Sonido
        try{

            // Sonido de clickear boton
            InputStream sonidoStreamClick = getClass().getResourceAsStream("/click.wav");
            if (sonidoStreamClick == null) {
                throw new IllegalArgumentException("Recurso no encontrado: /click.wav");
            }
            AudioInputStream audioInputStreamClick = AudioSystem.getAudioInputStream(new BufferedInputStream(sonidoStreamClick));
            clipClick = AudioSystem.getClip();
            clipClick.open(audioInputStreamClick);

            // Sonido de error
            InputStream sonidoStreamError = getClass().getResourceAsStream("/error.wav");
            if (sonidoStreamError == null) {
                throw new IllegalArgumentException("Recurso no encontrado: /error.wav");
            }
            AudioInputStream audioInputStreamError = AudioSystem.getAudioInputStream(new BufferedInputStream(sonidoStreamError));
            clipError= AudioSystem.getClip();
            clipError.open(audioInputStreamError);

            // Volumen
            FloatControl controlVolumenClick = (FloatControl) clipClick.getControl(FloatControl.Type.MASTER_GAIN);
            controlVolumenClick.setValue(-15.0f);
            FloatControl controlVolumenError = (FloatControl) clipError.getControl(FloatControl.Type.MASTER_GAIN);
            controlVolumenError.setValue(-15.0f);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        // Compartir el mismo clip para compartir recursos
        this.panelMonedas = new PanelMonedas(clipClick);
        this.add(panelMonedas);

        // Cargar imagen de moneda
        try {
            InputStream imageStream = getClass().getResourceAsStream("/moneda.png");
            if (imageStream != null) {
                imagenMoneda = new ImageIcon(ImageIO.read(imageStream));
            } else {
                throw new IOException("Imagen de moneda no disponible");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Funcionamiento de botón comprar
        botonComprar = new JButton("Elige un producto a comprar");
        botonComprar.setPreferredSize(new Dimension(220, 100));
        botonComprar.setFont(fuentePersonalizada);
        botonComprar.setFocusable(false);
        botonComprar.setForeground(new Color(255, 199, 39));
        botonComprar.setBorder(BorderFactory.createLineBorder(new Color(255, 199, 39), 2, true));
        botonComprar.setBackground(new Color(8, 8, 8));
        botonComprar.setSelected(false);
        botonComprar.setEnabled(false);
        this.add(botonComprar);
        botonComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panelExpendedor.getUltimoClickeado() >= 0) {
                    if (panelExpendedor.verStockActual(panelExpendedor.getUltimoClickeado()) > 0) {
                        try {
                            if(!botonConseguirProducto.isVisible()){
                                ultimoProducctoComprado = panelExpendedor.usarExpendedor(panelMonedas.getMonedaSeleccionada(), ProductosEnum.values()[panelExpendedor.getUltimoClickeado()]);
                                reproducirSonido(clipClick);
                                panelExpendedor.reducirStock(panelExpendedor.getUltimoClickeado());
                                System.out.println("Comprar " + ProductosEnum.values()[panelExpendedor.getUltimoClickeado()].getNombre());
                                JOptionPane.showMessageDialog(null, "¡Has comprado " + ProductosEnum.values()[panelExpendedor.getUltimoClickeado()].getNombre() + " exitosamente!", "Compra realizada", JOptionPane.INFORMATION_MESSAGE);
                                panelExpendedor.gestionVueltoExpendedor(ProductosEnum.values()[panelExpendedor.getUltimoClickeado()].getPrecio(), depositoMonedasVuelto, panelMonedas.getMonedaSeleccionada());
                                botonConseguirProducto.setVisible(true);
                            } else {
                                reproducirSonido(clipError);
                                JOptionPane.showMessageDialog(null, "Recuerda retirar tu última compra", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (NoHayProductoException ex) {
                            // Este catch no ocurrirá porque boton se desactiva cuando no hay producto.
                            reproducirSonido(clipError);
                            JOptionPane.showMessageDialog(null, "El producto seleccionado no está disponible", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        } catch (PagoInsuficienteException ex) {
                            reproducirSonido(clipError);
                            JOptionPane.showMessageDialog(null, "No tienes suficiente dinero", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        } catch (PagoIncorrectoException ex) {
                            reproducirSonido(clipError);
                            JOptionPane.showMessageDialog(null, "Debes elegir una moneda para pagar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    panelExpendedor.setUltimoClickeado(-1);
                    panelExpendedor.setStock(false, "", 0);
                    actualizarBotonComprar();
                }
            }
        });

        // Boton de ver saldo
        botonSaldo = new JButton("Vuelto");
        botonSaldo.setPreferredSize(new Dimension(100, 100));
        botonSaldo.setFocusable(false);
        botonSaldo.setFont(fuentePersonalizada);
        botonSaldo.setForeground(new Color(255, 199, 39));
        botonSaldo.setBorder(BorderFactory.createLineBorder(new Color(255, 199, 39), 2, true));
        botonSaldo.setBackground(new Color(8, 8, 8));
        this.depositoMonedasVuelto = new Deposito<>();
        this.add(botonSaldo);
        botonSaldo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(depositoMonedasVuelto.size() > 0){
                    reproducirSonido(clipClick);
                    crearVentanaVuelto();
                } else {
                    reproducirSonido(clipError);
                }
            }
        });

        // Botón de conseguir producto
        botonConseguirProducto = new JButton("Conseguir producto");
        botonConseguirProducto.setPreferredSize(new Dimension(300, 340));
        botonConseguirProducto.setFont(fuentePersonalizada);
        botonConseguirProducto.setForeground(new Color(255, 199, 39));
        botonConseguirProducto.setBorder(BorderFactory.createLineBorder(new Color(255, 199, 39), 2, true));
        botonConseguirProducto.setBackground(new Color(8, 8, 8));
        botonConseguirProducto.setFocusable(false);
        botonConseguirProducto.setVisible(false);
        this.add(botonConseguirProducto);
        botonConseguirProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproducirSonido(clipClick);
                crearVentanaConseguirProducto();
                System.out.println("presionada" + ultimoProducctoComprado.toString());
                botonConseguirProducto.setVisible(false);
            }
        });


        // Boton de recargar stock de todos los productos.
        botonRecargarStock = new JButton("Recargar stock");
        botonRecargarStock.setFont(fuentePersonalizada);
        botonRecargarStock.setPreferredSize(new Dimension(300, 30));
        botonRecargarStock.setForeground(new Color(255, 199, 39));
        botonRecargarStock.setBorder(BorderFactory.createLineBorder(new Color(255, 199, 39), 2, true));
        botonRecargarStock.setBackground(new Color(8, 8, 8));
        botonRecargarStock.setFocusable(false);
        botonRecargarStock.setSelected(false);
        this.add(botonRecargarStock);
        botonRecargarStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproducirSonido(clipClick);
                panelExpendedor.recargarStock(recargarStockNum());
            }
        });

    }

    // --------------------

    public void actualizarBotonComprar() {
        if (panelExpendedor.getUltimoClickeado() == -1) {
            botonComprar.setText("Elige un producto a comprar");
            botonComprar.setEnabled(false);
            return;
        }
        botonComprar.setText("Comprar " + ProductosEnum.values()[panelExpendedor.getUltimoClickeado()].getNombre());
        botonComprar.setEnabled(panelExpendedor.verStockActual(panelExpendedor.getUltimoClickeado()) > 0);
    }

    private int recargarStockNum() {
        JDialog ventana = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PanelComprador.this), "Recargar stock", true);
        ventana.setSize(250, 110);
        ventana.setLayout(new BorderLayout());
        ventana.setLocationRelativeTo(null);

        JPanel panelCentral = new JPanel(new FlowLayout(FlowLayout.CENTER) );

        panelCentral.add(new JLabel("Ingrese cantidad:"));
        JTextField inputCantidad = new JTextField();
        inputCantidad.setPreferredSize(new Dimension(100, 30));
        panelCentral.add(inputCantidad);

        ventana.add(panelCentral, BorderLayout.CENTER);

        JButton btnAceptar = new JButton("Aceptar");
        ventana.add(btnAceptar, BorderLayout.SOUTH);
        AtomicInteger aux = new AtomicInteger();

        // Si no se ingresa nada, por defecto se crean 5 productos.
        aux.set(5);

        btnAceptar.addActionListener(e -> {
            try {
                int cantidad = Integer.parseInt(inputCantidad.getText());
                if (cantidad > 0) {
                    ventana.dispose();
                    aux.set(cantidad);
                } else {
                    JOptionPane.showMessageDialog(ventana, "Ingrese un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "Entrada no válida. Ingrese un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        ventana.setVisible(true);
        return aux.get();
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

    private void recibirVuelto(JDialog ventana){
        int contadorDeMonedas = depositoMonedasVuelto.size();
        Moneda aux = depositoMonedasVuelto.get();
        while(aux != null){
            JLabel moneda = new JLabel(imagenMoneda);
            moneda.setText("Serie: " +aux.getSerie());
            moneda.setVerticalTextPosition(SwingConstants.BOTTOM);
            moneda.setHorizontalTextPosition(SwingConstants.CENTER);

            System.out.println(aux);
            ventana.add(moneda);
            aux = depositoMonedasVuelto.get();
        }
        ventana.add(new JLabel("Total: " + contadorDeMonedas * 100 + " pesos."));

    }

    private void crearVentanaVuelto(){
        JDialog ventana = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PanelComprador.this), "Vuelto", true);
        ventana.setLayout(new FlowLayout(FlowLayout.CENTER));
        ventana.setMaximumSize(new Dimension(1400, 600));
        ventana.setMinimumSize(new Dimension(1400, 600));
        ventana.setPreferredSize(new Dimension(1400, 600));
        recibirVuelto(ventana);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    private void crearVentanaConseguirProducto() {
        JDialog ventana = new JDialog((JFrame) SwingUtilities.getWindowAncestor(PanelComprador.this), "Conseguir producto", true);
        ventana.setLayout(new BorderLayout());

        JLabel texto = new JLabel(ultimoProducctoComprado.usar() + " Serie: " + ultimoProducctoComprado.getSerie());
        texto.setFont(fuentePersonalizada);
        texto.setHorizontalAlignment(SwingConstants.CENTER);
        ventana.add(texto, BorderLayout.SOUTH);
        
        int indiceImagen = 0;
        if(ultimoProducctoComprado instanceof CocaCola){
            indiceImagen = 1;
        } else if(ultimoProducctoComprado instanceof Sprite){
            indiceImagen = 2;
        } else if(ultimoProducctoComprado instanceof Fanta){
            indiceImagen = 3;
        } else if(ultimoProducctoComprado instanceof Snickers){
            indiceImagen = 4;
        } else if(ultimoProducctoComprado instanceof Super8){
            indiceImagen = 5;
        }

        // Cargar imagen
        try {
            InputStream imageStream = getClass().getResourceAsStream("/" + indiceImagen + ".png");
            if (imageStream != null) {
                ImageIcon icon = new ImageIcon(ImageIO.read(imageStream));
                ventana.add(new JLabel(icon), BorderLayout.CENTER);
            } else {
                throw new IOException("Imagen no disponible");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            ventana.add(new JLabel("Imagen no disponible"), BorderLayout.CENTER);
        }

        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


    }
}
