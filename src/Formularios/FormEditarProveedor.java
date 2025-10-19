package Formularios;

import Modelos.Proveedores;
import Paneles.ProveedoresPanel;
import dao.ProveedoresDAO;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import utils.FormValidator;
import utils.InputFilter;

public class FormEditarProveedor extends javax.swing.JFrame {
    
    private Proveedores proveedorOriginal;
    private ProveedoresPanel proveedoresPanel;
    
    private DefaultListModel<String> telefonosModel, correosModel;

    //clientes panel para recargar la tabla al terminar de editar
    public FormEditarProveedor(Proveedores prov, ProveedoresPanel proveedoresPanel) {
        proveedorOriginal = prov;
        this.proveedoresPanel = proveedoresPanel;
        
        initComponents();
        
        Image icon = Toolkit.getDefaultToolkit().getImage(FormEditarProveedor.class.getResource("/Imagenes/logoFlor.png"));
        setIconImage(icon);
        setTitle("Editar proveedor");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // para que solo se cierre este frame
        
        // aplicar el limite a los jtextfields
        ((AbstractDocument) txtfNombreProv.getDocument()).setDocumentFilter(new InputFilter(45, true, false));
        ((AbstractDocument) txtfDireccionProv.getDocument()).setDocumentFilter(new InputFilter(45, false, false));
        ((AbstractDocument) txtfRFCProv.getDocument()).setDocumentFilter(new InputFilter(13, false, false));
        ((AbstractDocument) txtfTelefono.getDocument()).setDocumentFilter(new InputFilter(10, false, true));
        ((AbstractDocument) txtfCorreos.getDocument()).setDocumentFilter(new InputFilter(45, false, false));
        
        // poner los datos en los txtf
        txtfNombreProv.setText(prov.getNombreProv());
        txtfDireccionProv.setText(prov.getDireccionProv());
        txtfRFCProv.setText(prov.getRFCProv());
        
        telefonosModel = new DefaultListModel<>();
        prov.getNumeroProv().forEach(telefonosModel::addElement);
        
        correosModel = new DefaultListModel<>();
        prov.getCorreoProv().forEach(correosModel::addElement);    
        
        telefonosList.setModel(telefonosModel);
        correosList.setModel(correosModel);
    }
    
    private void guardarDatos() {
        proveedorOriginal.setNombreProv(txtfNombreProv.getText());
        proveedorOriginal.setDireccionProv(txtfDireccionProv.getText());
        proveedorOriginal.setRFCProv(txtfRFCProv.getText());
        proveedorOriginal.setNumeroProv(Collections.list(telefonosModel.elements())); // convierte el defaultListModel a una list<string> asi se puede usar en el set
        proveedorOriginal.setCorreoProv(Collections.list(correosModel.elements()));
        
        ProveedoresDAO dao = new ProveedoresDAO();
        dao.actualizarProveedor(proveedorOriginal);
        dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtfRFCProv = new javax.swing.JTextField();
        txtfDireccionProv = new javax.swing.JTextField();
        txtfNombreProv = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        telefonosList = new javax.swing.JList<>();
        txtfTelefono = new javax.swing.JTextField();
        btnAnadirTelefono = new javax.swing.JButton();
        btnEliminarTelefono = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtfCorreos = new javax.swing.JTextField();
        btnAnadirCorreo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        correosList = new javax.swing.JList<>();
        btnEliminarCorreo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(0, 0));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombre");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Dirección");

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

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("RFC");

        txtfDireccionProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfDireccionProvActionPerformed(evt);
            }
        });

        txtfNombreProv.setToolTipText("");
        txtfNombreProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfNombreProvActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(52, 199, 89));
        jLabel8.setText("Datos del proveedor");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        telefonosList.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        telefonosList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(telefonosList);

        txtfTelefono.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        btnAnadirTelefono.setBackground(new java.awt.Color(248, 250, 252));
        btnAnadirTelefono.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnAnadirTelefono.setForeground(new java.awt.Color(102, 112, 133));
        btnAnadirTelefono.setText("Añadir telefono");
        btnAnadirTelefono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnadirTelefonoMouseClicked(evt);
            }
        });

        btnEliminarTelefono.setBackground(new java.awt.Color(248, 250, 252));
        btnEliminarTelefono.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnEliminarTelefono.setForeground(new java.awt.Color(102, 112, 133));
        btnEliminarTelefono.setText("Eliminar telefono");
        btnEliminarTelefono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarTelefonoMouseClicked(evt);
            }
        });
        btnEliminarTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTelefonoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Teléfono");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAnadirTelefono))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminarTelefono)))
                    .addComponent(jLabel3))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAnadirTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, 0))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Correo electrónico");

        btnAnadirCorreo.setBackground(new java.awt.Color(248, 250, 252));
        btnAnadirCorreo.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnAnadirCorreo.setForeground(new java.awt.Color(102, 112, 133));
        btnAnadirCorreo.setText("Añadir correo");
        btnAnadirCorreo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnadirCorreoMouseClicked(evt);
            }
        });
        btnAnadirCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirCorreoActionPerformed(evt);
            }
        });

        correosList.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        correosList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        correosList.setName(""); // NOI18N
        jScrollPane2.setViewportView(correosList);

        btnEliminarCorreo.setBackground(new java.awt.Color(248, 250, 252));
        btnEliminarCorreo.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnEliminarCorreo.setForeground(new java.awt.Color(102, 112, 133));
        btnEliminarCorreo.setText("Eliminar correo");
        btnEliminarCorreo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarCorreoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6)
                    .addComponent(txtfCorreos, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAnadirCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarCorreo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfCorreos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnadirCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(txtfRFCProv, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                            .addComponent(txtfDireccionProv, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                            .addComponent(txtfNombreProv))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel8)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6)
                        .addComponent(txtfNombreProv, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfDireccionProv, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfRFCProv, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        // se excluyen los txtfield de correo y telefono porque no necesitan estar llenos
        Set<Component> exclude = new HashSet<>();
        exclude.add(txtfTelefono);
        exclude.add(txtfCorreos);

        
        if (FormValidator.validarCampos(this.getContentPane().getComponents(), exclude)) {
            // todos los comboboxes y los textfield tienen algo asi que ok
            guardarDatos();
            proveedoresPanel.cargarDatos(); // actualiza la tabla
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Por favor llena todos los campos.");
        } 
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtfDireccionProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfDireccionProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfDireccionProvActionPerformed

    private void txtfNombreProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfNombreProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfNombreProvActionPerformed

    private void btnAnadirTelefonoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnadirTelefonoMouseClicked
        // TODO add your handling code here:
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

    private void btnEliminarTelefonoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarTelefonoMouseClicked
        // TODO add your handling code here:
        int selectedIndex = telefonosList.getSelectedIndex();
        if (selectedIndex != -1) {
            telefonosModel.removeElementAt(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un teléfono para eliminar.");
        }
    }//GEN-LAST:event_btnEliminarTelefonoMouseClicked

    private void btnAnadirCorreoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnadirCorreoMouseClicked
        // TODO add your handling code here:
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

    private void btnAnadirCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnadirCorreoActionPerformed

    private void btnEliminarCorreoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarCorreoMouseClicked
        // TODO add your handling code here:

        int selectedIndex = correosList.getSelectedIndex();
        if (selectedIndex != -1) {
            correosModel.removeElementAt(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un teléfono para eliminar.");
        }
    }//GEN-LAST:event_btnEliminarCorreoMouseClicked

    private void btnEliminarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarTelefonoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadirCorreo;
    private javax.swing.JButton btnAnadirTelefono;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarCorreo;
    private javax.swing.JButton btnEliminarTelefono;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JList<String> correosList;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> telefonosList;
    private javax.swing.JTextField txtfCorreos;
    private javax.swing.JTextField txtfDireccionProv;
    private javax.swing.JTextField txtfNombreProv;
    private javax.swing.JTextField txtfRFCProv;
    private javax.swing.JTextField txtfTelefono;
    // End of variables declaration//GEN-END:variables
}
