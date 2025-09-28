import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class menuCliente{

    private Map<Integer, Libro> catalogoLibros;
    private Map<Integer, ProductoCafeteria> menuCafeteria;
    private Map<Integer, Cliente> listaClientes;
    private List<Visita> visitas = new ArrayList<>();

    private Scanner escaner = new Scanner(System.in);
    private Visita visitaActual;

    public menuCliente(Map<Integer, Libro> catalogoLibros, 
                      Map<Integer, ProductoCafeteria> menuCafeteria,
                      Map<Integer, Cliente> listaClientes){
        this.catalogoLibros = catalogoLibros;
        this.menuCafeteria = menuCafeteria;
        this.listaClientes = listaClientes;
    }

    public void opcionesCliente(){
        System.out.println("\nMenú de Clientes");
        System.out.println("Clientes existentes:");
        for(Cliente clientesActuales : listaClientes.values()){
            System.out.println("ID: " + clientesActuales.getId() + ", " + clientesActuales.getNombre());
        }

        System.out.print("Ingrese su ID (o escriba '0' para registrarse): ");
        
        int idCliente = escaner.nextInt();
        escaner.nextLine();

        if(idCliente == 0){
            registrarNuevoCliente();
            return;
        }

        if (listaClientes.containsKey(idCliente)){
            Cliente clienteActual = listaClientes.get(idCliente);
            System.out.println("Bienvenido " + clienteActual.getNombre());
            iniciarVisita(clienteActual);
        } else {
            System.out.println("Cliente no encontrado");
        }
    }

    public void registrarNuevoCliente(){
        System.out.print("Nombre: ");
        String nombre = escaner.nextLine();

        System.out.print("Correo: ");
        String correo = escaner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = escaner.nextLine();

        Cliente nuevo = new Cliente(nombre,correo,telefono);
        listaClientes.put(nuevo.getId(),nuevo);
        System.out.println("Registro exitoso. Su ID es: " + nuevo.getId());
    }

    public void iniciarVisita(Cliente cliente){
        String fechaActual = obtenerFechaActual();
        visitaActual = new Visita(cliente,fechaActual);
        
        System.out.println("Nueva visita creada - Fecha: " + fechaActual);
        
        boolean continuar = true;

        while(continuar){
            System.out.println("\nBienvenido a la Cafetería, estas son nuestras opciones:");
            System.out.println("1. Ver catálogo de libros");
            System.out.println("2. Comprar libro");
            System.out.println("3. Rentar libro");
            System.out.println("4. Ver menú de cafetería");
            System.out.println("5. Comprar producto de cafetería");
            System.out.println("6. Ver resumen de mi visita");
            System.out.println("7. Finalizar visita y salir");
            System.out.print("Elija una opción: ");

            int opcion = escaner.nextInt();
            escaner.nextLine();

            switch(opcion){
                case 1 -> mostrarLibros();
                case 2 -> comprarLibro();
                case 3 -> rentarLibro();
                case 4 -> mostrarCafeteria();
                case 5 -> comprarProducto();
                case 6 -> verResumenVisita();
                case 7 -> { 
                    System.out.println("\nVisita Terminada");
                    visitaActual.finalizarVisita();
                    visitas.add(visitaActual);
                    
                    System.out.println("Total gastado: $" + visitaActual.getTotal());
                    System.out.println("Puntos ganados en esta visita: " + 
                                     (int)(visitaActual.getTotal() / 50) + " puntos");
                    System.out.println("Total puntos acumulados: " + cliente.getPuntos());
                    System.out.println("Gracias por su visita " + cliente.getNombre() + ", vuelva pronto c:");
                    
                    continuar = false;
                }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    public void mostrarLibros(){
        System.out.println("\nCatálogo de Libros");
        for (Libro libro : catalogoLibros.values()){
            System.out.println(libro);
        }
    }

    public void comprarLibro(){
        mostrarLibros();
        System.out.print("Ingrese el ID del libro a comprar: ");
        int idLibro = escaner.nextInt();

        escaner.nextLine();

        if(catalogoLibros.containsKey(idLibro)){
            Libro libro = catalogoLibros.get(idLibro);
            if(libro.estaDisponible()){
                libro.marcarNoDisponible();
                visitaActual.agregarLibroComprado(libro);
                visitaActual.calcularTotal();
                System.out.println("Libro agregado a tu carrito.");
            } else {
                System.out.println("El libro no está disponible.");
            }
        } else {
            System.out.println("ID no encontrado.");
        }
    }

    public void rentarLibro() {
        mostrarLibros();
        System.out.print("Ingrese el ID del libro a rentar: ");
        int idLibro = escaner.nextInt();

        escaner.nextLine();

        if (catalogoLibros.containsKey(idLibro)) {
            Libro libro = catalogoLibros.get(idLibro);
            if(libro.estaDisponible()){
                libro.marcarNoDisponible();
                String fechaEntrega = fechaDevolucion();

                System.out.println("Libro rentado / Tiene 7 días.");
                System.out.println("Devolver el día: " + fechaEntrega);
                libro.setDevolucion(fechaEntrega);
                visitaActual.agregarLibroRentado(libro);
                visitaActual.calcularTotal();
            } else {
                System.out.println("El libro no está disponible para renta");
            }
        } else {
            System.out.println("ID no encontrado");
        }
    }

    public void mostrarCafeteria(){
        System.out.println("\nMenú de Cafetería");
        for (ProductoCafeteria producto : menuCafeteria.values()) {
            System.out.println(producto);
        }
    }

    public void comprarProducto(){
        mostrarCafeteria();
        System.out.print("Ingrese el ID del producto a comprar: ");

        int idProd = escaner.nextInt();
        escaner.nextLine();

        if (menuCafeteria.containsKey(idProd)) {
            ProductoCafeteria prod = menuCafeteria.get(idProd);
            visitaActual.agregarProducto(prod);
            visitaActual.calcularTotal();
            System.out.println("Producto añadido a tu carrito.");
        } else {
            System.out.println("ID no encontrado.");
        }
    }

    public void verResumenVisita() {
        visitaActual.mostrarDetalles();
        
        if(visitaActual.getCantidadLibrosComprados() == 0 || 
            visitaActual.getCantidadLibrosRentados() == 0 || 
            visitaActual.getCantidadProductosConsumidos() == 0) {
                System.out.println("\nInformación de compra/visita:");
                System.out.println("Aún no has agregado items a tu visita.");
        }
    }

    public String obtenerFechaActual(){
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaRegistro = formato.format(hoy);
        return fechaRegistro;
    }

    public String fechaDevolucion(){
        LocalDate hoy = LocalDate.now();
        LocalDate sigSemana = hoy.plusDays(7);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String devolucion = formato.format(sigSemana);
        return devolucion;
    }
}