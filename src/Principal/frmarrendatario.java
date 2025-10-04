package Principal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Panel de Arrendatario
 * @author David Urrego
 */
public class frmarrendatario extends javax.swing.JDialog {

    private JTabbedPane tabbedPane;
    private JTable tablaContratos;
    private JTable tablaPagos;
    private JTable tablaMantenimiento;
    private JTable tablaNotificaciones;
    private JTextArea txtMantenimiento;
    private JButton btnSolicitarMantenimiento;
    private JButton btnRealizarPago;
    private JLabel lblBienvenida;
    private JSONArray notificacionesData; // Guardar datos completos de notificaciones
    private java.util.Set<Integer> notificacionesLeidas; // IDs de notificaciones leídas
    
    public frmarrendatario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        notificacionesLeidas = new java.util.HashSet<>();
        initComponents();
        setLocationRelativeTo(null);
        cargarDatos();
    }

    private void initComponents() {
        setTitle("Panel de Arrendatario - " + SesionUsuario.getNombre());
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(240, 240, 240));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Header
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(0, 123, 255));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        lblBienvenida = new JLabel("Bienvenido, " + SesionUsuario.getNombre());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        lblBienvenida.setForeground(Color.WHITE);
        
        JLabel lblRol = new JLabel("Arrendatario - ID: " + SesionUsuario.getUsuarioID());
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
        
        // Tab 1: Mi Contrato
        tabbedPane.addTab("MI CONTRATO", crearPanelContrato());
        
        // Tab 2: Mis Pagos
        tabbedPane.addTab("MIS PAGOS", crearPanelPagos());
        
        // Tab 3: Mantenimiento
        tabbedPane.addTab("MANTENIMIENTO", crearPanelMantenimiento());
        
        // Tab 4: Notificaciones
        tabbedPane.addTab("NOTIFICACIONES", crearPanelNotificaciones());
        
        // Tab 5: Mi Perfil
        tabbedPane.addTab("MI PERFIL", crearPanelPerfil());
        
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(tabbedPane, BorderLayout.CENTER);
        
        getContentPane().add(panelPrincipal);
        pack();
    }
    
    private JPanel crearPanelContrato() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Título
        JLabel titulo = new JLabel("Mi Contrato Actual");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Tabla de contratos
        String[] columnas = {"ID", "Inmueble", "Fecha Inicio", "Fecha Fin", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaContratos = new JTable(modelo);
        tablaContratos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaContratos.setRowHeight(25);
        tablaContratos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollContratos = new JScrollPane(tablaContratos);
        
        // Panel de info
        JPanel panelInfo = new JPanel(new GridLayout(0, 1, 5, 5));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información del Contrato"));
        panelInfo.add(new JLabel("Seleccione un contrato de la tabla para ver detalles"));
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarContratos());
        panelBotones.add(btnActualizar);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollContratos, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelPagos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Título
        JLabel titulo = new JLabel("Historial de Pagos");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Tabla de pagos
        String[] columnas = {"ID", "Fecha", "Monto", "Método", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPagos = new JTable(modelo);
        tablaPagos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaPagos.setRowHeight(25);
        tablaPagos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPagos = new JScrollPane(tablaPagos);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnRealizarPago = new JButton("Realizar Pago");
        btnRealizarPago.setBackground(new Color(40, 167, 69));
        btnRealizarPago.setForeground(Color.WHITE);
        btnRealizarPago.setFont(new Font("Arial", Font.BOLD, 12));
        btnRealizarPago.addActionListener(e -> realizarPago());
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarPagos());
        
        panelBotones.add(btnActualizar);
        panelBotones.add(btnRealizarPago);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPagos, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelMantenimiento() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Título
        JLabel titulo = new JLabel("Solicitar Mantenimiento");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Formulario
        JPanel panelForm = new JPanel(new BorderLayout(10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Nueva Solicitud"));
        
        JLabel lblInstrucciones = new JLabel("Describe el problema o solicitud de mantenimiento:");
        txtMantenimiento = new JTextArea(8, 40);
        txtMantenimiento.setLineWrap(true);
        txtMantenimiento.setWrapStyleWord(true);
        txtMantenimiento.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollTexto = new JScrollPane(txtMantenimiento);
        
        panelForm.add(lblInstrucciones, BorderLayout.NORTH);
        panelForm.add(scrollTexto, BorderLayout.CENTER);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnSolicitarMantenimiento = new JButton("Enviar Solicitud");
        btnSolicitarMantenimiento.setBackground(new Color(0, 123, 255));
        btnSolicitarMantenimiento.setForeground(Color.WHITE);
        btnSolicitarMantenimiento.setFont(new Font("Arial", Font.BOLD, 12));
        btnSolicitarMantenimiento.addActionListener(e -> solicitarMantenimiento());
        
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> txtMantenimiento.setText(""));
        
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnSolicitarMantenimiento);
        
        // Historial
        JPanel panelHistorial = new JPanel(new BorderLayout());
        panelHistorial.setBorder(BorderFactory.createTitledBorder("Mis Solicitudes"));
        
        String[] columnas = {"ID", "Fecha", "Estado", "Descripción"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaMantenimiento = new JTable(modelo);
        tablaMantenimiento.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaMantenimiento.setRowHeight(25);
        JScrollPane scrollHistorial = new JScrollPane(tablaMantenimiento);
        panelHistorial.add(scrollHistorial, BorderLayout.CENTER);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelForm, panelHistorial);
        splitPane.setDividerLocation(250);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(splitPane, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelNotificaciones() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titulo = new JLabel("Mis Notificaciones");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Información de ayuda
        JLabel lblInfo = new JLabel("💡 Doble clic en una notificación para ver el mensaje completo");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(100, 100, 100));
        
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.add(titulo, BorderLayout.WEST);
        panelTitulo.add(lblInfo, BorderLayout.EAST);
        
        // Cambiar columnas: Ahora mostramos "Asunto" en lugar de mensaje completo
        String[] columnas = {"Fecha", "Tipo", "Asunto"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaNotificaciones = new JTable(modelo);
        tablaNotificaciones.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaNotificaciones.setRowHeight(30);
        tablaNotificaciones.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Ajustar ancho de columnas
        tablaNotificaciones.getColumnModel().getColumn(0).setPreferredWidth(100); // Fecha
        tablaNotificaciones.getColumnModel().getColumn(1).setPreferredWidth(80);  // Tipo
        tablaNotificaciones.getColumnModel().getColumn(2).setPreferredWidth(400); // Asunto
        
        // Agregar listener para doble clic
        tablaNotificaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = tablaNotificaciones.getSelectedRow();
                    if (row >= 0) {
                        mostrarDetalleNotificacion(row);
                    }
                }
            }
        });
        
        JScrollPane scroll = new JScrollPane(tablaNotificaciones);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnVerDetalle = new JButton("Ver Detalle");
        btnVerDetalle.setBackground(new Color(0, 123, 255));
        btnVerDetalle.setForeground(Color.WHITE);
        btnVerDetalle.setFont(new Font("Arial", Font.BOLD, 12));
        btnVerDetalle.addActionListener(e -> {
            int row = tablaNotificaciones.getSelectedRow();
            if (row >= 0) {
                mostrarDetalleNotificacion(row);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Por favor selecciona una notificación de la tabla.", 
                    "Seleccionar Notificación", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarNotificaciones());
        
        panelBotones.add(btnActualizar);
        panelBotones.add(btnVerDetalle);
        
        panel.add(panelTitulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void cargarDatos() {
        cargarContratos();
        cargarPagos();
        cargarMantenimientos();
        cargarNotificaciones();
    }
    
    private void cargarContratos() {
        try {
            int arrendatarioId = SesionUsuario.getUsuarioID();
            JSONObject response = ApiClient.get(ApiClient.CONTRATOS_PORT, 
                "/api/contratos?arrendatarioId=" + arrendatarioId);
            
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
                        "Inmueble #" + contrato.optInt("inmuebleId", 0),
                        contrato.optString("fechaInicio", "N/A"),
                        contrato.optString("fechaFin", "N/A"),
                        contrato.optString("estado", "N/A")
                    };
                    modelo.addRow(fila);
                }
                
                if (contratos.length() == 0) {
                    JOptionPane.showMessageDialog(this, 
                        "No tienes contratos registrados actualmente.", 
                        "Sin Contratos", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar contratos: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void cargarPagos() {
        try {
            int arrendatarioId = SesionUsuario.getUsuarioID();
            // Usar filtro por arrendatario (no arrendatarioId)
            JSONObject response = ApiClient.get(ApiClient.PAGOS_PORT, 
                "/api/pagos?arrendatario=" + arrendatarioId);
            
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
                        pago.optString("fecha", "N/A"),
                        "$" + String.format("%.2f", pago.optDouble("valor", 0.0)),
                        pago.optString("metodoPago", "N/A"),
                        pago.optString("estado", "N/A")
                    };
                    modelo.addRow(fila);
                }
                
                if (pagos.length() == 0) {
                    JOptionPane.showMessageDialog(this, 
                        "No tienes pagos registrados.", 
                        "Sin Pagos", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar pagos: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void cargarMantenimientos() {
        try {
            int solicitanteId = SesionUsuario.getUsuarioID();
            JSONObject response = ApiClient.get(ApiClient.MANTENIMIENTO_PORT, 
                "/api/mantenimiento?solicitante=" + solicitanteId);
            
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
                        mant.optString("fechaSolicitud", "N/A"),
                        mant.optString("estado", "N/A"),
                        mant.optString("descripcion", "N/A")
                    };
                    modelo.addRow(fila);
                }
                
                if (mantenimientos.length() == 0) {
                    JOptionPane.showMessageDialog(this, 
                        "No tienes solicitudes de mantenimiento registradas.", 
                        "Sin Solicitudes", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar mantenimientos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void cargarNotificaciones() {
        try {
            String correo = SesionUsuario.getCorreo();
            
            // Obtener todas las notificaciones y filtrar por destinatario
            JSONObject response = ApiClient.get(ApiClient.NOTIFICACIONES_PORT, "/api/notificaciones");
            
            DefaultTableModel modelo = (DefaultTableModel) tablaNotificaciones.getModel();
            modelo.setRowCount(0);
            
            // Inicializar array para guardar datos completos
            notificacionesData = new JSONArray();
            
            if (response.getInt("statusCode") == 200) {
                Object dataObj = response.get("data");
                JSONArray todasNotificaciones;
                
                if (dataObj instanceof JSONArray) {
                    todasNotificaciones = (JSONArray) dataObj;
                } else {
                    todasNotificaciones = new JSONArray();
                    todasNotificaciones.put(dataObj);
                }
                
                int conteo = 0;
                for (int i = 0; i < todasNotificaciones.length(); i++) {
                    JSONObject notif = todasNotificaciones.getJSONObject(i);
                    String destinatario = notif.optString("destinatario", "");
                    
                    // Filtrar solo las notificaciones del usuario actual
                    if (destinatario.equals(correo)) {
                        // Guardar notificación completa
                        notificacionesData.put(notif);
                        
                        String fechaEnvio = notif.optString("fechaEnvio", "");
                        if (fechaEnvio.isEmpty() || fechaEnvio.equals("null")) {
                            fechaEnvio = notif.optString("fechaCreacion", "N/A");
                        }
                        
                        // Formatear fecha
                        String fechaFormateada = fechaEnvio;
                        if (fechaEnvio.length() > 10) {
                            fechaFormateada = fechaEnvio.substring(0, 10);
                        }
                        
                        // Mostrar solo asunto en la tabla (sin mensaje)
                        Object[] fila = {
                            fechaFormateada,
                            notif.optString("tipo", "N/A"),
                            notif.optString("asunto", "Sin asunto")
                        };
                        modelo.addRow(fila);
                        conteo++;
                    }
                }
                
                System.out.println("✅ Notificaciones cargadas: " + conteo + " de " + todasNotificaciones.length());
            } else {
                System.out.println("⚠️ No hay notificaciones disponibles (Código: " + response.getInt("statusCode") + ")");
            }
        } catch (Exception e) {
            System.err.println("❌ Error al cargar notificaciones: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void mostrarDetalleNotificacion(int row) {
        try {
            if (notificacionesData == null || row >= notificacionesData.length()) {
                JOptionPane.showMessageDialog(this, 
                    "No se pudo cargar la notificación.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JSONObject notif = notificacionesData.getJSONObject(row);
            
            // Extraer información
            String asunto = notif.optString("asunto", "Sin asunto");
            String mensaje = notif.optString("mensaje", "Sin mensaje");
            String tipo = notif.optString("tipo", "N/A");
            String estado = notif.optString("estado", "N/A");
            String fechaCreacion = notif.optString("fechaCreacion", "N/A");
            String fechaEnvio = notif.optString("fechaEnvio", "");
            
            if (fechaEnvio.isEmpty() || fechaEnvio.equals("null")) {
                fechaEnvio = fechaCreacion;
            }
            
            // Formatear fechas
            if (fechaCreacion.length() > 19) {
                fechaCreacion = fechaCreacion.substring(0, 19).replace("T", " ");
            }
            if (fechaEnvio.length() > 19) {
                fechaEnvio = fechaEnvio.substring(0, 19).replace("T", " ");
            }
            
            // Crear diálogo personalizado
            JDialog dialogo = new JDialog(this, "Detalle de Notificación", true);
            dialogo.setSize(600, 500);
            dialogo.setLocationRelativeTo(this);
            
            JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panelPrincipal.setBackground(Color.WHITE);
            
            // Header con ícono según tipo
            JPanel panelHeader = new JPanel(new BorderLayout());
            panelHeader.setBackground(getTipoColor(tipo));
            panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            JLabel lblIcono = new JLabel(getTipoIcono(tipo));
            lblIcono.setFont(new Font("Arial", Font.PLAIN, 48));
            lblIcono.setForeground(Color.WHITE);
            
            JLabel lblAsunto = new JLabel("<html><b>" + asunto + "</b></html>");
            lblAsunto.setFont(new Font("Arial", Font.BOLD, 16));
            lblAsunto.setForeground(Color.WHITE);
            
            JPanel panelTextoHeader = new JPanel(new BorderLayout());
            panelTextoHeader.setOpaque(false);
            panelTextoHeader.add(lblAsunto, BorderLayout.NORTH);
            
            panelHeader.add(lblIcono, BorderLayout.WEST);
            panelHeader.add(panelTextoHeader, BorderLayout.CENTER);
            
            // Panel de información
            JPanel panelInfo = new JPanel(new GridLayout(0, 2, 10, 10));
            panelInfo.setBackground(Color.WHITE);
            panelInfo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
            
            panelInfo.add(crearLabelBold("Tipo:"));
            panelInfo.add(new JLabel(tipo));
            
            panelInfo.add(crearLabelBold("Estado:"));
            JLabel lblEstado = new JLabel(estado);
            lblEstado.setForeground(estado.equals("ENVIADO") ? new Color(40, 167, 69) : new Color(220, 53, 69));
            panelInfo.add(lblEstado);
            
            panelInfo.add(crearLabelBold("Fecha Creación:"));
            panelInfo.add(new JLabel(fechaCreacion));
            
            panelInfo.add(crearLabelBold("Fecha Envío:"));
            panelInfo.add(new JLabel(fechaEnvio));
            
            // Panel de mensaje
            JPanel panelMensaje = new JPanel(new BorderLayout(10, 10));
            panelMensaje.setBackground(Color.WHITE);
            
            JLabel lblMensajeTitulo = crearLabelBold("Mensaje:");
            
            JTextArea txtMensaje = new JTextArea(mensaje);
            txtMensaje.setFont(new Font("Arial", Font.PLAIN, 13));
            txtMensaje.setLineWrap(true);
            txtMensaje.setWrapStyleWord(true);
            txtMensaje.setEditable(false);
            txtMensaje.setBackground(new Color(245, 245, 245));
            txtMensaje.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JScrollPane scrollMensaje = new JScrollPane(txtMensaje);
            scrollMensaje.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            
            panelMensaje.add(lblMensajeTitulo, BorderLayout.NORTH);
            panelMensaje.add(scrollMensaje, BorderLayout.CENTER);
            
            // Botón cerrar
            JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelBoton.setBackground(Color.WHITE);
            
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
            btnCerrar.addActionListener(e -> dialogo.dispose());
            panelBoton.add(btnCerrar);
            
            // Ensamblar
            JPanel panelCentro = new JPanel(new BorderLayout(0, 15));
            panelCentro.setBackground(Color.WHITE);
            panelCentro.add(panelInfo, BorderLayout.NORTH);
            panelCentro.add(panelMensaje, BorderLayout.CENTER);
            
            panelPrincipal.add(panelHeader, BorderLayout.NORTH);
            panelPrincipal.add(panelCentro, BorderLayout.CENTER);
            panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
            
            dialogo.add(panelPrincipal);
            dialogo.setVisible(true);
            
            // Marcar como leída (cambiar color de la fila)
            int notifId = notif.optInt("id", -1);
            if (notifId != -1 && !notificacionesLeidas.contains(notifId)) {
                notificacionesLeidas.add(notifId);
                // Actualizar el color de la fila en la tabla
                tablaNotificaciones.setRowSelectionInterval(row, row);
                tablaNotificaciones.repaint();
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al mostrar detalle: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private JLabel crearLabelBold(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }
    
    private Color getTipoColor(String tipo) {
        switch (tipo.toUpperCase()) {
            case "EMAIL":
                return new Color(0, 123, 255);
            case "SMS":
                return new Color(40, 167, 69);
            case "WHATSAPP":
                return new Color(37, 211, 102);
            default:
                return new Color(108, 117, 125);
        }
    }
    
    private String getTipoIcono(String tipo) {
        switch (tipo.toUpperCase()) {
            case "EMAIL":
                return "✉";
            case "SMS":
                return "📱";
            case "WHATSAPP":
                return "💬";
            default:
                return "🔔";
        }
    }
    
    private void realizarPago() {
        // Obtener ID del contrato del usuario actual
        int selectedRow = tablaContratos.getSelectedRow();
        int contratoId = 1; // Por defecto usar el contrato que existe
        
        if (selectedRow >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) tablaContratos.getModel();
            contratoId = (int) modelo.getValueAt(selectedRow, 0);
        }
        
        // Diálogo para registrar un nuevo pago
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField txtValor = new JTextField("1234");
        JComboBox<String> comboMetodo = new JComboBox<>(new String[]{"EFECTIVO", "TRANSFERENCIA", "TARJETA_CREDITO", "TARJETA_DEBITO"});
        JTextField txtContratoId = new JTextField(String.valueOf(contratoId));
        JTextField txtFecha = new JTextField("2025-10-05");
        JTextField txtMes = new JTextField("2025-10-01");
        
        panel.add(new JLabel("Valor del Pago:"));
        panel.add(txtValor);
        panel.add(new JLabel("Método de Pago:"));
        panel.add(comboMetodo);
        panel.add(new JLabel("ID Contrato:"));
        panel.add(txtContratoId);
        panel.add(new JLabel("Fecha (YYYY-MM-DD):"));
        panel.add(txtFecha);
        panel.add(new JLabel("Mes Correspondiente (YYYY-MM-DD):"));
        panel.add(txtMes);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Registrar Nuevo Pago", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                JSONObject pagoData = new JSONObject();
                pagoData.put("contratoId", Integer.parseInt(txtContratoId.getText()));
                pagoData.put("arrendatarioId", SesionUsuario.getUsuarioID());
                pagoData.put("valor", Double.parseDouble(txtValor.getText()));
                pagoData.put("fecha", txtFecha.getText());
                pagoData.put("mesCorrespondiente", txtMes.getText());
                pagoData.put("metodoPago", comboMetodo.getSelectedItem().toString());
                pagoData.put("tipo", "ARRIENDO");
                pagoData.put("estado", "PAGADO");
                
                JSONObject response = ApiClient.post(ApiClient.PAGOS_PORT, "/api/pagos", pagoData);
                
                int statusCode = response.getInt("statusCode");
                
                if (statusCode == 201 || statusCode == 200) {
                    JOptionPane.showMessageDialog(this, 
                        "¡Pago registrado exitosamente!\n\n" +
                        "El propietario podrá ver este pago en su panel.", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    cargarPagos();
                } else {
                    Object dataObj = response.opt("data");
                    String errorMsg = "Error al registrar el pago (Código: " + statusCode + ")";
                    
                    if (dataObj instanceof JSONObject) {
                        JSONObject errorData = (JSONObject) dataObj;
                        if (errorData.has("mensaje")) {
                            errorMsg = errorData.getString("mensaje");
                        } else if (errorData.has("error")) {
                            errorMsg = errorData.getString("error");
                        }
                    }
                    
                    JOptionPane.showMessageDialog(this, 
                        errorMsg + "\n\nVerifica que el contrato exista y los datos sean correctos.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error: " + e.getMessage() + "\n\nVerifica que todos los campos estén correctos.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void solicitarMantenimiento() {
        String descripcion = txtMantenimiento.getText().trim();
        
        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor describe el problema.", 
                "Campo Vacío", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Obtener inmueble ID del contrato (usar 3 que es el inmueble existente)
            int inmuebleId = 3;
            
            // Preguntar ID del inmueble
            String inputInmueble = JOptionPane.showInputDialog(this, 
                "Ingresa el ID del inmueble:\n(Verifica el ID en tu contrato)", 
                "3");
            
            if (inputInmueble != null && !inputInmueble.isEmpty()) {
                inmuebleId = Integer.parseInt(inputInmueble);
            }
            
            JSONObject mantenimientoData = new JSONObject();
            mantenimientoData.put("inmuebleId", inmuebleId);
            mantenimientoData.put("solicitanteId", SesionUsuario.getUsuarioID());
            mantenimientoData.put("titulo", "Solicitud de mantenimiento");
            mantenimientoData.put("descripcion", descripcion);
            mantenimientoData.put("tipo", "CORRECTIVO");
            mantenimientoData.put("prioridad", "MEDIA");
            
            JSONObject response = ApiClient.post(ApiClient.MANTENIMIENTO_PORT, 
                "/api/mantenimiento", mantenimientoData);
            
            int statusCode = response.getInt("statusCode");
            
            if (statusCode == 201 || statusCode == 200) {
                JOptionPane.showMessageDialog(this, 
                    "¡Solicitud de mantenimiento enviada exitosamente!\n\n" +
                    "El propietario y administrador recibirán la notificación.", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                txtMantenimiento.setText("");
                cargarMantenimientos();
            } else {
                Object dataObj = response.opt("data");
                String errorMsg = "Error al enviar la solicitud (Código: " + statusCode + ")";
                
                if (dataObj instanceof JSONObject) {
                    JSONObject errorData = (JSONObject) dataObj;
                    if (errorData.has("mensaje")) {
                        errorMsg = errorData.getString("mensaje");
                    } else if (errorData.has("error")) {
                        errorMsg = errorData.getString("error");
                    }
                }
                
                JOptionPane.showMessageDialog(this, 
                    errorMsg + "\n\nVerifica que el inmueble exista.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro que deseas cerrar sesión?", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            SesionUsuario.cerrarSesion();
            this.dispose();
            // Reabrir ventana de login
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
    
    private JPanel crearPanelPerfil() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        JLabel titulo = new JLabel("Mi Perfil");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        JPanel panelDatos = new JPanel(new GridLayout(10, 2, 15, 15));
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
        txtCorreo.setEnabled(false); // No se puede editar el correo
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
        JLabel lblRolValor = new JLabel(SesionUsuario.getRol());
        lblRolValor.setForeground(new Color(0, 123, 255));
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
        
        // Cargar datos actuales del usuario
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
        btnGuardar.setBackground(new Color(40, 167, 69));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGuardar.setPreferredSize(new Dimension(150, 35));
        
        btnGuardar.addActionListener(e -> {
            try {
                JSONObject datos = new JSONObject();
                datos.put("nombre", txtNombre.getText());
                datos.put("apellido", txtApellido.getText());
                datos.put("telefono", txtTelefono.getText());
                datos.put("direccion", txtDireccion.getText());
                datos.put("cedula", txtCedula.getText());
                datos.put("tipoUsuario", SesionUsuario.getRol());
                datos.put("correo", SesionUsuario.getCorreo());
                
                // Nota: El servicio de usuarios no tiene PUT completo aún
                // Mostramos mensaje informativo por ahora
                JOptionPane.showMessageDialog(this,
                    "Los cambios se han guardado localmente.\n\n" +
                    "Nota: El backend requiere implementar el endpoint PUT /api/usuarios/{id}\n" +
                    "para persistir estos cambios en la base de datos.",
                    "Cambios Guardados",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Actualizar sesión local
                SesionUsuario.setNombre(txtNombre.getText());
                SesionUsuario.setApellido(txtApellido.getText());
                lblBienvenida.setText("Bienvenido, " + SesionUsuario.getNombre());
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error al guardar cambios: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton btnCambiarPassword = new JButton("Cambiar Contraseña");
        btnCambiarPassword.setBackground(new Color(255, 193, 7));
        btnCambiarPassword.setForeground(Color.BLACK);
        btnCambiarPassword.setFont(new Font("Arial", Font.BOLD, 13));
        btnCambiarPassword.setPreferredSize(new Dimension(180, 35));
        
        btnCambiarPassword.addActionListener(e -> {
            JPasswordField txtPasswordActual = new JPasswordField();
            JPasswordField txtPasswordNuevo = new JPasswordField();
            JPasswordField txtPasswordConfirmar = new JPasswordField();
            
            Object[] message = {
                "Contraseña Actual:", txtPasswordActual,
                "Nueva Contraseña:", txtPasswordNuevo,
                "Confirmar Contraseña:", txtPasswordConfirmar
            };
            
            int option = JOptionPane.showConfirmDialog(this, message, "Cambiar Contraseña", 
                JOptionPane.OK_CANCEL_OPTION);
            
            if (option == JOptionPane.OK_OPTION) {
                String actual = new String(txtPasswordActual.getPassword());
                String nuevo = new String(txtPasswordNuevo.getPassword());
                String confirmar = new String(txtPasswordConfirmar.getPassword());
                
                if (nuevo.length() < 6) {
                    JOptionPane.showMessageDialog(this,
                        "La contraseña debe tener al menos 6 caracteres.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!nuevo.equals(confirmar)) {
                    JOptionPane.showMessageDialog(this,
                        "Las contraseñas no coinciden.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                JOptionPane.showMessageDialog(this,
                    "Funcionalidad de cambio de contraseña en desarrollo.\n\n" +
                    "Requiere implementar endpoint en el backend.",
                    "En Desarrollo",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCambiarPassword);
        
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
            java.util.logging.Logger.getLogger(frmarrendatario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
                frmarrendatario dialog = new frmarrendatario(new javax.swing.JFrame(), true);
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
