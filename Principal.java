import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Driver: maneja la consola y delega las operaciones a los objetos. */
public class Principal {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        try {
            System.out.println("AGENCIA DE DETECTIVES - CASO MISTERIOSO");
            Caso caso = null;
            while (caso == null) {
                try {
                    caso = leerCaso(entrada);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage() + " Intente nuevamente.");
                }
            }

            boolean continuar = true;
            while (continuar) {
                mostrarMenu();
                try {
                    int opcion = leerEntero(entrada, "Seleccione una opcion: ");
                    switch (opcion) {
                        case 1:
                            caso = leerCaso(entrada);
                            System.out.println("Nuevo caso creado sin ubicaciones ni pistas.");
                            break;
                        case 2:
                            int posicion = leerEntero(entrada, "Posicion (0 a 4): ");
                            Ubicacion ubicacion = new Ubicacion(
                                    leerTexto(entrada, "Codigo: "), leerTexto(entrada, "Nombre: "),
                                    leerTexto(entrada, "Direccion o descripcion: "),
                                    leerEntero(entrada, "Riesgo (1 a 10): "),
                                    leerTexto(entrada, "Estado: "));
                            caso.registrarUbicacion(posicion, ubicacion);
                            System.out.println("Ubicacion registrada.");
                            break;
                        case 3:
                            System.out.println(caso.consultarUbicaciones());
                            break;
                        case 4:
                            System.out.println(caso.consultarUbicacion(
                                    leerEntero(entrada, "Posicion (0 a 4): ")));
                            break;
                        case 5:
                            int posicionModificar = leerEntero(entrada, "Posicion (0 a 4): ");
                            System.out.println(caso.consultarUbicacion(posicionModificar));
                            caso.modificarUbicacion(posicionModificar,
                                    leerEntero(entrada, "Nuevo riesgo (1 a 10): "),
                                    leerTexto(entrada, "Nuevo estado: "));
                            System.out.println("Ubicacion modificada.");
                            break;
                        case 6:
                            caso.descartarUbicacion(leerEntero(entrada, "Posicion (0 a 4): "));
                            System.out.println("Ubicacion descartada; posicion disponible.");
                            break;
                        case 7:
                            caso.registrarPista(leerPista(entrada));
                            System.out.println("Pista registrada.");
                            break;
                        case 8:
                            System.out.println(caso.consultarPistas());
                            break;
                        case 9:
                            System.out.println(caso.buscarPista(leerTexto(entrada, "Codigo de pista: ")));
                            break;
                        case 10:
                            String codigoActual = leerTexto(entrada, "Codigo de la pista a modificar: ");
                            System.out.println(caso.buscarPista(codigoActual));
                            System.out.println("Ingrese todos los nuevos datos (puede conservar el codigo).");
                            Pista nueva = leerPista(entrada);
                            caso.modificarPista(codigoActual, nueva.getCodigo(), nueva.getDescripcion(),
                                    nueva.getTipoEvidencia(), nueva.getNivelImportancia(),
                                    nueva.getNivelConfiabilidad());
                            System.out.println("Pista modificada.");
                            break;
                        case 11:
                            caso.eliminarPista(leerTexto(entrada, "Codigo de pista: "));
                            System.out.println("Pista eliminada.");
                            break;
                        case 12:
                            System.out.println(caso.generarReporte());
                            break;
                        case 13:
                            continuar = false;
                            System.out.println("Hasta pronto.");
                            break;
                        default:
                            System.out.println("Opcion invalida. Seleccione un numero del 1 al 13.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nEntrada finalizada. Se cierra el programa.");
        } finally {
            // Liberar el recurso incluso si se cierra la entrada o sucede una excepcion.
            entrada.close();
        }
    }

    private static String leerTexto(Scanner entrada, String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine();
    }

    private static int leerEntero(Scanner entrada, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = entrada.nextInt();
                entrada.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                entrada.nextLine(); // Descartar el dato incorrecto para no repetir el error.
                System.out.println("Entrada incorrecta: ingrese un numero entero.");
            }
        }
    }

    private static Caso leerCaso(Scanner entrada) {
        System.out.println("--- Datos del caso ---");
        return new Caso(leerTexto(entrada, "Nombre del caso: "),
                leerTexto(entrada, "Codigo del caso: "),
                leerTexto(entrada, "Detective responsable: "));
    }

    private static Pista leerPista(Scanner entrada) {
        return new Pista(leerTexto(entrada, "Codigo: "),
                leerTexto(entrada, "Descripcion: "), leerTexto(entrada, "Tipo de evidencia: "),
                leerEntero(entrada, "Importancia (1 a 10): "),
                leerEntero(entrada, "Confiabilidad (0 a 100): "));
    }

    private static void mostrarMenu() {
        System.out.println("\n=== MENU ===\n1. Nuevo caso\n2. Registrar ubicacion"
                + "\n3. Consultar ubicaciones\n4. Consultar una ubicacion"
                + "\n5. Modificar ubicacion\n6. Descartar ubicacion\n7. Registrar pista"
                + "\n8. Consultar pistas\n9. Buscar pista\n10. Modificar pista"
                + "\n11. Eliminar pista\n12. Mostrar reporte de investigacion\n13. Salir");
    }
}
