package Formularios;

import Modelos.Clientes;
import Paneles.ClientesPanel;
import dao.ClientesDAO;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import utils.FormValidator;
import utils.InputFilter;

public class FormAgregarCliente extends javax.swing.JFrame {

    ClientesPanel clientesPanel;
    
    private DefaultListModel<String> telefonosModel;
    private DefaultListModel<String> correosModel;
    
    public FormAgregarCliente(ClientesPanel clientesPanel) {
        this.clientesPanel = clientesPanel;
        
        initComponents();
        
        Image icon = Toolkit.getDefaultToolkit().getImage(FormAgregarCliente.class.getResource("/Imagenes/logoFlor.png"));
        setIconImage(icon);
        setTitle("Agregar cliente");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // para que solo se cierre este frame
        
        // aplicar el limite a los jtextfields
        ((AbstractDocument) txtfNombreCliente.getDocument()).setDocumentFilter(new InputFilter(45, true, false));
        ((AbstractDocument) txtfApellidosCliente.getDocument()).setDocumentFilter(new InputFilter(45, true, false));
        ((AbstractDocument) txtfDireccionCliente.getDocument()).setDocumentFilter(new InputFilter(45, false, false));
        ((AbstractDocument) txtfRFC.getDocument()).setDocumentFilter(new InputFilter(20, false, false));
        ((AbstractDocument) txtfTelefono.getDocument()).setDocumentFilter(new InputFilter(10, false, true));
        ((AbstractDocument) txtfCorreos.getDocument()).setDocumentFilter(new InputFilter(45, false, false));
        
        telefonosModel = new DefaultListModel<>();
        correosModel = new DefaultListModel<>();
        
        telefonosList.setModel(telefonosModel);
        correosList.setModel(correosModel);
    }

    private void guardarCliente() {
        
        String nombre = txtfNombreCliente.getText().trim();
        

        List<String> telefonos = Collections.list(telefonosModel.elements()); // convierte el defaultListModel a una list<string> asi se puede usar en el set
        List<String> correos = Collections.list(correosModel.elements());
        
        if (!nombre.isEmpty()) {
            Clientes cliente = new Clientes();

            cliente.setNombreClie(txtfNombreCliente.getText().trim());
            cliente.setApellidosClie(txtfApellidosCliente.getText().trim());
            cliente.setDireccionClie(txtfDireccionCliente.getText().trim());
            cliente.setRFC_Clie(txtfRFC.getText().trim());
            cliente.setNumeroClie(telefonos); // set telefonos
            cliente.setCorreoClie(correos);
            
            ClientesDAO dao = new ClientesDAO();
            dao.insertarCliente(cliente);
            JOptionPane.showMessageDialog(this, "Cliente guardado");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnGuardarCliente = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtfCorreos = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        correosList = new javax.swing.JList<>();
        jLabel6 = new javax.swing.JLabel();
        btnAnadirCorreo = new javax.swing.JButton();
        btnEliminarCorreo = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtfTelefono = new javax.swing.JTextField();
        btnAnadirTelefono = new javax.swing.JButton();
        btnEliminarTelefono = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        telefonosList = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        txtfRFC = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtfDireccionCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtfApellidosCliente = new javax.swing.JTextField();
        txtfNombreCliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(850, 500));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(52, 199, 89));
        jLabel1.setText("Agregar Cliente");

        btnGuardarCliente.setBackground(new java.awt.Color(52, 199, 89));
        btnGuardarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardarCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarCliente.setText("Guardar");
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

        correosList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(correosList);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Correo electrónico");

        btnAnadirCorreo.setBackground(new java.awt.Color(248, 250, 252));
        btnAnadirCorreo.setFont(new java.awt.Font("Noto Sans", 1, 13)); // NOI18N
        btnAnadirCorreo.setForeground(new java.awt.Color(102, 112, 133));
        btnAnadirCorreo.setText("Anadir correo");
        btnAnadirCorreo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnadirCorreoMouseClicked(evt);
            }
        });

        btnEliminarCorreo.setBackground(new java.awt.Color(248, 250, 252));
        btnEliminarCorreo.setFont(new java.awt.Font("Noto Sans", 1, 13)); // NOI18N
        btnEliminarCorreo.setForeground(new java.awt.Color(102, 112, 133));
        btnEliminarCorreo.setText("Eliminar correo");
        btnEliminarCorreo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarCorreoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtfCorreos, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(btnAnadirCorreo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarCorreo)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(btnAnadirCorreo)
                    .addComponent(btnEliminarCorreo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfCorreos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Teléfono");

        btnAnadirTelefono.setBackground(new java.awt.Color(248, 250, 252));
        btnAnadirTelefono.setFont(new java.awt.Font("Noto Sans", 1, 13)); // NOI18N
        btnAnadirTelefono.setForeground(new java.awt.Color(102, 112, 133));
        btnAnadirTelefono.setText("Anadir telefono");
        btnAnadirTelefono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnadirTelefonoMouseClicked(evt);
            }
        });

        btnEliminarTelefono.setBackground(new java.awt.Color(248, 250, 252));
        btnEliminarTelefono.setFont(new java.awt.Font("Noto Sans", 1, 13)); // NOI18N
        btnEliminarTelefono.setForeground(new java.awt.Color(102, 112, 133));
        btnEliminarTelefono.setText("Eliminar telefono");
        btnEliminarTelefono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarTelefonoMouseClicked(evt);
            }
        });

        telefonosList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(telefonosList);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAnadirTelefono))
                    .addComponent(txtfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarTelefono))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
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
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("RFC");

        txtfDireccionCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfDireccionClienteActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Dirección");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Apellidos");

        txtfApellidosCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfApellidosClienteActionPerformed(evt);
            }
        });

        txtfNombreCliente.setToolTipText("");
        txtfNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfNombreClienteActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombre");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2)
                    .addComponent(txtfApellidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtfRFC, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfApellidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(6, 6, 6)
                .addComponent(txtfDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(6, 6, 6)
                .addComponent(txtfRFC, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardarCliente)
                        .addGap(50, 50, 50))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 865, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtfApellidosClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfApellidosClienteActionPerformed
    }//GEN-LAST:event_txtfApellidosClienteActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

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

    private void btnGuardarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarClienteMouseClicked
        // se excluyen los txtfield de correo y telefono porque no necesitan estar llenos
        Set<Component> exclude = new HashSet<>();
        exclude.add(txtfTelefono);
        exclude.add(txtfCorreos);
        
        if (FormValidator.validarCampos(this.getContentPane().getComponents(), exclude)) {
            // todos los comboboxes y los textfield tienen algo asi que ok
            guardarCliente();
            clientesPanel.cargarDatos(); // actualiza la tabla
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Por favor llena todos los campos.");
        }
    }//GEN-LAST:event_btnGuardarClienteMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        dispose();
    }//GEN-LAST:event_jButton2MouseClicked

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
