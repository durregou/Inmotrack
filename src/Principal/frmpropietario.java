package Principal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.math.BigDecimal;

/**
 * Panel de Propietario
 * @author David Urrego
 */
public class frmpropietario extends javax.swing.JDialog {

    private JTabbedPane tabbedPane;
    private JTable tablaInmuebles;
    private JTable tablaContratos;
    private JTable tablaPagos;
    private JButton btnRegistrarInmueble;
    private JButton btnCrearContrato;
    private JLabel lblBienvenida;
    
    // Labels para estadísticas dinámicas
    private JLabel lblTotalInmuebles;
    private JLabel lblInmueblesDisponibles;
    private JLabel lblInmueblesArrendados;
    private JLabel lblTotalRecibido;
    
    public frmpropietario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        cargarDatos();
    }

    private void initComponents() {
        setTitle("Panel de Propietario - " + SesionUsuario.getNombre());
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(240, 240, 240));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Header
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(40, 167, 69));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        lblBienvenida = new JLabel("Bienvenido, " + SesionUsuario.getNombre());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        lblBienvenida.setForeground(Color.WHITE);
        
        JLabel lblRol = new JLabel("Propietario - ID: " + SesionUsuario.getUsuarioID());
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
        
        // Tab 1: Mis Inmuebles
        tabbedPane.addTab("MIS INMUEBLES", crearPanelInmuebles());
        
        // Tab 2: Contratos
        tabbedPane.addTab("CONTRATOS", crearPanelContratos());
        
        // Tab 3: Pagos Recibidos
        tabbedPane.addTab("PAGOS RECIBIDOS", crearPanelPagos());
        
        // Tab 4: Reportes
        tabbedPane.addTab("REPORTES", crearPanelReportes());
        
        // Tab 5: Mi Perfil
        tabbedPane.addTab("MI PERFIL", crearPanelPerfil());
        
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(tabbedPane, BorderLayout.CENTER);
        
        getContentPane().add(panelPrincipal);
        pack();
    }
    
    private JPanel crearPanelInmuebles() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Título y descripción
        JPanel panelTitulo = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Inmuebles");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel descripcion = new JLabel("Administra tus propiedades en arriendo");
        descripcion.setFont(new Font("Arial", Font.PLAIN, 12));
        descripcion.setForeground(Color.GRAY);
        panelTitulo.add(titulo, BorderLayout.NORTH);
        panelTitulo.add(descripcion, BorderLayout.CENTER);
        
        // Tabla de inmuebles
        String[] columnas = {"ID", "Tipo", "Dirección", "Ciudad", "Precio Arriendo", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaInmuebles = new JTable(modelo);
        tablaInmuebles.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaInmuebles.setRowHeight(30);
        tablaInmuebles.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaInmuebles.getTableHeader().setBackground(new Color(40, 167, 69));
        tablaInmuebles.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollInmuebles = new JScrollPane(tablaInmuebles);
        
        // Panel de estadísticas
        JPanel panelStats = new JPanel(new GridLayout(1, 3, 10, 0));
        panelStats.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Inicializar los labels de estadísticas
        lblTotalInmuebles = new JLabel("0");
        lblInmueblesDisponibles = new JLabel("0");
        lblInmueblesArrendados = new JLabel("0");
        
        panelStats.add(crearTarjetaEstadistica("Total Inmuebles", lblTotalInmuebles, new Color(0, 123, 255)));
        panelStats.add(crearTarjetaEstadistica("Disponibles", lblInmueblesDisponibles, new Color(40, 167, 69)));
        panelStats.add(crearTarjetaEstadistica("Arrendados", lblInmueblesArrendados, new Color(255, 193, 7)));
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
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
        
        btnRegistrarInmueble = new JButton("Registrar Inmueble");
        btnRegistrarInmueble.setBackground(new Color(40, 167, 69));
        btnRegistrarInmueble.setForeground(Color.WHITE);
        btnRegistrarInmueble.setFont(new Font("Arial", Font.BOLD, 12));
        btnRegistrarInmueble.addActionListener(e -> registrarInmueble());
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> {
            cargarEstadisticas();
            cargarInmuebles();
        });
        
        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRegistrarInmueble);
        panelBotones.add(btnActualizar);
        
        // Ensamblar panel
        JPanel panelContenido = new JPanel(new BorderLayout(0, 10));
        panelContenido.add(panelStats, BorderLayout.NORTH);
        panelContenido.add(scrollInmuebles, BorderLayout.CENTER);
        
        panel.add(panelTitulo, BorderLayout.NORTH);
        panel.add(panelContenido, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearTarjetaEstadistica(String titulo, JLabel lblValor, Color color) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(color);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 11));
        
        // Configurar el label que se pasa como parámetro
        lblValor.setForeground(Color.WHITE);
        lblValor.setFont(new Font("Arial", Font.BOLD, 24));
        
        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(lblValor, BorderLayout.CENTER);
        
        return tarjeta;
    }
    
    private JPanel crearPanelContratos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titulo = new JLabel("Contratos de Arrendamiento");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Tabla de contratos
        String[] columnas = {"ID", "Inmueble", "Arrendatario", "Fecha Inicio", "Fecha Fin", "Valor", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaContratos = new JTable(modelo);
        tablaContratos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaContratos.setRowHeight(30);
        tablaContratos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollContratos = new JScrollPane(tablaContratos);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnVerDetalle = new JButton("Ver Detalle");
        btnVerDetalle.setBackground(new Color(0, 123, 255));
        btnVerDetalle.setForeground(Color.WHITE);
        btnVerDetalle.setFont(new Font("Arial", Font.BOLD, 11));
        btnVerDetalle.addActionListener(e -> verDetalleContrato());
        
        JButton btnFinalizarContrato = new JButton("Finalizar Contrato");
        btnFinalizarContrato.setBackground(new Color(220, 53, 69));
        btnFinalizarContrato.setForeground(Color.WHITE);
        btnFinalizarContrato.setFont(new Font("Arial", Font.BOLD, 11));
        btnFinalizarContrato.addActionListener(e -> finalizarContrato());
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarContratos());
        
        btnCrearContrato = new JButton("Crear Contrato");
        btnCrearContrato.setBackground(new Color(0, 123, 255));
        btnCrearContrato.setForeground(Color.WHITE);
        btnCrearContrato.setFont(new Font("Arial", Font.BOLD, 12));
        btnCrearContrato.addActionListener(e -> crearContrato());
        
        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnFinalizarContrato);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCrearContrato);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollContratos, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelPagos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titulo = new JLabel("Pagos Recibidos");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Tabla de pagos
        String[] columnas = {"ID", "Contrato", "Fecha", "Monto", "Método", "Estado"};
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
        
        // Panel de totales
        JPanel panelTotales = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotalRecibido = new JLabel("Total Recibido: $0.00");
        lblTotalRecibido.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalRecibido.setForeground(new Color(40, 167, 69));
        panelTotales.add(lblTotalRecibido);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarPagos());
        panelBotones.add(btnActualizar);
        
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(panelTotales, BorderLayout.NORTH);
        panelSur.add(panelBotones, BorderLayout.SOUTH);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPagos, BorderLayout.CENTER);
        panel.add(panelSur, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titulo = new JLabel("Reportes y Estadísticas");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel panelCentro = new JPanel(new GridLayout(3, 2, 15, 15));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Botones de reportes
        JButton btnRentabilidad = crearBotonReporte("Reporte de Rentabilidad", 
            "Ver ingresos y gastos por periodo");
        btnRentabilidad.addActionListener(e -> generarReporteRentabilidad());
        
        JButton btnOcupacion = crearBotonReporte("Reporte de Ocupación", 
            "Ver tasa de ocupación de inmuebles");
        btnOcupacion.addActionListener(e -> generarReporteOcupacion());
        
        JButton btnPagos = crearBotonReporte("Reporte de Pagos", 
            "Histórico de pagos recibidos");
        btnPagos.addActionListener(e -> mostrarReportePagos());
        
        JButton btnMantenimiento = crearBotonReporte("Reporte de Mantenimiento", 
            "Costos y solicitudes de mantenimiento");
        btnMantenimiento.addActionListener(e -> mostrarReporteMantenimiento());
        
        JButton btnInmuebles = crearBotonReporte("Estado de Inmuebles", 
            "Resumen del estado de propiedades");
        btnInmuebles.addActionListener(e -> mostrarEstadoInmuebles());
        
        JButton btnExportar = crearBotonReporte("Exportar a Excel", 
            "Descargar reportes en Excel");
        btnExportar.addActionListener(e -> exportarReportes());
        
        panelCentro.add(btnRentabilidad);
        panelCentro.add(btnOcupacion);
        panelCentro.add(btnPagos);
        panelCentro.add(btnMantenimiento);
        panelCentro.add(btnInmuebles);
        panelCentro.add(btnExportar);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelCentro, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JButton crearBotonReporte(String titulo, String descripcion) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout(10, 5));
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblDesc = new JLabel("<html>" + descripcion + "</html>");
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 11));
        lblDesc.setForeground(Color.GRAY);
        
        JPanel panelTexto = new JPanel(new BorderLayout(0, 5));
        panelTexto.setOpaque(false);
        panelTexto.add(lblTitulo, BorderLayout.NORTH);
        panelTexto.add(lblDesc, BorderLayout.CENTER);
        
        btn.add(panelTexto, BorderLayout.CENTER);
        
        return btn;
    }
    
    private void cargarDatos() {
        cargarEstadisticas();
        cargarInmuebles();
        cargarContratos();
        cargarPagos();
    }
    
    private void cargarEstadisticas() {
        try {
            // Usar propietarioId=1 de la BD de propietarios
            int propietarioId = 1;
            
            JSONObject response = ApiClient.get(ApiClient.INMUEBLES_PORT, 
                "/api/inmuebles?propietarioId=" + propietarioId);
            
            int totalInmuebles = 0;
            int disponibles = 0;
            int arrendados = 0;
            
            if (response.getInt("statusCode") == 200) {
                Object dataObj = response.get("data");
                JSONArray inmuebles;
                
                if (dataObj instanceof JSONArray) {
                    inmuebles = (JSONArray) dataObj;
                } else {
                    inmuebles = new JSONArray();
                    inmuebles.put(dataObj);
                }
                
                totalInmuebles = inmuebles.length();
                
                // Contar disponibles y arrendados
                for (int i = 0; i < inmuebles.length(); i++) {
                    JSONObject inmueble = inmuebles.getJSONObject(i);
                    boolean esDisponible = inmueble.optBoolean("disponible", false);
                    
                    if (esDisponible) {
                        disponibles++;
                    } else {
                        arrendados++;
                    }
                }
            }
            
            // Actualizar los labels
            lblTotalInmuebles.setText(String.valueOf(totalInmuebles));
            lblInmueblesDisponibles.setText(String.valueOf(disponibles));
            lblInmueblesArrendados.setText(String.valueOf(arrendados));
            
            System.out.println("✅ Estadísticas actualizadas: Total=" + totalInmuebles + 
                             ", Disponibles=" + disponibles + ", Arrendados=" + arrendados);
            
        } catch (Exception e) {
            System.err.println("❌ Error al cargar estadísticas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void cargarInmuebles() {
        try {
            // Usar propietarioId=1 de la BD de propietarios
            int propietarioId = 1;
            JSONObject response = ApiClient.get(ApiClient.INMUEBLES_PORT, 
                "/api/inmuebles?propietarioId=" + propietarioId);
            
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
                        inmueble.optBoolean("disponible", false) ? "Disponible" : "Arrendado"
                    };
                    modelo.addRow(fila);
                }
                
                if (inmuebles.length() == 0) {
                    JOptionPane.showMessageDialog(this, 
                        "No tienes inmuebles registrados.\nHaz clic en 'Registrar Inmueble' para agregar uno.", 
                        "Sin Inmuebles", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar inmuebles: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void cargarContratos() {
        try {
            // Usar propietarioId=1 de la BD de propietarios
            int propietarioId = 1;
            JSONObject response = ApiClient.get(ApiClient.CONTRATOS_PORT, 
                "/api/contratos?propietarioId=" + propietarioId);
            
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
                        "Arrendatario #" + contrato.optInt("arrendatarioId", 0),
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
    
    private void cargarPagos() {
        try {
            // Cargar todos los pagos del contrato (usar contrato ID 1)
            int contratoId = 1;
            JSONObject response = ApiClient.get(ApiClient.PAGOS_PORT, 
                "/api/pagos?contrato=" + contratoId);
            
            DefaultTableModel modelo = (DefaultTableModel) tablaPagos.getModel();
            modelo.setRowCount(0);
            
            double totalRecibido = 0.0;
            
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
                    double valor = pago.optDouble("valor", 0.0);
                    
                    // Sumar solo pagos en estado PAGADO
                    if (pago.optString("estado", "").equals("PAGADO")) {
                        totalRecibido += valor;
                    }
                    
                    Object[] fila = {
                        pago.optInt("id", 0),
                        "Contrato #" + pago.optInt("contratoId", 0),
                        pago.optString("fecha", "N/A"),
                        "$" + String.format("%.2f", valor),
                        pago.optString("metodoPago", "N/A"),
                        pago.optString("estado", "N/A")
                    };
                    modelo.addRow(fila);
                }
            }
            
            // Actualizar el label del total
            lblTotalRecibido.setText("Total Recibido: $" + String.format("%.2f", totalRecibido));
            
        } catch (Exception e) {
            System.err.println("Error al cargar pagos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void registrarInmueble() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"APARTAMENTO", "CASA", "LOCAL", "OFICINA", "BODEGA"});
        JTextField txtDireccion = new JTextField();
        JTextField txtCiudad = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtHabitaciones = new JTextField("2");
        JTextField txtBanos = new JTextField("1");
        JTextArea txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setLineWrap(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        
        panel.add(new JLabel("Tipo de Inmueble:"));
        panel.add(comboTipo);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);
        panel.add(new JLabel("Ciudad:"));
        panel.add(txtCiudad);
        panel.add(new JLabel("Precio Arriendo:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Habitaciones:"));
        panel.add(txtHabitaciones);
        panel.add(new JLabel("Baños:"));
        panel.add(txtBanos);
        panel.add(new JLabel("Descripción:"));
        panel.add(scrollDesc);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Registrar Nuevo Inmueble", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            // Validaciones
            StringBuilder errores = new StringBuilder();
            
            Validador.noVacio(txtDireccion.getText(), "Dirección", errores);
            Validador.noVacio(txtCiudad.getText(), "Ciudad", errores);
            Validador.noVacio(txtPrecio.getText(), "Precio", errores);
            Validador.noVacio(txtHabitaciones.getText(), "Habitaciones", errores);
            Validador.noVacio(txtBanos.getText(), "Baños", errores);
            
            if (errores.length() == 0) {
                Validador.numeroPositivo(txtPrecio.getText(), "Precio", errores);
                Validador.enteroPositivo(txtHabitaciones.getText(), "Habitaciones", errores);
                Validador.enteroPositivo(txtBanos.getText(), "Baños", errores);
                Validador.longitudMinima(txtDescripcion.getText(), "Descripción", 10, errores);
                Validador.longitudMaxima(txtDescripcion.getText(), "Descripción", 500, errores);
            }
            
            if (errores.length() > 0) {
                JOptionPane.showMessageDialog(this,
                    "Por favor corrige los siguientes errores:\n\n" + errores.toString(),
                    "Errores de Validación",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                JSONObject inmuebleData = new JSONObject();
                // NOTA: Usar ID 1 que es el propietario en la BD de propietarios
                inmuebleData.put("propietarioId", 1);
                inmuebleData.put("tipo", comboTipo.getSelectedItem().toString());
                inmuebleData.put("direccion", txtDireccion.getText().trim());
                inmuebleData.put("ciudad", txtCiudad.getText().trim());
                inmuebleData.put("precioArriendo", Double.parseDouble(txtPrecio.getText().trim()));
                inmuebleData.put("habitaciones", Integer.parseInt(txtHabitaciones.getText().trim()));
                inmuebleData.put("banos", Integer.parseInt(txtBanos.getText().trim()));
                inmuebleData.put("descripcion", txtDescripcion.getText().trim());
                
                JSONObject response = ApiClient.post(ApiClient.INMUEBLES_PORT, "/api/inmuebles", inmuebleData);
                
                if (response.getInt("statusCode") == 201 || response.getInt("statusCode") == 200) {
                    JOptionPane.showMessageDialog(this, 
                        "✅ Inmueble registrado exitosamente!", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    cargarEstadisticas();
                    cargarInmuebles();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Error al registrar inmueble: " + response.optString("message", "Error desconocido"), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error en formato numérico: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void crearContrato() {
        // Primero mostrar información de usuarios disponibles
        try {
            JSONObject usersResponse = ApiClient.get(ApiClient.USUARIOS_PORT, "/api/usuarios?tipo=ARRENDATARIO");
            
            if (usersResponse.getInt("statusCode") == 200) {
                Object dataObj = usersResponse.get("data");
                JSONArray usuarios;
                
                if (dataObj instanceof JSONArray) {
                    usuarios = (JSONArray) dataObj;
                } else {
                    usuarios = new JSONArray();
                    usuarios.put(dataObj);
                }
                
                // Crear lista de arrendatarios disponibles
                StringBuilder listaArrendatarios = new StringBuilder("Arrendatarios disponibles:\n\n");
                for (int i = 0; i < usuarios.length(); i++) {
                    JSONObject user = usuarios.getJSONObject(i);
                    listaArrendatarios.append(String.format("ID: %d - %s %s (%s)\n", 
                        user.getInt("id"),
                        user.getString("nombre"),
                        user.getString("apellido"),
                        user.getString("correo")));
                }
                
                JOptionPane.showMessageDialog(this, listaArrendatarios.toString(), 
                    "Arrendatarios Disponibles", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar arrendatarios: " + e.getMessage());
        }
        
        // Ahora mostrar el formulario
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Obtener lista de inmuebles del propietario
        DefaultTableModel modeloInmuebles = (DefaultTableModel) tablaInmuebles.getModel();
        String[] inmueblesDisponibles = new String[modeloInmuebles.getRowCount()];
        for (int i = 0; i < modeloInmuebles.getRowCount(); i++) {
            inmueblesDisponibles[i] = "ID " + modeloInmuebles.getValueAt(i, 0) + " - " + 
                                       modeloInmuebles.getValueAt(i, 2);
        }
        
        JComboBox<String> comboInmueble = new JComboBox<>(inmueblesDisponibles);
        JTextField txtArrendatarioId = new JTextField("7");
        JTextField txtFechaInicio = new JTextField("2025-01-01");
        JTextField txtFechaFin = new JTextField("2025-12-31");
        JTextField txtValor = new JTextField();
        
        panel.add(new JLabel("Seleccionar Inmueble:"));
        panel.add(comboInmueble);
        panel.add(new JLabel("ID Arrendatario:"));
        panel.add(txtArrendatarioId);
        panel.add(new JLabel("Fecha Inicio (YYYY-MM-DD):"));
        panel.add(txtFechaInicio);
        panel.add(new JLabel("Fecha Fin (YYYY-MM-DD):"));
        panel.add(txtFechaFin);
        panel.add(new JLabel("Valor Arriendo:"));
        panel.add(txtValor);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Crear Nuevo Contrato", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Extraer ID del inmueble seleccionado
                String seleccion = (String) comboInmueble.getSelectedItem();
                int inmuebleId = Integer.parseInt(seleccion.split(" ")[1]);
                
                JSONObject contratoData = new JSONObject();
                contratoData.put("inmuebleId", inmuebleId);
                // NOTA: Usar ID 1 que es el propietario en la BD de propietarios
                // El servicio de usuarios tiene ID diferente
                contratoData.put("propietarioId", 1);
                contratoData.put("arrendatarioId", Integer.parseInt(txtArrendatarioId.getText()));
                contratoData.put("fechaInicio", txtFechaInicio.getText());
                contratoData.put("fechaFin", txtFechaFin.getText());
                contratoData.put("valorArriendo", Double.parseDouble(txtValor.getText()));
                
                JSONObject response = ApiClient.post(ApiClient.CONTRATOS_PORT, "/api/contratos", contratoData);
                
                int statusCode = response.getInt("statusCode");
                
                if (statusCode == 201 || statusCode == 200) {
                    JOptionPane.showMessageDialog(this, 
                        "¡Contrato creado exitosamente!\n\n" +
                        "El arrendatario ahora puede:\n" +
                        "• Ver su contrato\n" +
                        "• Realizar pagos\n" +
                        "• Solicitar mantenimiento", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    cargarContratos();
                    cargarInmuebles();
                } else {
                    Object dataObj = response.opt("data");
                    String errorMsg = "Error al crear contrato (Código: " + statusCode + ")";
                    
                    if (dataObj instanceof JSONObject) {
                        JSONObject errorData = (JSONObject) dataObj;
                        if (errorData.has("mensaje")) {
                            errorMsg = errorData.getString("mensaje");
                        } else if (errorData.has("error")) {
                            errorMsg = errorData.getString("error");
                        }
                    }
                    
                    errorMsg += "\n\nNOTA: El microservicio valida que el arrendatario\n" +
                                "esté registrado en su base de datos interna.\n" +
                                "Usa IDs de arrendatarios existentes: 1, 2, 3, 4, 7";
                    
                    JOptionPane.showMessageDialog(this, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
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
    
    private void verDetalleInmueble() {
        int selectedRow = tablaInmuebles.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) tablaInmuebles.getModel();
            Object[] datos = new Object[modelo.getColumnCount()];
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                datos[i] = modelo.getValueAt(selectedRow, i);
            }
            
            String mensaje = String.format(
                "ID: %s\nTipo: %s\nDirección: %s\nCiudad: %s\nPrecio: %s\nEstado: %s",
                datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]
            );
            
            JOptionPane.showMessageDialog(this, mensaje, "Detalle del Inmueble", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un inmueble de la tabla", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void generarReporteRentabilidad() {
        try {
            int propietarioId = 1;
            
            // Obtener pagos recibidos
            JSONObject responsePagos = ApiClient.get(ApiClient.PAGOS_PORT, "/api/pagos");
            double totalIngresos = 0.0;
            int cantidadPagos = 0;
            
            if (responsePagos.getInt("statusCode") == 200) {
                Object dataObj = responsePagos.get("data");
                JSONArray pagos = (dataObj instanceof JSONArray) ? (JSONArray) dataObj : new JSONArray().put(dataObj);
                
                for (int i = 0; i < pagos.length(); i++) {
                    JSONObject pago = pagos.getJSONObject(i);
                    if (pago.optString("estado", "").equals("PAGADO")) {
                        totalIngresos += pago.optDouble("valor", 0.0);
                        cantidadPagos++;
                    }
                }
            }
            
            // Obtener mantenimientos (gastos)
            JSONObject responseMantenimiento = ApiClient.get(ApiClient.MANTENIMIENTO_PORT, "/api/mantenimiento");
            double totalGastos = 0.0;
            int cantidadMantenimientos = 0;
            
            if (responseMantenimiento.getInt("statusCode") == 200) {
                Object dataObj = responseMantenimiento.get("data");
                JSONArray mantenimientos = (dataObj instanceof JSONArray) ? (JSONArray) dataObj : new JSONArray().put(dataObj);
                
                for (int i = 0; i < mantenimientos.length(); i++) {
                    JSONObject mant = mantenimientos.getJSONObject(i);
                    totalGastos += mant.optDouble("costo", 0.0);
                    cantidadMantenimientos++;
                }
            }
            
            double utilidad = totalIngresos - totalGastos;
            double margenRentabilidad = totalIngresos > 0 ? (utilidad / totalIngresos) * 100 : 0;
            
            // Crear diálogo con reporte
            JDialog dialogo = new JDialog(this, "Reporte de Rentabilidad", true);
            dialogo.setSize(500, 400);
            dialogo.setLocationRelativeTo(this);
            
            JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panelPrincipal.setBackground(Color.WHITE);
            
            // Título
            JLabel lblTitulo = new JLabel("Reporte de Rentabilidad");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
            
            // Panel de datos
            JPanel panelDatos = new JPanel(new GridLayout(7, 2, 10, 15));
            panelDatos.setBackground(Color.WHITE);
            panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            
            panelDatos.add(crearLabelBold("Total Ingresos:"));
            JLabel lblIngresos = new JLabel(String.format("$%.2f", totalIngresos));
            lblIngresos.setForeground(new Color(40, 167, 69));
            lblIngresos.setFont(new Font("Arial", Font.BOLD, 14));
            panelDatos.add(lblIngresos);
            
            panelDatos.add(crearLabelBold("Pagos Recibidos:"));
            panelDatos.add(new JLabel(String.valueOf(cantidadPagos)));
            
            panelDatos.add(crearLabelBold("Total Gastos:"));
            JLabel lblGastos = new JLabel(String.format("$%.2f", totalGastos));
            lblGastos.setForeground(new Color(220, 53, 69));
            lblGastos.setFont(new Font("Arial", Font.BOLD, 14));
            panelDatos.add(lblGastos);
            
            panelDatos.add(crearLabelBold("Mantenimientos:"));
            panelDatos.add(new JLabel(String.valueOf(cantidadMantenimientos)));
            
            panelDatos.add(new JSeparator());
            panelDatos.add(new JSeparator());
            
            panelDatos.add(crearLabelBold("Utilidad Neta:"));
            JLabel lblUtilidad = new JLabel(String.format("$%.2f", utilidad));
            lblUtilidad.setForeground(utilidad >= 0 ? new Color(40, 167, 69) : new Color(220, 53, 69));
            lblUtilidad.setFont(new Font("Arial", Font.BOLD, 16));
            panelDatos.add(lblUtilidad);
            
            panelDatos.add(crearLabelBold("Margen:"));
            JLabel lblMargen = new JLabel(String.format("%.2f%%", margenRentabilidad));
            lblMargen.setFont(new Font("Arial", Font.BOLD, 14));
            panelDatos.add(lblMargen);
            
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
                "Error al generar reporte: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private JLabel crearLabelBold(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        return lbl;
    }
    
    private void generarReporteOcupacion() {
        try {
            int propietarioId = 1;
            
            // Obtener inmuebles
            JSONObject response = ApiClient.get(ApiClient.INMUEBLES_PORT, 
                "/api/inmuebles?propietarioId=" + propietarioId);
            
            int totalInmuebles = 0;
            int inmueblesOcupados = 0;
            int inmueblesDisponibles = 0;
            
            if (response.getInt("statusCode") == 200) {
                Object dataObj = response.get("data");
                JSONArray inmuebles = (dataObj instanceof JSONArray) ? (JSONArray) dataObj : new JSONArray().put(dataObj);
                
                totalInmuebles = inmuebles.length();
                
                for (int i = 0; i < inmuebles.length(); i++) {
                    JSONObject inmueble = inmuebles.getJSONObject(i);
                    if (inmueble.optBoolean("disponible", false)) {
                        inmueblesDisponibles++;
                    } else {
                        inmueblesOcupados++;
                    }
                }
            }
            
            double tasaOcupacion = totalInmuebles > 0 ? (inmueblesOcupados * 100.0 / totalInmuebles) : 0;
            
            // Crear diálogo
            JDialog dialogo = new JDialog(this, "Reporte de Ocupación", true);
            dialogo.setSize(450, 350);
            dialogo.setLocationRelativeTo(this);
            
            JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panelPrincipal.setBackground(Color.WHITE);
            
            JLabel lblTitulo = new JLabel("Reporte de Ocupación de Inmuebles");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
            
            JPanel panelDatos = new JPanel(new GridLayout(4, 2, 10, 15));
            panelDatos.setBackground(Color.WHITE);
            panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            
            panelDatos.add(crearLabelBold("Total Inmuebles:"));
            panelDatos.add(new JLabel(String.valueOf(totalInmuebles)));
            
            panelDatos.add(crearLabelBold("Inmuebles Ocupados:"));
            JLabel lblOcupados = new JLabel(String.valueOf(inmueblesOcupados));
            lblOcupados.setForeground(new Color(255, 193, 7));
            lblOcupados.setFont(new Font("Arial", Font.BOLD, 14));
            panelDatos.add(lblOcupados);
            
            panelDatos.add(crearLabelBold("Inmuebles Disponibles:"));
            JLabel lblDisponibles = new JLabel(String.valueOf(inmueblesDisponibles));
            lblDisponibles.setForeground(new Color(40, 167, 69));
            lblDisponibles.setFont(new Font("Arial", Font.BOLD, 14));
            panelDatos.add(lblDisponibles);
            
            panelDatos.add(crearLabelBold("Tasa de Ocupación:"));
            JLabel lblTasa = new JLabel(String.format("%.1f%%", tasaOcupacion));
            lblTasa.setFont(new Font("Arial", Font.BOLD, 16));
            lblTasa.setForeground(new Color(0, 123, 255));
            panelDatos.add(lblTasa);
            
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
                "Error al generar reporte: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void mostrarReportePagos() {
        try {
            // Obtener pagos del contrato
            int contratoId = 1;
            JSONObject response = ApiClient.get(ApiClient.PAGOS_PORT, "/api/pagos?contrato=" + contratoId);
            
            StringBuilder reporte = new StringBuilder();
            reporte.append("<html><body style='width: 400px; font-family: Arial;'>");
            reporte.append("<h2 style='color: #007bff;'>Histórico de Pagos Recibidos</h2>");
            reporte.append("<hr>");
            
            int totalPagos = 0;
            double totalMonto = 0.0;
            
            if (response.getInt("statusCode") == 200) {
                Object dataObj = response.get("data");
                JSONArray pagos = (dataObj instanceof JSONArray) ? (JSONArray) dataObj : new JSONArray().put(dataObj);
                
                totalPagos = pagos.length();
                
                for (int i = 0; i < pagos.length(); i++) {
                    JSONObject pago = pagos.getJSONObject(i);
                    double valor = pago.optDouble("valor", 0.0);
                    totalMonto += valor;
                    
                    reporte.append("<p><b>Pago #").append(pago.optInt("id", 0)).append("</b><br>");
                    reporte.append("Fecha: ").append(pago.optString("fecha", "N/A")).append("<br>");
                    reporte.append("Monto: <span style='color: green;'><b>$").append(String.format("%.2f", valor)).append("</b></span><br>");
                    reporte.append("Método: ").append(pago.optString("metodoPago", "N/A")).append("<br>");
                    reporte.append("Estado: <span style='color: ").append(
                        pago.optString("estado", "").equals("PAGADO") ? "green" : "orange"
                    ).append(";'>").append(pago.optString("estado", "N/A")).append("</span></p>");
                    reporte.append("<hr>");
                }
            }
            
            reporte.append("<h3>Resumen</h3>");
            reporte.append("<p><b>Total de pagos:</b> ").append(totalPagos).append("<br>");
            reporte.append("<b>Monto total:</b> <span style='color: green; font-size: 16px;'><b>$").append(String.format("%.2f", totalMonto)).append("</b></span></p>");
            reporte.append("</body></html>");
            
            JOptionPane.showMessageDialog(this, 
                new JLabel(reporte.toString()), 
                "Reporte de Pagos", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al generar reporte: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void mostrarReporteMantenimiento() {
        try {
            // Obtener solicitudes de mantenimiento
            JSONObject response = ApiClient.get(ApiClient.MANTENIMIENTO_PORT, "/api/mantenimiento");
            
            int totalSolicitudes = 0;
            int pendientes = 0;
            int enProceso = 0;
            int completadas = 0;
            double costoTotal = 0.0;
            
            if (response.getInt("statusCode") == 200) {
                Object dataObj = response.get("data");
                JSONArray mantenimientos = (dataObj instanceof JSONArray) ? (JSONArray) dataObj : new JSONArray().put(dataObj);
                
                totalSolicitudes = mantenimientos.length();
                
                for (int i = 0; i < mantenimientos.length(); i++) {
                    JSONObject mant = mantenimientos.getJSONObject(i);
                    String estado = mant.optString("estado", "");
                    
                    switch (estado) {
                        case "PENDIENTE":
                            pendientes++;
                            break;
                        case "EN_PROCESO":
                            enProceso++;
                            break;
                        case "COMPLETADO":
                            completadas++;
                            break;
                    }
                    
                    costoTotal += mant.optDouble("costo", 0.0);
                }
            }
            
            // Crear diálogo
            JDialog dialogo = new JDialog(this, "Reporte de Mantenimiento", true);
            dialogo.setSize(450, 400);
            dialogo.setLocationRelativeTo(this);
            
            JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panelPrincipal.setBackground(Color.WHITE);
            
            JLabel lblTitulo = new JLabel("Reporte de Mantenimiento");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
            
            JPanel panelDatos = new JPanel(new GridLayout(5, 2, 10, 15));
            panelDatos.setBackground(Color.WHITE);
            panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            
            panelDatos.add(crearLabelBold("Total Solicitudes:"));
            panelDatos.add(new JLabel(String.valueOf(totalSolicitudes)));
            
            panelDatos.add(crearLabelBold("Pendientes:"));
            JLabel lblPendientes = new JLabel(String.valueOf(pendientes));
            lblPendientes.setForeground(new Color(255, 193, 7));
            lblPendientes.setFont(new Font("Arial", Font.BOLD, 14));
            panelDatos.add(lblPendientes);
            
            panelDatos.add(crearLabelBold("En Proceso:"));
            JLabel lblProceso = new JLabel(String.valueOf(enProceso));
            lblProceso.setForeground(new Color(0, 123, 255));
            lblProceso.setFont(new Font("Arial", Font.BOLD, 14));
            panelDatos.add(lblProceso);
            
            panelDatos.add(crearLabelBold("Completadas:"));
            JLabel lblCompletadas = new JLabel(String.valueOf(completadas));
            lblCompletadas.setForeground(new Color(40, 167, 69));
            lblCompletadas.setFont(new Font("Arial", Font.BOLD, 14));
            panelDatos.add(lblCompletadas);
            
            panelDatos.add(crearLabelBold("Costo Total:"));
            JLabel lblCosto = new JLabel(String.format("$%.2f", costoTotal));
            lblCosto.setFont(new Font("Arial", Font.BOLD, 16));
            lblCosto.setForeground(new Color(220, 53, 69));
            panelDatos.add(lblCosto);
            
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
                "Error al generar reporte: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void mostrarEstadoInmuebles() {
        try {
            int propietarioId = 1;
            JSONObject response = ApiClient.get(ApiClient.INMUEBLES_PORT, 
                "/api/inmuebles?propietarioId=" + propietarioId);
            
            StringBuilder reporte = new StringBuilder();
            reporte.append("<html><body style='width: 450px; font-family: Arial;'>");
            reporte.append("<h2 style='color: #007bff;'>Estado de Inmuebles</h2>");
            reporte.append("<hr>");
            
            int totalInmuebles = 0;
            int disponibles = 0;
            int arrendados = 0;
            
            if (response.getInt("statusCode") == 200) {
                Object dataObj = response.get("data");
                JSONArray inmuebles = (dataObj instanceof JSONArray) ? (JSONArray) dataObj : new JSONArray().put(dataObj);
                
                totalInmuebles = inmuebles.length();
                
                for (int i = 0; i < inmuebles.length(); i++) {
                    JSONObject inmueble = inmuebles.getJSONObject(i);
                    boolean esDisponible = inmueble.optBoolean("disponible", false);
                    
                    if (esDisponible) {
                        disponibles++;
                    } else {
                        arrendados++;
                    }
                    
                    String estadoColor = esDisponible ? "green" : "orange";
                    String estadoTexto = esDisponible ? "Disponible" : "Arrendado";
                    
                    reporte.append("<div style='margin: 10px 0; padding: 10px; border: 1px solid #ddd; border-radius: 5px;'>");
                    reporte.append("<p><b>").append(inmueble.optString("tipo", "N/A")).append("</b> - ID: ").append(inmueble.optInt("id", 0)).append("<br>");
                    reporte.append("<b>Dirección:</b> ").append(inmueble.optString("direccion", "N/A")).append("<br>");
                    reporte.append("<b>Ciudad:</b> ").append(inmueble.optString("ciudad", "N/A")).append("<br>");
                    reporte.append("<b>Precio:</b> $").append(String.format("%.2f", inmueble.optDouble("precioArriendo", 0.0))).append("<br>");
                    reporte.append("<b>Estado:</b> <span style='color: ").append(estadoColor).append("; font-weight: bold;'>")
                           .append(estadoTexto).append("</span></p>");
                    reporte.append("</div>");
                }
            }
            
            reporte.append("<hr>");
            reporte.append("<h3>Resumen</h3>");
            reporte.append("<p><b>Total:</b> ").append(totalInmuebles).append(" inmuebles<br>");
            reporte.append("<span style='color: green;'><b>Disponibles:</b> ").append(disponibles).append("</span><br>");
            reporte.append("<span style='color: orange;'><b>Arrendados:</b> ").append(arrendados).append("</span></p>");
            reporte.append("</body></html>");
            
            JScrollPane scrollPane = new JScrollPane(new JLabel(reporte.toString()));
            scrollPane.setPreferredSize(new Dimension(500, 400));
            
            JOptionPane.showMessageDialog(this, 
                scrollPane, 
                "Estado de Inmuebles", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al generar reporte: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void exportarReportes() {
        String[] opciones = {
            "Reporte de Rentabilidad",
            "Reporte de Ocupación",
            "Reporte de Pagos",
            "Reporte de Mantenimiento",
            "Estado de Inmuebles"
        };
        
        String seleccion = (String) JOptionPane.showInputDialog(
            this,
            "Selecciona el reporte que deseas exportar:\n(Funcionalidad de exportación en desarrollo)",
            "Exportar a Excel",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );
        
        if (seleccion != null) {
            JOptionPane.showMessageDialog(this, 
                "Exportación de \"" + seleccion + "\" a Excel.\n\n" +
                "Esta funcionalidad estará disponible próximamente.\n" +
                "Por ahora, puedes copiar la información desde los reportes visuales.", 
                "Exportar a Excel", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // ============= GESTIÓN DE INMUEBLES =============
    
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
            
            JSONObject response = ApiClient.get(ApiClient.INMUEBLES_PORT, "/api/inmuebles/" + inmuebleId);
            
            if (response.getInt("statusCode") != 200) {
                JOptionPane.showMessageDialog(this,
                    "Error al obtener datos del inmueble.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JSONObject inmueble = response.getJSONObject("data");
            
            JDialog dialogo = new JDialog(this, "Editar Inmueble #" + inmuebleId, true);
            dialogo.setSize(500, 600);
            dialogo.setLocationRelativeTo(this);
            
            JPanel panelForm = new JPanel(new GridLayout(11, 2, 10, 10));
            panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
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
                    datos.put("propietarioId", inmueble.optInt("propietarioId", 1));
                    
                    JSONObject respuesta = ApiClient.put(ApiClient.INMUEBLES_PORT, 
                        "/api/inmuebles/" + inmuebleId, datos);
                    
                    if (respuesta.getInt("statusCode") == 200) {
                        JOptionPane.showMessageDialog(dialogo,
                            "Inmueble actualizado correctamente.",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                        dialogo.dispose();
                        cargarInmuebles();
                        cargarEstadisticas();
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
            String estado = (String) tablaInmuebles.getValueAt(selectedRow, 5);
            
            if (estado.equals("Arrendado")) {
                int confirm = JOptionPane.showConfirmDialog(this,
                    "⚠️ ADVERTENCIA: Este inmueble está actualmente ARRENDADO.\n\n" +
                    "Si lo eliminas, podrías afectar contratos activos.\n" +
                    "¿Estás seguro de que deseas continuar?",
                    "Inmueble Arrendado",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (confirm != JOptionPane.YES_OPTION) return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de eliminar el inmueble:\n" + 
                direccion + " (ID: " + inmuebleId + ")?\n\n" +
                "Esta acción no se puede deshacer.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) return;
            
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
            
            JDialog dialogo = new JDialog(this, "Detalle de Contrato #" + contratoId, true);
            dialogo.setSize(600, 600);
            dialogo.setLocationRelativeTo(this);
            
            JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panelPrincipal.setBackground(Color.WHITE);
            
            // Header con estado del contrato
            JPanel panelHeader = new JPanel(new BorderLayout());
            String estado = contrato.optString("estado", "N/A");
            Color colorEstado = estado.equals("ACTIVO") ? new Color(40, 167, 69) : 
                               estado.equals("FINALIZADO") ? new Color(108, 117, 125) : 
                               new Color(255, 193, 7);
            panelHeader.setBackground(colorEstado);
            panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            JLabel lblTitulo = new JLabel("Contrato de Arrendamiento");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            
            JLabel lblEstado = new JLabel("Estado: " + estado);
            lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
            lblEstado.setForeground(Color.WHITE);
            
            JPanel panelTextoHeader = new JPanel(new BorderLayout());
            panelTextoHeader.setOpaque(false);
            panelTextoHeader.add(lblTitulo, BorderLayout.NORTH);
            panelTextoHeader.add(lblEstado, BorderLayout.SOUTH);
            
            panelHeader.add(panelTextoHeader, BorderLayout.CENTER);
            
            // Panel de datos
            JPanel panelDatos = new JPanel(new GridLayout(11, 2, 10, 10));
            panelDatos.setBackground(Color.WHITE);
            panelDatos.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
            
            panelDatos.add(crearLabelBold("ID:"));
            panelDatos.add(new JLabel(String.valueOf(contrato.optInt("id", 0))));
            
            panelDatos.add(crearLabelBold("Inmueble ID:"));
            panelDatos.add(new JLabel(String.valueOf(contrato.optInt("inmuebleId", 0))));
            
            panelDatos.add(crearLabelBold("Arrendatario ID:"));
            panelDatos.add(new JLabel(String.valueOf(contrato.optInt("arrendatarioId", 0))));
            
            panelDatos.add(crearLabelBold("Fecha Inicio:"));
            String fechaInicio = contrato.optString("fechaInicio", "N/A");
            if (fechaInicio.length() > 10) fechaInicio = fechaInicio.substring(0, 10);
            panelDatos.add(new JLabel(fechaInicio));
            
            panelDatos.add(crearLabelBold("Fecha Fin:"));
            String fechaFin = contrato.optString("fechaFin", "N/A");
            if (fechaFin.length() > 10) fechaFin = fechaFin.substring(0, 10);
            panelDatos.add(new JLabel(fechaFin));
            
            panelDatos.add(crearLabelBold("Valor Arriendo:"));
            JLabel lblValor = new JLabel(String.format("$%,.2f", contrato.optDouble("valorArriendo", 0.0)));
            lblValor.setFont(new Font("Arial", Font.BOLD, 12));
            lblValor.setForeground(new Color(40, 167, 69));
            panelDatos.add(lblValor);
            
            panelDatos.add(crearLabelBold("Valor Administración:"));
            panelDatos.add(new JLabel(String.format("$%,.2f", contrato.optDouble("valorAdministracion", 0.0))));
            
            panelDatos.add(crearLabelBold("Depósito:"));
            panelDatos.add(new JLabel(String.format("$%,.2f", contrato.optDouble("deposito", 0.0))));
            
            panelDatos.add(crearLabelBold("Día de Pago:"));
            panelDatos.add(new JLabel(String.valueOf(contrato.optInt("diaPago", 0))));
            
            panelDatos.add(crearLabelBold("Fecha Creación:"));
            String fechaCreacion = contrato.optString("fechaCreacion", "N/A");
            if (fechaCreacion.length() > 19) fechaCreacion = fechaCreacion.substring(0, 19).replace("T", " ");
            panelDatos.add(new JLabel(fechaCreacion));
            
            panelDatos.add(crearLabelBold("Observaciones:"));
            JTextArea txtObs = new JTextArea(contrato.optString("observaciones", "N/A"));
            txtObs.setEditable(false);
            txtObs.setLineWrap(true);
            txtObs.setWrapStyleWord(true);
            txtObs.setBackground(panelDatos.getBackground());
            panelDatos.add(txtObs);
            
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
    
    private void finalizarContrato() {
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
            String estado = (String) tablaContratos.getValueAt(selectedRow, 6);
            
            if (estado.equals("FINALIZADO")) {
                JOptionPane.showMessageDialog(this,
                    "Este contrato ya está FINALIZADO.",
                    "Contrato Finalizado",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de finalizar el contrato #" + contratoId + "?\n\n" +
                "Esto marcará el inmueble como DISPONIBLE nuevamente.\n" +
                "Esta acción no se puede deshacer.",
                "Confirmar Finalización",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) return;
            
            JSONObject response = ApiClient.put(ApiClient.CONTRATOS_PORT, 
                "/api/contratos/" + contratoId + "/finalizar", new JSONObject());
            
            if (response.getInt("statusCode") == 200) {
                JOptionPane.showMessageDialog(this,
                    "Contrato #" + contratoId + " finalizado correctamente.\n" +
                    "El inmueble ahora está DISPONIBLE.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarContratos();
                cargarInmuebles();
                cargarEstadisticas();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al finalizar: " + response.optString("message", "Error desconocido"),
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
        
        JLabel titulo = new JLabel("Mi Perfil");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        
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
        JLabel lblRolValor = new JLabel(SesionUsuario.getRol());
        lblRolValor.setForeground(new Color(40, 167, 69));
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
        btnGuardar.setBackground(new Color(40, 167, 69));
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
            lblBienvenida.setText("Bienvenido, " + SesionUsuario.getNombre() + " " + SesionUsuario.getApellido());
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
            java.util.logging.Logger.getLogger(frmpropietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
                frmpropietario dialog = new frmpropietario(new javax.swing.JFrame(), true);
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
