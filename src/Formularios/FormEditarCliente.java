package Formularios;

import Modelos.Clientes;
import Paneles.ClientesPanel;
import dao.ClientesDAO;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import utils.FormValidator;
import utils.InputFilter;

public class FormEditarCliente extends javax.swing.JFrame {
    private Clientes clienteOriginal;
    private ClientesPanel clientesPanel;
    
    private DefaultListModel<String> telefonosModel, correosModel;
    
    //clientes panel para recargar la tabla al terminar de editar
    public FormEditarCliente(ClientesPanel clientesPanel, Clientes cliente) {
        this.clientesPanel = clientesPanel;
        clienteOriginal = cliente;
        
        initComponents();
        
        Image icon = Toolkit.getDefaultToolkit().getImage(FormEditarCliente.class.getResource("/Imagenes/logoFlor.png"));
        setIconImage(icon);
        setTitle("Editar cliente");
        setLocationRelativeTo(null);

        // aplicar el limite a los jtextfields
        ((AbstractDocument) txtfNombreCliente.getDocument()).setDocumentFilter(new InputFilter(45, true, false));
        ((AbstractDocument) txtfApellidosCliente.getDocument()).setDocumentFilter(new InputFilter(45, true, false));
        ((AbstractDocument) txtfDireccionCliente.getDocument()).setDocumentFilter(new InputFilter(45, false, false));
        ((AbstractDocument) txtfRFC.getDocument()).setDocumentFilter(new InputFilter(20, false, false));
        ((AbstractDocument) txtfTelefono.getDocument()).setDocumentFilter(new InputFilter(10, false, true));
        ((AbstractDocument) txtfCorreos.getDocument()).setDocumentFilter(new InputFilter(45, false, false));
        
        // poner los datos en los txtf
        txtfNombreCliente.setText(cliente.getNombreClie());
        txtfApellidosCliente.setText(cliente.getApellidosClie());
        txtfDireccionCliente.setText(cliente.getDireccionClie());
        txtfRFC.setText(cliente.getRFC_Clie());
        
        telefonosModel = new DefaultListModel<>();
        cliente.getNumeroClie().forEach(telefonosModel::addElement);
        
        correosModel = new DefaultListModel<>();
        cliente.getCorreoClie().forEach(correosModel::addElement);    
        
        telefonosList.setModel(telefonosModel);
        correosList.setModel(correosModel);
    }
    
    private void guardarDatos() {
        clienteOriginal.setNombreClie(txtfNombreCliente.getText());
        clienteOriginal.setApellidosClie(txtfApellidosCliente.getText());
        clienteOriginal.setDireccionClie(txtfDireccionCliente.getText());
        clienteOriginal.setRFC_Clie(txtfRFC.getText());
        clienteOriginal.setNumeroClie(Collections.list(telefonosModel.elements())); // convierte el defaultListModel a una list<string> asi se puede usar en el set
        clienteOriginal.setCorreoClie(Collections.list(correosModel.elements()));
        
        ClientesDAO dao = new ClientesDAO();
        dao.actualizarCliente(clienteOriginal);
        clientesPanel.cargarDatos();// actualizar la tabla de clientes
        JOptionPane.showMessageDialog(this, "Cliente guardado");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnGuardarCliente = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtfRFC = new javax.swing.JTextField();
        txtfDireccionCliente = new javax.swing.JTextField();
        txtfNombreCliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtfApellidosCliente = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnEliminarTelefono = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        telefonosList = new javax.swing.JList<>();
        btnAnadirTelefono = new javax.swing.JButton();
        txtfTelefono = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnEliminarCorreo = new javax.swing.JButton();
        btnAnadirCorreo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        correosList = new javax.swing.JList<>();
        txtfCorreos = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(850, 500));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(52, 199, 89));
        jLabel1.setText("Editar Cliente");

        btnGuardarCliente.setBackground(new java.awt.Color(52, 199, 89));
        btnGuardarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardarCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarCliente.setText("Agregar");
        btnGuardarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarClienteMouseClicked(evt);
            }
        });
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(248, 250, 252));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(102, 112, 133));
        jButton2.setText("Cancelar");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Dirección");

        txtfDireccionCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfDireccionClienteActionPerformed(evt);
            }
        });

        txtfNombreCliente.setToolTipText("");
        txtfNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfNombreClienteActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("RFC");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombre");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Apellidos");

        txtfApellidosCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfApellidosClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2)
                    .addComponent(txtfApellidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtfDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfRFC, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfApellidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel5)
                .addGap(6, 6, 6)
                .addComponent(txtfRFC, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Teléfono");

        btnEliminarTelefono.setBackground(new java.awt.Color(248, 250, 252));
        btnEliminarTelefono.setFont(new java.awt.Font("Noto Sans", 1, 13)); // NOI18N
        btnEliminarTelefono.setForeground(new java.awt.Color(102, 112, 133));
        btnEliminarTelefono.setText("Eliminar telefono");
        btnEliminarTelefono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarTelefonoMouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(telefonosList);

        btnAnadirTelefono.setBackground(new java.awt.Color(248, 250, 252));
        btnAnadirTelefono.setFont(new java.awt.Font("Noto Sans", 1, 13)); // NOI18N
        btnAnadirTelefono.setForeground(new java.awt.Color(102, 112, 133));
        btnAnadirTelefono.setText("Anadir telefono");
        btnAnadirTelefono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnadirTelefonoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAnadirTelefono)
                        .addGap(29, 29, 29)
                        .addComponent(btnEliminarTelefono))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(txtfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnAnadirTelefono)
                    .addComponent(btnEliminarTelefono))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Correo electrónico");

        btnEliminarCorreo.setBackground(new java.awt.Color(248, 250, 252));
        btnEliminarCorreo.setFont(new java.awt.Font("Noto Sans", 1, 13)); // NOI18N
        btnEliminarCorreo.setForeground(new java.awt.Color(102, 112, 133));
        btnEliminarCorreo.setText("Eliminar correo");
        btnEliminarCorreo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarCorreoMouseClicked(evt);
            }
        });

        btnAnadirCorreo.setBackground(new java.awt.Color(248, 250, 252));
        btnAnadirCorreo.setFont(new java.awt.Font("Noto Sans", 1, 13)); // NOI18N
        btnAnadirCorreo.setForeground(new java.awt.Color(102, 112, 133));
        btnAnadirCorreo.setText("Anadir correo");
        btnAnadirCorreo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnadirCorreoMouseClicked(evt);
            }
        });

        jScrollPane2.setViewportView(correosList);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(btnAnadirCorreo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarCorreo))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtfCorreos, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(btnAnadirCorreo)
                    .addComponent(btnEliminarCorreo))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfCorreos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGuardarCliente)
                .addGap(35, 35, 35))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 863, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtfApellidosClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfApellidosClienteActionPerformed
    }//GEN-LAST:event_txtfApellidosClienteActionPerformed

    private void btnGuardarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarClienteMouseClicked
        // se excluyen los txtfield de correo y telefono porque no necesitan estar llenos
        Set<Component> exclude = new HashSet<>();
        exclude.add(txtfTelefono);
        exclude.add(txtfCorreos);

        
        if (FormValidator.validarCampos(this.getContentPane().getComponents(), exclude)) {
            // todos los comboboxes y los textfield tienen algo asi que ok
            guardarDatos();
            clientesPanel.cargarDatos(); // actualiza la tabla
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Por favor llena todos los campos.");
        }
    }//GEN-LAST:event_btnGuardarClienteMouseClicked

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        dispose();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtfDireccionClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfDireccionClienteActionPerformed
    }//GEN-LAST:event_txtfDireccionClienteActionPerformed

    private void txtfNombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfNombreClienteActionPerformed
    }//GEN-LAST:event_txtfNombreClienteActionPerformed

    private void btnAnadirTelefonoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnadirTelefonoMouseClicked
        String tel = txtfTelefono.getText().trim(); // trim
        if (!tel.isEmpty()) {
            if (telefonosModel.getSize() >= 3) {
                JOptionPane.showMessageDialog(this, "No puedes agregar más de 3 telefonos.");
                return;
            }
            telefonosModel.addElement(tel);
            txtfTelefono.setText("");
        }
    }//GEN-LAST:event_btnAnadirTelefonoMouseClicked

    private void btnAnadirCorreoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnadirCorreoMouseClicked
        String mail = txtfCorreos.getText().trim();
        if (!mail.isEmpty()) {
            if (correosModel.getSize() >= 3) {
                JOptionPane.showMessageDialog(this, "No puedes agregar más de 3 correos.");
                return;
            }
            correosModel.addElement(mail);
            txtfCorreos.setText("");
        }
    }//GEN-LAST:event_btnAnadirCorreoMouseClicked

    private void btnEliminarTelefonoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarTelefonoMouseClicked
        int selectedIndex = telefonosList.getSelectedIndex();
        if (selectedIndex != -1) {
            telefonosModel.removeElementAt(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un teléfono para eliminar.");
        }
    }//GEN-LAST:event_btnEliminarTelefonoMouseClicked

    private void btnEliminarCorreoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarCorreoMouseClicked
        int selectedIndex = correosList.getSelectedIndex();
        if (selectedIndex != -1) {
            correosModel.removeElementAt(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un teléfono para eliminar.");
        }
    }//GEN-LAST:event_btnEliminarCorreoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadirCorreo;
    private javax.swing.JButton btnAnadirTelefono;
    private javax.swing.JButton btnEliminarCorreo;
    private javax.swing.JButton btnEliminarTelefono;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JList<String> correosList;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> telefonosList;
    private javax.swing.JTextField txtfApellidosCliente;
    private javax.swing.JTextField txtfCorreos;
    private javax.swing.JTextField txtfDireccionCliente;
    private javax.swing.JTextField txtfNombreCliente;
    private javax.swing.JTextField txtfRFC;
    private javax.swing.JTextField txtfTelefono;
    // End of variables declaration//GEN-END:variables
}
