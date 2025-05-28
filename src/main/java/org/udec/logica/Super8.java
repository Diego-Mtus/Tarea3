package org.udec.logica;

/** Representación especifica de un Super 8, que es un {@link Dulce} y {@link Producto},
 * por lo tanto, se puede usar.
 * */

public class Super8 extends Dulce{

    /** Constructor que recibe una serie y se la pasa a su superclase {@link Dulce}, que a su vez, se la pasa a {@link Producto}.
     * Simula el identificador único de ese Super 8.
     * @param serie Número de serie deseado para Super 8.*/
    public Super8(int serie){
        super(serie);
    }

    /** Método que sobreescribe el método heredado de {@link Dulce} para desplegar el consumo de Super 8.
     * @return Retorna "Se ha comido Super 8."*/
    public String usar(){return super.usar() + "Super 8.";}
}