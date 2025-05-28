package org.udec.logica;

/** Clase abstracta que representa una moneda, estas tienen un número de serie único y un valor asociado.
 * <p>
 *     La clase {@code Moneda} implementa la interfaz {@link Comparable} para poder compararlas mediante su valor.
 * </p>
 * <p>
 *     Las subclases que extiendan esta clase deberán tener su propia implementación del método {@code getValor()}.
 * </p>*/
abstract public class Moneda implements Comparable<Moneda>{

    /** Contador estático para asignarle un número de serie único a cada moneda.*/
    static private int serieContador = 1;

    /** Número de serie único de cada moneda.*/
    private int serie;

    /** Constructor que asigna un número de serie a la moneda. Este se obtiene del valor que tenga el contador estático
     * {@code serieContador}.*/
    public Moneda(){
        this.serie = serieContador;
        serieContador++;
    };

    /**
     * Obtiene el número de serie único de la moneda.
     * @return El número de serie de la moneda.
     */
    public int getSerie(){
        return this.serie;
    }

    /**
     * Método abstracto que debe ser implementado por las clases que extienden {@link Moneda}.
     * @return El valor de la moneda.
     */
    public abstract int getValor();

    /** Método que retorna una representación en String de la moneda, conteniendo su número de serie y valor.
     * @return Un String que describe a la moneda, incluyendo su número de serie y valor.*/
    @Override
    public String toString(){
        return "Moneda con serie " + this.getSerie() + " tiene valor de " + this.getValor();
    }

    /** Método que compara esta moneda con otra dependiendo de su valor.
     * @param m La moneda con la que se compara.
     * @return <ul><li>{@code -1} si esta moneda es menor.</li> <li>{@code 0} si esta moneda es igual.</li> <li>{@code 1} si esta moneda es mayor.</li></ul>*/
    @Override
    public int compareTo(Moneda m) {
        if(this.getValor() > m.getValor()){
            return 1;
        } else if (this.getValor() == m.getValor()){
            return 0;
        } else {
            return -1;
        }
    }
}
