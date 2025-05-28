package org.udec.logica;

/** Representación especifica de una bebida Coca-Cola, que es una {@link Bebida} y {@link Producto},
 * por lo tanto, se puede usar.
 * */
public class CocaCola extends Bebida{

    /** Constructor que recibe una serie y se la pasa a su superclase {@link Bebida}, que a su vez, se la pasa a {@link Producto}.
     * Simula el identificador único de esa Coca-cola.
     * @param serie Número de serie deseado para Coca-cola.*/
    public CocaCola(int serie) {
        super(serie);
    }

    /** Método que sobreescribe el método heredado de {@link Bebida} para desplegar el consumo de la Coca-cola.
     * @return Retorna "Se ha tomado Coca-Cola."*/
    @Override
    public String usar() {
        return super.usar() + "Coca-Cola.";
    }
}
