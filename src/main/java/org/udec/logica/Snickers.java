package org.udec.logica;

/** Representación especifica de un Snickers, que es un {@link Dulce} y {@link Producto},
 * por lo tanto, se puede usar.
 * */
public class Snickers extends Dulce{

    /** Constructor que recibe una serie y se la pasa a su superclase {@link Dulce}, que a su vez, se la pasa a {@link Producto}.
     * Simula el identificador único de ese Snickers.
     * @param serie Número de serie deseado para Snickers.*/
    public Snickers(int serie){
        super(serie);
    }

    /** Método que sobreescribe el método heredado de {@link Dulce} para desplegar el consumo de Snickers.
     * @return Retorna "Se ha comido Snickers."*/
    public String usar(){ return super.usar() + "Snickers.";}
}