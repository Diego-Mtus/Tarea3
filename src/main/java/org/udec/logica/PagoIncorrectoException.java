package org.udec.logica;

/** Excepción que se lanza cuando se intenta comprar algo con una {@link Moneda} no válida.*/
public class PagoIncorrectoException extends Exception {

    /** Constructor que inicializa la excepción con un mensaje predeterminado.
     * <p>
     * El mensaje es "pago incorrecto.", indicando que se intentó pagar con {@link Moneda} {@code null}.
     * </p>*/
    public PagoIncorrectoException() {
        super("pago incorrecto.");
    }
}
