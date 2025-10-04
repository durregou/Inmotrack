
package Principal;

public class SesionUsuario {
    private static int usuarioID;
    private static String nombre;
    private static String apellido;
    private static String correo;
    private static String rol;
    private static boolean sesionActiva = false;

    // Setters
    public static void setUsuarioID(int id) {
        usuarioID = id;
        sesionActiva = true;
    }

    public static void setNombre(String n) {
        nombre = n;
    }

    public static void setApellido(String a) {
        apellido = a;
    }

    public static void setCorreo(String c) {
        correo = c;
    }

    public static void setRol(String r) {
        rol = r;
    }

    // Getters
    public static int getUsuarioID() {
        return usuarioID;
    }

    public static String getNombre() {
        return nombre;
    }

    public static String getApellido() {
        return apellido;
    }

    public static String getCorreo() {
        return correo;
    }

    public static String getRol() {
        return rol;
    }

    public static boolean isSesionActiva() {
        return sesionActiva;
    }

    // Cerrar sesión
    public static void cerrarSesion() {
        usuarioID = 0;
        nombre = null;
        apellido = null;
        correo = null;
        rol = null;
        sesionActiva = false;
        System.out.println("Sesión cerrada.");
    }
}

