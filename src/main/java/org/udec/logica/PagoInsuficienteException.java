package org.udec.logica;

/** Excepción que se lanza cuando se intenta comprar algo con una {@link Moneda} con un valor menor al del {@link Producto}.*/
public class PagoInsuficienteException extends Exception {

    /** Constructor que inicializa la excepción con un mensaje predeterminado.
     * <p>
     * El mensaje es "dinero insuficiente.", indicando que con la {@link Moneda} no alcanza para comprar el {@link Producto}.
     * </p>*/
    public PagoInsuficienteException() {
        super("dinero insuficiente.");
    }
}
