import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class administrarTienda{
    private Map<Integer, Libro> catalogoLibros;
    private Map<Integer, ProductoCafeteria> menuCafeteria;
    private Scanner sc = new Scanner(System.in);

    public administrarTienda(Map<Integer, Libro> catalogoLibros, Map<Integer, ProductoCafeteria> menuCafeteria){
        this.catalogoLibros = catalogoLibros;
        this.menuCafeteria = menuCafeteria;
    }

	public void menuAdmin(){
		int opcEmpleado;
		System.out.println("\nMenú para empleados");
		do{
			System.out.println("1)Agregar Libro\n2)Retirar Libro\n3)Ver Libros"
				+"\n4)Cambiar información de libro");
			System.out.println("5)Agregar Productos de Cafetería\n6)Eliminar Producto"
				+ "\n7)Ver Menú de Cafetería\n8)Cambiar Producto");
			System.out.println("9)Salir");
			System.out.print("Escoja la acción a modificar: ");
			opcEmpleado = sc.nextInt();
			sc.nextLine();

			switch(opcEmpleado){
				case 1 -> anidarLibro();
				case 2 -> retirarLibro();
				case 3 -> verLibros();
				case 4 -> editarLibro();
				case 5 -> anidarProducto();
				case 6 -> eliminarProducto();
				case 7 -> verProductos();
				case 8 -> editarProducto();
				case 9 -> System.out.println("Saliendo.");
				default -> System.out.println("Error...");
			}
		} while(opcEmpleado != 9);
	}

	public void anidarLibro(){
		System.out.print("Ingrese el nombre del libro: ");
		String nombreLibro = sc.nextLine();
	
		System.out.print("Ingrese el autor: ");
		String autorLibro = sc.nextLine();

		System.out.print("Ingrese el género del libro: ");
		String generoLibro = sc.nextLine();

		System.out.print("Ingrese el precio del libro: ");
		double precioLibro = sc.nextDouble();
		sc.nextLine();

		Libro libroNuevo = new Libro(nombreLibro, autorLibro, generoLibro, precioLibro, true);
		catalogoLibros.put(libroNuevo.getId(), libroNuevo);
	}

	public void retirarLibro(){
		if(catalogoLibros.isEmpty()){
			System.out.println("La colección de libros se encuentra vacía.");
			return;
		}

		int idBorrar;
		do{
			System.out.print("Ingrese el ID del libro a retirar: ");
			idBorrar = sc.nextInt();
			if (!catalogoLibros.containsKey(idBorrar)){
        		System.out.println("No existe un libro con ese ID.");
			}
		} while(!catalogoLibros.containsKey(idBorrar));

		catalogoLibros.remove(idBorrar);
		System.out.println("Libro retirado exitosamente.");
	}

	public void verLibros(){
		if(catalogoLibros.isEmpty()){
			System.out.println("La colección de libros se encuentra vacía.");
			return;
		}

		System.out.println("Lista de Libros: ");
		for(Libro libro : catalogoLibros.values()){
			System.out.println(libro.toString());
		}
	}

	public void editarLibro(){
		if(catalogoLibros.isEmpty()){
			System.out.println("La colección de libros se encuentra vacía.");
			return;
		}

		int idBuscar;
		do{
			System.out.print("Ingrese la clave del libro a editar: ");
			idBuscar = sc.nextInt();

			if (!catalogoLibros.containsKey(idBuscar)){
        		System.out.println("No existe un libro con ese ID.");
			}
		} while(!catalogoLibros.containsKey(idBuscar));

		Libro libroEditar = catalogoLibros.get(idBuscar);

		int opcEditar;
		do{
			System.out.println("1)Titulo\n2)Autor\n3)Genero"
				+"\n4)Precio\n5)Salir");
			System.out.print("¿Qué desea editar?: ");
			opcEditar = sc.nextInt();
			sc.nextLine();
			switch(opcEditar){
				case 1 -> {
					System.out.print("Ingrese el nuevo título: ");
					String nuevoNombre = sc.nextLine();
					libroEditar.setTitulo(nuevoNombre);
				}
				case 2 -> {
					System.out.print("Ingrese el nuevo autor: ");
					String nuevoAutor = sc.nextLine();
					libroEditar.setAutor(nuevoAutor);
				}
				case 3 -> {
					System.out.print("Ingrese el género: ");
					String nuevoGenero = sc.nextLine();
					libroEditar.setGenero(nuevoGenero);
				}
				case 4 -> {
					System.out.print("Ingrese el nuevo precio: ");
					double nuevoPrecio;

					do{
						System.out.print("Ingrese el precio del libro: ");
						nuevoPrecio = sc.nextDouble();

						if(nuevoPrecio <= 0){
							System.out.println("Precio no válido. Inserte otro.");
						}
					} while(nuevoPrecio <= 0);
					libroEditar.setPrecio(nuevoPrecio);
				}
				case 5 -> System.out.println("Saliendo de la edición de libros...");
				default -> System.out.println("Error...");
			}
		} while(opcEditar != 5);
	}

	///Productos de Cafetería
	public void anidarProducto(){
		System.out.print("Ingrese el nombre del producto: ");
		String cafeProducto = sc.nextLine();

		System.out.print("Ingrese la categoría del producto: ");
		String tipoProducto = sc.nextLine();

		double precioProducto;
		do{
			System.out.print("Ingrese el precio del producto: ");
			precioProducto = sc.nextDouble();
			sc.nextLine();

			if(precioProducto <= 0){
				System.out.println("Precio no válido. Inserte otro.");
			}
		} while(precioProducto <= 0);

		ProductoCafeteria productoNuevo = new ProductoCafeteria(cafeProducto, precioProducto, tipoProducto);
		
		menuCafeteria.put(productoNuevo.getId(), productoNuevo);
	}

	public void eliminarProducto(){
		if(menuCafeteria.isEmpty()){
			System.out.println("Sin elementos en cafetería.");
			return;
		}

		int idBorrar;
		do{
			System.out.print("Ingrese el ID del producto a retirar: ");
			idBorrar = sc.nextInt();
			if (!menuCafeteria.containsKey(idBorrar)) {
        System.out.println("No existe un producto con ese ID");
			}
		} while(!menuCafeteria.containsKey(idBorrar));

		menuCafeteria.remove(idBorrar);
		System.out.println("Producto retirado exitosamente.");
	}

	public void verProductos(){
		if(menuCafeteria.isEmpty()){
			System.out.println("Sin productos añadidos.");
			return;
		}

		System.out.println("Lista de Productos de Cafetería: ");
		for(ProductoCafeteria alimento : menuCafeteria.values()){
			System.out.println(alimento.toString());
		}
	}

	public void editarProducto(){
		if(menuCafeteria.isEmpty()){
        	System.out.println("Sin productos añadidos.");
        	return;
    	}

	    int idBuscar;
	    do{
	        System.out.print("Ingrese el ID del producto a editar: ");
	        idBuscar = sc.nextInt();
	        sc.nextLine();

	        if (!menuCafeteria.containsKey(idBuscar)) {
	            System.out.println("No existe un producto con ese ID");
	        }
    	} while(!menuCafeteria.containsKey(idBuscar));

	    ProductoCafeteria productoEditar = menuCafeteria.get(idBuscar);
	    int opcEditar;
		do{
			System.out.println("¿Qué desea editar?:");
			System.out.println("1. Nombre");
			System.out.println("2. Categoría");
			System.out.println("3. Precio");
			System.out.println("4. Salir");
			System.out.print("Opción: ");

			opcEditar = sc.nextInt();
			sc.nextLine();

			switch (opcEditar){
				case 1:
					System.out.print("Ingrese el nuevo nombre: ");
					String nuevoNombre = sc.nextLine();
					productoEditar.setProducto(nuevoNombre);
					break;
				case 2:
					System.out.print("Ingrese la nueva categoría: ");
					String nuevaCategoria = sc.nextLine();
					productoEditar.setCategoria(nuevaCategoria);
					break;
				case 3:
					System.out.print("Ingrese el nuevo precio: ");
					double nuevoPrecio = sc.nextDouble();
					sc.nextLine();
					if(nuevoPrecio > 0){
						productoEditar.setPrecio(nuevoPrecio);
					} else {
						System.out.println("El precio debe ser mayor que 0.");
					}
					break;
				case 4:
					System.out.println("Terminando edición.");
					break;
				default:
					System.out.println("Opción no válida.");
			}
		} while (opcEditar != 4);
	}
}