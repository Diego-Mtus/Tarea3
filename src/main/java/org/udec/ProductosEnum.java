package org.udec;

/**
 * Enumeración que representa los productos disponibles, que incluyen tanto bebidas como dulces.
 * Cada producto tiene un índice único, un precio modificable y un nombre.
 * <p>
 * Productos existentes:
 * </p>
 * <ul>
 *     <li><strong>Bebidas:</strong> Coca-Cola, Sprite, Fanta.</li>
 *     <li><strong>Dulces:</strong> Snickers, Super8.</li>
 * </ul>
 *
 */

public enum ProductosEnum {

    // Bebidas
    /** Representa el producto Coca-Cola con un índice 1, un precio de 500 y el nombre "Coca-Cola". */
    COCACOLA(1, 500, "Coca-Cola"),
    /** Representa el producto Sprite con un índice 2, un precio de 500 y el nombre "Sprite". */
    SPRITE(2, 500, "Sprite"),
    /** Representa el producto Fanta con un índice 3, un precio de 400 y el nombre "Fanta". */
    FANTA(3, 400, "Fanta"),

    // Dulces
    /** Representa el producto Snickers con un índice 4, un precio de 500 y el nombre "Snickers". */
    SNICKERS(4, 500, "Snickers"),
    /** Representa el producto Super8 con un índice 5, un precio de 300 y el nombre "Super8". */
    SUPER8(5, 300, "Super8");


    private final int indice;
    private int precio;
    private final String nombre;

    /** Constructor que inicializa valores de la enumeración.
     * @param indice El índice único para cada producto.
     * @param precio El precio del producto.
     * @param nombre El nombre del producto.*/
    ProductosEnum(int indice, int precio, String nombre){
        this.indice = indice;
        this.precio = precio;
        this.nombre = nombre;
    }

    /**
     * Establece el precio del producto.
     * @param precio El nuevo precio del producto.
     */
    public void setPrecio(int precio){
        this.precio = precio;
    }

    /**
     * Obtiene el precio del producto.
     * @return El precio del producto.
     */
    public int getPrecio(){
        return this.precio;
    }

    /**
     * Obtiene el índice único del producto.
     * @return El índice único del producto.
     */
    public int getIndice() {
        return this.indice;
    }

    /**
     * Obtiene el nombre del producto.
     * @return El nombre del producto.
     */
    public String getNombre(){
        return this.nombre;
    }

}