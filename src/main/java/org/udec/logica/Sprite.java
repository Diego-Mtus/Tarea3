package org.udec.logica;

/** Representación especifica de una bebida Sprite, que es una {@link Bebida} y {@link Producto},
 * por lo tanto, se puede usar.
 * */

public class Sprite extends Bebida{

    /** Constructor que recibe una serie y se la pasa a su superclase {@link Bebida}, que a su vez, se la pasa a {@link Producto}.
     * Simula el identificador único de esa Sprite.
     * @param serie Número de serie deseado para Sprite.*/
    public Sprite(int serie) {
        super(serie);
    }

    /** Método que sobreescribe el método heredado de {@link Bebida} para desplegar el consumo de la Sprite.
     * @return Retorna "Se ha tomado Sprite."*/
    @Override
    public String usar() {
        return super.usar() + "Sprite.";
    }
}
