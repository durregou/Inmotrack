
package Principal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import org.json.JSONObject;
/**
 *
 * @author David Urrego
 */
public class frmlogin extends javax.swing.JDialog {

    public static frmregistro fr;
    private frmadministrador adminWindow;
    private frmpropietario propietarioWindow;
    private frmarrendatario arrendatarioWindow;

    
    public frmlogin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        mejorarEstiloBotonRegistro();
        agregarBotonRecuperarContrasena();
        setLocationRelativeTo(null);
    }
    
    // Mejorar el estilo del botón de registro
    private void mejorarEstiloBotonRegistro() {
        // Botón de Registro - Estilo moderno y compacto
        jButton2.setText("Crear cuenta");
        jButton2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 11));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setBackground(new java.awt.Color(40, 167, 69)); // Verde
        jButton2.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(40, 167, 69), 1),
            javax.swing.BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        jButton2.setFocusPainted(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        // Botón de Iniciar Sesión - Estilo principal
        jButton1.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 13));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setBackground(new java.awt.Color(0, 123, 255)); // Azul
        jButton1.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 123, 255), 1),
            javax.swing.BorderFactory.createEmptyBorder(10, 30, 10, 30)
        ));
        jButton1.setFocusPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
    
    // Agregar botón de recuperar contraseña después del layout generado
    private void agregarBotonRecuperarContrasena() {
        // Crear botón con estilo de enlace
        javax.swing.JButton btnOlvideContrasena = new javax.swing.JButton();
        btnOlvideContrasena.setText("¿Olvidaste tu contraseña?");
        btnOlvideContrasena.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 11));
        btnOlvideContrasena.setForeground(new java.awt.Color(0, 102, 204));
        btnOlvideContrasena.setBorderPainted(false);
        btnOlvideContrasena.setContentAreaFilled(false);
        btnOlvideContrasena.setFocusPainted(false);
        btnOlvideContrasena.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        btnOlvideContrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recuperarContrasena();
            }
        });
        
        // Agregar efecto hover
        btnOlvideContrasena.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnOlvideContrasena.setForeground(new java.awt.Color(0, 50, 150));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnOlvideContrasena.setForeground(new java.awt.Color(0, 102, 204));
            }
        });
        
        // Agregar directamente al jPanel1 y actualizar el layout
        jPanel1.add(btnOlvideContrasena);
        
        // Obtener el layout actual y modificarlo para incluir el nuevo botón
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout) jPanel1.getLayout();
        
        // Actualizar el grupo vertical para agregar el botón debajo de jButton1
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtcontraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOlvideContrasena)
                .addGap(15, 15, 15))
        );
        
        // Actualizar el grupo horizontal para centrar el botón
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jButton2)
                .addGap(55, 55, 55)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(129, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(152, 152, 152))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnOlvideContrasena)
                        .addGap(120, 120, 120))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtusuario, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(txtcontraseña))
                        .addGap(95, 95, 95))))
        );
        
        // Revalidar y repintar
        jPanel1.revalidate();
        jPanel1.repaint();
        this.pack(); // Reajustar el tamaño del diálogo
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtusuario = new javax.swing.JTextField();
        txtcontraseña = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 255, 255));

        jButton2.setText("Registro");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario.png"))); // NOI18N

        jButton1.setText("Iniciar sesión");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Usuario");

        jLabel3.setText("Contraseña");

        txtusuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtusuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jButton2)
                .addGap(55, 55, 55)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(129, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(152, 152, 152))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtusuario, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(txtcontraseña))
                        .addGap(95, 95, 95))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtcontraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtusuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtusuarioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String usuario = txtusuario.getText();
        String contrasena = txtcontraseña.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese todos los campos.");
            return;
        }

        try {
            // Crear el JSON para el login
            JSONObject loginData = new JSONObject();
            loginData.put("correo", usuario);
            loginData.put("contrasena", contrasena);

            // Llamar al API de usuarios
            JSONObject response = ApiClient.post(ApiClient.USUARIOS_PORT, "/api/usuarios/login", loginData);
            
            int statusCode = response.getInt("statusCode");
            
            if (statusCode == 200) {
                JSONObject data = response.getJSONObject("data");
                
                // Obtener información del usuario
                String tipoUsuario = data.getString("tipoUsuario");
                int usuarioID = data.getInt("id");
                String nombre = data.optString("nombre", "Usuario");
                String apellido = data.optString("apellido", "");
                String nombreCompleto = nombre + " " + apellido;
                
                // Guardar sesión
                SesionUsuario.setUsuarioID(usuarioID);
                SesionUsuario.setNombre(nombre);
                SesionUsuario.setApellido(apellido);
                SesionUsuario.setCorreo(usuario);
                SesionUsuario.setRol(tipoUsuario);
                
                System.out.println("Usuario autenticado: " + nombreCompleto + " (ID: " + usuarioID + ", Rol: " + tipoUsuario + ")");
                
                // Abrir ventana correspondiente según el rol
                switch (tipoUsuario.toUpperCase()) {
                    case "ADMINISTRADOR":
                        adminWindow = new frmadministrador(null, true);
                        adminWindow.setVisible(true);
                        break;
                    case "PROPIETARIO":
                        propietarioWindow = new frmpropietario(null, true);
                        propietarioWindow.setVisible(true);
                        break;
                    case "ARRENDATARIO":
                        arrendatarioWindow = new frmarrendatario(null, true);
                        arrendatarioWindow.setVisible(true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Rol de usuario no reconocido: " + tipoUsuario);
                        return;
                }
                
                this.dispose(); // Cierra la ventana de login
                
            } else if (statusCode == 401) {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
            } else {
                Object dataObj = response.opt("data");
                String errorMsg = "Error al iniciar sesión. Código: " + statusCode;
                
                if (dataObj instanceof JSONObject) {
                    JSONObject errorData = (JSONObject) dataObj;
                    if (errorData.has("error")) {
                        errorMsg = errorData.getString("error");
                    } else if (errorData.has("mensaje")) {
                        errorMsg = errorData.getString("mensaje");
                    }
                }
                
                JOptionPane.showMessageDialog(this, errorMsg);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error de conexión con el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        fr = new frmregistro(null, true);
        fr.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    // Método para recuperar contraseña
    private void recuperarContrasena() {
        String correo = JOptionPane.showInputDialog(this, 
            "Ingresa tu correo electrónico registrado:\n(Recibirás un enlace de recuperación)", 
            "Recuperar Contraseña", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (correo == null || correo.trim().isEmpty()) {
            return; // Usuario canceló
        }
        
        correo = correo.trim();
        
        // Validar formato de correo
        if (!correo.contains("@") || !correo.contains(".")) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingresa un correo electrónico válido.", 
                "Correo Inválido", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Verificar si el usuario existe
            JSONObject verificarData = new JSONObject();
            verificarData.put("correo", correo);
            
            // Crear notificación de recuperación
            JSONObject notificacionData = new JSONObject();
            notificacionData.put("destinatario", correo);
            notificacionData.put("tipo", "EMAIL");
            notificacionData.put("asunto", "Recuperación de Contraseña");
            notificacionData.put("mensaje", 
                "Hemos recibido una solicitud para restablecer tu contraseña.\n\n" +
                "Para tu seguridad, por favor contacta al administrador del sistema con tu correo electrónico: " + correo + "\n\n" +
                "El administrador te ayudará a restablecer tu contraseña de forma segura.\n\n" +
                "Si no solicitaste este cambio, ignora este mensaje.\n\n" +
                "Equipo de Soporte - Sistema de Arrendamiento"
            );
            notificacionData.put("estado", "PENDIENTE");
            
            // Enviar notificación
            JSONObject response = ApiClient.post(ApiClient.NOTIFICACIONES_PORT, 
                "/api/notificaciones", notificacionData);
            
            int statusCode = response.getInt("statusCode");
            
            if (statusCode == 201 || statusCode == 200) {
                JOptionPane.showMessageDialog(this, 
                    "✓ Solicitud enviada exitosamente\n\n" +
                    "Se ha enviado un correo a: " + correo + "\n\n" +
                    "Por favor revisa tu bandeja de entrada y sigue las instrucciones.\n" +
                    "Si no recibes el correo en unos minutos, contacta al administrador.", 
                    "Correo Enviado", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se pudo enviar el correo de recuperación.\n\n" +
                    "Por favor contacta directamente al administrador del sistema.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al procesar la solicitud: " + e.getMessage() + "\n\n" +
                "Por favor contacta al administrador.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmlogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmlogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmlogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmlogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmlogin dialog = new frmlogin(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtcontraseña;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}
