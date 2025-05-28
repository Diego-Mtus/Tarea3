package org.udec.logica;

/** Representación especifica de una bebida Fanta, que es una {@link Bebida} y {@link Producto},
 * por lo tanto, se puede usar.
 * */

public class Fanta extends Bebida{

    /** Constructor que recibe una serie y se la pasa a su superclase {@link Bebida}, que a su vez, se la pasa a {@link Producto}.
     * Simula el identificador único de esa Fanta.
     * @param serie Número de serie deseado para Fanta.*/
    public Fanta(int serie) {
        super(serie);
    }

    /** Método que sobreescribe el método heredado de {@link Bebida} para desplegar el consumo de la Fanta.
     * @return Retorna "Se ha tomado Fanta."*/
    @Override
    public String usar() {
        return super.usar() + "Fanta.";
    }
}
