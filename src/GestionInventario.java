// Clase responsable de gestionar un inventario simple usando ArrayList.
// Permite mostrar, buscar, añadir, eliminar y modificar productos, además de
// algunas operaciones agregadas (total, productos caros, producto más caro).
import java.util.ArrayList;
import java.util.Scanner;

public class GestionInventario {

    // Lista que almacena los productos del inventario.
    private static final ArrayList<Producto> inventario = new ArrayList<>();
    // Scanner compartido para leer entradas desde la consola.
    private static final Scanner sc = new Scanner(System.in);

    // Punto de entrada del programa. Muestra un menú y permite al usuario
    // ejecutar las operaciones disponibles hasta que elija salir.
    public static void main(String[] args) {
        // Inicializar inventario con algunos productos de ejemplo.
        inicializarInventario();

        int opcion;
        do {
            mostrarMenu();
            // Leer la opción elegida por el usuario (con manejo de errores).
            opcion = leerEntero("Seleccione una opción: ");

            // Selección de la operación mediante switch.
            switch (opcion) {
                case 1 -> mostrarInventario();
                case 2 -> buscarProductoPorCodigo();
                case 3 -> buscarProductoPorNombre();
                case 4 -> agregarProducto();
                case 5 -> eliminarProducto();
                case 6 -> modificarPrecioProducto();
                case 7 -> operacionesAvanzadas();
                case 8 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción incorrecta, intente nuevamente.");
            }
        } while (opcion != 8);
    }

    // Menú principal que muestra las opciones disponibles al usuario.
    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ DE INVENTARIO ---");
        System.out.println("1. Mostrar inventario");
        System.out.println("2. Buscar producto por código");
        System.out.println("3. Buscar producto por nombre");
        System.out.println("4. Añadir producto");
        System.out.println("5. Eliminar producto");
        System.out.println("6. Modificar precio de un producto");
        System.out.println("7. Operaciones avanzadas");
        System.out.println("8. Salir");
    }

    // Inicializar inventario con productos base de ejemplo.
    private static void inicializarInventario() {
        inventario.add(new Producto("P001", "Portátil", 899.99));
        inventario.add(new Producto("P002", "Ratón", 25.50));
        inventario.add(new Producto("P003", "Teclado", 45.00));
        inventario.add(new Producto("P004", "Monitor", 199.99));
        inventario.add(new Producto("P005", "Webcam", 59.90));
    }

    // Mostrar todos los productos del inventario por pantalla.
    private static void mostrarInventario() {
        System.out.println("\n--- INVENTARIO ---");
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            // El método toString() de Producto es usado para la representación.
            for (Producto p : inventario) {
                System.out.println(p);
            }
        }
    }

    // Buscar producto por código (búsqueda no sensible a mayúsculas/minúsculas).
    private static void buscarProductoPorCodigo() {
        System.out.print("Ingrese el código del producto a buscar: ");
        String codigo = sc.nextLine();
        Producto encontrado = null;
        for (Producto p : inventario) {
            // equalsIgnoreCase permite que "P001" y "p001" se consideren iguales.
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                encontrado = p;
                break; // Se asume código único, se rompe tras encontrarlo.
            }
        }
        if (encontrado != null) {
            System.out.println("Producto encontrado: " + encontrado);
        } else {
            System.out.println("No se encontró ningún producto con ese código.");
        }
    }

    // Buscar productos por nombre y mostrar todos los que coincidan (no solo el primero).
    private static void buscarProductoPorNombre() {
        System.out.print("Ingrese el nombre del producto a buscar: ");
        String nombre = sc.nextLine();
        boolean encontrado = false;
        for (Producto p : inventario) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Producto encontrado: " + p);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró ningún producto con ese nombre.");
        }
    }

    // Añadir un nuevo producto tras verificar que el código no exista ya.
    private static void agregarProducto() {
        System.out.print("Ingrese código del nuevo producto: ");
        String codigo = sc.nextLine();

        // Verificar que el código no exista (evitar duplicados).
        for (Producto p : inventario) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                System.out.println("Error: Ya existe un producto con ese código.");
                return; // Salir del método sin añadir.
            }
        }

        System.out.print("Ingrese nombre del producto: ");
        String nombre = sc.nextLine();

        // Leer precio con control de formato (evita NumberFormatException visible al usuario).
        double precio = leerDouble("Ingrese precio del producto: ");
        if(precio < 0){
            System.out.println("Error: El precio no puede ser negativo.");
            return;
        }

        // Añadir el nuevo Producto a la lista.
        inventario.add(new Producto(codigo, nombre, precio));
        System.out.println("Producto añadido correctamente.");
    }

    // Eliminar producto por código; usa removeIf para eliminar todas las coincidencias.
    private static void eliminarProducto() {
        System.out.print("Ingrese el código del producto a eliminar: ");
        String codigo = sc.nextLine();
        // removeIf devuelve true si al menos un elemento fue eliminado.
        boolean eliminado = inventario.removeIf(p -> p.getCodigo().equalsIgnoreCase(codigo));
        if (eliminado) {
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún producto con ese código.");
        }
    }

    // Modificar el precio de un producto buscando por código.
    private static void modificarPrecioProducto() {
        System.out.print("Ingrese el código del producto a modificar: ");
        String codigo = sc.nextLine();
        Producto encontrado = null;
        for (Producto p : inventario) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                encontrado = p;
                break;
            }
        }
        if (encontrado != null) {
            double nuevoPrecio = leerDouble("Ingrese el nuevo precio: ");
            if(nuevoPrecio < 0){
                System.out.println("Error: El precio no puede ser negativo.");
                return;
            }
            // Actualiza el precio usando el setter de Producto.
            encontrado.setPrecio(nuevoPrecio);
            System.out.println("Precio actualizado correctamente.");
        } else {
            System.out.println("No se encontró ningún producto con ese código.");
        }
    }

    // Operaciones avanzadas: calcula el total, muestra productos con precio > 50€
    // y determina el producto más caro del inventario.
    private static void operacionesAvanzadas() {
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío.");
            return;
        }

        double total = 0;
        // Se inicializa masCaro con el primer elemento para poder compararlo.
        Producto masCaro = inventario.get(0);
        System.out.println("\nProductos con precio > 50€:");
        for (Producto p : inventario) {
            total += p.getPrecio();
            if (p.getPrecio() > masCaro.getPrecio()) {
                masCaro = p; // Actualiza el producto más caro encontrado hasta ahora.
            }
            if (p.getPrecio() > 50) {
                System.out.println(p);
            }
        }
        System.out.println("Precio total del inventario: " + total + "€");
        System.out.println("Producto más caro: " + masCaro);
    }

    // Método para leer enteros con manejo de excepciones. Lee una línea y la parsea
    // como entero; si falla, repite la petición hasta que el usuario introduzca
    // un valor válido.
    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Debe ingresar un número entero.");
            }
        }
    }

    // Método para leer doubles (decimales) con manejo de excepciones similar.
    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Debe ingresar un número decimal.");
            }
        }
    }
}
