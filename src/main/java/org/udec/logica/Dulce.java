package org.udec.logica;

/** Representación abstracta de dulces que extiende a la clase {@link Producto}.
 * Todos los productos que sean del tipo dulce deberán heredar esta clase.
 * */
public abstract class Dulce extends Producto{

    /** Constructor que recibe una serie y se la pasa al constructor de su superclase {@link Producto}.
     * Sirve para simular un identificador único.
     * @param serie Número de serie deseado para el dulce.*/
    public Dulce(int serie) {
        super(serie);
    }

    /** Implementación de método abstracto heredado de {@link Producto}.
     * Su retorno está hecho para que los dulces que hereden esta clase puedan
     * concatenar su respectivo nombre.
     * @return Retorna "Se ha comido " */
    @Override
    public String usar() {
        return "Se ha comido ";
    }
}
