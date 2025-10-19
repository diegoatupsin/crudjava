package Formularios;

import Modelos.Empleados;
import Paneles.EmpleadosPanel;
import dao.EmpleadosDAO;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import utils.FormValidator;
import utils.InputFilter;

public class FormEditarEmpleado extends javax.swing.JFrame {

    private Empleados empleado;
    private EmpleadosPanel empleadosPanel;
    
    private DefaultListModel<String> telefonosModel;
    private DefaultListModel<String> correosModel;
    
    public FormEditarEmpleado(Empleados empleado, EmpleadosPanel empleadosPanel) {
        this.empleado = empleado;
        this.empleadosPanel = empleadosPanel;
        
        initComponents();
        
        Image icon = Toolkit.getDefaultToolkit().getImage(FormEditarEmpleado.class.getResource("/Imagenes/logoFlor.png"));
        setIconImage(icon);
        setTitle("Editar empleado");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

         // aplicar el limite a los jtextfields
        ((AbstractDocument) txtfNombreEmpleado.getDocument()).setDocumentFilter(new InputFilter(45, true, false));
        ((AbstractDocument) txtfApellidosEmpleado.getDocument()).setDocumentFilter(new InputFilter(45, true, false));
        ((AbstractDocument) txtfDireccionEmpleado.getDocument()).setDocumentFilter(new InputFilter(45, false, false));
        ((AbstractDocument) txtfNumeroSS.getDocument()).setDocumentFilter(new InputFilter(11, false, true));
        ((AbstractDocument) txtfTelefono.getDocument()).setDocumentFilter(new InputFilter(45, false, true));
        ((AbstractDocument) txtfCorreos.getDocument()).setDocumentFilter(new InputFilter(45, false, false));
        
        cbYear.addItem("--year--"); // placeholder para year
        // Populate years (e.g., from 1980 to current year + 10)
        int currentYear = LocalDate.now().getYear();
        for (int year = 2020; year <= currentYear + 10; year++) {
            cbYear.addItem(year);
        }
        
        cbMonth.addItem("--month--"); // placeholder para month
        // Populate months (1-12)
        for (int month = 1; month <= 12; month++) {
            cbMonth.addItem(month);
        }
        
        llenarCamposConDatos();
    }
    
    private void llenarCamposConDatos() {
        // poner los datos en los txtf
        txtfNombreEmpleado.setText(empleado.getNombreEmp());
        txtfApellidosEmpleado.setText(empleado.getApellidoEmp());
        txtfDireccionEmpleado.setText(empleado.getDireccionEmp());
        txtfNumeroSS.setText(empleado.getNumeroSS());
        cbPuesto.setSelectedItem(empleado.getPuesto());

        
        telefonosModel = new DefaultListModel<>();
        empleado.getNumeroEmp().forEach(telefonosModel::addElement);
        
        correosModel = new DefaultListModel<>();
        empleado.getCorreosEmp().forEach(correosModel::addElement);
        
        telefonosList.setModel(telefonosModel);
        correosList.setModel(correosModel);
        
                // divide la fecha en year month day
        java.sql.Date fecha = empleado.getFechaContrato();
        LocalDate localDate = fecha.toLocalDate();
        
        cbYear.setSelectedItem(localDate.getYear());
        cbMonth.setSelectedIndex(localDate.getMonthValue());
        cbDay.setSelectedItem(localDate.getDayOfMonth());
        

    }
    
    // actualiza los dias de el combo box dependiendo de el mes y year que sea
    // a este metodo se le pasan como argumento comoboxes omggg
    private void updateDays(JComboBox<Object> cbYear, JComboBox<Object> cbMonth, JComboBox<Object> cbDay) {
        cbDay.removeAllItems();
        cbDay.addItem("--day--"); // Add placeholder again

        Object yearObj = cbYear.getSelectedItem();
        Object monthObj = cbMonth.getSelectedItem();

        if (yearObj instanceof Integer && monthObj instanceof Integer) {
            int year = (Integer) yearObj;
            int month = (Integer) monthObj;
        
            YearMonth yearMonth = YearMonth.of(year, month);
            int daysInMonth = yearMonth.lengthOfMonth();
        
            for (int day = 1; day <= daysInMonth; day++) {
                cbDay.addItem(day);
            }
        }
    }
    
    private void guardarDatos() {
        
        String nombre = txtfNombreEmpleado.getText().trim();
        

        List<String> telefonos = Collections.list(telefonosModel.elements()); // convierte el defaultListModel a una list<string> asi se puede usar en el set
        List<String> correos = Collections.list(correosModel.elements());
        
        if (!nombre.isEmpty()) {            
            empleado.setNombreEmp(txtfNombreEmpleado.getText().trim());
            empleado.setApellidoEmp(txtfApellidosEmpleado.getText().trim());
            empleado.setDireccionEmp(txtfDireccionEmpleado.getText().trim());
            empleado.setNumeroSS(txtfNumeroSS.getText().trim());
            empleado.setPuesto( (String) cbPuesto.getSelectedItem());
            
            empleado.setNumeroEmp(telefonos);
            empleado.setCorreosEmp(correos);
            
            // guardar la fecha
            Integer year = (Integer) cbYear.getSelectedItem();
            Integer month = (Integer) cbMonth.getSelectedItem();
            Integer day = (Integer) cbDay.getSelectedItem();

            // comprueba que se haya ingresado una fecha
            // el tipo de la fecha es date pero de sql
            if (year != null && month != null && day != null) {
                LocalDate localDate = LocalDate.of(year, month, day);
                java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

            // Use sqlDate in your PreparedStatement
                empleado.setFechaContrato(sqlDate);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor selecciona una fecha completa.");
            }
            
            EmpleadosDAO dao = new EmpleadosDAO();
            dao.actualizarEmpleado(empleado);
            JOptionPane.showMessageDialog(this, "Empleado guardado");
            
            dispose();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtfTelefono = new javax.swing.JTextField();
        btnAnadirTelefono = new javax.swing.JButton();
        btnEliminarTelefono = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        telefonosList = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        btnAnadirCorreo = new javax.swing.JButton();
        btnEliminarCorreo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        correosList = new javax.swing.JList<>();
        txtfCorreos = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbMonth = new javax.swing.JComboBox<>();
        txtfDireccionEmpleado = new javax.swing.JTextField();
        cbDay = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbYear = new javax.swing.JComboBox<>();
        txtfNumeroSS = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtfApellidosEmpleado = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbPuesto = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtfNombreEmpleado = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(870, 500));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(52, 199, 89));
        jLabel1.setText("Agregar Empleado");

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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAnadirTelefono)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarTelefono))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnAnadirTelefono)
                    .addComponent(btnEliminarTelefono))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

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

        correosList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(correosList);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Correo electrónico");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtfCorreos, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(btnAnadirCorreo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarCorreo)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(btnAnadirCorreo)
                    .addComponent(btnEliminarCorreo))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfCorreos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(410, 400));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombre");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Year");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Dirección");

        cbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMonthActionPerformed(evt);
            }
        });

        txtfDireccionEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfDireccionEmpleadoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Puesto");

        cbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbYearActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Numero de Seguridad Social");

        txtfApellidosEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfApellidosEmpleadoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Month");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Day");

        cbPuesto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona un puesto", "Administrador", "Jardinero", "Vendedor" }));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Apellidos");

        txtfNombreEmpleado.setToolTipText("");
        txtfNombreEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfNombreEmpleadoActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Fecha de contrato");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(cbPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtfNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel2)
                            .addComponent(txtfApellidosEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txtfDireccionEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtfNumeroSS, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 41, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(jLabel10)
                                .addGap(46, 46, 46)
                                .addComponent(jLabel11))
                            .addComponent(jLabel9)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfApellidosEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addGap(6, 6, 6)
                .addComponent(txtfDireccionEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfNumeroSS, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(178, 178, 178)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnGuardar))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(23, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtfApellidosEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfApellidosEmpleadoActionPerformed
    }//GEN-LAST:event_txtfApellidosEmpleadoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtfDireccionEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfDireccionEmpleadoActionPerformed
    }//GEN-LAST:event_txtfDireccionEmpleadoActionPerformed

    private void txtfNombreEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfNombreEmpleadoActionPerformed
    }//GEN-LAST:event_txtfNombreEmpleadoActionPerformed

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

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        // se excluyen los txtfield de correo y telefono porque no necesitan estar llenos
        Set<Component> exclude = new HashSet<>();
        exclude.add(txtfTelefono);
        exclude.add(txtfCorreos);
        
        if (FormValidator.validarCampos(this.getContentPane().getComponents(), exclude)) {
            // todos los comboboxes y los textfield tienen algo asi que ok
            guardarDatos();
            empleadosPanel.cargarDatos();
            dispose(); // cierra el panel
        } else {
            JOptionPane.showMessageDialog(null, "Por favor llena todos los campos.");
        }
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        dispose();
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnEliminarTelefonoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarTelefonoMouseClicked
        // TODO add your handling code here:
        int selectedIndex = telefonosList.getSelectedIndex();
        if (selectedIndex != -1) {
            telefonosModel.removeElementAt(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un teléfono para eliminar.");
        }
    }//GEN-LAST:event_btnEliminarTelefonoMouseClicked

    private void btnEliminarCorreoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarCorreoMouseClicked
        // TODO add your handling code here:

        int selectedIndex = correosList.getSelectedIndex();
        if (selectedIndex != -1) {
            correosModel.removeElementAt(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un teléfono para eliminar.");
        }
    }//GEN-LAST:event_btnEliminarCorreoMouseClicked

    private void cbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbYearActionPerformed
        // TODO add your handling code here:
        updateDays(cbYear, cbMonth, cbDay);
    }//GEN-LAST:event_cbYearActionPerformed

    private void cbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMonthActionPerformed
        // TODO add your handling code here:
        updateDays(cbYear, cbMonth, cbDay);
    }//GEN-LAST:event_cbMonthActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadirCorreo;
    private javax.swing.JButton btnAnadirTelefono;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarCorreo;
    private javax.swing.JButton btnEliminarTelefono;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<Object> cbDay;
    private javax.swing.JComboBox<Object> cbMonth;
    private javax.swing.JComboBox<String> cbPuesto;
    private javax.swing.JComboBox<Object> cbYear;
    private javax.swing.JList<String> correosList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> telefonosList;
    private javax.swing.JTextField txtfApellidosEmpleado;
    private javax.swing.JTextField txtfCorreos;
    private javax.swing.JTextField txtfDireccionEmpleado;
    private javax.swing.JTextField txtfNombreEmpleado;
    private javax.swing.JTextField txtfNumeroSS;
    private javax.swing.JTextField txtfTelefono;
    // End of variables declaration//GEN-END:variables
}
