package org.udec.logica;
import java.util.Scanner;


/** Main interactivo para poder probar la funcionalidad del proyecto de manera interactiva, teniendo un menú principal con
 * las acciones que se quieran realizar.*/
public class MainInteractivo {

    /** Cantidad de opciones disponibles en nuestro menú interactivo.
     * <p>Se debe actualizar manualmente de acuerdo a cuántas opciones definamos.</p> */
    static final int CANTIDAD_INDICE = 7;

    /** Despliegue de menú principal con sus índices para poder ir a la opción que queramos.*/
    private static void pantallaPrincipal(){
        System.out.println("\nMenú principal");
        System.out.println("0. Salir.");
        System.out.println("1. Ver precio actual de los productos.");
        System.out.println("2. Cambiar el precio de productos.");
        System.out.println("3. Obtener moneda.");
        System.out.println("4. Crear expendedor (solo podrá haber un expendedor activo).");
        System.out.println("5. Comprar producto.");
        System.out.println("6. Consumir producto.");
        System.out.println("7. Revisar vuelto.");
        System.out.println("Escribe el número de la operación que quieres realizar, y presiona enter:");
    }

    /** Verificador que lee una entrada y comprueba que sea un número entero y que esté dentro de cierto rango.
     * @param input El scanner que lee la entrada.
     * @param cantidadOpciones El límite superior del rango permitido de la entrada.
     * @return Retorna el entero ingresado que cumpla las condiciones.*/
    public static int leerNumero(Scanner input, int cantidadOpciones){
        boolean inError = true;
        int eleccion = -1;

        while (inError) {

            if (input.hasNextInt()) {
                eleccion = input.nextInt();
            } else {
                input.next();
                System.out.println("Debes ingresar un número entero.");
                continue;
            }

            if (eleccion < 0 || eleccion > cantidadOpciones) {
                System.out.println("El número elegido está fuera de rango.");
                continue;
            }

            inError = false;
        }

        return eleccion;
    }


    public static void main(String[] args) {

        Scanner inputPrincipal = new Scanner(System.in);
        boolean loopMenu = true;
        Moneda monedaUsuario = null;
        Expendedor expendedor = null;
        Comprador comprador = null;

        while(loopMenu) {

            pantallaPrincipal();
            int eleccion = leerNumero(inputPrincipal, CANTIDAD_INDICE);
            System.out.println("Número elegido: " + eleccion);

            switch (eleccion) {
                case 0:
                    loopMenu = false;
                    break;

                case 1:
                    System.out.println("Precio de productos:");
                    for (ProductosEnum producto : ProductosEnum.values()) {
                        System.out.println(producto.getIndice() + ". " + producto.getNombre() + ": $" + producto.getPrecio());
                    }
                    break;

                case 2:
                    System.out.println("Elige el índice del producto que quieras cambiar el precio, o elige 0 para salir");
                    for (ProductosEnum producto : ProductosEnum.values()) {
                        System.out.println(producto.getIndice() + ". " + producto.getNombre() + ": $" + producto.getPrecio());
                    }

                    int elegirProductoCambio = leerNumero(inputPrincipal, ProductosEnum.values().length);
                    if (elegirProductoCambio == 0) {
                        break;
                    }
                    System.out.println("Se escogió " + ProductosEnum.values()[elegirProductoCambio -1].getNombre() + ", ¿Qué precio deseas ponerle?");
                    int cambioPrecio = leerNumero(inputPrincipal, Integer.MAX_VALUE);
                    ProductosEnum.values()[elegirProductoCambio - 1].setPrecio(cambioPrecio);
                    System.out.println("El nuevo precio asignado para " + ProductosEnum.values()[elegirProductoCambio - 1].getNombre() + " es de $" + ProductosEnum.values()[elegirProductoCambio - 1].getPrecio());
                    break;

                case 3:
                    if(monedaUsuario == null) {
                        System.out.println("Actualmente no se tiene ninguna moneda");
                    } else {
                        System.out.println("La moneda que se tiene actualmente es de $" + monedaUsuario.getValor());
                    }

                    System.out.println("Elige el índice de la moneda que quieras, o elige 0 para salir" +
                            "\n 1. $100" +
                            "\n 2. $500" +
                            "\n 3. $1000");
                    int eleccionMoneda = leerNumero(inputPrincipal, 3);
                    if(eleccionMoneda == 0) { break;}
                    monedaUsuario = switch (eleccionMoneda) {
                        case 1 -> new Moneda100();
                        case 2 -> new Moneda500();
                        case 3 -> new Moneda1000();
                        default -> monedaUsuario;
                    };
                    break;

                case 4:
                    System.out.println("Ingresa cuánto stock tendrá de cada producto, o elige 0 para salir:");
                    int stock = leerNumero(inputPrincipal, 100);
                    if(stock == 0) { break;}
                    expendedor = new Expendedor(stock);
                    break;

                case 5:
                    if(expendedor == null) {
                        System.out.println("Necesitas crear un expendedor para poder comprar.");
                        break;
                    }
                    System.out.println("Elige el índice del producto que quieras comprar, o elige 0 para salir");
                    for (ProductosEnum producto : ProductosEnum.values()) {
                        System.out.println(producto.getIndice() + ". " + producto.getNombre() + ": $" + producto.getPrecio());
                    }

                    int elegirCompra = leerNumero(inputPrincipal, ProductosEnum.values().length);
                    if (elegirCompra == 0) {
                        break;
                    }
                    System.out.println("Se ha elegido " + ProductosEnum.values()[elegirCompra - 1].getNombre());
                    comprador = new Comprador(monedaUsuario, ProductosEnum.values()[elegirCompra - 1], expendedor);
                    break;

                case 6:
                    if(comprador == null) {
                        System.out.println("No has comprado nada todavía.");
                        break;
                    }
                    String sonido = comprador.queCompro();
                    if(sonido == null){
                        System.out.println("No has comprado nada todavía.");
                        break;
                    }
                    System.out.println(comprador.queCompro());
                    break;

                case 7:
                    if(comprador == null) {
                        System.out.println("No has comprado nada todavía.");
                        break;
                    }
                    System.out.println("En tu última compra has recibido un vuelto de $" + comprador.cuantoVuelto());
                    break;

            }
        }
        inputPrincipal.close();
        System.out.println("El programa ha finalizado exitosamente :)");

    }
}
