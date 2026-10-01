/** Validaciones compartidas por los objetos del sistema. */
public final class Validacion {
    private Validacion() { }

    public static String texto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacio.");
        }
        return valor.trim();
    }

    public static int rango(int valor, int minimo, int maximo, String campo) {
        if (valor < minimo || valor > maximo) {
            throw new IllegalArgumentException(campo + " debe estar entre "
                    + minimo + " y " + maximo + ".");
        }
        return valor;
    }
}
