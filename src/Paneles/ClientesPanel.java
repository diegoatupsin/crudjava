package Paneles;

import Formularios.FormAgregarCliente;
import Formularios.FormEditarCliente;
import Modelos.Clientes;
import Modelos.Usuarios;
import dao.ClientesDAO;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import utils.manejadorDePermisos;


public class ClientesPanel extends javax.swing.JPanel {
    
    private boolean mostrarObjetosEliminados = false;

    
    public ClientesPanel(Usuarios usuario) {
        manejadorDePermisos permisos = new manejadorDePermisos(usuario);
        
        initComponents();
        
        btnCrearCliente.setEnabled(permisos.puedeInsertarClientes());
        btnEliminarCliente.setEnabled(permisos.puedeEliminarClientes());
        ckbMostrarEliminados.setEnabled(permisos.puedeEliminarClientes());
        btnReactivarCliente.setEnabled(permisos.puedeEliminarClientes());
        btnModificarCliente.setEnabled(permisos.puedeActualizarClientes());
        tablaClientes.setVisible(permisos.puedeConsultarClientes());
        
        cargarDatos();
                
    }
    
    public void cargarDatos() {        
        ClientesDAO clientesdao = new ClientesDAO();
        List<Clientes> clientes = clientesdao.obtenerClientes();

        DefaultTableModel model = new DefaultTableModel(new String[]{"idCliente", "NombreClie", "ApellidosCli", "DireccionCli", "RFC_Cli", "Telefonos", "Correos"}, 0);
        for (Clientes clie : clientes) {
            if (!mostrarObjetosEliminados) {
                if (clie.getEstatusCliente() == 1) {
                    String telefonos = String.join(", ", clie.getNumeroClie());
                    String correos = String.join(", ", clie.getCorreoClie());

                    model.addRow(new Object[]{
                        clie.getIdClientes(), 
                        clie.getNombreClie(), 
                        clie.getApellidosClie(), 
                        clie.getDireccionClie(), 
                        clie.getRFC_Clie(), 
                        telefonos,
                        correos
                    });
                }
            } else {
                if (clie.getEstatusCliente() == 0) {
                    String telefonos = String.join(", ", clie.getNumeroClie());
                    String correos = String.join(", ", clie.getCorreoClie());

                    model.addRow(new Object[]{
                        clie.getIdClientes(), 
                        clie.getNombreClie(), 
                        clie.getApellidosClie(), 
                        clie.getDireccionClie(), 
                        clie.getRFC_Clie(), 
                        telefonos,
                        correos
                    });
                }
            }
        }
        tablaClientes.setModel(model);
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tablaClientes.setRowSorter(sorter);
        
        txtfBuscarCliente.getDocument().addDocumentListener(new DocumentListener() {
            
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
            String text = txtfBuscarCliente.getText();
            if (text.trim().length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        }
        });
        
    }
    
    private void editarCliente() {
        int row = tablaClientes.getSelectedRow();
        
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario para editar.");
            return;
        }
        
        // obtiene los valores del cliente a editar
        int id = (int) tablaClientes.getValueAt(row, 0);
        String nombre = (String) tablaClientes.getValueAt(row, 1);
        String apellidos = (String) tablaClientes.getValueAt(row, 2);
        String direccion = (String) tablaClientes.getValueAt(row, 3);
        String rfc = (String) tablaClientes.getValueAt(row, 4);

        String telefonosStr = (String) tablaClientes.getValueAt(row, 5);
        String correosStr = (String) tablaClientes.getValueAt(row, 6);
        
        // poner los datos en un objeto cliente
        Clientes cliente = new Clientes(id, nombre, apellidos, direccion, rfc, 1);
        cliente.setNumeroClie(Arrays.asList(telefonosStr.split(",\\s*"))); // esto transforma los telefonos separados por comas en una lista que puede ser manipulada
        cliente.setCorreoClie(Arrays.asList(correosStr.split(",\\s*")));
        
        // abre el nuevo formulario con el cliente seleccionado
        FormEditarCliente form = new FormEditarCliente(this, cliente);
        form.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        lblTablaClientes = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtfBuscarCliente = new javax.swing.JTextField();
        btnCrearCliente = new javax.swing.JButton();
        lblBuscarCliente = new javax.swing.JLabel();
        btnModificarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnReactivarCliente = new javax.swing.JButton();
        ckbMostrarEliminados = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(900, 740));

        tablaClientes.setFont(new java.awt.Font("Noto Sans", 0, 12)); // NOI18N
        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "apellidos", "Dirección", "RFC", "Teléfono", "Email"
            }
        ));
        tablaClientes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaClientes.setRowHeight(80);
        jScrollPane1.setViewportView(tablaClientes);

        lblTablaClientes.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblTablaClientes.setText("Tabla: Clientes");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtfBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfBuscarClienteActionPerformed(evt);
            }
        });

        btnCrearCliente.setBackground(new java.awt.Color(52, 199, 89));
        btnCrearCliente.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCrearCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnCrearCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-añadir-20.png"))); // NOI18N
        btnCrearCliente.setText("Añadir cliente\n");
        btnCrearCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCrearClienteMouseClicked(evt);
            }
        });
        btnCrearCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearClienteActionPerformed(evt);
            }
        });

        lblBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-búsqueda-20.png"))); // NOI18N
        lblBuscarCliente.setText("Buscar:");

        btnModificarCliente.setBackground(new java.awt.Color(248, 250, 252));
        btnModificarCliente.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnModificarCliente.setForeground(new java.awt.Color(102, 112, 133));
        btnModificarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-editar-20.png"))); // NOI18N
        btnModificarCliente.setText("Modificar");
        btnModificarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarClienteMouseClicked(evt);
            }
        });
        btnModificarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarClienteActionPerformed(evt);
            }
        });

        btnEliminarCliente.setBackground(new java.awt.Color(255, 85, 85));
        btnEliminarCliente.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnEliminarCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarCliente.setText("Eliminar");
        btnEliminarCliente.setPreferredSize(new java.awt.Dimension(142, 37));
        btnEliminarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarClienteMouseClicked(evt);
            }
        });
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        btnReactivarCliente.setBackground(new java.awt.Color(52, 199, 89));
        btnReactivarCliente.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnReactivarCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnReactivarCliente.setText("Reactivar");
        btnReactivarCliente.setPreferredSize(new java.awt.Dimension(93, 37));
        btnReactivarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReactivarClienteMouseClicked(evt);
            }
        });
        btnReactivarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReactivarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBuscarCliente)
                .addGap(18, 18, 18)
                .addComponent(txtfBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(btnReactivarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnModificarCliente)
                .addGap(18, 18, 18)
                .addComponent(btnCrearCliente)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarCliente)
                    .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReactivarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTablaClientes)
                        .addGap(513, 513, 513)
                        .addComponent(ckbMostrarEliminados))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 831, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTablaClientes)
                    .addComponent(ckbMostrarEliminados))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearClienteMouseClicked
    }//GEN-LAST:event_btnCrearClienteMouseClicked

    private void btnCrearClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearClienteActionPerformed
        FormAgregarCliente form = new FormAgregarCliente(this);
        form.setVisible(true);
    }//GEN-LAST:event_btnCrearClienteActionPerformed

    private void btnModificarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarClienteMouseClicked
    }//GEN-LAST:event_btnModificarClienteMouseClicked

    private void btnModificarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarClienteActionPerformed
        // TODO add your handling code here:
        editarCliente();
    }//GEN-LAST:event_btnModificarClienteActionPerformed

    private void txtfBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfBuscarClienteActionPerformed

    private void ckbMostrarEliminadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckbMostrarEliminadosActionPerformed
        mostrarObjetosEliminados = ckbMostrarEliminados.isSelected();

        cargarDatos();
    }//GEN-LAST:event_ckbMostrarEliminadosActionPerformed

    private void btnEliminarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarClienteMouseClicked
    }//GEN-LAST:event_btnEliminarClienteMouseClicked

    private void btnReactivarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReactivarClienteMouseClicked
    }//GEN-LAST:event_btnReactivarClienteMouseClicked

    private void btnReactivarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReactivarClienteActionPerformed
        if(!mostrarObjetosEliminados) {
            return;
        }
        int row = tablaClientes.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un cliente a reactivar");
            return;
        }

        int clienteId = (int) tablaClientes.getValueAt(row, 0);
        String nombreCliente = (String) tablaClientes.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Estas seguro que quieres reactivar este cliente: " + nombreCliente + "?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        ClientesDAO clientesDAO = new ClientesDAO();

        try {
            boolean eliminado;
            eliminado = clientesDAO.reactivarCliente(clienteId);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Cliente reactivado");
                cargarDatos(); // actualiza la tabla
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reactivando cliente");
        } 
    }//GEN-LAST:event_btnReactivarClienteActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        int row = tablaClientes.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un cliente a eliminar");
            return;
        }

        int clienteId = (int) tablaClientes.getValueAt(row, 0);
        String nombreCliente = (String) tablaClientes.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Estas seguro que quieres eliminar este cliente: " + nombreCliente + "?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        ClientesDAO clientesDAO = new ClientesDAO();

        try {
            boolean eliminado;
            eliminado = clientesDAO.eliminarCliente(clienteId);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado");
                cargarDatos(); // actualiza la tabla
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error eliminando cliente");
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCrearCliente;
    private javax.swing.JButton btnEliminarCliente;
    public javax.swing.JButton btnModificarCliente;
    public javax.swing.JButton btnReactivarCliente;
    private javax.swing.JCheckBox ckbMostrarEliminados;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscarCliente;
    private javax.swing.JLabel lblTablaClientes;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTextField txtfBuscarCliente;
    // End of variables declaration//GEN-END:variables
}