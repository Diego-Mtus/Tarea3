package org.udec.logica;

/** Clase que simula un comprador que quiere comprar {@link Producto} desde un {@link Expendedor},
 * una vez que tenga el producto, puede usarlo y ver su vuelto tras esa compra.*/
public class Comprador {
    private String accion;
    private int vuelto;


    /** Constructor que simula la compra de un producto de parte del comprador.
     * El comprador selecciona un producto del {@link Expendedor} usando la moneda proporcionada.
     * <p>Si la compra es exitosa, se guarda la accion del producto y el vuelto. Si falla debido a una excepción,
     * solamente se guardará el vuelto.</p>
     *
     * @param moneda La {@link Moneda} utilizada para la compra.
     * @param cualProducto El índice del producto a comprar en la lista de productos disponibles. Vease índices de {@link ProductosEnum}
     * @param exp El {@link Expendedor} desde el cual se realiza la compra.
     * */
    public Comprador(Moneda moneda, ProductosEnum cualProducto, Expendedor exp){
        try {
            Producto productoComprado = exp.comprarProducto(moneda, cualProducto);
            accion = productoComprado.usar();
            vuelto = 0;
            Moneda aux = exp.getVuelto();
            while(aux != null){
                vuelto += aux.getValor();
                aux = exp.getVuelto();
            }
        } catch (PagoIncorrectoException e) {
            System.out.println("Comprador no pudo obtener producto debido a " + e.getMessage());
        } catch (NoHayProductoException | PagoInsuficienteException e){
            vuelto = moneda.getValor();
            System.out.println("Comprador no pudo obtener producto debido a " + e.getMessage());
            System.out.println("Su moneda ha sido devuelta.");
        }
    }

    /**
     * Obtiene el vuelto que recibió el comprador después de la compra.
     * @return El vuelto recibido por el comprador.
     */
    public int cuantoVuelto(){
        return this.vuelto;
    }

    /** Método que simula al comprador usando el producto.
     * @return Acción de usar el producto.*/
    public String queCompro(){
        return this.accion;
    }
}
