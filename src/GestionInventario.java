import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GestionInventario {

    private static ArrayList<Producto> inventario = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicializar inventario base
        inicializarInventario();

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

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

    // Menú principal
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

    // Inicializar inventario con productos base
    private static void inicializarInventario() {
        inventario.add(new Producto("P001", "Portátil", 899.99));
        inventario.add(new Producto("P002", "Ratón", 25.50));
        inventario.add(new Producto("P003", "Teclado", 45.00));
        inventario.add(new Producto("P004", "Monitor", 199.99));
        inventario.add(new Producto("P005", "Webcam", 59.90));
    }

    // Mostrar todos los productos
    private static void mostrarInventario() {
        System.out.println("\n--- INVENTARIO ---");
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            for (Producto p : inventario) {
                System.out.println(p);
            }
        }
    }

    // Buscar producto por código
    private static void buscarProductoPorCodigo() {
        System.out.print("Ingrese el código del producto a buscar: ");
        String codigo = sc.nextLine();
        Producto encontrado = null;
        for (Producto p : inventario) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                encontrado = p;
                break;
            }
        }
        if (encontrado != null) {
            System.out.println("Producto encontrado: " + encontrado);
        } else {
            System.out.println("No se encontró ningún producto con ese código.");
        }
    }

    // Buscar producto por nombre
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

    // Añadir nuevo producto
    private static void agregarProducto() {
        System.out.print("Ingrese código del nuevo producto: ");
        String codigo = sc.nextLine();

        // Verificar que el código no exista
        for (Producto p : inventario) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                System.out.println("Error: Ya existe un producto con ese código.");
                return;
            }
        }

        System.out.print("Ingrese nombre del producto: ");
        String nombre = sc.nextLine();

        double precio = leerDouble("Ingrese precio del producto: ");
        if(precio < 0){
            System.out.println("Error: El precio no puede ser negativo.");
            return;
        }

        inventario.add(new Producto(codigo, nombre, precio));
        System.out.println("Producto añadido correctamente.");
    }

    // Eliminar producto
    private static void eliminarProducto() {
        System.out.print("Ingrese el código del producto a eliminar: ");
        String codigo = sc.nextLine();
        boolean eliminado = inventario.removeIf(p -> p.getCodigo().equalsIgnoreCase(codigo));
        if (eliminado) {
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún producto con ese código.");
        }
    }

    // Modificar precio de un producto
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
            encontrado.setPrecio(nuevoPrecio);
            System.out.println("Precio actualizado correctamente.");
        } else {
            System.out.println("No se encontró ningún producto con ese código.");
        }
    }

    // Operaciones avanzadas
    private static void operacionesAvanzadas() {
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío.");
            return;
        }

        double total = 0;
        Producto masCaro = inventario.get(0);
        System.out.println("\nProductos con precio > 50€:");
        for (Producto p : inventario) {
            total += p.getPrecio();
            if (p.getPrecio() > masCaro.getPrecio()) {
                masCaro = p;
            }
            if (p.getPrecio() > 50) {
                System.out.println(p);
            }
        }
        System.out.println("Precio total del inventario: " + total + "€");
        System.out.println("Producto más caro: " + masCaro);
    }

    // Método para leer enteros con manejo de excepciones
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

    // Método para leer doubles con manejo de excepciones
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

