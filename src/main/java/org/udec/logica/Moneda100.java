package org.udec.logica;

/** Clase que representa una moneda de 100 y extiende a {@link Moneda}, por lo tanto, tiene un valor asociado.*/
public class Moneda100 extends Moneda{

    /** Constructor por defecto, le asignará un número de serie único a la moneda.*/
    public Moneda100(){
        super();
    }

    /**
     * Devuelve el valor de esta moneda, que en este caso es 100.
     * @return El valor de la moneda, que es 100.
     */
    @Override
    public int getValor() {
        return 100;
    }
}
