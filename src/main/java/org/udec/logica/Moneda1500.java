package org.udec.logica;

/** Clase que representa una moneda de 1000 y extiende a {@link Moneda}, por lo tanto, tiene un valor asociado.*/
public class Moneda1500 extends Moneda{

    /** Constructor por defecto, le asignará un número de serie único a la moneda.*/
    public Moneda1500(){
        super();
    }

    /**
     * Devuelve el valor de esta moneda, que en este caso es 1000.
     * @return El valor de la moneda, que es 1000.
     */
    @Override
    public int getValor() {
        return 1500;
    }
}
