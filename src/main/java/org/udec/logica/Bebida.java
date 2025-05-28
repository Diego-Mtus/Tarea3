package org.udec.logica;

/** Representación abstracta de bebidas que extiende a la clase {@link Producto}.
 * Todos los productos que sean del tipo bebida deberán heredar esta clase.
 * */
public abstract class Bebida extends Producto{

    /** Constructor que recibe una serie y se la pasa al constructor de su superclase {@link Producto}.
     * Sirve para simular un identificador único.
     * @param serie Número de serie deseado para bebida.*/
    public Bebida(int serie) {
        super(serie);
    }

    /** Implementación de método abstracto heredado de {@link Producto}.
     * Su retorno está hecho para que las bebidas que hereden esta clase puedan
     * concatenar su respectivo nombre.
     * @return Retorna "Se ha tomado " */
    @Override
    public String usar() {
        return "Se ha tomado ";
    }
}