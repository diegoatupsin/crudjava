package Paneles;

import Formularios.FormAgregarUsuario;
import Modelos.Usuarios;
import dao.UsuariosDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class AdministracionPanel extends javax.swing.JPanel {
    
    private boolean mostrarObjetosEliminados = false;

    public AdministracionPanel(Usuarios user) {        
        initComponents();
        
        cargarDatos();
    }
    
    // se llama desde formAgregarUsuario
    public void cargarDatos() {
        UsuariosDAO dao = new UsuariosDAO();
        List<Usuarios> users = dao.obtenerUsuarios();
        
        int adminNum = 0;
        int jardinerosNum = 0;
        int vendedoresNum = 0;
         
        // cuenta los tipos de usuario en la base de datos
        for (Usuarios u : users) {
            if (u.getEstatusUsuario() == 1) {
                switch (u.getTipoUsuario()) {
                    case "admin" -> adminNum++;
                    case "jardinero" -> jardinerosNum++;
                    default -> vendedoresNum++;
                }
            }
        }   
        
        // actualizar las labels de resumen
        lblUsuariosTotales.setText("Usuarios totales: " + users.size());
        lblAdministradores.setText("Administradores: " + adminNum);
        lblJardineros.setText("Jardineros: " + jardinerosNum);
        lblVendedores.setText("Vendedores: " + vendedoresNum);
        
        // llenar la tabla
        DefaultTableModel model = new DefaultTableModel(new String[]{"idUsuario", "Usuario", "Tipo", "id_empleado"}, 0);
        for (Usuarios u : users) {
            if (!mostrarObjetosEliminados) {
                if (u.getEstatusUsuario() == 1) {
                    model.addRow(new Object[]{
                        u.getIdUsuarios(), 
                        u.getNombreUser(), 
                        u.getTipoUsuario(), 
                        u.getEmpleados_idEmpleados()
                    });
                }
            } else {
                if (u.getEstatusUsuario() == 0) {
                    model.addRow(new Object[]{
                        u.getIdUsuarios(), 
                        u.getNombreUser(), 
                        u.getTipoUsuario(), 
                        u.getEmpleados_idEmpleados()
                    });
                }
            }
        }
        tablaUsuarios.setModel(model);
        
        
        // implementacion de busqueda
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tablaUsuarios.setRowSorter(sorter);
        
        txtfBuscarUsuario.getDocument().addDocumentListener(new DocumentListener() {
            
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
            String text = txtfBuscarUsuario.getText();
            if (text.trim().length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        }
        });

    }
    
    // Selecciona una fila de la tabla, entonces elimina ese registro
    private void deleteSelectedUser() {
        int row = tablaUsuarios.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un usuario a eliminar.");
            return;
        }

        int userId = (int) tablaUsuarios.getValueAt(row, 0);
        String userName = (String) tablaUsuarios.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Estas seguro que quieres eliminar este usuario: " + userName + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // el dao llama a eliminar, para poder eliminar un usuario
        UsuariosDAO dao = new UsuariosDAO();
        
        try {
            boolean deleted;
            deleted = dao.eliminar(userId);

            if (deleted) {
                ((DefaultTableModel) tablaUsuarios.getModel()).removeRow(row);
                JOptionPane.showMessageDialog(this, "Usuario eliminado.");
                cargarDatos(); // actualiza los labels de resumen
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error eliminando usuario.");
        }
    }
    
    private void cambiarContrasenaUsuario() {
        int columna = tablaUsuarios.getSelectedRow();
        if (columna == -1) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un usuario.");
            return;
        }
        int userId = (int) tablaUsuarios.getValueAt(columna, 0);
        String nombreUsuario = (String) tablaUsuarios.getValueAt(columna, 1);
        
        // preguntar por la contrasena
        JPasswordField passPassword = new JPasswordField();
        int opcion = JOptionPane.showConfirmDialog(this, passPassword, "Ingresa la nueva contrasena para " + nombreUsuario, JOptionPane.OK_CANCEL_OPTION);
        
        if (opcion == JOptionPane.OK_OPTION) {
            String nuevaPassword = new String(passPassword.getPassword());
            
            if (nuevaPassword.length() < 4) {
                JOptionPane.showMessageDialog(this, "La contrasena debe de contener almenos 4 caracteres.");
                return;
            }
            
            // Hash la nueva contrasena
            String hashedPassword = BCrypt.hashpw(nuevaPassword, BCrypt.gensalt());
            
            // Llamar el dao para actualizar
            UsuariosDAO dao = new UsuariosDAO();
            boolean ok = dao.actualizarPassword(userId, hashedPassword);
            
            if (ok) {
                JOptionPane.showMessageDialog(this, "Contrasena actualizada para: " + nombreUsuario + ".");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo cambiar la contrasena");
            }
        }
    }
    
    // esto es el boton de autodestruccion hehe
    public void borrarTodosLosDatos() {
        String[] tablas = {
            "Clientes",
            "CorreoEmpleados",
            "CorreosClie",
            "CorreosProveedores",
            "DetalleItemPres",
            "Empleados",
            "errores",
            "Items",
            "Presupuestos",
            "Proveedores",
            "TelefonosClientes",
            "TelefonosEmpleados",
            "TelefonosProveedores",
            "Usuarios"
        };

        String url = "jdbc:mysql://localhost:3306/proyectojardines";
        String user = "root";
        String password = "password";
                
        
        try (Connection conn = DriverManager.getConnection(url, user, password); Statement stmt = conn.createStatement()) {
            // Disable foreign key checks (MySQL)
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            for (String tabla : tablas) {
                stmt.executeUpdate("DELETE FROM " + tabla);
            }

            // Optionally reset AUTO_INCREMENT counters
            for (String tabla : tablas) {
                stmt.executeUpdate("ALTER TABLE " + tabla + " AUTO_INCREMENT = 1");
            }

            // Re-enable foreign key checks
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error during autodestruction:\n" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        resumenPanel = new javax.swing.JPanel();
        lblUsuariosTotales = new javax.swing.JLabel();
        lblAdministradores = new javax.swing.JLabel();
        lblJardineros = new javax.swing.JLabel();
        lblVendedores = new javax.swing.JLabel();
        buscarUsuarioPanel = new javax.swing.JPanel();
        btnAutodestruccion = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblBuscarUsuario = new javax.swing.JLabel();
        txtfBuscarUsuario = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnAnadirUsuario = new javax.swing.JButton();
        btnEliminarUsuario = new javax.swing.JButton();
        btnCambiarCon = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        ckbMostrarEliminados = new javax.swing.JCheckBox();
        btnReactivarCliente = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(900, 700));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Administracion");

        resumenPanel.setBackground(new java.awt.Color(255, 255, 255));

        lblUsuariosTotales.setText("Usuarios totales: 0");

        lblAdministradores.setText("Administradores: 0");

        lblJardineros.setText("Jardineros: 0");

        lblVendedores.setText("Vendedores: 0");

        javax.swing.GroupLayout resumenPanelLayout = new javax.swing.GroupLayout(resumenPanel);
        resumenPanel.setLayout(resumenPanelLayout);
        resumenPanelLayout.setHorizontalGroup(
            resumenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resumenPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsuariosTotales)
                .addGap(42, 42, 42)
                .addComponent(lblAdministradores)
                .addGap(38, 38, 38)
                .addComponent(lblJardineros)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblVendedores)
                .addGap(22, 22, 22))
        );
        resumenPanelLayout.setVerticalGroup(
            resumenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resumenPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(resumenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuariosTotales)
                    .addComponent(lblAdministradores)
                    .addComponent(lblJardineros)
                    .addComponent(lblVendedores))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buscarUsuarioPanel.setBackground(new java.awt.Color(255, 255, 255));
        buscarUsuarioPanel.setForeground(new java.awt.Color(102, 112, 133));

        javax.swing.GroupLayout buscarUsuarioPanelLayout = new javax.swing.GroupLayout(buscarUsuarioPanel);
        buscarUsuarioPanel.setLayout(buscarUsuarioPanelLayout);
        buscarUsuarioPanelLayout.setHorizontalGroup(
            buscarUsuarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        buscarUsuarioPanelLayout.setVerticalGroup(
            buscarUsuarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnAutodestruccion.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnAutodestruccion.setForeground(new java.awt.Color(102, 112, 133));
        btnAutodestruccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fire_esc.png"))); // NOI18N
        btnAutodestruccion.setText("Autodestruccion");
        btnAutodestruccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAutodestruccionMouseClicked(evt);
            }
        });
        btnAutodestruccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutodestruccionActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblBuscarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-búsqueda-20.png"))); // NOI18N
        lblBuscarUsuario.setText("Buscar:");

        txtfBuscarUsuario.setPreferredSize(new java.awt.Dimension(64, 30));
        txtfBuscarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfBuscarUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBuscarUsuario)
                .addGap(18, 18, 18)
                .addComponent(txtfBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscarUsuario)
                    .addComponent(txtfBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnAnadirUsuario.setBackground(new java.awt.Color(52, 199, 89));
        btnAnadirUsuario.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnAnadirUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnAnadirUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-añadir-20.png"))); // NOI18N
        btnAnadirUsuario.setText("Anadir usuario");
        btnAnadirUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnadirUsuarioMouseClicked(evt);
            }
        });
        btnAnadirUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirUsuarioActionPerformed(evt);
            }
        });

        btnEliminarUsuario.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnEliminarUsuario.setForeground(new java.awt.Color(102, 112, 133));
        btnEliminarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-eliminar-20.png"))); // NOI18N
        btnEliminarUsuario.setText("Eliminar");
        btnEliminarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarUsuarioMouseClicked(evt);
            }
        });
        btnEliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUsuarioActionPerformed(evt);
            }
        });

        btnCambiarCon.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCambiarCon.setForeground(new java.awt.Color(102, 112, 133));
        btnCambiarCon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-editar-20.png"))); // NOI18N
        btnCambiarCon.setText("Cambiar contrasena");
        btnCambiarCon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCambiarConMouseClicked(evt);
            }
        });
        btnCambiarCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarConActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEliminarUsuario)
                .addGap(18, 18, 18)
                .addComponent(btnCambiarCon)
                .addGap(18, 18, 18)
                .addComponent(btnAnadirUsuario)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCambiarCon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnadirUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "nombre", "tipo", "id_empleado"
            }
        ));
        jScrollPane1.setViewportView(tablaUsuarios);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 805, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                .addGap(50, 50, 50))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        ckbMostrarEliminados.setText("Mostrar usuarios eliminados");
        ckbMostrarEliminados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckbMostrarEliminadosActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ckbMostrarEliminados)
                .addGap(18, 18, 18)
                .addComponent(btnReactivarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReactivarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckbMostrarEliminados))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(buscarUsuarioPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(82, 82, 82)
                        .addComponent(resumenPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(50, 50, 50))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(101, 101, 101)
                        .addComponent(btnAutodestruccion)
                        .addGap(101, 101, 101))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(resumenPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel9)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(buscarUsuarioPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAutodestruccion, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnadirUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnadirUsuarioMouseClicked
        // Pasa el admin panel actual a form agregar usuario
        FormAgregarUsuario form = new FormAgregarUsuario(this);
        form.setVisible(true);
    }//GEN-LAST:event_btnAnadirUsuarioMouseClicked

    private void btnAnadirUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirUsuarioActionPerformed
    }//GEN-LAST:event_btnAnadirUsuarioActionPerformed

    private void btnEliminarUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarUsuarioMouseClicked
        deleteSelectedUser();
    }//GEN-LAST:event_btnEliminarUsuarioMouseClicked

    private void btnEliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUsuarioActionPerformed
    }//GEN-LAST:event_btnEliminarUsuarioActionPerformed

    private void txtfBuscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfBuscarUsuarioActionPerformed
    }//GEN-LAST:event_txtfBuscarUsuarioActionPerformed

    private void btnCambiarConMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCambiarConMouseClicked
        cambiarContrasenaUsuario();
    }//GEN-LAST:event_btnCambiarConMouseClicked

    private void btnCambiarConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarConActionPerformed
    }//GEN-LAST:event_btnCambiarConActionPerformed

    private void btnAutodestruccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAutodestruccionMouseClicked
        String input = JOptionPane.showInputDialog("Escribe 'hola' para confirmar la eliminacion");
        if ("hola".equals(input)) {
            borrarTodosLosDatos();
            System.exit(0);
        }
    }//GEN-LAST:event_btnAutodestruccionMouseClicked

    private void btnAutodestruccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutodestruccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAutodestruccionActionPerformed

    private void ckbMostrarEliminadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckbMostrarEliminadosActionPerformed
        mostrarObjetosEliminados = ckbMostrarEliminados.isSelected();
        cargarDatos();
    }//GEN-LAST:event_ckbMostrarEliminadosActionPerformed

    private void btnReactivarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReactivarClienteMouseClicked
        if(!mostrarObjetosEliminados) {
            return;
        }

        int row = tablaUsuarios.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un usuario a reactivar");
            return;
        }

        int userId = (int) tablaUsuarios.getValueAt(row, 0);
        String userName = (String) tablaUsuarios.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Estas seguro que quieres reactivar este usuario: " + userName + "?",
            "Confirmar reactivacion",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // el dao llama a eliminar, para poder eliminar un usuario
        UsuariosDAO dao = new UsuariosDAO();
        
        try {
            boolean deleted;
            deleted = dao.reactivarUsuario(userId);

            if (deleted) {
                ((DefaultTableModel) tablaUsuarios.getModel()).removeRow(row);
                JOptionPane.showMessageDialog(this, "Usuario reactivado.");
                cargarDatos(); // actualiza los labels de resumen
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reactivando usuario.");
        }
    }//GEN-LAST:event_btnReactivarClienteMouseClicked

    private void btnReactivarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReactivarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnReactivarClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAnadirUsuario;
    public javax.swing.JButton btnAutodestruccion;
    public javax.swing.JButton btnCambiarCon;
    public javax.swing.JButton btnEliminarUsuario;
    public javax.swing.JButton btnReactivarCliente;
    private javax.swing.JPanel buscarUsuarioPanel;
    private javax.swing.JCheckBox ckbMostrarEliminados;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAdministradores;
    private javax.swing.JLabel lblBuscarUsuario;
    private javax.swing.JLabel lblJardineros;
    private javax.swing.JLabel lblUsuariosTotales;
    private javax.swing.JLabel lblVendedores;
    private javax.swing.JPanel resumenPanel;
    private javax.swing.JTable tablaUsuarios;
    private javax.swing.JTextField txtfBuscarUsuario;
    // End of variables declaration//GEN-END:variables
}