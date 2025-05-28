package org.udec.logica;

/** Excepción que se lanza cuando no hay productos disponibles en el expendedor*/
public class NoHayProductoException extends Exception {

  /** Constructor que inicializa la excepción con un mensaje predeterminado.
   * <p>
   * El mensaje es "falta de stock.", indicando que no hay más productos.
   * </p> */
  public NoHayProductoException() {
    super("falta de stock.");
  }
}
