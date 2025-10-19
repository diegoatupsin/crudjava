package Paneles;

import Formularios.FormAgregarEmpleado;
import Formularios.FormEditarEmpleado;
import Frames.MainFrame;
import Modelos.Empleados;
import Modelos.Usuarios;
import dao.EmpleadosDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Date;
import java.util.Arrays;
import utils.manejadorDePermisos;


public class EmpleadosPanel extends javax.swing.JPanel {

    MainFrame mainFrame;
    private boolean mostrarObjetosEliminados = false;
    Usuarios usuario;
    
    public EmpleadosPanel(Usuarios usuario) {
        this.usuario = usuario;
        manejadorDePermisos permisos = new manejadorDePermisos(usuario);
        
        initComponents();
        
        btnCrearEmpleado.setEnabled(permisos.puedeInsertarEmpleados());
        btnEliminarEmpleados.setEnabled(permisos.puedeEliminarEmpleados());
        btnModificarEmpleado.setEnabled(permisos.puedeActualizarEmpleados());
        btnReactivarEmpleado.setEnabled(permisos.puedeEliminarEmpleados());
        ckbMostrarEliminados.setEnabled(permisos.puedeEliminarEmpleados());
        tablaEmpleados.setVisible(permisos.puedeConsultarEmpleados());
        
        cargarDatos();
    }
    
    public void cargarDatos() {
        EmpleadosDAO empleadosdao = new EmpleadosDAO();
        List<Empleados> empleados = empleadosdao.obtenerEmpleados();
        
        DefaultTableModel model = new DefaultTableModel( new String[]{"idEmpleado",
            "NombreEmp", "ApellidoEmp", "DireccionEmp", "NumeroSS", "Puesto",
            "FechaContrato", "Telefono", "Correo"}, 0);
        for (Empleados emp : empleados) {
            if (!mostrarObjetosEliminados) {
                if (emp.getEstatusEmpleado() == 1) {
                    String telefonos = String.join(", ", emp.getNumeroEmp());
                    String correos = String.join(", ", emp.getCorreosEmp());
            
                    model.addRow(new Object[]{
                        emp.getIdEmpleados(),
                        emp.getNombreEmp(),
                        emp.getApellidoEmp(),
                        emp.getDireccionEmp(),
                        emp.getNumeroSS(),
                        emp.getPuesto(),
                        emp.getFechaContrato(),
                        telefonos,
                        correos
                    });
                }
            } else {
                if (emp.getEstatusEmpleado() == 0) {
                    String telefonos = String.join(", ", emp.getNumeroEmp());
                    String correos = String.join(", ", emp.getCorreosEmp());
            
                    model.addRow(new Object[]{
                        emp.getIdEmpleados(),
                        emp.getNombreEmp(),
                        emp.getApellidoEmp(),
                        emp.getDireccionEmp(),
                        emp.getNumeroSS(),
                        emp.getPuesto(),
                        emp.getFechaContrato(),
                        telefonos,
                        correos
                    });
                }
            }

        }
        tablaEmpleados.setModel(model);
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tablaEmpleados.setRowSorter(sorter);
        
        txtfBuscarEmpleado.getDocument().addDocumentListener(new DocumentListener() {
            
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
                String text = txtfBuscarEmpleado.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }
    
    private void editarEmpleado() {
        int row = tablaEmpleados.getSelectedRow();
        
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un empleado para editar.");
            return;
        }
        
        int id = (int) tablaEmpleados.getValueAt(row, 0);
        String nombre = (String) tablaEmpleados.getValueAt(row, 1);
        String apellidos = (String) tablaEmpleados.getValueAt(row, 2);
        String direccion = (String) tablaEmpleados.getValueAt(row, 3);
        String numeroSS = (String) tablaEmpleados.getValueAt(row, 4);
        String puesto = (String) tablaEmpleados.getValueAt(row, 5);
        Date date = (Date) tablaEmpleados.getValueAt(row, 6);
        
        String telefonosStr = (String) tablaEmpleados.getValueAt(row, 7);
        String correosStr = (String) tablaEmpleados.getValueAt(row, 8);
        
        // poner los datos en un objeto empleado
        Empleados empleado = new Empleados(id, nombre, apellidos, numeroSS, puesto, date, direccion, 1);
        empleado.setNumeroEmp(Arrays.asList(telefonosStr.split(",\\s*")));// transforma la telefnos separados por comas en una lista 
        empleado.setCorreosEmp(Arrays.asList(correosStr.split(",\\s*")));
        
        FormEditarEmpleado form = new FormEditarEmpleado(empleado, this);
        form.setVisible(true);       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEmpleados = new javax.swing.JTable();
        lblTablaEmpleados = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblBuscarEmpleado = new javax.swing.JLabel();
        btnModificarEmpleado = new javax.swing.JButton();
        txtfBuscarEmpleado = new javax.swing.JTextField();
        btnCrearEmpleado = new javax.swing.JButton();
        btnEliminarEmpleados = new javax.swing.JButton();
        btnReactivarEmpleado = new javax.swing.JButton();
        ckbMostrarEliminados = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(900, 740));

        tablaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
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
                "ID", "Nombre", "Puesto", "NumeroSS", "Direccion", "Fecha de contrato", "Acciones"
            }
        ));
        tablaEmpleados.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaEmpleados.setRowHeight(80);
        jScrollPane1.setViewportView(tablaEmpleados);

        lblTablaEmpleados.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblTablaEmpleados.setText("Tabla: Empleados\n");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblBuscarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-búsqueda-20.png"))); // NOI18N
        lblBuscarEmpleado.setText("Buscar:");

        btnModificarEmpleado.setBackground(new java.awt.Color(248, 250, 252));
        btnModificarEmpleado.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnModificarEmpleado.setForeground(new java.awt.Color(102, 112, 133));
        btnModificarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-editar-20.png"))); // NOI18N
        btnModificarEmpleado.setText("Modificar");
        btnModificarEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarEmpleadoMouseClicked(evt);
            }
        });
        btnModificarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarEmpleadoActionPerformed(evt);
            }
        });

        txtfBuscarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfBuscarEmpleadoActionPerformed(evt);
            }
        });

        btnCrearEmpleado.setBackground(new java.awt.Color(52, 199, 89));
        btnCrearEmpleado.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCrearEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        btnCrearEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-añadir-20.png"))); // NOI18N
        btnCrearEmpleado.setText("Añadir empleado\n");
        btnCrearEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCrearEmpleadoMouseClicked(evt);
            }
        });
        btnCrearEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearEmpleadoActionPerformed(evt);
            }
        });

        btnEliminarEmpleados.setBackground(new java.awt.Color(255, 85, 85));
        btnEliminarEmpleados.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnEliminarEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarEmpleados.setText("Eliminar");
        btnEliminarEmpleados.setPreferredSize(new java.awt.Dimension(142, 37));
        btnEliminarEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarEmpleadosMouseClicked(evt);
            }
        });
        btnEliminarEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEmpleadosActionPerformed(evt);
            }
        });

        btnReactivarEmpleado.setBackground(new java.awt.Color(52, 199, 89));
        btnReactivarEmpleado.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnReactivarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        btnReactivarEmpleado.setText("Reactivar");
        btnReactivarEmpleado.setPreferredSize(new java.awt.Dimension(93, 37));
        btnReactivarEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReactivarEmpleadoMouseClicked(evt);
            }
        });
        btnReactivarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReactivarEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBuscarEmpleado)
                .addGap(18, 18, 18)
                .addComponent(txtfBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(btnReactivarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEliminarEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificarEmpleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCrearEmpleado)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarEmpleado)
                    .addComponent(btnEliminarEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReactivarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTablaEmpleados)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ckbMostrarEliminados))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTablaEmpleados)
                    .addComponent(ckbMostrarEliminados))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearEmpleadoMouseClicked
    }//GEN-LAST:event_btnCrearEmpleadoMouseClicked

    private void btnCrearEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearEmpleadoActionPerformed
        FormAgregarEmpleado form = new FormAgregarEmpleado(this);
        form.setVisible(true);
    }//GEN-LAST:event_btnCrearEmpleadoActionPerformed

    private void btnModificarEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarEmpleadoMouseClicked

    }//GEN-LAST:event_btnModificarEmpleadoMouseClicked

    private void btnModificarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEmpleadoActionPerformed
        editarEmpleado();
    }//GEN-LAST:event_btnModificarEmpleadoActionPerformed

    private void txtfBuscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfBuscarEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfBuscarEmpleadoActionPerformed

    private void btnEliminarEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarEmpleadosMouseClicked

    }//GEN-LAST:event_btnEliminarEmpleadosMouseClicked

    private void ckbMostrarEliminadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckbMostrarEliminadosActionPerformed
        mostrarObjetosEliminados = ckbMostrarEliminados.isSelected();
        cargarDatos();
    }//GEN-LAST:event_ckbMostrarEliminadosActionPerformed

    private void btnReactivarEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReactivarEmpleadoMouseClicked
    }//GEN-LAST:event_btnReactivarEmpleadoMouseClicked

    private void btnReactivarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReactivarEmpleadoActionPerformed
        if(!mostrarObjetosEliminados) {
            return;
        }
        
        int row = tablaEmpleados.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un empleado a reactivar");
            return;
        }

        int empleadoId = (int) tablaEmpleados.getValueAt(row, 0);
        String nombreEmpleado = (String) tablaEmpleados.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Estas seguro que quieres reactivar este empleado: " + nombreEmpleado + "?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        EmpleadosDAO empleadosDAO = new EmpleadosDAO();
        try {
            boolean eliminado;
            eliminado = empleadosDAO.reactivarEmpleado(empleadoId);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Empleado reactivado");
                cargarDatos(); // actualiza la tabla
            } else {
                JOptionPane.showMessageDialog(this, "Empleado no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reactivando Empleado");
        }
    }//GEN-LAST:event_btnReactivarEmpleadoActionPerformed

    private void btnEliminarEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEmpleadosActionPerformed
        int row = tablaEmpleados.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un empleado a eliminar");
            return;
        }

        int empleadoId = (int) tablaEmpleados.getValueAt(row, 0);
        String nombreEmpleado = (String) tablaEmpleados.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Estas seguro que quieres eliminar este empleado: " + nombreEmpleado + "?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        EmpleadosDAO empleadosDAO = new EmpleadosDAO();
        try {
            boolean eliminado;
            eliminado = empleadosDAO.eliminarEmpleado(empleadoId);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado");
                cargarDatos(); // actualiza la tabla
            } else {
                JOptionPane.showMessageDialog(this, "Empleado no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error eliminando Empleado");
        }
    }//GEN-LAST:event_btnEliminarEmpleadosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCrearEmpleado;
    private javax.swing.JButton btnEliminarEmpleados;
    public javax.swing.JButton btnModificarEmpleado;
    public javax.swing.JButton btnReactivarEmpleado;
    private javax.swing.JCheckBox ckbMostrarEliminados;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscarEmpleado;
    private javax.swing.JLabel lblTablaEmpleados;
    private javax.swing.JTable tablaEmpleados;
    private javax.swing.JTextField txtfBuscarEmpleado;
    // End of variables declaration//GEN-END:variables
}
