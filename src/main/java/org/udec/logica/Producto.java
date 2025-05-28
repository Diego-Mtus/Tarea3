package org.udec.logica;

/** Clase que define un producto abstracto. Sirve como base para crear distintos productos que se puedan usar.*/
public abstract class Producto {

    /** Almacena el número de serie de un producto. Simula un identificador único.*/
    private int serie;


    /** Constructor que inicializa número de serie.
     * @param serie Número de serie deseado para producto. */
    public Producto(int serie) {
        this.serie = serie;
    }

    /** Método accesor del atributo serie.
     * @return Número de serie del producto.*/
    public int getSerie() {
        return serie;
    }

    /** Método abstracto que debe implementarse a cada producto que extienda {@link Producto}.
     * Define el comportamiento especifico al usar un producto.
     * @return Acción de usar el producto*/
    public abstract String usar();
}
