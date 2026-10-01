/** Una ubicacion registrada en la investigacion. */
public class Ubicacion {
    private final String codigo;
    private final String nombre;
    private final String direccion;
    private int nivelRiesgo;
    private String estado;

    public Ubicacion(String codigo, String nombre, String direccion,
                     int nivelRiesgo, String estado) {
        this.codigo = Validacion.texto(codigo, "Codigo");
        this.nombre = Validacion.texto(nombre, "Nombre");
        this.direccion = Validacion.texto(direccion, "Direccion o descripcion");
        modificar(nivelRiesgo, estado);
    }

    public void modificar(int nivelRiesgo, String estado) {
        // Validar todo antes de cambiar el objeto evita modificaciones parciales.
        Validacion.rango(nivelRiesgo, 1, 10, "Nivel de riesgo");
        String estadoValido = Validacion.texto(estado, "Estado");
        this.nivelRiesgo = nivelRiesgo;
        this.estado = estadoValido;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public int getNivelRiesgo() { return nivelRiesgo; }
    public String getEstado() { return estado; }

    @Override
    public String toString() {
        return "Codigo: " + codigo + " | Nombre: " + nombre
                + " | Direccion: " + direccion + " | Riesgo: " + nivelRiesgo
                + " | Estado: " + estado;
    }
}
