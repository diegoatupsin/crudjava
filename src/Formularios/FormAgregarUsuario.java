package Formularios;

import Modelos.Empleados;
import Modelos.Usuarios;
import Paneles.AdministracionPanel;
import dao.EmpleadosDAO;
import dao.UsuariosDAO;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import org.mindrot.jbcrypt.BCrypt;
import utils.FormValidator;
import utils.InputFilter;

// jdialog parece ser la opcion para hacer forms
// cerrar jframes cuando son forms no funciona como deberia
public class FormAgregarUsuario extends javax.swing.JFrame {

    // esto es para que cuando se muestren los usuarios en el combo box en form se guarde el id del usuario seleccionado en la tabla.
    private Map<String, Integer> empleadosMap = new LinkedHashMap<>();
    private AdministracionPanel adminPanel;
    
    // parent es el mainframe
    // se necesita el objeto del admin panel anterior para poder actualizar los datos de el
    public FormAgregarUsuario(AdministracionPanel adminPanel) {
        this.adminPanel = adminPanel;
      
        initComponents();
        
        // aplicar el limite a los jtextfields
        ((AbstractDocument) txtfNombre.getDocument()).setDocumentFilter(new InputFilter(45, false, false));
        ((AbstractDocument) passPassword.getDocument()).setDocumentFilter(new InputFilter(60, false, false));
        
        Image icon = Toolkit.getDefaultToolkit().getImage(FormAgregarUsuario.class.getResource("/Imagenes/logoFlor.png"));
        setIconImage(icon);
        setTitle("Agregar usuario");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        cargarEmpleados();  
    }
    
    private void cargarEmpleados() {
        EmpleadosDAO empleadosDAO = new EmpleadosDAO();
        List<Empleados> lista = empleadosDAO.obtenerEmpleados();
        
        cbEmpleado.addItem("Selecciona un empleado"); // un placeholder para el lugar 0
        for (Empleados e : lista) {
            empleadosMap.put(e.getNombreEmp(), e.getIdEmpleados()); // mapea el nombre con el id nombre -- id
            cbEmpleado.addItem(e.getNombreEmp());
        }
        
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron empleados.");
        }
    }
    
    private void guardarUsuario() {
        Usuarios usuario = new Usuarios();

        String nombreUsuario = txtfNombre.getText().trim();
        String passwordPlano = new String(passPassword.getPassword());
        String tipoUsuario = ((String) cbTipoUsuario.getSelectedItem()).toLowerCase();
        String empleadoNombre = (String) cbEmpleado.getSelectedItem();

        if (nombreUsuario.isEmpty() || passwordPlano.length() < 4 || empleadoNombre == null) {
            JOptionPane.showMessageDialog(this, "Datos inválidos. Asegúrate de llenar todos los campos correctamente.");
            return;
        }

        int idEmpleado = empleadosMap.get(empleadoNombre);
    
        // verifica si ya hay un usuario asignado a ese empleado
        UsuariosDAO usuarioDAO = new UsuariosDAO();
        boolean yaAsignado = usuarioDAO.existeUsuarioPorEmpleado(idEmpleado);

        if (yaAsignado) {
            JOptionPane.showMessageDialog(this, "Este empleado ya tiene un usuario asignado.");
            return;
        }

        // Set basic info
        usuario.setNombreUser(nombreUsuario);
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setEmpleados_idEmpleados(idEmpleado);

        // Hash password and set it
        String hashedPassword = BCrypt.hashpw(passwordPlano, BCrypt.gensalt());
        usuario.setPassword(hashedPassword);

        boolean exitoQuery = usuarioDAO.insertar(usuario);

        if (exitoQuery) {
            JOptionPane.showMessageDialog(this, "Usuario creado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear usuario.");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtfNombre = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        lblContra = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblTipo = new javax.swing.JLabel();
        passPassword = new javax.swing.JPasswordField();
        cbTipoUsuario = new javax.swing.JComboBox<>();
        cbEmpleado = new javax.swing.JComboBox<>();
        lblEmp = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(52, 199, 89));
        jLabel1.setText("Agregar Usuario");

        txtfNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfNombreActionPerformed(evt);
            }
        });

        lblNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNombre.setText("Nombre");

        lblContra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblContra.setText("Contrasena");

        btnGuardar.setBackground(new java.awt.Color(52, 199, 89));
        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(248, 250, 252));
        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(102, 112, 133));
        btnCancelar.setText("Cancelar");
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblTipo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTipo.setText("Tipo de Usuario");

        passPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passPasswordActionPerformed(evt);
            }
        });

        cbTipoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona un tipo", "Admin", "Jardinero", "Vendedor" }));

        lblEmp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEmp.setText("Empleado");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblEmp)
                    .addComponent(cbEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passPassword)
                    .addComponent(jLabel1)
                    .addComponent(lblNombre)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTipo)
                        .addComponent(lblContra)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(156, 156, 156)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(7, 7, 7)
                            .addComponent(btnGuardar)))
                    .addComponent(txtfNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNombre)
                .addGap(5, 5, 5)
                .addComponent(txtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lblContra)
                .addGap(5, 5, 5)
                .addComponent(passPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTipo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(lblEmp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtfNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfNombreActionPerformed
    }//GEN-LAST:event_txtfNombreActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void passPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passPasswordActionPerformed
    }//GEN-LAST:event_passPasswordActionPerformed

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        if (FormValidator.validarCampos(this.getContentPane().getComponents())) {
            // todos los comboboxes y los textfield tienen algo asi que ok
            guardarUsuario();
            adminPanel.cargarDatos(); // actualiza los datos en la tabla de usuarios
            dispose(); // cierra el panel
        } else {
            JOptionPane.showMessageDialog(null, "Por favor llena todos los campos.");
        }
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        dispose();
    }//GEN-LAST:event_btnCancelarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cbEmpleado;
    private javax.swing.JComboBox<String> cbTipoUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblContra;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JPasswordField passPassword;
    private javax.swing.JTextField txtfNombre;
    // End of variables declaration//GEN-END:variables
}
