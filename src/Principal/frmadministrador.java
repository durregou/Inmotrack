package Principal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Panel de Administrador
 * @author David Urrego
 */
public class frmadministrador extends javax.swing.JDialog {

    private JTabbedPane tabbedPane;
    private JTable tablaUsuarios;
    private JTable tablaContratos;
    private JTable tablaPagos;
    private JTable tablaInmuebles;
    private JTable tablaMantenimiento;
    private JLabel lblBienvenida;
    
    // Referencias a las etiquetas de estadísticas del Dashboard
    private JLabel lblTotalUsuarios;
    private JLabel lblInmueblesActivos;
    private JLabel lblContratosActivos;
    private JLabel lblPagosDelMes;
    private JLabel lblMantenimientosPendientes;
    private JLabel lblIngresosTotales;
    
    public frmadministrador(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        cargarDatos();
    }

    private void initComponents() {
        setTitle("Panel de Administrador - " + SesionUsuario.getNombre());
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1100, 750);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(240, 240, 240));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Header
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(220, 53, 69));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        lblBienvenida = new JLabel("Bienvenido, " + SesionUsuario.getNombre());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        lblBienvenida.setForeground(Color.WHITE);
        
        JLabel lblRol = new JLabel("Administrador del Sistema - Control Total");
        lblRol.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRol.setForeground(Color.WHITE);
        
        JPanel panelTextos = new JPanel(new BorderLayout());
        panelTextos.setOpaque(false);
        panelTextos.add(lblBienvenida, BorderLayout.NORTH);
        panelTextos.add(lblRol, BorderLayout.SOUTH);
        
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        
        panelHeader.add(panelTextos, BorderLayout.WEST);
        panelHeader.add(btnCerrarSesion, BorderLayout.EAST);
        
        // Tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));
        
        // Tab 1: Dashboard
        tabbedPane.addTab("DASHBOARD", crearPanelDashboard());
        
        // Tab 2: Usuarios
        tabbedPane.addTab("USUARIOS", crearPanelUsuarios());
        
        // Tab 3: Contratos
        tabbedPane.addTab("CONTRATOS", crearPanelContratos());
        
        // Tab 4: Pagos
        tabbedPane.addTab("PAGOS", crearPanelPagos());
        
        // Tab 5: Inmuebles
        tabbedPane.addTab("INMUEBLES", crearPanelInmuebles());
        
        // Tab 6: Mantenimiento
        tabbedPane.addTab("MANTENIMIENTO", crearPanelMantenimiento());
        
        // Tab 7: Mi Perfil
        tabbedPane.addTab("MI PERFIL", crearPanelPerfil());
        
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(tabbedPane, BorderLayout.CENTER);
        
        getContentPane().add(panelPrincipal);
        pack();
    }
    
    private JPanel crearPanelDashboard() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titulo = new JLabel("Panel de Control General");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Panel de estadísticas
        JPanel panelStats = new JPanel(new GridLayout(2, 3, 15, 15));
        panelStats.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Crear tarjetas y guardar referencias a los labels
        lblTotalUsuarios = new JLabel("0");
        panelStats.add(crearTarjetaEstadistica("Total Usuarios", lblTotalUsuarios, new Color(0, 123, 255), "👥"));
        
        lblInmueblesActivos = new JLabel("0");
        panelStats.add(crearTarjetaEstadistica("Inmuebles Activos", lblInmueblesActivos, new Color(40, 167, 69), "🏠"));
        
        lblContratosActivos = new JLabel("0");
        panelStats.add(crearTarjetaEstadistica("Contratos Activos", lblContratosActivos, new Color(255, 193, 7), "📄"));
        
        lblPagosDelMes = new JLabel("0");
        panelStats.add(crearTarjetaEstadistica("Pagos del Mes", lblPagosDelMes, new Color(23, 162, 184), "💰"));
        
        lblMantenimientosPendientes = new JLabel("0");
        panelStats.add(crearTarjetaEstadistica("Mantenimientos Pendientes", lblMantenimientosPendientes, new Color(220, 53, 69), "🔧"));
        
        lblIngresosTotales = new JLabel("$0");
        panelStats.add(crearTarjetaEstadistica("Ingresos Totales", lblIngresosTotales, new Color(111, 66, 193), "📈"));
        
        // Panel de acciones rápidas
        JPanel panelAcciones = new JPanel(new GridLayout(0, 2, 10, 10));
        panelAcciones.setBorder(BorderFactory.createTitledBorder("Acciones Rápidas"));
        
        JButton btnActualizar = new JButton("Actualizar Estadísticas");
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizar.addActionListener(e -> actualizarEstadisticas());
        
        JButton btnReporte = new JButton("Generar Reporte General");
        btnReporte.setFont(new Font("Arial", Font.BOLD, 12));
        btnReporte.addActionListener(e -> generarReporteGeneral());
        
        JButton btnNotificar = new JButton("Enviar Notificación Masiva");
        btnNotificar.setFont(new Font("Arial", Font.BOLD, 12));
        btnNotificar.addActionListener(e -> enviarNotificacionMasiva());
        
        JButton btnBackup = new JButton("Backup del Sistema");
        btnBackup.setFont(new Font("Arial", Font.BOLD, 12));
        btnBackup.addActionListener(e -> realizarBackup());
        
        panelAcciones.add(btnActualizar);
        panelAcciones.add(btnReporte);
        panelAcciones.add(btnNotificar);
        panelAcciones.add(btnBackup);
        
        JPanel panelCentro = new JPanel(new BorderLayout(0, 20));
        panelCentro.add(panelStats, BorderLayout.NORTH);
        panelCentro.add(panelAcciones, BorderLayout.CENTER);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelCentro, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearTarjetaEstadistica(String titulo, JLabel lblValor, Color color, String emoji) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(color);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblEmoji = new JLabel(emoji);
        lblEmoji.setFont(new Font("Arial", Font.PLAIN, 32));
        lblEmoji.setForeground(Color.WHITE);
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Configurar el label de valor que fue pasado como parámetro
        lblValor.setForeground(Color.WHITE);
        lblValor.setFont(new Font("Arial", Font.BOLD, 28));
        
        JPanel panelTexto = new JPanel(new BorderLayout());
        panelTexto.setOpaque(false);
        panelTexto.add(lblTitulo, BorderLayout.NORTH);
        panelTexto.add(lblValor, BorderLayout.CENTER);
        
        tarjeta.add(lblEmoji, BorderLayout.WEST);
        tarjeta.add(panelTexto, BorderLayout.CENTER);
        
        return tarjeta;
    }
    
    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titulo = new JLabel("Gestión de Usuarios del Sistema");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Tabla de usuarios
        String[] columnas = {"ID", "Nombre", "Apellido", "Correo", "Rol", "Activo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaUsuarios = new JTable(modelo);
        tablaUsuarios.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaUsuarios.setRowHeight(30);
        tablaUsuarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaUsuarios.getTableHeader().setBackground(new Color(220, 53, 69));
        tablaUsuarios.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        JButton btnVerDetalle = new JButton("Ver Detalle");
        btnVerDetalle.setBackground(new Color(0, 123, 255));
        btnVerDetalle.setForeground(Color.WHITE);
        btnVerDetalle.setFont(new Font("Arial", Font.BOLD, 11));
        btnVerDetalle.addActionListener(e -> verDetalleUsuario());
        
        JButton btnEditar = new JButton("Editar Usuario");
        btnEditar.setBackground(new Color(255, 193, 7));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 11));
        btnEditar.addActionListener(e -> editarUsuario());
        
        JButton btnActivar = new JButton("Activar");
        btnActivar.setBackground(new Color(40, 167, 69));
        btnActivar.setForeground(Color.WHITE);
        btnActivar.setFont(new Font("Arial", Font.BOLD, 11));
        btnActivar.addActionListener(e -> activarUsuario());
        
        JButton btnDesactivar = new JButton("Desactivar");
        btnDesactivar.setBackground(new Color(220, 53, 69));
        btnDesactivar.setForeground(Color.WHITE);
        btnDesactivar.setFont(new Font("Arial", Font.BOLD, 11));
        btnDesactivar.addActionListener(e -> desactivarUsuario());
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(108, 117, 125));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 11));
        btnEliminar.addActionListener(e -> eliminarUsuario());
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarUsuarios());
        
        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnEditar);
        panelBotones.add(btnActivar);
        panelBotones.add(btnDesactivar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollUsuarios, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelContratos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titulo = new JLabel("Todos los Contratos del Sistema");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        String[] columnas = {"ID", "Inmueble", "Propietario", "Arrendatario", "Inicio", "Fin", "Valor", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaContratos = new JTable(modelo);
        tablaContratos.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaContratos.setRowHeight(30);
        tablaContratos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        
        JScrollPane scrollContratos = new JScrollPane(tablaContratos);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnVerDetalle = new JButton("Ver Detalle");
        btnVerDetalle.setBackground(new Color(0, 123, 255));
        btnVerDetalle.setForeground(Color.WHITE);
        btnVerDetalle.setFont(new Font("Arial", Font.BOLD, 11));
        btnVerDetalle.addActionListener(e -> verDetalleContrato());
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(255, 193, 7));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 11));
        btnEditar.addActionListener(e -> editarContrato());
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 11));
        btnEliminar.addActionListener(e -> eliminarContrato());
        
        JButton btnFinalizar = new JButton("Finalizar Contrato");
        btnFinalizar.setBackground(new Color(108, 117, 125));
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 11));
        btnFinalizar.addActionListener(e -> finalizarContratoAdmin());
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarContratos());
        
        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnFinalizar);
        panelBotones.add(btnActualizar);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollContratos, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelPagos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titulo = new JLabel("Todos los Pagos del Sistema");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        String[] columnas = {"ID", "Contrato", "Arrendatario", "Valor", "Fecha", "Método", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPagos = new JTable(modelo);
        tablaPagos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaPagos.setRowHeight(30);
        tablaPagos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPagos = new JScrollPane(tablaPagos);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        JButton btnVerDetalle = new JButton("Ver Detalle");
        btnVerDetalle.setBackground(new Color(0, 123, 255));
        btnVerDetalle.setForeground(Color.WHITE);
        btnVerDetalle.setFont(new Font("Arial", Font.BOLD, 11));
        btnVerDetalle.addActionListener(e -> verDetallePago());
        
        JButton btnCambiarEstado = new JButton("Cambiar Estado");
        btnCambiarEstado.setBackground(new Color(255, 193, 7));
        btnCambiarEstado.setForeground(Color.BLACK);
        btnCambiarEstado.setFont(new Font("Arial", Font.BOLD, 11));
        btnCambiarEstado.addActionListener(e -> cambiarEstadoPago());
        
        JButton btnMarcarPagado = new JButton("Marcar Pagado");
        btnMarcarPagado.setBackground(new Color(40, 167, 69));
        btnMarcarPagado.setForeground(Color.WHITE);
        btnMarcarPagado.setFont(new Font("Arial", Font.BOLD, 11));
        btnMarcarPagado.addActionListener(e -> marcarComoPagado());
        
        JButton btnMarcarVencido = new JButton("Marcar Vencido");
        btnMarcarVencido.setBackground(new Color(220, 53, 69));
        btnMarcarVencido.setForeground(Color.WHITE);
        btnMarcarVencido.setFont(new Font("Arial", Font.BOLD, 11));
        btnMarcarVencido.addActionListener(e -> marcarComoVencido());
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarTodosPagos());
        
        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnCambiarEstado);
        panelBotones.add(btnMarcarPagado);
        panelBotones.add(btnMarcarVencido);
        panelBotones.add(btnActualizar);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPagos, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelInmuebles() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titulo = new JLabel("Todos los Inmuebles del Sistema");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        String[] columnas = {"ID", "Tipo", "Dirección", "Ciudad", "Precio", "Propietario", "Disponible"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaInmuebles = new JTable(modelo);
        tablaInmuebles.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaInmuebles.setRowHeight(30);
        tablaInmuebles.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        
        JScrollPane scrollInmuebles = new JScrollPane(tablaInmuebles);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnVerDetalle = new JButton("Ver Detalle");
        btnVerDetalle.setBackground(new Color(0, 123, 255));
        btnVerDetalle.setForeground(Color.WHITE);
        btnVerDetalle.setFont(new Font("Arial", Font.BOLD, 11));
        btnVerDetalle.addActionListener(e -> verDetalleInmueble());
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(255, 193, 7));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 11));
        btnEditar.addActionListener(e -> editarInmueble());
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 11));
        btnEliminar.addActionListener(e -> eliminarInmueble());
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarInmuebles());
        
        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollInmuebles, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelMantenimiento() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titulo = new JLabel("Solicitudes de Mantenimiento");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        String[] columnas = {"ID", "Inmueble", "Solicitante", "Título", "Tipo", "Prioridad", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaMantenimiento = new JTable(modelo);
        tablaMantenimiento.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaMantenimiento.setRowHeight(30);
        tablaMantenimiento.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        
        JScrollPane scrollMantenimiento = new JScrollPane(tablaMantenimiento);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        JButton btnVerDetalle = new JButton("Ver Detalle");
        btnVerDetalle.setBackground(new Color(0, 123, 255));
        btnVerDetalle.setForeground(Color.WHITE);
        btnVerDetalle.setFont(new Font("Arial", Font.BOLD, 11));
        btnVerDetalle.addActionListener(e -> verDetalleMantenimiento());
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(255, 193, 7));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 11));
        btnEditar.addActionListener(e -> editarMantenimiento());
        
        JButton btnAprobar = new JButton("Aprobar");
        btnAprobar.setBackground(new Color(40, 167, 69));
        btnAprobar.setForeground(Color.WHITE);
        btnAprobar.setFont(new Font("Arial", Font.BOLD, 11));
        btnAprobar.addActionListener(e -> aprobarMantenimiento());
        
        JButton btnIniciar = new JButton("Iniciar");
        btnIniciar.setBackground(new Color(23, 162, 184));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 11));
        btnIniciar.addActionListener(e -> iniciarMantenimiento());
        
        JButton btnCompletar = new JButton("Completar");
        btnCompletar.setBackground(new Color(108, 117, 125));
        btnCompletar.setForeground(Color.WHITE);
        btnCompletar.setFont(new Font("Arial", Font.BOLD, 11));
        btnCompletar.addActionListener(e -> completarMantenimiento());
        
        JButton btnRechazar = new JButton("Rechazar");
        btnRechazar.setBackground(new Color(220, 53, 69));
        btnRechazar.setForeground(Color.WHITE);
        btnRechazar.setFont(new Font("Arial", Font.BOLD, 11));
        btnRechazar.addActionListener(e -> rechazarMantenimiento());
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarMantenimientos());
        
        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnEditar);
        panelBotones.add(btnAprobar);
        panelBotones.add(btnIniciar);
        panelBotones.add(btnCompletar);
        panelBotones.add(btnRechazar);
        panelBotones.add(btnActualizar);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollMantenimiento, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void cargarDatos() {
        cargarUsuarios();
        cargarContratos();
        cargarTodosPagos();
        cargarInmuebles();
        cargarMantenimientos();
        cargarEstadisticas();
    }
    
    private void cargarEstadisticas() {
        try {
            // 1. Total Usuarios
            JSONObject responseUsuarios = ApiClient.get(ApiClient.USUARIOS_PORT, "/api/usuarios");
            int totalUsuarios = 0;
            if (responseUsuarios.getInt("statusCode") == 200) {
                Object dataObj = responseUsuarios.get("data");
                if (dataObj instanceof JSONArray) {
                    totalUsuarios = ((JSONArray) dataObj).length();
                } else {
                    totalUsuarios = 1;
                }
            }
            lblTotalUsuarios.setText(String.valueOf(totalUsuarios));
            
            // 2. Inmuebles Activos
            JSONObject responseInmuebles = ApiClient.get(ApiClient.INMUEBLES_PORT, "/api/inmuebles?todos=true");
            int inmueblesActivos = 0;
            if (responseInmuebles.getInt("statusCode") == 200) {
                Object dataObj = responseInmuebles.get("data");
                if (dataObj instanceof JSONArray) {
                    JSONArray inmuebles = (JSONArray) dataObj;
                    for (int i = 0; i < inmuebles.length(); i++) {
                        JSONObject inmueble = inmuebles.getJSONObject(i);
                        if (inmueble.optBoolean("disponible", false)) {
                            inmueblesActivos++;
                        }
                    }
                }
            }
            lblInmueblesActivos.setText(String.valueOf(inmueblesActivos));
            
            // 3. Contratos Activos
            JSONObject responseContratos = ApiClient.get(ApiClient.CONTRATOS_PORT, "/api/contratos");
            int contratosActivos = 0;
            if (responseContratos.getInt("statusCode") == 200) {
                Object dataObj = responseContratos.get("data");
                if (dataObj instanceof JSONArray) {
                    JSONArray contratos = (JSONArray) dataObj;
                    for (int i = 0; i < contratos.length(); i++) {
                        JSONObject contrato = contratos.getJSONObject(i);
                        String estado = contrato.optString("estado", "");
                        if (estado.equals("ACTIVO")) {
                            contratosActivos++;
                        }
                    }
                } else if (dataObj instanceof JSONObject) {
                    JSONObject contrato = (JSONObject) dataObj;
                    if (contrato.optString("estado", "").equals("ACTIVO")) {
                        contratosActivos = 1;
                    }
                }
            }
            lblContratosActivos.setText(String.valueOf(contratosActivos));
            
            // 4. Pagos del Mes
            JSONObject responsePagos = ApiClient.get(ApiClient.PAGOS_PORT, "/api/pagos?contrato=1");
            int pagosDelMes = 0;
            if (responsePagos.getInt("statusCode") == 200) {
                Object dataObj = responsePagos.get("data");
                if (dataObj instanceof JSONArray) {
                    pagosDelMes = ((JSONArray) dataObj).length();
                } else if (dataObj instanceof JSONObject) {
                    pagosDelMes = 1;
                }
            }
            lblPagosDelMes.setText(String.valueOf(pagosDelMes));
            
            // 5. Mantenimientos Pendientes
            JSONObject responseMantenimiento = ApiClient.get(ApiClient.MANTENIMIENTO_PORT, "/api/mantenimiento");
            int mantenimientosPendientes = 0;
            if (responseMantenimiento.getInt("statusCode") == 200) {
                Object dataObj = responseMantenimiento.get("data");
                if (dataObj instanceof JSONArray) {
                    JSONArray mantenimientos = (JSONArray) dataObj;
                    for (int i = 0; i < mantenimientos.length(); i++) {
                        JSONObject mant = mantenimientos.getJSONObject(i);
                        String estado = mant.optString("estado", "");
                        if (estado.equals("PENDIENTE")) {
                            mantenimientosPendientes++;
                        }
                    }
                }
            }
            lblMantenimientosPendientes.setText(String.valueOf(mantenimientosPendientes));
            
            // 6. Ingresos Totales (sumar todos los pagos)
            double ingresosTotales = 0.0;
            if (responsePagos.getInt("statusCode") == 200) {
                Object dataObj = responsePagos.get("data");
                if (dataObj instanceof JSONArray) {
                    JSONArray pagos = (JSONArray) dataObj;
                    for (int i = 0; i < pagos.length(); i++) {
                        JSONObject pago = pagos.getJSONObject(i);
                        ingresosTotales += pago.optDouble("valor", 0.0);
                    }
                } else if (dataObj instanceof JSONObject) {
                    ingresosTotales = ((JSONObject) dataObj).optDouble("valor", 0.0);
                }
            }
            lblIngresosTotales.setText(String.format("$%.2f", ingresosTotales));
            
        } catch (Exception e) {
            System.err.println("Error al cargar estadísticas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void cargarUsuarios() {
        try {
            JSONObject response = ApiClient.get(ApiClient.USUARIOS_PORT, "/api/usuarios");
            
            DefaultTableModel modelo = (DefaultTableModel) tablaUsuarios.getModel();
            modelo.setRowCount(0);
            
            if (response.getInt("statusCode") == 200) {
                Object dataObj = response.get("data");
                JSONArray usuarios;
                
                if (dataObj instanceof JSONArray) {
                    usuarios = (JSONArray) dataObj;
                } else {
                    usuarios = new JSONArray();
                    usuarios.put(dataObj);
                }
                
                for (int i = 0; i < usuarios.length(); i++) {
                    JSONObject usuario = usuarios.getJSONObject(i);
                    Object[] fila = {
                        usuario.optInt("id", 0),
                        usuario.optString("nombre", "N/A"),
                        usuario.optString("apellido", "N/A"),
                        usuario.optString("correo", "N/A"),
                        usuario.optString("tipoUsuario", "N/A"),
                        usuario.optBoolean("activo", true) ? "✓" : "✗"
                    };
                    modelo.addRow(fila);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar usuarios: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarContratos() {
        try {
            JSONObject response = ApiClient.get(ApiClient.CONTRATOS_PORT, "/api/contratos");
            
            DefaultTableModel modelo = (DefaultTableModel) tablaContratos.getModel();
            modelo.setRowCount(0);
            
            if (response.getInt("statusCode") == 200) {
                Object dataObj = response.get("data");
                JSONArray contratos;
                
                if (dataObj instanceof JSONArray) {
                    contratos = (JSONArray) dataObj;
                } else {
                    contratos = new JSONArray();
                    contratos.put(dataObj);
                }
                
                for (int i = 0; i < contratos.length(); i++) {
                    JSONObject contrato = contratos.getJSONObject(i);
                    Object[] fila = {
                        contrato.optInt("id", 0),
                        "#" + contrato.optInt("inmuebleId", 0),
                        "Prop #" + contrato.optInt("propietarioId", 0),
                        "Arr #" + contrato.optInt("arrendatarioId", 0),
                        contrato.optString("fechaInicio", "N/A"),
                        contrato.optString("fechaFin", "N/A"),
                        "$" + contrato.optDouble("valorArriendo", 0.0),
                        contrato.optString("estado", "N/A")
                    };
                    modelo.addRow(fila);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar contratos: " + e.getMessage());
        }
    }
    
    private void cargarTodosPagos() {
        try {
            // Cargar todos los pagos del contrato 1
            JSONObject response = ApiClient.get(ApiClient.PAGOS_PORT, "/api/pagos?contrato=1");
            
            DefaultTableModel modelo = (DefaultTableModel) tablaPagos.getModel();
            modelo.setRowCount(0);
            
            if (response.getInt("statusCode") == 200) {
                Object dataObj = response.get("data");
                JSONArray pagos;
                
                if (dataObj instanceof JSONArray) {
                    pagos = (JSONArray) dataObj;
                } else {
                    pagos = new JSONArray();
                    pagos.put(dataObj);
                }
                
                for (int i = 0; i < pagos.length(); i++) {
                    JSONObject pago = pagos.getJSONObject(i);
                    Object[] fila = {
                        pago.optInt("id", 0),
                        "#" + pago.optInt("contratoId", 0),
                        "Arr #" + pago.optInt("arrendatarioId", 0),
                        "$" + pago.optDouble("valor", 0.0),
                        pago.optString("fecha", "N/A"),
                        pago.optString("metodoPago", "N/A"),
                        pago.optString("estado", "N/A")
                    };
                    modelo.addRow(fila);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar pagos: " + e.getMessage());
        }
    }
    
    private void cargarInmuebles() {
        try {
            JSONObject response = ApiClient.get(ApiClient.INMUEBLES_PORT, "/api/inmuebles?todos=true");
            
            DefaultTableModel modelo = (DefaultTableModel) tablaInmuebles.getModel();
            modelo.setRowCount(0);
            
            if (response.getInt("statusCode") == 200) {
                Object dataObj = response.get("data");
                JSONArray inmuebles;
                
                if (dataObj instanceof JSONArray) {
                    inmuebles = (JSONArray) dataObj;
                } else {
                    inmuebles = new JSONArray();
                    inmuebles.put(dataObj);
                }
                
                for (int i = 0; i < inmuebles.length(); i++) {
                    JSONObject inmueble = inmuebles.getJSONObject(i);
                    Object[] fila = {
                        inmueble.optInt("id", 0),
                        inmueble.optString("tipo", "N/A"),
                        inmueble.optString("direccion", "N/A"),
                        inmueble.optString("ciudad", "N/A"),
                        "$" + inmueble.optDouble("precioArriendo", 0.0),
                        "Prop #" + inmueble.optInt("propietarioId", 0),
                        inmueble.optBoolean("disponible", false) ? "✓" : "✗"
                    };
                    modelo.addRow(fila);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar inmuebles: " + e.getMessage());
        }
    }
    
    private void cargarMantenimientos() {
        try {
            JSONObject response = ApiClient.get(ApiClient.MANTENIMIENTO_PORT, "/api/mantenimiento");
            
            DefaultTableModel modelo = (DefaultTableModel) tablaMantenimiento.getModel();
            modelo.setRowCount(0);
            
            if (response.getInt("statusCode") == 200) {
                Object dataObj = response.get("data");
                JSONArray mantenimientos;
                
                if (dataObj instanceof JSONArray) {
                    mantenimientos = (JSONArray) dataObj;
                } else {
                    mantenimientos = new JSONArray();
                    mantenimientos.put(dataObj);
                }
                
                for (int i = 0; i < mantenimientos.length(); i++) {
                    JSONObject mant = mantenimientos.getJSONObject(i);
                    Object[] fila = {
                        mant.optInt("id", 0),
                        "#" + mant.optInt("inmuebleId", 0),
                        "User #" + mant.optInt("solicitanteId", 0),
                        mant.optString("titulo", "N/A"),
                        mant.optString("tipo", "N/A"),
                        mant.optString("prioridad", "N/A"),
                        mant.optString("estado", "N/A")
                    };
                    modelo.addRow(fila);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar mantenimientos: " + e.getMessage());
        }
    }
    
    private void actualizarEstadisticas() {
        cargarEstadisticas();
        JOptionPane.showMessageDialog(this, 
            "Estadísticas actualizadas correctamente.", 
            "Actualizado", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void generarReporteGeneral() {
        int usuarios = tablaUsuarios.getRowCount();
        int contratos = tablaContratos.getRowCount();
        int pagos = tablaPagos.getRowCount();
        int inmuebles = tablaInmuebles.getRowCount();
        int mantenimientos = tablaMantenimiento.getRowCount();
        
        String reporte = String.format(
            "📊 REPORTE GENERAL DEL SISTEMA\n\n" +
            "👥 Total Usuarios: %d\n" +
            "🏠 Total Inmuebles: %d\n" +
            "📄 Contratos Activos: %d\n" +
            "💰 Total Pagos: %d\n" +
            "🔧 Mantenimientos: %d\n\n" +
            "Fecha: %s",
            usuarios, inmuebles, contratos, pagos, mantenimientos,
            java.time.LocalDate.now()
        );
        
        JOptionPane.showMessageDialog(this, reporte, 
            "Reporte General", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void enviarNotificacionMasiva() {
        try {
            // Paso 1: Seleccionar destinatarios
            String[] opcionesDestinatarios = {
                "Todos los usuarios",
                "Solo propietarios",
                "Solo arrendatarios"
            };
            
            String destinatariosSeleccion = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona a quién enviar la notificación:",
                "Destinatarios",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesDestinatarios,
                opcionesDestinatarios[0]
            );
            
            if (destinatariosSeleccion == null) {
                return; // Usuario canceló
            }
            
            // Paso 2: Obtener usuarios según la selección
            JSONObject responseUsuarios = ApiClient.get(ApiClient.USUARIOS_PORT, "/api/usuarios");
            
            if (responseUsuarios.getInt("statusCode") != 200) {
                JOptionPane.showMessageDialog(this,
                    "Error al obtener lista de usuarios.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Object dataObj = responseUsuarios.get("data");
            JSONArray todosUsuarios = (dataObj instanceof JSONArray) ? (JSONArray) dataObj : new JSONArray().put(dataObj);
            
            // Filtrar usuarios según selección
            java.util.List<String> destinatarios = new java.util.ArrayList<>();
            for (int i = 0; i < todosUsuarios.length(); i++) {
                JSONObject usuario = todosUsuarios.getJSONObject(i);
                String rol = usuario.optString("tipoUsuario", "");
                String correo = usuario.optString("correo", "");
                
                if (correo.isEmpty()) continue;
                
                boolean incluir = false;
                switch (destinatariosSeleccion) {
                    case "Todos los usuarios":
                        incluir = true;
                        break;
                    case "Solo propietarios":
                        incluir = rol.equalsIgnoreCase("PROPIETARIO");
                        break;
                    case "Solo arrendatarios":
                        incluir = rol.equalsIgnoreCase("ARRENDATARIO");
                        break;
                }
                
                if (incluir) {
                    destinatarios.add(correo);
                }
            }
            
            if (destinatarios.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No se encontraron destinatarios para enviar la notificación.",
                    "Sin Destinatarios",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Paso 3: Solicitar tipo de notificación
            String[] tiposNotificacion = {"EMAIL", "SMS", "PUSH"};
            String tipo = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona el tipo de notificación:",
                "Tipo de Notificación",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tiposNotificacion,
                tiposNotificacion[0]
            );
            
            if (tipo == null) {
                return;
            }
            
            // Paso 4: Formulario para asunto y mensaje
            JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JTextField txtAsunto = new JTextField();
            JTextArea txtMensaje = new JTextArea(5, 20);
            txtMensaje.setLineWrap(true);
            txtMensaje.setWrapStyleWord(true);
            JScrollPane scrollMensaje = new JScrollPane(txtMensaje);
            
            JLabel lblDestinatarios = new JLabel(destinatarios.size() + " destinatarios");
            lblDestinatarios.setFont(new Font("Arial", Font.BOLD, 12));
            lblDestinatarios.setForeground(new Color(40, 167, 69));
            
            panel.add(new JLabel("Asunto:"));
            panel.add(txtAsunto);
            panel.add(new JLabel("Mensaje:"));
            panel.add(scrollMensaje);
            panel.add(new JLabel("Destinatarios:"));
            panel.add(lblDestinatarios);
            
            int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Redactar Notificación Masiva",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            
            if (result != JOptionPane.OK_OPTION) {
                return;
            }
            
            String asunto = txtAsunto.getText().trim();
            String mensaje = txtMensaje.getText().trim();
            
            if (asunto.isEmpty() || mensaje.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "El asunto y el mensaje son obligatorios.",
                    "Campos Incompletos",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Paso 5: Enviar notificaciones
            int exitosas = 0;
            int fallidas = 0;
            
            for (String destinatario : destinatarios) {
                try {
                    JSONObject notificacionData = new JSONObject();
                    notificacionData.put("destinatario", destinatario);
                    notificacionData.put("tipo", tipo);
                    notificacionData.put("asunto", asunto);
                    notificacionData.put("mensaje", mensaje);
                    notificacionData.put("estado", "PENDIENTE");
                    
                    JSONObject response = ApiClient.post(ApiClient.NOTIFICACIONES_PORT,
                        "/api/notificaciones", notificacionData);
                    
                    if (response.getInt("statusCode") == 201 || response.getInt("statusCode") == 200) {
                        exitosas++;
                    } else {
                        fallidas++;
                    }
                } catch (Exception e) {
                    fallidas++;
                    System.err.println("Error enviando a " + destinatario + ": " + e.getMessage());
                }
            }
            
            // Mostrar resultado
            String resultadoMensaje = String.format(
                "Notificaciones enviadas:\n\n" +
                "Exitosas: %d\n" +
                "Fallidas: %d\n" +
                "Total: %d\n\n" +
                "Tipo: %s\n" +
                "Asunto: %s",
                exitosas, fallidas, destinatarios.size(), tipo, asunto
            );
            
            JOptionPane.showMessageDialog(this,
                resultadoMensaje,
                "Resultado del Envío",
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al enviar notificaciones masivas: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void realizarBackup() {
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad de backup en desarrollo.", 
            "Próximamente", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void activarUsuario() {
        int selectedRow = tablaUsuarios.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = (int) tablaUsuarios.getValueAt(selectedRow, 0);
            try {
                JSONObject response = ApiClient.put(ApiClient.USUARIOS_PORT, 
                    "/api/usuarios/" + userId + "/activar", null);
                
                if (response.getInt("statusCode") == 200) {
                    JOptionPane.showMessageDialog(this, 
                        "Usuario activado correctamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    cargarUsuarios();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Selecciona un usuario de la tabla", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void desactivarUsuario() {
        int selectedRow = tablaUsuarios.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = (int) tablaUsuarios.getValueAt(selectedRow, 0);
            try {
                JSONObject response = ApiClient.put(ApiClient.USUARIOS_PORT, 
                    "/api/usuarios/" + userId + "/desactivar", null);
                
                if (response.getInt("statusCode") == 200) {
                    JOptionPane.showMessageDialog(this, 
                        "Usuario desactivado correctamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    cargarUsuarios();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Selecciona un usuario de la tabla", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private JLabel crearLabelBold(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        return lbl;
    }
    
    private void verDetalleContrato() {
        int selectedRow = tablaContratos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un contrato de la tabla.",
                "Ningún Contrato Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int contratoId = (int) tablaContratos.getValueAt(selectedRow, 0);
            JSONObject response = ApiClient.get(ApiClient.CONTRATOS_PORT, "/api/contratos/" + contratoId);
            
            if (response.getInt("statusCode") != 200) {
                JOptionPane.showMessageDialog(this,
                    "Error al obtener detalles del contrato.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JSONObject contrato = response.getJSONObject("data");
            
            // Crear diálogo de detalle
            JDialog dialogo = new JDialog(this, "Detalle del Contrato #" + contratoId, true);
            dialogo.setSize(600, 500);
            dialogo.setLocationRelativeTo(this);
            
            JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panelPrincipal.setBackground(Color.WHITE);
            
            JLabel lblTitulo = new JLabel("Información del Contrato #" + contratoId);
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
            
            JPanel panelDatos = new JPanel(new GridLayout(11, 2, 10, 10));
            panelDatos.setBackground(Color.WHITE);
            panelDatos.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
            
            panelDatos.add(crearLabelBold("ID Contrato:"));
            panelDatos.add(new JLabel(String.valueOf(contrato.optInt("id", 0))));
            
            panelDatos.add(crearLabelBold("Inmueble ID:"));
            panelDatos.add(new JLabel(String.valueOf(contrato.optInt("inmuebleId", 0))));
            
            panelDatos.add(crearLabelBold("Propietario ID:"));
            panelDatos.add(new JLabel(String.valueOf(contrato.optInt("propietarioId", 0))));
            
            panelDatos.add(crearLabelBold("Arrendatario ID:"));
            panelDatos.add(new JLabel(String.valueOf(contrato.optInt("arrendatarioId", 0))));
            
            panelDatos.add(crearLabelBold("Fecha Inicio:"));
            panelDatos.add(new JLabel(contrato.optString("fechaInicio", "N/A")));
            
            panelDatos.add(crearLabelBold("Fecha Fin:"));
            panelDatos.add(new JLabel(contrato.optString("fechaFin", "N/A")));
            
            panelDatos.add(crearLabelBold("Valor Arriendo:"));
            JLabel lblValor = new JLabel("$" + String.format("%.2f", contrato.optDouble("valorArriendo", 0.0)));
            lblValor.setForeground(new Color(40, 167, 69));
            lblValor.setFont(new Font("Arial", Font.BOLD, 14));
            panelDatos.add(lblValor);
            
            panelDatos.add(crearLabelBold("Depósito:"));
            panelDatos.add(new JLabel("$" + String.format("%.2f", contrato.optDouble("deposito", 0.0))));
            
            panelDatos.add(crearLabelBold("Día de Pago:"));
            panelDatos.add(new JLabel(String.valueOf(contrato.optInt("diaPago", 0))));
            
            panelDatos.add(crearLabelBold("Estado:"));
            JLabel lblEstado = new JLabel(contrato.optString("estado", "N/A"));
            lblEstado.setFont(new Font("Arial", Font.BOLD, 12));
            String estado = contrato.optString("estado", "");
            if (estado.equals("ACTIVO")) {
                lblEstado.setForeground(new Color(40, 167, 69));
            } else {
                lblEstado.setForeground(new Color(220, 53, 69));
            }
            panelDatos.add(lblEstado);
            
            panelDatos.add(crearLabelBold("Fecha Creación:"));
            String fechaCreacion = contrato.optString("fechaCreacion", "N/A");
            if (fechaCreacion.length() > 10) {
                fechaCreacion = fechaCreacion.substring(0, 10);
            }
            panelDatos.add(new JLabel(fechaCreacion));
            
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.addActionListener(e -> dialogo.dispose());
            
            JPanel panelBoton = new JPanel();
            panelBoton.setBackground(Color.WHITE);
            panelBoton.add(btnCerrar);
            
            panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
            panelPrincipal.add(panelDatos, BorderLayout.CENTER);
            panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
            
            dialogo.add(panelPrincipal);
            dialogo.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al mostrar detalle: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void editarContrato() {
        int selectedRow = tablaContratos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un contrato de la tabla.",
                "Ningún Contrato Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int contratoId = (int) tablaContratos.getValueAt(selectedRow, 0);
            JSONObject response = ApiClient.get(ApiClient.CONTRATOS_PORT, "/api/contratos/" + contratoId);
            
            if (response.getInt("statusCode") != 200) {
                JOptionPane.showMessageDialog(this,
                    "Error al obtener datos del contrato.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JSONObject contrato = response.getJSONObject("data");
            
            // Formulario de edición
            JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JTextField txtFechaInicio = new JTextField(contrato.optString("fechaInicio", ""));
            JTextField txtFechaFin = new JTextField(contrato.optString("fechaFin", ""));
            JTextField txtValor = new JTextField(String.valueOf(contrato.optDouble("valorArriendo", 0.0)));
            JTextField txtDeposito = new JTextField(String.valueOf(contrato.optDouble("deposito", 0.0)));
            JTextField txtDiaPago = new JTextField(String.valueOf(contrato.optInt("diaPago", 5)));
            
            String[] estados = {"ACTIVO", "FINALIZADO", "CANCELADO", "SUSPENDIDO"};
            JComboBox<String> comboEstado = new JComboBox<>(estados);
            comboEstado.setSelectedItem(contrato.optString("estado", "ACTIVO"));
            
            panel.add(new JLabel("Fecha Inicio (YYYY-MM-DD):"));
            panel.add(txtFechaInicio);
            panel.add(new JLabel("Fecha Fin (YYYY-MM-DD):"));
            panel.add(txtFechaFin);
            panel.add(new JLabel("Valor Arriendo:"));
            panel.add(txtValor);
            panel.add(new JLabel("Depósito:"));
            panel.add(txtDeposito);
            panel.add(new JLabel("Día de Pago:"));
            panel.add(txtDiaPago);
            panel.add(new JLabel("Estado:"));
            panel.add(comboEstado);
            
            int result = JOptionPane.showConfirmDialog(this, panel,
                "Editar Contrato #" + contratoId,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
            
            if (result == JOptionPane.OK_OPTION) {
                JSONObject contratoActualizado = new JSONObject();
                contratoActualizado.put("inmuebleId", contrato.optInt("inmuebleId"));
                contratoActualizado.put("propietarioId", contrato.optInt("propietarioId"));
                contratoActualizado.put("arrendatarioId", contrato.optInt("arrendatarioId"));
                contratoActualizado.put("fechaInicio", txtFechaInicio.getText());
                contratoActualizado.put("fechaFin", txtFechaFin.getText());
                contratoActualizado.put("valorArriendo", Double.parseDouble(txtValor.getText()));
                contratoActualizado.put("deposito", Double.parseDouble(txtDeposito.getText()));
                contratoActualizado.put("diaPago", Integer.parseInt(txtDiaPago.getText()));
                contratoActualizado.put("estado", comboEstado.getSelectedItem().toString());
                
                JSONObject updateResponse = ApiClient.put(ApiClient.CONTRATOS_PORT,
                    "/api/contratos/" + contratoId, contratoActualizado);
                
                if (updateResponse.getInt("statusCode") == 200) {
                    JOptionPane.showMessageDialog(this,
                        "Contrato actualizado exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    cargarContratos();
                    cargarEstadisticas();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Error al actualizar el contrato.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al editar contrato: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void eliminarContrato() {
        int selectedRow = tablaContratos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un contrato de la tabla.",
                "Ningún Contrato Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int contratoId = (int) tablaContratos.getValueAt(selectedRow, 0);
        String inmueble = (String) tablaContratos.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de eliminar el contrato #" + contratoId + "?\n" +
            "Inmueble: " + inmueble + "\n\n" +
            "ADVERTENCIA: Esta acción no se puede deshacer.",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                JSONObject response = ApiClient.delete(ApiClient.CONTRATOS_PORT,
                    "/api/contratos/" + contratoId);
                
                if (response.getInt("statusCode") == 200 || response.getInt("statusCode") == 204) {
                    JOptionPane.showMessageDialog(this,
                        "Contrato eliminado exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    cargarContratos();
                    cargarEstadisticas();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Error al eliminar el contrato.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar contrato: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void verDetallePago() {
        int selectedRow = tablaPagos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un pago de la tabla.",
                "Ningún Pago Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int pagoId = (int) tablaPagos.getValueAt(selectedRow, 0);
            String contratoStr = (String) tablaPagos.getValueAt(selectedRow, 1);
            
            // Extraer el ID del contrato del string "Contrato #X"
            int contratoId = Integer.parseInt(contratoStr.replaceAll("[^0-9]", ""));
            
            // Obtener pagos del contrato
            JSONObject response = ApiClient.get(ApiClient.PAGOS_PORT, "/api/pagos?contrato=" + contratoId);
            
            if (response.getInt("statusCode") != 200) {
                JOptionPane.showMessageDialog(this,
                    "Error al obtener detalles del pago.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Buscar el pago específico
            Object dataObj = response.get("data");
            JSONArray pagos = (dataObj instanceof JSONArray) ? (JSONArray) dataObj : new JSONArray().put(dataObj);
            
            JSONObject pago = null;
            for (int i = 0; i < pagos.length(); i++) {
                JSONObject p = pagos.getJSONObject(i);
                if (p.optInt("id") == pagoId) {
                    pago = p;
                    break;
                }
            }
            
            if (pago == null) {
                JOptionPane.showMessageDialog(this,
                    "No se encontró el pago seleccionado.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear diálogo de detalle
            JDialog dialogo = new JDialog(this, "Detalle del Pago #" + pagoId, true);
            dialogo.setSize(550, 550);
            dialogo.setLocationRelativeTo(this);
            
            JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panelPrincipal.setBackground(Color.WHITE);
            
            // Header con ícono según estado
            JPanel panelHeader = new JPanel(new BorderLayout());
            String estado = pago.optString("estado", "");
            Color colorEstado = estado.equals("PAGADO") ? new Color(40, 167, 69) : new Color(255, 193, 7);
            panelHeader.setBackground(colorEstado);
            panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            JLabel lblTitulo = new JLabel("Información del Pago #" + pagoId);
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            
            JLabel lblEstadoHeader = new JLabel(estado);
            lblEstadoHeader.setFont(new Font("Arial", Font.BOLD, 14));
            lblEstadoHeader.setForeground(Color.WHITE);
            
            JPanel panelTextoHeader = new JPanel(new BorderLayout());
            panelTextoHeader.setOpaque(false);
            panelTextoHeader.add(lblTitulo, BorderLayout.NORTH);
            panelTextoHeader.add(lblEstadoHeader, BorderLayout.SOUTH);
            
            panelHeader.add(panelTextoHeader, BorderLayout.CENTER);
            
            // Panel de datos
            JPanel panelDatos = new JPanel(new GridLayout(12, 2, 10, 10));
            panelDatos.setBackground(Color.WHITE);
            panelDatos.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
            
            panelDatos.add(crearLabelBold("ID Pago:"));
            panelDatos.add(new JLabel(String.valueOf(pago.optInt("id", 0))));
            
            panelDatos.add(crearLabelBold("Contrato ID:"));
            panelDatos.add(new JLabel(String.valueOf(pago.optInt("contratoId", 0))));
            
            panelDatos.add(crearLabelBold("Arrendatario ID:"));
            panelDatos.add(new JLabel(String.valueOf(pago.optInt("arrendatarioId", 0))));
            
            panelDatos.add(crearLabelBold("Fecha de Pago:"));
            panelDatos.add(new JLabel(pago.optString("fecha", "N/A")));
            
            panelDatos.add(crearLabelBold("Mes Correspondiente:"));
            panelDatos.add(new JLabel(pago.optString("mesCorrespondiente", "N/A")));
            
            panelDatos.add(crearLabelBold("Valor:"));
            JLabel lblValor = new JLabel("$" + String.format("%.2f", pago.optDouble("valor", 0.0)));
            lblValor.setForeground(new Color(40, 167, 69));
            lblValor.setFont(new Font("Arial", Font.BOLD, 16));
            panelDatos.add(lblValor);
            
            panelDatos.add(crearLabelBold("Método de Pago:"));
            panelDatos.add(new JLabel(pago.optString("metodoPago", "N/A")));
            
            panelDatos.add(crearLabelBold("Tipo:"));
            panelDatos.add(new JLabel(pago.optString("tipo", "N/A")));
            
            panelDatos.add(crearLabelBold("Referencia:"));
            String referencia = pago.optString("referenciaTransaccion", "");
            panelDatos.add(new JLabel(referencia.isEmpty() ? "N/A" : referencia));
            
            panelDatos.add(crearLabelBold("Mora:"));
            double mora = pago.optDouble("mora", 0.0);
            JLabel lblMora = new JLabel("$" + String.format("%.2f", mora));
            if (mora > 0) {
                lblMora.setForeground(new Color(220, 53, 69));
                lblMora.setFont(new Font("Arial", Font.BOLD, 12));
            }
            panelDatos.add(lblMora);
            
            panelDatos.add(crearLabelBold("Fecha Vencimiento:"));
            String fechaVenc = pago.optString("fechaVencimiento", "");
            panelDatos.add(new JLabel(fechaVenc.isEmpty() ? "N/A" : fechaVenc));
            
            panelDatos.add(crearLabelBold("Fecha Registro:"));
            String fechaRegistro = pago.optString("fechaRegistro", "N/A");
            if (fechaRegistro.length() > 19) {
                fechaRegistro = fechaRegistro.substring(0, 19).replace("T", " ");
            }
            panelDatos.add(new JLabel(fechaRegistro));
            
            // Observaciones (si existen)
            String observaciones = pago.optString("observaciones", "");
            if (!observaciones.isEmpty()) {
                JPanel panelObs = new JPanel(new BorderLayout(10, 5));
                panelObs.setBackground(Color.WHITE);
                panelObs.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
                
                JLabel lblObsTitulo = crearLabelBold("Observaciones:");
                
                JTextArea txtObs = new JTextArea(observaciones);
                txtObs.setFont(new Font("Arial", Font.PLAIN, 12));
                txtObs.setLineWrap(true);
                txtObs.setWrapStyleWord(true);
                txtObs.setEditable(false);
                txtObs.setBackground(new Color(245, 245, 245));
                txtObs.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                
                JScrollPane scrollObs = new JScrollPane(txtObs);
                scrollObs.setPreferredSize(new Dimension(400, 60));
                
                panelObs.add(lblObsTitulo, BorderLayout.NORTH);
                panelObs.add(scrollObs, BorderLayout.CENTER);
                
                JPanel panelContenido = new JPanel(new BorderLayout());
                panelContenido.setBackground(Color.WHITE);
                panelContenido.add(panelDatos, BorderLayout.NORTH);
                panelContenido.add(panelObs, BorderLayout.CENTER);
                
                panelPrincipal.add(panelHeader, BorderLayout.NORTH);
                panelPrincipal.add(panelContenido, BorderLayout.CENTER);
            } else {
                panelPrincipal.add(panelHeader, BorderLayout.NORTH);
                panelPrincipal.add(panelDatos, BorderLayout.CENTER);
            }
            
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.addActionListener(e -> dialogo.dispose());
            
            JPanel panelBoton = new JPanel();
            panelBoton.setBackground(Color.WHITE);
            panelBoton.add(btnCerrar);
            
            panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
            
            dialogo.add(panelPrincipal);
            dialogo.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al mostrar detalle: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // ============= GESTIÓN DE INMUEBLES =============
    
    private void verDetalleInmueble() {
        int selectedRow = tablaInmuebles.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un inmueble de la tabla.",
                "Ningún Inmueble Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int inmuebleId = (int) tablaInmuebles.getValueAt(selectedRow, 0);
            
            // Obtener detalle del inmueble
            JSONObject response = ApiClient.get(ApiClient.INMUEBLES_PORT, "/api/inmuebles/" + inmuebleId);
            
            if (response.getInt("statusCode") != 200) {
                JOptionPane.showMessageDialog(this,
                    "Error al obtener detalles del inmueble.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JSONObject inmueble = response.getJSONObject("data");
            
            // Crear diálogo de detalle
            JDialog dialogo = new JDialog(this, "Detalle del Inmueble #" + inmuebleId, true);
            dialogo.setSize(550, 650);
            dialogo.setLocationRelativeTo(this);
            
            JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panelPrincipal.setBackground(Color.WHITE);
            
            // Header
            JPanel panelHeader = new JPanel(new BorderLayout());
            boolean disponible = inmueble.optBoolean("disponible", false);
            Color colorEstado = disponible ? new Color(40, 167, 69) : new Color(220, 53, 69);
            panelHeader.setBackground(colorEstado);
            panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            JLabel lblTitulo = new JLabel("Información del Inmueble");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            
            JLabel lblEstadoHeader = new JLabel(disponible ? "DISPONIBLE" : "ARRENDADO");
            lblEstadoHeader.setFont(new Font("Arial", Font.BOLD, 14));
            lblEstadoHeader.setForeground(Color.WHITE);
            
            JPanel panelTextoHeader = new JPanel(new BorderLayout());
            panelTextoHeader.setOpaque(false);
            panelTextoHeader.add(lblTitulo, BorderLayout.NORTH);
            panelTextoHeader.add(lblEstadoHeader, BorderLayout.SOUTH);
            
            panelHeader.add(panelTextoHeader, BorderLayout.CENTER);
            
            // Panel de datos
            JPanel panelDatos = new JPanel(new GridLayout(12, 2, 10, 10));
            panelDatos.setBackground(Color.WHITE);
            panelDatos.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
            
            panelDatos.add(crearLabelBold("ID:"));
            panelDatos.add(new JLabel(String.valueOf(inmueble.optInt("id", 0))));
            
            panelDatos.add(crearLabelBold("Tipo:"));
            panelDatos.add(new JLabel(inmueble.optString("tipo", "N/A")));
            
            panelDatos.add(crearLabelBold("Dirección:"));
            panelDatos.add(new JLabel(inmueble.optString("direccion", "N/A")));
            
            panelDatos.add(crearLabelBold("Ciudad:"));
            panelDatos.add(new JLabel(inmueble.optString("ciudad", "N/A")));
            
            panelDatos.add(crearLabelBold("Precio Arriendo:"));
            JLabel lblPrecio = new JLabel("$" + String.format("%.2f", inmueble.optDouble("precioArriendo", 0.0)));
            lblPrecio.setForeground(new Color(40, 167, 69));
            lblPrecio.setFont(new Font("Arial", Font.BOLD, 16));
            panelDatos.add(lblPrecio);
            
            panelDatos.add(crearLabelBold("Área (m²):"));
            panelDatos.add(new JLabel(String.valueOf(inmueble.optDouble("area", 0.0))));
            
            panelDatos.add(crearLabelBold("Habitaciones:"));
            panelDatos.add(new JLabel(String.valueOf(inmueble.optInt("habitaciones", 0))));
            
            panelDatos.add(crearLabelBold("Baños:"));
            panelDatos.add(new JLabel(String.valueOf(inmueble.optInt("banos", 0))));
            
            panelDatos.add(crearLabelBold("Estrato:"));
            panelDatos.add(new JLabel(String.valueOf(inmueble.optInt("estrato", 0))));
            
            panelDatos.add(crearLabelBold("Propietario ID:"));
            panelDatos.add(new JLabel(String.valueOf(inmueble.optInt("propietarioId", 0))));
            
            panelDatos.add(crearLabelBold("Fecha Registro:"));
            String fechaRegistro = inmueble.optString("fechaRegistro", "N/A");
            if (fechaRegistro.length() > 19) {
                fechaRegistro = fechaRegistro.substring(0, 19).replace("T", " ");
            }
            panelDatos.add(new JLabel(fechaRegistro));
            
            panelDatos.add(crearLabelBold("Disponible:"));
            JLabel lblDisponible = new JLabel(disponible ? "Sí" : "No");
            lblDisponible.setForeground(disponible ? new Color(40, 167, 69) : new Color(220, 53, 69));
            lblDisponible.setFont(new Font("Arial", Font.BOLD, 12));
            panelDatos.add(lblDisponible);
            
            // Descripción (si existe)
            String descripcion = inmueble.optString("descripcion", "");
            if (!descripcion.isEmpty()) {
                JPanel panelDesc = new JPanel(new BorderLayout(10, 5));
                panelDesc.setBackground(Color.WHITE);
                panelDesc.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
                
                JLabel lblDescTitulo = crearLabelBold("Descripción:");
                
                JTextArea txtDesc = new JTextArea(descripcion);
                txtDesc.setFont(new Font("Arial", Font.PLAIN, 12));
                txtDesc.setLineWrap(true);
                txtDesc.setWrapStyleWord(true);
                txtDesc.setEditable(false);
                txtDesc.setBackground(new Color(245, 245, 245));
                txtDesc.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                
                JScrollPane scrollDesc = new JScrollPane(txtDesc);
                scrollDesc.setPreferredSize(new Dimension(400, 80));
                
                panelDesc.add(lblDescTitulo, BorderLayout.NORTH);
                panelDesc.add(scrollDesc, BorderLayout.CENTER);
                
                JPanel panelContenido = new JPanel(new BorderLayout());
                panelContenido.setBackground(Color.WHITE);
                panelContenido.add(panelDatos, BorderLayout.NORTH);
                panelContenido.add(panelDesc, BorderLayout.CENTER);
                
                panelPrincipal.add(panelHeader, BorderLayout.NORTH);
                panelPrincipal.add(panelContenido, BorderLayout.CENTER);
            } else {
                panelPrincipal.add(panelHeader, BorderLayout.NORTH);
                panelPrincipal.add(panelDatos, BorderLayout.CENTER);
            }
            
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.addActionListener(e -> dialogo.dispose());
            
            JPanel panelBoton = new JPanel();
            panelBoton.setBackground(Color.WHITE);
            panelBoton.add(btnCerrar);
            
            panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
            
            dialogo.add(panelPrincipal);
            dialogo.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al mostrar detalle: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void editarInmueble() {
        int selectedRow = tablaInmuebles.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un inmueble de la tabla.",
                "Ningún Inmueble Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int inmuebleId = (int) tablaInmuebles.getValueAt(selectedRow, 0);
            
            // Obtener datos actuales
            JSONObject response = ApiClient.get(ApiClient.INMUEBLES_PORT, "/api/inmuebles/" + inmuebleId);
            
            if (response.getInt("statusCode") != 200) {
                JOptionPane.showMessageDialog(this,
                    "Error al obtener datos del inmueble.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JSONObject inmueble = response.getJSONObject("data");
            
            // Crear formulario de edición
            JDialog dialogo = new JDialog(this, "Editar Inmueble #" + inmuebleId, true);
            dialogo.setSize(500, 600);
            dialogo.setLocationRelativeTo(this);
            
            JPanel panelForm = new JPanel(new GridLayout(11, 2, 10, 10));
            panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Campos del formulario
            JTextField txtTipo = new JTextField(inmueble.optString("tipo", ""));
            JTextField txtDireccion = new JTextField(inmueble.optString("direccion", ""));
            JTextField txtCiudad = new JTextField(inmueble.optString("ciudad", ""));
            JTextField txtPrecio = new JTextField(String.valueOf(inmueble.optDouble("precioArriendo", 0.0)));
            JTextField txtArea = new JTextField(String.valueOf(inmueble.optDouble("area", 0.0)));
            JTextField txtHabitaciones = new JTextField(String.valueOf(inmueble.optInt("habitaciones", 0)));
            JTextField txtBanos = new JTextField(String.valueOf(inmueble.optInt("banos", 0)));
            JTextField txtEstrato = new JTextField(String.valueOf(inmueble.optInt("estrato", 0)));
            JTextArea txtDescripcion = new JTextArea(inmueble.optString("descripcion", ""));
            txtDescripcion.setLineWrap(true);
            txtDescripcion.setWrapStyleWord(true);
            JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
            JCheckBox chkDisponible = new JCheckBox("", inmueble.optBoolean("disponible", false));
            
            panelForm.add(new JLabel("Tipo:"));
            panelForm.add(txtTipo);
            
            panelForm.add(new JLabel("Dirección:"));
            panelForm.add(txtDireccion);
            
            panelForm.add(new JLabel("Ciudad:"));
            panelForm.add(txtCiudad);
            
            panelForm.add(new JLabel("Precio Arriendo:"));
            panelForm.add(txtPrecio);
            
            panelForm.add(new JLabel("Área (m²):"));
            panelForm.add(txtArea);
            
            panelForm.add(new JLabel("Habitaciones:"));
            panelForm.add(txtHabitaciones);
            
            panelForm.add(new JLabel("Baños:"));
            panelForm.add(txtBanos);
            
            panelForm.add(new JLabel("Estrato:"));
            panelForm.add(txtEstrato);
            
            panelForm.add(new JLabel("Descripción:"));
            panelForm.add(scrollDesc);
            
            panelForm.add(new JLabel("Disponible:"));
            panelForm.add(chkDisponible);
            
            JPanel panelBotones = new JPanel();
            JButton btnGuardar = new JButton("Guardar");
            JButton btnCancelar = new JButton("Cancelar");
            
            btnGuardar.addActionListener(e -> {
                try {
                    // Crear JSON con datos actualizados
                    JSONObject datos = new JSONObject();
                    datos.put("tipo", txtTipo.getText());
                    datos.put("direccion", txtDireccion.getText());
                    datos.put("ciudad", txtCiudad.getText());
                    datos.put("precioArriendo", Double.parseDouble(txtPrecio.getText()));
                    datos.put("area", Double.parseDouble(txtArea.getText()));
                    datos.put("habitaciones", Integer.parseInt(txtHabitaciones.getText()));
                    datos.put("banos", Integer.parseInt(txtBanos.getText()));
                    datos.put("estrato", Integer.parseInt(txtEstrato.getText()));
                    datos.put("descripcion", txtDescripcion.getText());
                    datos.put("disponible", chkDisponible.isSelected());
                    datos.put("propietarioId", inmueble.optInt("propietarioId", 0));
                    
                    // Enviar actualización
                    System.out.println("📝 Datos a enviar: " + datos.toString());
                    JSONObject respuesta = ApiClient.put(ApiClient.INMUEBLES_PORT, 
                        "/api/inmuebles/" + inmuebleId, datos);
                    
                    System.out.println("📥 Respuesta del servidor: " + respuesta.toString());
                    
                    if (respuesta.getInt("statusCode") == 200) {
                        JOptionPane.showMessageDialog(dialogo,
                            "Inmueble actualizado correctamente.",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                        dialogo.dispose();
                        cargarInmuebles();
                        cargarEstadisticas();
                    } else {
                        String errorMsg = respuesta.optString("message", "Error desconocido");
                        System.err.println("❌ Error al actualizar: StatusCode=" + respuesta.getInt("statusCode") + ", Message=" + errorMsg);
                        JOptionPane.showMessageDialog(dialogo,
                            "Error al actualizar: " + errorMsg + "\n\nCódigo: " + respuesta.getInt("statusCode"),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialogo,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });
            
            btnCancelar.addActionListener(e -> dialogo.dispose());
            
            panelBotones.add(btnGuardar);
            panelBotones.add(btnCancelar);
            
            dialogo.add(panelForm, BorderLayout.CENTER);
            dialogo.add(panelBotones, BorderLayout.SOUTH);
            dialogo.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al editar inmueble: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void eliminarInmueble() {
        int selectedRow = tablaInmuebles.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un inmueble de la tabla.",
                "Ningún Inmueble Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int inmuebleId = (int) tablaInmuebles.getValueAt(selectedRow, 0);
            String direccion = (String) tablaInmuebles.getValueAt(selectedRow, 2);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de eliminar el inmueble #" + inmuebleId + 
                " (" + direccion + ")?\n\nEsta acción no se puede deshacer.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            
            // Eliminar inmueble
            JSONObject response = ApiClient.delete(ApiClient.INMUEBLES_PORT, "/api/inmuebles/" + inmuebleId);
            
            if (response.getInt("statusCode") == 200) {
                JOptionPane.showMessageDialog(this,
                    "Inmueble eliminado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarInmuebles();
                cargarEstadisticas();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar: " + response.optString("message", "Error desconocido"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al eliminar inmueble: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // ============= FIN GESTIÓN DE INMUEBLES =============
    
    // ============= GESTIÓN DE MANTENIMIENTO =============
    
    private void verDetalleMantenimiento() {
        int selectedRow = tablaMantenimiento.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona una solicitud de mantenimiento.",
                "Ninguna Solicitud Seleccionada",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int mantenimientoId = (int) tablaMantenimiento.getValueAt(selectedRow, 0);
            
            JSONObject response = ApiClient.get(ApiClient.MANTENIMIENTO_PORT, "/api/mantenimiento/" + mantenimientoId);
            
            if (response.getInt("statusCode") != 200) {
                JOptionPane.showMessageDialog(this,
                    "Error al obtener detalles del mantenimiento.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JSONObject mant = response.getJSONObject("data");
            
            JDialog dialogo = new JDialog(this, "Detalle de Mantenimiento #" + mantenimientoId, true);
            dialogo.setSize(600, 700);
            dialogo.setLocationRelativeTo(this);
            
            JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panelPrincipal.setBackground(Color.WHITE);
            
            // Header con color según estado
            JPanel panelHeader = new JPanel(new BorderLayout());
            String estado = mant.optString("estado", "");
            Color colorEstado;
            switch (estado) {
                case "PENDIENTE": colorEstado = new Color(255, 193, 7); break;
                case "APROBADO": colorEstado = new Color(40, 167, 69); break;
                case "EN_PROCESO": colorEstado = new Color(23, 162, 184); break;
                case "COMPLETADO": colorEstado = new Color(108, 117, 125); break;
                case "RECHAZADO": colorEstado = new Color(220, 53, 69); break;
                default: colorEstado = new Color(108, 117, 125);
            }
            panelHeader.setBackground(colorEstado);
            panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            JLabel lblTitulo = new JLabel("Solicitud de Mantenimiento");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            
            JLabel lblEstadoHeader = new JLabel(estado);
            lblEstadoHeader.setFont(new Font("Arial", Font.BOLD, 14));
            lblEstadoHeader.setForeground(Color.WHITE);
            
            JPanel panelTextoHeader = new JPanel(new BorderLayout());
            panelTextoHeader.setOpaque(false);
            panelTextoHeader.add(lblTitulo, BorderLayout.NORTH);
            panelTextoHeader.add(lblEstadoHeader, BorderLayout.SOUTH);
            
            panelHeader.add(panelTextoHeader, BorderLayout.CENTER);
            
            // Panel de datos
            JPanel panelDatos = new JPanel(new GridLayout(14, 2, 10, 10));
            panelDatos.setBackground(Color.WHITE);
            panelDatos.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
            
            panelDatos.add(crearLabelBold("ID:"));
            panelDatos.add(new JLabel(String.valueOf(mant.optInt("id", 0))));
            
            panelDatos.add(crearLabelBold("Título:"));
            panelDatos.add(new JLabel(mant.optString("titulo", "N/A")));
            
            panelDatos.add(crearLabelBold("Inmueble ID:"));
            panelDatos.add(new JLabel(String.valueOf(mant.optInt("inmuebleId", 0))));
            
            panelDatos.add(crearLabelBold("Solicitante ID:"));
            panelDatos.add(new JLabel(String.valueOf(mant.optInt("solicitanteId", 0))));
            
            panelDatos.add(crearLabelBold("Tipo:"));
            panelDatos.add(new JLabel(mant.optString("tipo", "N/A")));
            
            panelDatos.add(crearLabelBold("Prioridad:"));
            JLabel lblPrioridad = new JLabel(mant.optString("prioridad", "N/A"));
            String prioridad = mant.optString("prioridad", "");
            if (prioridad.equals("URGENTE") || prioridad.equals("ALTA")) {
                lblPrioridad.setForeground(new Color(220, 53, 69));
                lblPrioridad.setFont(new Font("Arial", Font.BOLD, 12));
            }
            panelDatos.add(lblPrioridad);
            
            panelDatos.add(crearLabelBold("Técnico Asignado:"));
            String tecnico = mant.optString("tecnicoAsignado", "");
            panelDatos.add(new JLabel(tecnico.isEmpty() ? "Sin asignar" : tecnico));
            
            panelDatos.add(crearLabelBold("Costo Estimado:"));
            JLabel lblCostoEst = new JLabel("$" + String.format("%.2f", mant.optDouble("costoEstimado", 0.0)));
            lblCostoEst.setForeground(new Color(0, 123, 255));
            lblCostoEst.setFont(new Font("Arial", Font.BOLD, 12));
            panelDatos.add(lblCostoEst);
            
            panelDatos.add(crearLabelBold("Costo Real:"));
            double costoReal = mant.optDouble("costoReal", 0.0);
            JLabel lblCostoReal = new JLabel(costoReal > 0 ? "$" + String.format("%.2f", costoReal) : "Pendiente");
            if (costoReal > 0) {
                lblCostoReal.setForeground(new Color(40, 167, 69));
                lblCostoReal.setFont(new Font("Arial", Font.BOLD, 12));
            }
            panelDatos.add(lblCostoReal);
            
            panelDatos.add(crearLabelBold("Fecha Solicitud:"));
            String fechaSol = mant.optString("fechaSolicitud", "N/A");
            if (fechaSol.length() > 19) fechaSol = fechaSol.substring(0, 19).replace("T", " ");
            panelDatos.add(new JLabel(fechaSol));
            
            panelDatos.add(crearLabelBold("Fecha Inicio:"));
            String fechaIni = mant.optString("fechaInicio", "");
            panelDatos.add(new JLabel(fechaIni.isEmpty() ? "N/A" : fechaIni.substring(0, Math.min(19, fechaIni.length())).replace("T", " ")));
            
            panelDatos.add(crearLabelBold("Fecha Finalización:"));
            String fechaFin = mant.optString("fechaFinalizacion", "");
            panelDatos.add(new JLabel(fechaFin.isEmpty() ? "N/A" : fechaFin.substring(0, Math.min(19, fechaFin.length())).replace("T", " ")));
            
            panelDatos.add(crearLabelBold("Contrato ID:"));
            int contratoId = mant.optInt("contratoId", 0);
            panelDatos.add(new JLabel(contratoId > 0 ? String.valueOf(contratoId) : "N/A"));
            
            panelDatos.add(crearLabelBold("Estado:"));
            JLabel lblEstado = new JLabel(estado);
            lblEstado.setForeground(colorEstado);
            lblEstado.setFont(new Font("Arial", Font.BOLD, 12));
            panelDatos.add(lblEstado);
            
            // Descripción y observaciones
            JPanel panelTextos = new JPanel(new GridLayout(2, 1, 10, 10));
            panelTextos.setBackground(Color.WHITE);
            panelTextos.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            
            String descripcion = mant.optString("descripcion", "");
            if (!descripcion.isEmpty()) {
                JPanel panelDesc = new JPanel(new BorderLayout(5, 5));
                panelDesc.setBackground(Color.WHITE);
                panelDesc.add(crearLabelBold("Descripción:"), BorderLayout.NORTH);
                
                JTextArea txtDesc = new JTextArea(descripcion);
                txtDesc.setFont(new Font("Arial", Font.PLAIN, 12));
                txtDesc.setLineWrap(true);
                txtDesc.setWrapStyleWord(true);
                txtDesc.setEditable(false);
                txtDesc.setBackground(new Color(245, 245, 245));
                txtDesc.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                
                JScrollPane scrollDesc = new JScrollPane(txtDesc);
                scrollDesc.setPreferredSize(new Dimension(500, 60));
                panelDesc.add(scrollDesc, BorderLayout.CENTER);
                panelTextos.add(panelDesc);
            }
            
            String observaciones = mant.optString("observaciones", "");
            if (!observaciones.isEmpty()) {
                JPanel panelObs = new JPanel(new BorderLayout(5, 5));
                panelObs.setBackground(Color.WHITE);
                panelObs.add(crearLabelBold("Observaciones:"), BorderLayout.NORTH);
                
                JTextArea txtObs = new JTextArea(observaciones);
                txtObs.setFont(new Font("Arial", Font.PLAIN, 12));
                txtObs.setLineWrap(true);
                txtObs.setWrapStyleWord(true);
                txtObs.setEditable(false);
                txtObs.setBackground(new Color(245, 245, 245));
                txtObs.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                
                JScrollPane scrollObs = new JScrollPane(txtObs);
                scrollObs.setPreferredSize(new Dimension(500, 60));
                panelObs.add(scrollObs, BorderLayout.CENTER);
                panelTextos.add(panelObs);
            }
            
            JPanel panelContenido = new JPanel(new BorderLayout());
            panelContenido.setBackground(Color.WHITE);
            panelContenido.add(panelDatos, BorderLayout.NORTH);
            if (!descripcion.isEmpty() || !observaciones.isEmpty()) {
                panelContenido.add(panelTextos, BorderLayout.CENTER);
            }
            
            panelPrincipal.add(panelHeader, BorderLayout.NORTH);
            panelPrincipal.add(panelContenido, BorderLayout.CENTER);
            
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.addActionListener(e -> dialogo.dispose());
            
            JPanel panelBoton = new JPanel();
            panelBoton.setBackground(Color.WHITE);
            panelBoton.add(btnCerrar);
            
            panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
            
            dialogo.add(panelPrincipal);
            dialogo.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al mostrar detalle: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void editarMantenimiento() {
        int selectedRow = tablaMantenimiento.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona una solicitud de mantenimiento.",
                "Ninguna Solicitud Seleccionada",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int mantenimientoId = (int) tablaMantenimiento.getValueAt(selectedRow, 0);
            
            JSONObject response = ApiClient.get(ApiClient.MANTENIMIENTO_PORT, "/api/mantenimiento/" + mantenimientoId);
            
            if (response.getInt("statusCode") != 200) {
                JOptionPane.showMessageDialog(this,
                    "Error al obtener datos del mantenimiento.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JSONObject mant = response.getJSONObject("data");
            
            JDialog dialogo = new JDialog(this, "Editar Mantenimiento #" + mantenimientoId, true);
            dialogo.setSize(550, 650);
            dialogo.setLocationRelativeTo(this);
            
            JPanel panelForm = new JPanel(new GridLayout(8, 2, 10, 10));
            panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Campos del formulario
            JTextField txtTitulo = new JTextField(mant.optString("titulo", ""));
            JTextArea txtDescripcion = new JTextArea(mant.optString("descripcion", ""));
            txtDescripcion.setLineWrap(true);
            txtDescripcion.setWrapStyleWord(true);
            JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
            
            String[] tipos = {"PREVENTIVO", "CORRECTIVO", "EMERGENCIA"};
            JComboBox<String> cmbTipo = new JComboBox<>(tipos);
            cmbTipo.setSelectedItem(mant.optString("tipo", "CORRECTIVO"));
            
            String[] prioridades = {"BAJA", "MEDIA", "ALTA", "URGENTE"};
            JComboBox<String> cmbPrioridad = new JComboBox<>(prioridades);
            cmbPrioridad.setSelectedItem(mant.optString("prioridad", "MEDIA"));
            
            JTextField txtTecnico = new JTextField(mant.optString("tecnicoAsignado", ""));
            JTextField txtCostoEst = new JTextField(String.valueOf(mant.optDouble("costoEstimado", 0.0)));
            
            JTextArea txtObservaciones = new JTextArea(mant.optString("observaciones", ""));
            txtObservaciones.setLineWrap(true);
            txtObservaciones.setWrapStyleWord(true);
            JScrollPane scrollObs = new JScrollPane(txtObservaciones);
            
            panelForm.add(new JLabel("Título:"));
            panelForm.add(txtTitulo);
            
            panelForm.add(new JLabel("Descripción:"));
            panelForm.add(scrollDesc);
            
            panelForm.add(new JLabel("Tipo:"));
            panelForm.add(cmbTipo);
            
            panelForm.add(new JLabel("Prioridad:"));
            panelForm.add(cmbPrioridad);
            
            panelForm.add(new JLabel("Técnico Asignado:"));
            panelForm.add(txtTecnico);
            
            panelForm.add(new JLabel("Costo Estimado:"));
            panelForm.add(txtCostoEst);
            
            panelForm.add(new JLabel("Observaciones:"));
            panelForm.add(scrollObs);
            
            JPanel panelBotones = new JPanel();
            JButton btnGuardar = new JButton("Guardar");
            JButton btnCancelar = new JButton("Cancelar");
            
            btnGuardar.addActionListener(e -> {
                try {
                    JSONObject datos = new JSONObject();
                    datos.put("titulo", txtTitulo.getText());
                    datos.put("descripcion", txtDescripcion.getText());
                    datos.put("tipo", cmbTipo.getSelectedItem().toString());
                    datos.put("prioridad", cmbPrioridad.getSelectedItem().toString());
                    datos.put("tecnicoAsignado", txtTecnico.getText());
                    datos.put("costoEstimado", Double.parseDouble(txtCostoEst.getText()));
                    datos.put("observaciones", txtObservaciones.getText());
                    
                    JSONObject respuesta = ApiClient.put(ApiClient.MANTENIMIENTO_PORT, 
                        "/api/mantenimiento/" + mantenimientoId, datos);
                    
                    if (respuesta.getInt("statusCode") == 200) {
                        JOptionPane.showMessageDialog(dialogo,
                            "Mantenimiento actualizado correctamente.",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                        dialogo.dispose();
                        cargarMantenimientos();
                    } else {
                        JOptionPane.showMessageDialog(dialogo,
                            "Error al actualizar: " + respuesta.optString("message", "Error desconocido"),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialogo,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });
            
            btnCancelar.addActionListener(e -> dialogo.dispose());
            
            panelBotones.add(btnGuardar);
            panelBotones.add(btnCancelar);
            
            dialogo.add(panelForm, BorderLayout.CENTER);
            dialogo.add(panelBotones, BorderLayout.SOUTH);
            dialogo.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al editar mantenimiento: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void aprobarMantenimiento() {
        int selectedRow = tablaMantenimiento.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona una solicitud de mantenimiento.",
                "Ninguna Solicitud Seleccionada",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int mantenimientoId = (int) tablaMantenimiento.getValueAt(selectedRow, 0);
            String estado = (String) tablaMantenimiento.getValueAt(selectedRow, 6);
            
            if (!estado.equals("PENDIENTE")) {
                JOptionPane.showMessageDialog(this,
                    "Solo se pueden aprobar solicitudes en estado PENDIENTE.\nEstado actual: " + estado,
                    "Estado No Válido",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Deseas aprobar esta solicitud de mantenimiento?",
                "Confirmar Aprobación",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm != JOptionPane.YES_OPTION) return;
            
            JSONObject response = ApiClient.put(ApiClient.MANTENIMIENTO_PORT, 
                "/api/mantenimiento/" + mantenimientoId + "/aprobar", new JSONObject());
            
            if (response.getInt("statusCode") == 200) {
                JOptionPane.showMessageDialog(this,
                    "Solicitud aprobada correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarMantenimientos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al aprobar: " + response.optString("message", "Error desconocido"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al aprobar mantenimiento: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void iniciarMantenimiento() {
        int selectedRow = tablaMantenimiento.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona una solicitud de mantenimiento.",
                "Ninguna Solicitud Seleccionada",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int mantenimientoId = (int) tablaMantenimiento.getValueAt(selectedRow, 0);
            String estado = (String) tablaMantenimiento.getValueAt(selectedRow, 6);
            
            if (!estado.equals("APROBADO")) {
                JOptionPane.showMessageDialog(this,
                    "Solo se pueden iniciar solicitudes en estado APROBADO.\nEstado actual: " + estado,
                    "Estado No Válido",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Deseas iniciar el trabajo de mantenimiento?",
                "Confirmar Inicio",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm != JOptionPane.YES_OPTION) return;
            
            JSONObject response = ApiClient.put(ApiClient.MANTENIMIENTO_PORT, 
                "/api/mantenimiento/" + mantenimientoId + "/iniciar", new JSONObject());
            
            if (response.getInt("statusCode") == 200) {
                JOptionPane.showMessageDialog(this,
                    "Mantenimiento iniciado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarMantenimientos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al iniciar: " + response.optString("message", "Error desconocido"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al iniciar mantenimiento: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void completarMantenimiento() {
        int selectedRow = tablaMantenimiento.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona una solicitud de mantenimiento.",
                "Ninguna Solicitud Seleccionada",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int mantenimientoId = (int) tablaMantenimiento.getValueAt(selectedRow, 0);
            String estado = (String) tablaMantenimiento.getValueAt(selectedRow, 6);
            
            if (!estado.equals("EN_PROCESO")) {
                JOptionPane.showMessageDialog(this,
                    "Solo se pueden completar solicitudes en estado EN_PROCESO.\nEstado actual: " + estado,
                    "Estado No Válido",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Solicitar costo real y observaciones
            JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
            panel.add(new JLabel("Costo Real:"));
            JTextField txtCostoReal = new JTextField();
            panel.add(txtCostoReal);
            
            panel.add(new JLabel("Observaciones:"));
            JTextArea txtObservaciones = new JTextArea(3, 20);
            txtObservaciones.setLineWrap(true);
            txtObservaciones.setWrapStyleWord(true);
            JScrollPane scrollObs = new JScrollPane(txtObservaciones);
            panel.add(scrollObs);
            
            int result = JOptionPane.showConfirmDialog(this, panel, 
                "Completar Mantenimiento", JOptionPane.OK_CANCEL_OPTION);
            
            if (result != JOptionPane.OK_OPTION) return;
            
            String costoRealStr = txtCostoReal.getText().trim();
            if (costoRealStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "El costo real es obligatorio.",
                    "Datos Incompletos",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double costoReal = Double.parseDouble(costoRealStr);
            String observaciones = txtObservaciones.getText().trim();
            
            String url = "/api/mantenimiento/" + mantenimientoId + "/completar?costoReal=" + costoReal;
            if (!observaciones.isEmpty()) {
                url += "&observaciones=" + java.net.URLEncoder.encode(observaciones, "UTF-8");
            }
            
            JSONObject response = ApiClient.put(ApiClient.MANTENIMIENTO_PORT, url, new JSONObject());
            
            if (response.getInt("statusCode") == 200) {
                JOptionPane.showMessageDialog(this,
                    "Mantenimiento completado correctamente.\nCosto Real: $" + String.format("%.2f", costoReal),
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarMantenimientos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al completar: " + response.optString("message", "Error desconocido"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "El costo real debe ser un número válido.",
                "Error de Formato",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al completar mantenimiento: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void rechazarMantenimiento() {
        int selectedRow = tablaMantenimiento.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona una solicitud de mantenimiento.",
                "Ninguna Solicitud Seleccionada",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int mantenimientoId = (int) tablaMantenimiento.getValueAt(selectedRow, 0);
            String estado = (String) tablaMantenimiento.getValueAt(selectedRow, 6);
            
            if (!estado.equals("PENDIENTE")) {
                JOptionPane.showMessageDialog(this,
                    "Solo se pueden rechazar solicitudes en estado PENDIENTE.\nEstado actual: " + estado,
                    "Estado No Válido",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String motivo = JOptionPane.showInputDialog(this,
                "Motivo del rechazo:",
                "Rechazar Solicitud",
                JOptionPane.QUESTION_MESSAGE);
            
            if (motivo == null || motivo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Debe proporcionar un motivo para el rechazo.",
                    "Motivo Requerido",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String url = "/api/mantenimiento/" + mantenimientoId + "/rechazar?motivo=" + 
                java.net.URLEncoder.encode(motivo, "UTF-8");
            
            JSONObject response = ApiClient.put(ApiClient.MANTENIMIENTO_PORT, url, new JSONObject());
            
            if (response.getInt("statusCode") == 200) {
                JOptionPane.showMessageDialog(this,
                    "Solicitud rechazada correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarMantenimientos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al rechazar: " + response.optString("message", "Error desconocido"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al rechazar mantenimiento: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // ============= FIN GESTIÓN DE MANTENIMIENTO =============
    
    // ============= GESTIÓN DE PAGOS =============
    
    private void cambiarEstadoPago() {
        int selectedRow = tablaPagos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un pago de la tabla.",
                "Ningún Pago Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int pagoId = (int) tablaPagos.getValueAt(selectedRow, 0);
            String estadoActual = (String) tablaPagos.getValueAt(selectedRow, 6);
            
            String[] estados = {"PENDIENTE", "PAGADO", "VENCIDO", "PARCIAL"};
            String nuevoEstado = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona el nuevo estado para el pago #" + pagoId + "\n" +
                "Estado actual: " + estadoActual,
                "Cambiar Estado de Pago",
                JOptionPane.QUESTION_MESSAGE,
                null,
                estados,
                estadoActual);
            
            if (nuevoEstado == null || nuevoEstado.equals(estadoActual)) {
                return;
            }
            
            JSONObject response = ApiClient.put(ApiClient.PAGOS_PORT, 
                "/api/pagos/" + pagoId + "/estado?estado=" + nuevoEstado, new JSONObject());
            
            if (response.getInt("statusCode") == 200) {
                JOptionPane.showMessageDialog(this,
                    "Estado actualizado correctamente a: " + nuevoEstado,
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarTodosPagos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al cambiar estado: " + response.optString("message", "Error desconocido"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cambiar estado del pago: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void marcarComoPagado() {
        int selectedRow = tablaPagos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un pago de la tabla.",
                "Ningún Pago Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int pagoId = (int) tablaPagos.getValueAt(selectedRow, 0);
            String estadoActual = (String) tablaPagos.getValueAt(selectedRow, 6);
            
            if (estadoActual.equals("PAGADO")) {
                JOptionPane.showMessageDialog(this,
                    "Este pago ya está marcado como PAGADO.",
                    "Ya Pagado",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Confirmas que deseas marcar el pago #" + pagoId + " como PAGADO?",
                "Confirmar Pago",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm != JOptionPane.YES_OPTION) return;
            
            JSONObject response = ApiClient.put(ApiClient.PAGOS_PORT, 
                "/api/pagos/" + pagoId + "/estado?estado=PAGADO", new JSONObject());
            
            if (response.getInt("statusCode") == 200) {
                JOptionPane.showMessageDialog(this,
                    "Pago #" + pagoId + " marcado como PAGADO exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarTodosPagos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al marcar como pagado: " + response.optString("message", "Error desconocido"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al marcar como pagado: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void marcarComoVencido() {
        int selectedRow = tablaPagos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un pago de la tabla.",
                "Ningún Pago Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int pagoId = (int) tablaPagos.getValueAt(selectedRow, 0);
            String estadoActual = (String) tablaPagos.getValueAt(selectedRow, 6);
            
            if (estadoActual.equals("PAGADO")) {
                JOptionPane.showMessageDialog(this,
                    "No se puede marcar como vencido un pago que ya está PAGADO.",
                    "Acción No Permitida",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (estadoActual.equals("VENCIDO")) {
                JOptionPane.showMessageDialog(this,
                    "Este pago ya está marcado como VENCIDO.",
                    "Ya Vencido",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Confirmas que deseas marcar el pago #" + pagoId + " como VENCIDO?\n" +
                "Esto puede generar moras adicionales.",
                "Confirmar Vencimiento",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) return;
            
            JSONObject response = ApiClient.put(ApiClient.PAGOS_PORT, 
                "/api/pagos/" + pagoId + "/estado?estado=VENCIDO", new JSONObject());
            
            if (response.getInt("statusCode") == 200) {
                JOptionPane.showMessageDialog(this,
                    "Pago #" + pagoId + " marcado como VENCIDO.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarTodosPagos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al marcar como vencido: " + response.optString("message", "Error desconocido"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al marcar como vencido: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // ============= FIN GESTIÓN DE PAGOS =============
    
    // ============= GESTIÓN DE USUARIOS =============
    
    private void verDetalleUsuario() {
        int selectedRow = tablaUsuarios.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un usuario de la tabla.",
                "Ningún Usuario Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int usuarioId = (int) tablaUsuarios.getValueAt(selectedRow, 0);
            
            JSONObject response = ApiClient.get(ApiClient.USUARIOS_PORT, "/api/usuarios/" + usuarioId);
            
            if (response.getInt("statusCode") != 200) {
                JOptionPane.showMessageDialog(this,
                    "Error al obtener detalles del usuario.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JSONObject usuario = response.getJSONObject("data");
            
            JDialog dialogo = new JDialog(this, "Detalle de Usuario #" + usuarioId, true);
            dialogo.setSize(550, 550);
            dialogo.setLocationRelativeTo(this);
            
            JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panelPrincipal.setBackground(Color.WHITE);
            
            // Header
            JPanel panelHeader = new JPanel(new BorderLayout());
            boolean activo = usuario.optBoolean("activo", false);
            Color colorEstado = activo ? new Color(40, 167, 69) : new Color(220, 53, 69);
            panelHeader.setBackground(colorEstado);
            panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            JLabel lblTitulo = new JLabel("Información del Usuario");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            
            JLabel lblEstadoHeader = new JLabel(activo ? "ACTIVO" : "INACTIVO");
            lblEstadoHeader.setFont(new Font("Arial", Font.BOLD, 14));
            lblEstadoHeader.setForeground(Color.WHITE);
            
            JPanel panelTextoHeader = new JPanel(new BorderLayout());
            panelTextoHeader.setOpaque(false);
            panelTextoHeader.add(lblTitulo, BorderLayout.NORTH);
            panelTextoHeader.add(lblEstadoHeader, BorderLayout.SOUTH);
            
            panelHeader.add(panelTextoHeader, BorderLayout.CENTER);
            
            // Panel de datos
            JPanel panelDatos = new JPanel(new GridLayout(10, 2, 10, 10));
            panelDatos.setBackground(Color.WHITE);
            panelDatos.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
            
            panelDatos.add(crearLabelBold("ID:"));
            panelDatos.add(new JLabel(String.valueOf(usuario.optInt("id", 0))));
            
            panelDatos.add(crearLabelBold("Nombre:"));
            panelDatos.add(new JLabel(usuario.optString("nombre", "N/A")));
            
            panelDatos.add(crearLabelBold("Apellido:"));
            panelDatos.add(new JLabel(usuario.optString("apellido", "N/A")));
            
            panelDatos.add(crearLabelBold("Correo:"));
            panelDatos.add(new JLabel(usuario.optString("correo", "N/A")));
            
            panelDatos.add(crearLabelBold("Teléfono:"));
            panelDatos.add(new JLabel(usuario.optString("telefono", "N/A")));
            
            panelDatos.add(crearLabelBold("Dirección:"));
            panelDatos.add(new JLabel(usuario.optString("direccion", "N/A")));
            
            panelDatos.add(crearLabelBold("Cédula:"));
            panelDatos.add(new JLabel(usuario.optString("cedula", "N/A")));
            
            panelDatos.add(crearLabelBold("Tipo de Usuario:"));
            JLabel lblTipo = new JLabel(usuario.optString("tipoUsuario", "N/A"));
            lblTipo.setFont(new Font("Arial", Font.BOLD, 12));
            lblTipo.setForeground(new Color(0, 123, 255));
            panelDatos.add(lblTipo);
            
            panelDatos.add(crearLabelBold("Fecha de Registro:"));
            String fechaReg = usuario.optString("fechaRegistro", "N/A");
            if (fechaReg.length() > 19) fechaReg = fechaReg.substring(0, 19).replace("T", " ");
            panelDatos.add(new JLabel(fechaReg));
            
            panelDatos.add(crearLabelBold("Estado:"));
            JLabel lblEstado = new JLabel(activo ? "Activo" : "Inactivo");
            lblEstado.setForeground(colorEstado);
            lblEstado.setFont(new Font("Arial", Font.BOLD, 12));
            panelDatos.add(lblEstado);
            
            panelPrincipal.add(panelHeader, BorderLayout.NORTH);
            panelPrincipal.add(panelDatos, BorderLayout.CENTER);
            
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.addActionListener(e -> dialogo.dispose());
            
            JPanel panelBoton = new JPanel();
            panelBoton.setBackground(Color.WHITE);
            panelBoton.add(btnCerrar);
            
            panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
            
            dialogo.add(panelPrincipal);
            dialogo.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al mostrar detalle: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void editarUsuario() {
        int selectedRow = tablaUsuarios.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un usuario de la tabla.",
                "Ningún Usuario Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int usuarioId = (int) tablaUsuarios.getValueAt(selectedRow, 0);
            
            JSONObject response = ApiClient.get(ApiClient.USUARIOS_PORT, "/api/usuarios/" + usuarioId);
            
            if (response.getInt("statusCode") != 200) {
                JOptionPane.showMessageDialog(this,
                    "Error al obtener datos del usuario.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JSONObject usuario = response.getJSONObject("data");
            
            JDialog dialogo = new JDialog(this, "Editar Usuario #" + usuarioId, true);
            dialogo.setSize(500, 550);
            dialogo.setLocationRelativeTo(this);
            
            JPanel panelForm = new JPanel(new GridLayout(8, 2, 10, 10));
            panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JTextField txtNombre = new JTextField(usuario.optString("nombre", ""));
            JTextField txtApellido = new JTextField(usuario.optString("apellido", ""));
            JTextField txtCorreo = new JTextField(usuario.optString("correo", ""));
            txtCorreo.setEnabled(false); // No se puede cambiar el correo
            JTextField txtTelefono = new JTextField(usuario.optString("telefono", ""));
            JTextField txtDireccion = new JTextField(usuario.optString("direccion", ""));
            JTextField txtCedula = new JTextField(usuario.optString("cedula", ""));
            
            String[] tiposUsuario = {"ADMINISTRADOR", "PROPIETARIO", "ARRENDATARIO"};
            JComboBox<String> cmbTipo = new JComboBox<>(tiposUsuario);
            cmbTipo.setSelectedItem(usuario.optString("tipoUsuario", "ARRENDATARIO"));
            
            panelForm.add(new JLabel("Nombre:"));
            panelForm.add(txtNombre);
            
            panelForm.add(new JLabel("Apellido:"));
            panelForm.add(txtApellido);
            
            panelForm.add(new JLabel("Correo:"));
            panelForm.add(txtCorreo);
            
            panelForm.add(new JLabel("Teléfono:"));
            panelForm.add(txtTelefono);
            
            panelForm.add(new JLabel("Dirección:"));
            panelForm.add(txtDireccion);
            
            panelForm.add(new JLabel("Cédula:"));
            panelForm.add(txtCedula);
            
            panelForm.add(new JLabel("Tipo de Usuario:"));
            panelForm.add(cmbTipo);
            
            JPanel panelBotones = new JPanel();
            JButton btnGuardar = new JButton("Guardar");
            JButton btnCancelar = new JButton("Cancelar");
            
            btnGuardar.addActionListener(e -> {
                try {
                    JSONObject datos = new JSONObject();
                    datos.put("nombre", txtNombre.getText());
                    datos.put("apellido", txtApellido.getText());
                    datos.put("telefono", txtTelefono.getText());
                    datos.put("direccion", txtDireccion.getText());
                    datos.put("cedula", txtCedula.getText());
                    datos.put("tipoUsuario", cmbTipo.getSelectedItem().toString());
                    
                    // Nota: El servicio de usuarios no tiene endpoint PUT general, solo para activar/desactivar
                    // Por ahora mostraremos un mensaje
                    JOptionPane.showMessageDialog(dialogo,
                        "Nota: El servicio de usuarios actualmente no soporta edición completa.\n" +
                        "Puedes activar/desactivar usuarios desde los botones correspondientes.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                    dialogo.dispose();
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialogo,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });
            
            btnCancelar.addActionListener(e -> dialogo.dispose());
            
            panelBotones.add(btnGuardar);
            panelBotones.add(btnCancelar);
            
            dialogo.add(panelForm, BorderLayout.CENTER);
            dialogo.add(panelBotones, BorderLayout.SOUTH);
            dialogo.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al editar usuario: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void eliminarUsuario() {
        int selectedRow = tablaUsuarios.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un usuario de la tabla.",
                "Ningún Usuario Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int usuarioId = (int) tablaUsuarios.getValueAt(selectedRow, 0);
            String nombre = (String) tablaUsuarios.getValueAt(selectedRow, 1);
            String apellido = (String) tablaUsuarios.getValueAt(selectedRow, 2);
            
            // Verificar que no se elimine a sí mismo
            if (usuarioId == SesionUsuario.getUsuarioID()) {
                JOptionPane.showMessageDialog(this,
                    "No puedes eliminar tu propia cuenta mientras estás logueado.",
                    "Acción No Permitida",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de eliminar al usuario " + nombre + " " + apellido + "?\n\n" +
                "⚠️ ADVERTENCIA: Esta acción es IRREVERSIBLE.\n" +
                "Se perderán todos los datos asociados a este usuario.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) return;
            
            // Nota: El servicio actual no tiene endpoint DELETE
            // Por ahora solo lo desactivamos
            JSONObject response = ApiClient.put(ApiClient.USUARIOS_PORT, 
                "/api/usuarios/" + usuarioId + "/desactivar", new JSONObject());
            
            if (response.getInt("statusCode") == 200) {
                JOptionPane.showMessageDialog(this,
                    "Usuario desactivado correctamente.\n\n" +
                    "Nota: El usuario fue desactivado en lugar de eliminado\n" +
                    "para preservar la integridad de los datos del sistema.",
                    "Usuario Desactivado",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar usuario: " + response.optString("message", "Error desconocido"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al eliminar usuario: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // ============= FIN GESTIÓN DE USUARIOS =============
    
    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro que deseas cerrar sesión?", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            SesionUsuario.cerrarSesion();
            this.dispose();
            java.awt.EventQueue.invokeLater(() -> {
                frmlogin dialog = new frmlogin(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            });
        }
    }
    
    private void finalizarContratoAdmin() {
        int selectedRow = tablaContratos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un contrato de la tabla.",
                "Ningún Contrato Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int contratoId = (int) tablaContratos.getValueAt(selectedRow, 0);
            String estado = (String) tablaContratos.getValueAt(selectedRow, 7);
            
            if (estado.equals("FINALIZADO")) {
                JOptionPane.showMessageDialog(this,
                    "Este contrato ya está FINALIZADO.",
                    "Contrato Finalizado",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de finalizar el contrato #" + contratoId + "?\n\n" +
                "Acciones:\n" +
                "• El contrato cambiará a estado FINALIZADO\n" +
                "• El inmueble se marcará como DISPONIBLE\n" +
                "• Esta acción no se puede deshacer\n\n" +
                "¿Deseas continuar?",
                "Confirmar Finalización de Contrato",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) return;
            
            JSONObject response = ApiClient.put(ApiClient.CONTRATOS_PORT, 
                "/api/contratos/" + contratoId + "/finalizar", new JSONObject());
            
            if (response.getInt("statusCode") == 200) {
                JOptionPane.showMessageDialog(this,
                    "✅ Contrato #" + contratoId + " finalizado correctamente.\n\n" +
                    "El inmueble asociado ahora está DISPONIBLE para nuevos contratos.",
                    "Contrato Finalizado",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarContratos();
                cargarEstadisticas();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al finalizar contrato: " + response.optString("message", "Error desconocido"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al finalizar contrato: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private JPanel crearPanelPerfil() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        JLabel titulo = new JLabel("Mi Perfil de Administrador");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(220, 53, 69));
        
        JPanel panelDatos = new JPanel(new GridLayout(8, 2, 15, 15));
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 13));
        JTextField txtNombre = new JTextField(SesionUsuario.getNombre());
        
        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(new Font("Arial", Font.BOLD, 13));
        JTextField txtApellido = new JTextField(SesionUsuario.getApellido());
        
        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setFont(new Font("Arial", Font.BOLD, 13));
        JTextField txtCorreo = new JTextField(SesionUsuario.getCorreo());
        txtCorreo.setEnabled(false);
        txtCorreo.setBackground(new Color(240, 240, 240));
        
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 13));
        JTextField txtTelefono = new JTextField();
        
        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setFont(new Font("Arial", Font.BOLD, 13));
        JTextField txtDireccion = new JTextField();
        
        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setFont(new Font("Arial", Font.BOLD, 13));
        JTextField txtCedula = new JTextField();
        
        JLabel lblRol = new JLabel("Rol:");
        lblRol.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel lblRolValor = new JLabel(SesionUsuario.getRol() + " ⭐");
        lblRolValor.setForeground(new Color(220, 53, 69));
        lblRolValor.setFont(new Font("Arial", Font.BOLD, 13));
        
        JLabel lblId = new JLabel("ID Usuario:");
        lblId.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel lblIdValor = new JLabel(String.valueOf(SesionUsuario.getUsuarioID()));
        
        panelDatos.add(lblNombre);
        panelDatos.add(txtNombre);
        panelDatos.add(lblApellido);
        panelDatos.add(txtApellido);
        panelDatos.add(lblCorreo);
        panelDatos.add(txtCorreo);
        panelDatos.add(lblTelefono);
        panelDatos.add(txtTelefono);
        panelDatos.add(lblDireccion);
        panelDatos.add(txtDireccion);
        panelDatos.add(lblCedula);
        panelDatos.add(txtCedula);
        panelDatos.add(lblRol);
        panelDatos.add(lblRolValor);
        panelDatos.add(lblId);
        panelDatos.add(lblIdValor);
        
        try {
            JSONObject response = ApiClient.get(ApiClient.USUARIOS_PORT, "/api/usuarios/" + SesionUsuario.getUsuarioID());
            if (response.getInt("statusCode") == 200) {
                JSONObject usuario = response.getJSONObject("data");
                txtTelefono.setText(usuario.optString("telefono", ""));
                txtDireccion.setText(usuario.optString("direccion", ""));
                txtCedula.setText(usuario.optString("cedula", ""));
            }
        } catch (Exception e) {
            System.err.println("Error al cargar datos del perfil: " + e.getMessage());
        }
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBackground(new Color(220, 53, 69));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGuardar.setPreferredSize(new Dimension(150, 35));
        
        btnGuardar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Perfil actualizado localmente.\n\n" +
                "Nota: Requiere endpoint PUT /api/usuarios/{id} en el backend.",
                "Cambios Guardados",
                JOptionPane.INFORMATION_MESSAGE);
            SesionUsuario.setNombre(txtNombre.getText());
            SesionUsuario.setApellido(txtApellido.getText());
            lblBienvenida.setText("Admin: " + SesionUsuario.getNombre() + " " + SesionUsuario.getApellido());
        });
        
        panelBotones.add(btnGuardar);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelDatos, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(frmadministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
                frmadministrador dialog = new frmadministrador(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
        });
    }
}
