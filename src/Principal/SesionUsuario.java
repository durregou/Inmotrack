
package Principal;

public class SesionUsuario {
    private static SesionUsuario instancia;
    private int idUsuario;
    private boolean sesionActiva;

    // Constructor privado para implementar el patrón Singleton
    private SesionUsuario() {
        this.sesionActiva = false;
    }

    // Método para obtener la única instancia de la sesión
    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    // Iniciar sesión
    public void iniciarSesion(int idUsuario) {
        this.idUsuario = idUsuario;
        this.sesionActiva = true;
        System.out.println("Sesión iniciada para el usuario con ID: " + idUsuario);
    }

    // Obtener el ID del usuario actual
    public int getIdUsuario() {
        if (sesionActiva) {
            return idUsuario;
        } else {
            throw new IllegalStateException("No hay ninguna sesión activa.");
        }
    }

    // Cerrar sesión
    public void cerrarSesion() {
        this.idUsuario = 0;
        this.sesionActiva = false;
        System.out.println("Sesión cerrada.");
    }

    // Verificar si la sesión está activa
    public boolean isSesionActiva() {
        return sesionActiva;
    }
}

