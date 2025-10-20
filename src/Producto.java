// Clase que representa un producto del inventario.
// Contiene un código identificador, un nombre y un precio.
public class Producto {
    // Código único del producto (ej. "P001").
    private final String codigo;
    // Nombre descriptivo del producto (ej. "Portátil").
    private final String nombre;
    // Precio en euros. Puede ser un número con decimales.
    private double precio;

    // Constructor
    // Parámetros:
    //  - codigo: identificador único del producto
    //  - nombre: nombre descriptivo
    //  - precio: precio en euros (double)
    public Producto(String codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters: permiten obtener los valores de los campos.
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }

    // Setter para precio: permite actualizar únicamente el precio del producto.
    public void setPrecio(double precio) { this.precio = precio; }

    // toString() sobrescrito para mostrar una representación legible del producto.
    // Ejemplo de salida: "P001 - Portátil (899.99€)"
    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + precio + "€)";
    }
}
