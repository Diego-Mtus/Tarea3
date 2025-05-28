package org.udec.visual;

import org.udec.logica.*;

import javax.swing.*;
import java.awt.*;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelComprador extends JPanel {

    private PanelExpendedor panelExpendedor;
    private JButton botonComprar;
    private PanelMonedas panelMonedas;
    private Producto ultimoProducctoComprado;


    public PanelComprador(PanelExpendedor panelExpendedor) {
        this.setPreferredSize(new Dimension(400, 900));
        this.setOpaque(false);

        // Tiene sentido que el comprador necesite un expendedor para funcionar
        this.panelExpendedor = panelExpendedor;


        this.panelMonedas = new PanelMonedas();
        this.add(panelMonedas);


        // Funcionamiento de botón comprar
        botonComprar = new JButton("Elige un producto a comprar");
        botonComprar.setPreferredSize(new Dimension(200, 100));
        botonComprar.setFocusable(false);
        this.add(botonComprar);
        botonComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panelExpendedor.getUltimoClickeado() >= 0) {
                    if (panelExpendedor.verStockActual(panelExpendedor.getUltimoClickeado()) > 0) {
                        try {
                            panelExpendedor.usarExpendedor(panelMonedas.getMonedaSeleccionada(), ProductosEnum.values()[panelExpendedor.getUltimoClickeado()]);
                            panelExpendedor.reducirStock(panelExpendedor.getUltimoClickeado());
                            System.out.println("Comprar " + ProductosEnum.values()[panelExpendedor.getUltimoClickeado()].getNombre());
                            JOptionPane.showMessageDialog(null, "¡Has comprado " + ProductosEnum.values()[panelExpendedor.getUltimoClickeado()].getNombre() + " exitosamente!", "Compra realizada", JOptionPane.INFORMATION_MESSAGE);

                        } catch (NoHayProductoException ex) {
                            // Este catch no ocurrirá porque boton se desactiva cuando no hay producto.
                            JOptionPane.showMessageDialog(null, "El producto seleccionado no está disponible", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        } catch (PagoInsuficienteException ex) {
                            JOptionPane.showMessageDialog(null, "No tienes suficiente dinero", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        } catch (PagoIncorrectoException ex) {
                            JOptionPane.showMessageDialog(null, "Debes elegir una moneda para pagar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    panelExpendedor.setUltimoClickeado(-1);
                    panelExpendedor.setStock(false, "", 0);
                    actualizarBotonComprar();
                }
            }
        });


    }

    public void actualizarBotonComprar() {
        if (panelExpendedor.getUltimoClickeado() == -1) {
            botonComprar.setText("Elige un producto a comprar");
            botonComprar.setEnabled(false);
            return;
        }
        botonComprar.setText("Comprar " + ProductosEnum.values()[panelExpendedor.getUltimoClickeado()].getNombre());
        botonComprar.setEnabled(panelExpendedor.verStockActual(panelExpendedor.getUltimoClickeado()) > 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


    }
}
