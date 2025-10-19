package Paneles;

import Formularios.FormAgregarProveedor;
import Formularios.FormEditarProveedor;
import Frames.MainFrame;
import Modelos.Proveedores;
import Modelos.Usuarios;
import dao.ProveedoresDAO;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import utils.manejadorDePermisos;


public class ProveedoresPanel extends javax.swing.JPanel {

    MainFrame mainFrame;
    private boolean mostrarObjetosEliminados = false;
    Usuarios usuario;

    
    public ProveedoresPanel(Usuarios usuario) {
        this.usuario = usuario;
        manejadorDePermisos permisos = new manejadorDePermisos(usuario);

        
        initComponents();
        
        btnCrearProveedor.setEnabled(permisos.puedeInsertarProveedores());
        btnEliminarProveedor.setEnabled(permisos.puedeEliminarProveedores());
        btnModificarProveedor.setEnabled(permisos.puedeActualizarProveedores());
        btnReactivarProveedor.setEnabled(permisos.puedeEliminarProveedores());
        ckbMostrarEliminados.setEnabled(permisos.puedeEliminarProveedores());
        tablaProveedores.setVisible(permisos.puedeConsultarProveedores());
        
        
        cargarDatos();        
    }
    
    public void cargarDatos() {
        ProveedoresDAO proveedoresdao = new ProveedoresDAO();
        List<Proveedores> proveedores = proveedoresdao.obtenerProveedores();

        DefaultTableModel model = new DefaultTableModel(new String[]{"idProveedores", "NombreProv", "DireccionProv", "RFCProv", "Telefonos", "Correos"}, 0);
        for (Proveedores prov : proveedores) {
            if (!mostrarObjetosEliminados) {
                if (prov.getEstatusProveedor() == 1) {
                    String telefonos = String.join(", ", prov.getNumeroProv());
                    String correos = String.join(", ", prov.getCorreoProv());

                    model.addRow(new Object[]{
                        prov.getIdProveedores(), 
                        prov.getNombreProv(), 
                        prov.getDireccionProv(), 
                        prov.getRFCProv(), 
                        telefonos,
                        correos
                    });
                }
            } else {
                if (prov.getEstatusProveedor() == 0) {
                    String telefonos = String.join(", ", prov.getNumeroProv());
                    String correos = String.join(", ", prov.getCorreoProv());

                    model.addRow(new Object[]{
                        prov.getIdProveedores(), 
                        prov.getNombreProv(), 
                        prov.getDireccionProv(), 
                        prov.getRFCProv(), 
                        telefonos,
                        correos
                    });
                }
            }
        }
        
        tablaProveedores.setModel(model);

        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tablaProveedores.setRowSorter(sorter);
        
        txtfBuscarProv.getDocument().addDocumentListener(new DocumentListener() {
            
        public void insertUpdate(DocumentEvent e) {
            filter();
        }

        public void removeUpdate(DocumentEvent e) {
            filter();
        }

        public void changedUpdate(DocumentEvent e) {
            filter();
        }

        private void filter() {
            String text = txtfBuscarProv.getText();
            if (text.trim().length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        }
        });
        
    }
    
    private void editarProveedor() {
        int row = tablaProveedores.getSelectedRow();
        
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un proveedor para editar.");
            return;
        }
        
        // obtiene los valores del cliente a editar
        int id = (int) tablaProveedores.getValueAt(row, 0);
        String nombre = (String) tablaProveedores.getValueAt(row, 1);
        String direccion = (String) tablaProveedores.getValueAt(row, 2);
        String rfc = (String) tablaProveedores.getValueAt(row, 3);
        String telefonosStr = (String) tablaProveedores.getValueAt(row, 4);
        String correosStr = (String) tablaProveedores.getValueAt(row, 5);
        
        // poner los datos en un objeto cliente
        Proveedores proveedores = new Proveedores(id, nombre, direccion, rfc, 1);
        proveedores.setNumeroProv(Arrays.asList(telefonosStr.split(",\\s*"))); // esto transforma los telefonos separados por comas en una lista que puede ser manipulada
        proveedores.setCorreoProv(Arrays.asList(correosStr.split(",\\s*")));
        
        // abre el nuevo formulario
        FormEditarProveedor form = new FormEditarProveedor(proveedores, this);
        form.setVisible(true);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProveedores = new javax.swing.JTable();
        lblTablaClientes = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnModificarProveedor = new javax.swing.JButton();
        lblBuscarCliente = new javax.swing.JLabel();
        btnCrearProveedor = new javax.swing.JButton();
        txtfBuscarProv = new javax.swing.JTextField();
        btnEliminarProveedor = new javax.swing.JButton();
        btnReactivarProveedor = new javax.swing.JButton();
        ckbMostrarEliminados = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(900, 740));

        tablaProveedores.setFont(new java.awt.Font("Noto Sans", 0, 12)); // NOI18N
        tablaProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Dirección", "RFC", "Teléfono", "Email"
            }
        ));
        tablaProveedores.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaProveedores.setRowHeight(80);
        jScrollPane1.setViewportView(tablaProveedores);

        lblTablaClientes.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblTablaClientes.setText("Tabla: Proveedores");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnModificarProveedor.setBackground(new java.awt.Color(248, 250, 252));
        btnModificarProveedor.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnModificarProveedor.setForeground(new java.awt.Color(102, 112, 133));
        btnModificarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-editar-20.png"))); // NOI18N
        btnModificarProveedor.setText("Modificar");
        btnModificarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarProveedorMouseClicked(evt);
            }
        });
        btnModificarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarProveedorActionPerformed(evt);
            }
        });

        lblBuscarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBuscarCliente.setForeground(new java.awt.Color(102, 112, 133));
        lblBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-búsqueda-20.png"))); // NOI18N
        lblBuscarCliente.setText("Buscar:");
        lblBuscarCliente.setMinimumSize(new java.awt.Dimension(7, 20));

        btnCrearProveedor.setBackground(new java.awt.Color(52, 199, 89));
        btnCrearProveedor.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCrearProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnCrearProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-añadir-20.png"))); // NOI18N
        btnCrearProveedor.setText("Añadir proveedor ");
        btnCrearProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCrearProveedorMouseClicked(evt);
            }
        });
        btnCrearProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearProveedorActionPerformed(evt);
            }
        });

        txtfBuscarProv.setMargin(new java.awt.Insets(5, 0, 5, 0));
        txtfBuscarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfBuscarProvActionPerformed(evt);
            }
        });

        btnEliminarProveedor.setBackground(new java.awt.Color(255, 85, 85));
        btnEliminarProveedor.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnEliminarProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarProveedor.setText("Eliminar");
        btnEliminarProveedor.setPreferredSize(new java.awt.Dimension(142, 37));
        btnEliminarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarProveedorMouseClicked(evt);
            }
        });
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });

        btnReactivarProveedor.setBackground(new java.awt.Color(52, 199, 89));
        btnReactivarProveedor.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnReactivarProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnReactivarProveedor.setText("Reactivar");
        btnReactivarProveedor.setPreferredSize(new java.awt.Dimension(93, 37));
        btnReactivarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReactivarProveedorMouseClicked(evt);
            }
        });
        btnReactivarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReactivarProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtfBuscarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(btnReactivarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEliminarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnModificarProveedor)
                .addGap(18, 18, 18)
                .addComponent(btnCrearProveedor))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfBuscarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReactivarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        ckbMostrarEliminados.setText("Mostrar objetos eliminados");
        ckbMostrarEliminados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckbMostrarEliminadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(lblTablaClientes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ckbMostrarEliminados))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 837, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTablaClientes)
                    .addComponent(ckbMostrarEliminados))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearProveedorMouseClicked
    }//GEN-LAST:event_btnCrearProveedorMouseClicked

    private void btnCrearProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearProveedorActionPerformed
        FormAgregarProveedor form = new FormAgregarProveedor(this);
        form.setVisible(true);
    }//GEN-LAST:event_btnCrearProveedorActionPerformed

    private void btnModificarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProveedorMouseClicked
    }//GEN-LAST:event_btnModificarProveedorMouseClicked

    private void btnModificarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarProveedorActionPerformed
        editarProveedor();
    }//GEN-LAST:event_btnModificarProveedorActionPerformed

    private void txtfBuscarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfBuscarProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfBuscarProvActionPerformed

    private void btnEliminarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProveedorMouseClicked

    }//GEN-LAST:event_btnEliminarProveedorMouseClicked

    private void ckbMostrarEliminadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckbMostrarEliminadosActionPerformed
        mostrarObjetosEliminados = ckbMostrarEliminados.isSelected();
        cargarDatos();
    }//GEN-LAST:event_ckbMostrarEliminadosActionPerformed

    private void btnReactivarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReactivarProveedorMouseClicked
    }//GEN-LAST:event_btnReactivarProveedorMouseClicked

    private void btnReactivarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReactivarProveedorActionPerformed
        if(!mostrarObjetosEliminados) {
            return;
        }
        int row = tablaProveedores.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un proveedor a reactivar");
            return;
        }

        int proveedorId = (int) tablaProveedores.getValueAt(row, 0);
        String nombreProveedor = (String) tablaProveedores.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Estas seguro que quieres reactivar este proveedor: " + nombreProveedor + "?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        ProveedoresDAO proveedoresDAO = new ProveedoresDAO();

        try {
            boolean eliminado;
            eliminado = proveedoresDAO.reactivarProveedor(proveedorId);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Proveedor reactivado");
                cargarDatos(); // actualiza la tabla
            } else {
                JOptionPane.showMessageDialog(this, "Proveedor no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reactivando proveedor");
        }
    }//GEN-LAST:event_btnReactivarProveedorActionPerformed

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        int row = tablaProveedores.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un proveedor a eliminar");
            return;
        }

        int proveedorId = (int) tablaProveedores.getValueAt(row, 0);
        String nombreProveedor = (String) tablaProveedores.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Estas seguro que quieres eliminar este proveedor: " + nombreProveedor + "?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        ProveedoresDAO proveedoresDAO = new ProveedoresDAO();

        try {
            boolean eliminado;
            eliminado = proveedoresDAO.eliminarProveedor(proveedorId);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Proveedor eliminado");
                cargarDatos(); // actualiza la tabla
            } else {
                JOptionPane.showMessageDialog(this, "Proveedor no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error eliminando proveedor");
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCrearProveedor;
    private javax.swing.JButton btnEliminarProveedor;
    public javax.swing.JButton btnModificarProveedor;
    public javax.swing.JButton btnReactivarProveedor;
    private javax.swing.JCheckBox ckbMostrarEliminados;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscarCliente;
    private javax.swing.JLabel lblTablaClientes;
    private javax.swing.JTable tablaProveedores;
    private javax.swing.JTextField txtfBuscarProv;
    // End of variables declaration//GEN-END:variables
}