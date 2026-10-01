import java.util.ArrayList;

/** Administra los datos y las operaciones de un solo caso. */
public class Caso {
    private final String nombre;
    private final String codigo;
    private final String detective;
    private final Ubicacion[] ubicaciones;
    private final ArrayList<Pista> pistas;

    public Caso(String nombre, String codigo, String detective) {
        this.nombre = Validacion.texto(nombre, "Nombre del caso");
        this.codigo = Validacion.texto(codigo, "Codigo del caso");
        this.detective = Validacion.texto(detective, "Detective");
        ubicaciones = new Ubicacion[5];
        pistas = new ArrayList<Pista>();
    }

    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public String getDetective() { return detective; }

    private void validarPosicion(int posicion) {
        if (posicion < 0 || posicion >= ubicaciones.length) {
            throw new IllegalArgumentException("La posicion debe estar entre 0 y 4.");
        }
    }

    public void registrarUbicacion(int posicion, Ubicacion ubicacion) {
        validarPosicion(posicion);
        if (ubicaciones[posicion] != null) {
            throw new IllegalArgumentException("La posicion ya esta ocupada.");
        }
        if (ubicacion == null) {
            throw new IllegalArgumentException("La ubicacion no puede ser null.");
        }
        ubicaciones[posicion] = ubicacion;
    }

    public Ubicacion consultarUbicacion(int posicion) {
        validarPosicion(posicion);
        if (ubicaciones[posicion] == null) {
            throw new IllegalArgumentException("La posicion esta vacia.");
        }
        return ubicaciones[posicion];
    }

    public String consultarUbicaciones() {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < ubicaciones.length; i++) {
            if (ubicaciones[i] != null) {
                resultado.append("Posicion ").append(i).append(": ")
                         .append(ubicaciones[i]).append('\n');
            }
        }
        return resultado.length() == 0 ? "No hay ubicaciones registradas." : resultado.toString();
    }

    public void modificarUbicacion(int posicion, int riesgo, String estado) {
        consultarUbicacion(posicion).modificar(riesgo, estado);
    }

    public void descartarUbicacion(int posicion) {
        consultarUbicacion(posicion);
        ubicaciones[posicion] = null;
    }

    private int buscarIndicePista(String codigo) {
        String codigoValido = Validacion.texto(codigo, "Codigo de pista");
        for (int i = 0; i < pistas.size(); i++) {
            if (pistas.get(i).getCodigo().equalsIgnoreCase(codigoValido)) {
                return i;
            }
        }
        return -1;
    }

    public void registrarPista(Pista pista) {
        if (pista == null) {
            throw new IllegalArgumentException("La pista no puede ser null.");
        }
        if (buscarIndicePista(pista.getCodigo()) != -1) {
            throw new IllegalArgumentException("Ya existe una pista con ese codigo.");
        }
        pistas.add(pista);
    }

    public Pista buscarPista(String codigo) {
        int indice = buscarIndicePista(codigo);
        if (indice == -1) {
            throw new IllegalArgumentException("No existe una pista con ese codigo.");
        }
        return pistas.get(indice);
    }

    public String consultarPistas() {
        if (pistas.isEmpty()) { return "No hay pistas registradas."; }
        StringBuilder resultado = new StringBuilder();
        for (Pista pista : pistas) {
            resultado.append(pista).append('\n');
        }
        return resultado.toString();
    }

    public void modificarPista(String codigoActual, String nuevoCodigo,
                               String descripcion, String tipo, int importancia,
                               int confiabilidad) {
        int indice = buscarIndicePista(codigoActual);
        if (indice == -1) {
            throw new IllegalArgumentException("No existe una pista con ese codigo.");
        }
        // Construir primero valida todos los datos sin alterar la pista anterior.
        Pista nueva = new Pista(nuevoCodigo, descripcion, tipo, importancia, confiabilidad);
        int repetido = buscarIndicePista(nueva.getCodigo());
        if (repetido != -1 && repetido != indice) {
            throw new IllegalArgumentException("Ya existe otra pista con ese codigo.");
        }
        pistas.set(indice, nueva);
    }

    public void eliminarPista(String codigo) {
        int indice = buscarIndicePista(codigo);
        if (indice == -1) {
            throw new IllegalArgumentException("No existe una pista con ese codigo.");
        }
        pistas.remove(indice);
    }

    public int contarUbicaciones() {
        int cantidad = 0;
        for (Ubicacion ubicacion : ubicaciones) {
            if (ubicacion != null) { cantidad++; }
        }
        return cantidad;
    }

    public int contarEspaciosDisponibles() { return ubicaciones.length - contarUbicaciones(); }
    public int contarPistas() { return pistas.size(); }

    public Ubicacion ubicacionMayorRiesgo() {
        Ubicacion mayor = null;
        for (Ubicacion ubicacion : ubicaciones) {
            if (ubicacion != null && (mayor == null
                    || ubicacion.getNivelRiesgo() > mayor.getNivelRiesgo())) {
                mayor = ubicacion;
            }
        }
        return mayor;
    }

    public Pista pistaMayorImportancia() {
        Pista mayor = null;
        for (Pista pista : pistas) {
            if (mayor == null || pista.getNivelImportancia() > mayor.getNivelImportancia()) {
                mayor = pista;
            }
        }
        return mayor;
    }

    public Pista pistaMayorConfiabilidad() {
        Pista mayor = null;
        for (Pista pista : pistas) {
            if (mayor == null || pista.getNivelConfiabilidad() > mayor.getNivelConfiabilidad()) {
                mayor = pista;
            }
        }
        return mayor;
    }

    public double promedioImportancia() {
        if (pistas.isEmpty()) { return 0.0; }
        double suma = 0;
        for (Pista pista : pistas) { suma += pista.getNivelImportancia(); }
        return suma / pistas.size();
    }

    public String generarReporte() {
        Ubicacion mayor = ubicacionMayorRiesgo();
        String reporte = "=== REPORTE DE INVESTIGACION ===\nCaso: " + nombre
                + " | Codigo: " + codigo + " | Detective: " + detective
                + "\nUbicaciones registradas: " + contarUbicaciones()
                + "\nEspacios disponibles: " + contarEspaciosDisponibles()
                + "\nUbicacion con mayor riesgo: " + (mayor == null ? "No hay ubicaciones." : mayor)
                + "\nPistas registradas: " + contarPistas();
        if (pistas.isEmpty()) {
            return reporte + "\nNo hay pistas para calcular maximos ni promedio.";
        }
        return reporte + "\nPista con mayor importancia: " + pistaMayorImportancia()
                + "\nPista con mayor confiabilidad: " + pistaMayorConfiabilidad()
                + String.format("\nPromedio de importancia: %.2f", promedioImportancia());
    }
}
