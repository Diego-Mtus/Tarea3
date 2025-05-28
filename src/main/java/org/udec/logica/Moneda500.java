package org.udec.logica;

/** Clase que representa una moneda de 500 y extiende a {@link Moneda}, por lo tanto, tiene un valor asociado.*/
public class Moneda500 extends Moneda{

    /** Constructor por defecto, le asignará un número de serie único a la moneda.*/
    public Moneda500(){
        super();
    }

    /**
     * Devuelve el valor de esta moneda, que en este caso es 500.
     * @return El valor de la moneda, que es 500.
     */
    @Override
    public int getValor() {
        return 500;
    }
}
