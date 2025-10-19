package Formularios;

import Modelos.Clientes;
import Modelos.DetalleItemPres;
import Modelos.Empleados;
import Modelos.Items;
import Modelos.Presupuestos;
import Paneles.PresupuestosPanel;
import dao.ClientesDAO;
import dao.DetalleItemDAO;
import dao.EmpleadosDAO;
import dao.ItemsDAO;
import dao.PresupuestosDAO;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import utils.FormValidator;
import utils.InputFilter;
import utils.PriceInputFilter;

public class FormAgregarPresupuesto extends javax.swing.JFrame {
    
    DefaultTableModel modelo = new DefaultTableModel(new String[]{"Nombre", "Descripción", "Cantidad", "Precio Unitario", "Subtotal"}, 0);

    private PresupuestosPanel presupuestosPanel;
    
    public FormAgregarPresupuesto(PresupuestosPanel presupuestosPanel) {
        this.presupuestosPanel = presupuestosPanel;
        
        initComponents();
        
        Image icon = Toolkit.getDefaultToolkit().getImage(FormAgregarPresupuesto.class.getResource("/Imagenes/logoFlor.png"));
        setIconImage(icon);
        setTitle("Agregar presupuesto");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // para que solo se cierre este frame

        tablaArticulos.setModel(modelo);

        // aplicar el limite a los jtextfields
        ((AbstractDocument) txtfNombrePresupuesto.getDocument()).setDocumentFilter(new InputFilter(45, true, false));
        ((AbstractDocument) txtfCantidad.getDocument()).setDocumentFilter(new InputFilter(10, false, true));
        ((AbstractDocument) txtfPrecioUnitario.getDocument()).setDocumentFilter(new PriceInputFilter(10, 2)); // 10 digitos 2 decimales
        
        // crear el placeholder para cbClientes
        Clientes placeholderCliente = new Clientes();
        placeholderCliente.setIdClientes(-1);
        placeholderCliente.setNombreClie("Selecciona un cliente");
        placeholderCliente.setApellidosClie("");
        cbClientes.addItem(placeholderCliente);
        
        // pone los clientes en cbClientes
        ClientesDAO clientesDAO = new ClientesDAO();
        List<Clientes> listaClientes = clientesDAO.obtenerClientes();
        
        for (Clientes c : listaClientes) {
            cbClientes.addItem(c);
        }
        
        Empleados placeholderEmpleado = new Empleados();
        placeholderEmpleado.setIdEmpleados(-1);
        placeholderEmpleado.setNombreEmp("Selecciona un empleado");
        placeholderEmpleado.setApellidoEmp("");
        cbEmpleados.addItem(placeholderEmpleado);
        
        // pone los empleados en cbEmpleados
        EmpleadosDAO empleadosDAO = new EmpleadosDAO();
        List<Empleados> listaEmpleados = empleadosDAO.obtenerEmpleados();
        
        for (Empleados e : listaEmpleados) {
            cbEmpleados.addItem(e);
        }
        
        Items placeholderItem = new Items();
        placeholderItem.setIdItems(-1);
        placeholderItem.setNombreItem("Selecciona un item");
        cbItem.addItem(placeholderItem);
        
        // pone los items en cbItems
        ItemsDAO itemsDAO = new ItemsDAO();
        List<Items> listaItems = itemsDAO.obtenerItems();
        
        for (Items i : listaItems) {
            cbItem.addItem(i);
        }
        
        cbYear.addItem("--year--"); // placeholder para year
        // Populate years
        int currentYear = LocalDate.now().getYear();
        for (int year = 2020; year <= currentYear + 10; year++) {
            cbYear.addItem(year);
        }
        
        cbMonth.addItem("--month--"); // placeholder para month
        // Populate months (1-12)
        for (int month = 1; month <= 12; month++) {
            cbMonth.addItem(month);
        }
        
        // Day ComboBox se actualiza en updateDays
        cbDay.addItem("--day--"); // placeholder
        
        // actionlistener para rellenar automaticamente el precio unitario
        cbItem.addActionListener(e -> {
        Items selectedItem = (Items) cbItem.getSelectedItem();
        if (selectedItem != null) {
            txtfPrecioUnitario.setText(String.valueOf(selectedItem.getPrecioProducto()));
        }
        });
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
    

    private void guardarPresupuesto() {
        String NombrePres = txtfNombrePresupuesto.getText().trim();
        String Estatus = (String) cbEstatus.getSelectedItem();
        
        Presupuestos presupuesto = new Presupuestos();
            
        presupuesto.setNombrePres(NombrePres);
        presupuesto.setEstatus(Estatus);
            
        // pone el cliente
        Clientes clienteSeleccionado = (Clientes) cbClientes.getSelectedItem();
        int idCliente = clienteSeleccionado.getIdClientes();
        presupuesto.setClientes_idClientes(idCliente);

        //pone el empleado
        Empleados empleadoSeleccionado = (Empleados) cbEmpleados.getSelectedItem();
        int idEmpleado = empleadoSeleccionado.getIdEmpleados();
        presupuesto.setEmpleados_idEmpleados(idEmpleado);
        
        // guardar la fecha
        Integer year = (Integer) cbYear.getSelectedItem();
        Integer month = (Integer) cbMonth.getSelectedItem();
        Integer day = (Integer) cbDay.getSelectedItem();

        // el tipo de la fecha es date pero de sql
        if (year != null && month != null && day != null) {
            LocalDate localDate = LocalDate.of(year, month, day);
            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

            // Use sqlDate in your PreparedStatement
            presupuesto.setFechaMovimiento(sqlDate);
        } else {
                JOptionPane.showMessageDialog(this, "Por favor selecciona una fecha completa.");
        }
            
        // insertar el presupuesto y obtener el id
        PresupuestosDAO presupuestosDAO = new PresupuestosDAO();
        int idPresupuesto = presupuestosDAO.insertarPresupuesto(presupuesto);
            
        // obtener las columnas de la tabla de items y guardar en detalles item presupuesto
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String nombreItem = (String) modelo.getValueAt(i, 0);
            int cantidad = (int) modelo.getValueAt(i, 2);
            float precioUnitario = (float) modelo.getValueAt(i, 3);
                
            //pasar el nombre de el item a su id
            ItemsDAO itemsDAO = new ItemsDAO();
            int idItem = itemsDAO.obtenerIdPorNombre(nombreItem);
                
            DetalleItemPres detalle = new DetalleItemPres();
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setItems_idItems(idItem);
            detalle.setPresupuestos_idPresupuestos(idPresupuesto);
                
            DetalleItemDAO detalleDAO = new DetalleItemDAO();
            detalleDAO.insertarDetalle(detalle);
        }
        JOptionPane.showMessageDialog(null, "Presupuesto guardado exitosamente.");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnGuardarPresupuesto = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaArticulos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtfPrecioUnitario = new javax.swing.JTextField();
        btnEliminarArticulo = new javax.swing.JButton();
        txtfCantidad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnAnadirArticulo = new javax.swing.JButton();
        cbItem = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        cbEstatus = new javax.swing.JComboBox<>();
        cbClientes = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtfNombrePresupuesto = new javax.swing.JTextField();
        cbDay = new javax.swing.JComboBox<>();
        cbMonth = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbYear = new javax.swing.JComboBox<>();
        cbEmpleados = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(870, 500));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(52, 199, 89));
        jLabel1.setText("Crear presupuesto");

        btnGuardarPresupuesto.setBackground(new java.awt.Color(52, 199, 89));
        btnGuardarPresupuesto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardarPresupuesto.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarPresupuesto.setText("Guardar");
        btnGuardarPresupuesto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarPresupuestoMouseClicked(evt);
            }
        });
        btnGuardarPresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPresupuestoActionPerformed(evt);
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

        tablaArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaArticulos);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        txtfPrecioUnitario.setPreferredSize(new java.awt.Dimension(64, 32));

        btnEliminarArticulo.setBackground(new java.awt.Color(248, 250, 252));
        btnEliminarArticulo.setFont(new java.awt.Font("Noto Sans", 1, 13)); // NOI18N
        btnEliminarArticulo.setForeground(new java.awt.Color(102, 112, 133));
        btnEliminarArticulo.setText("Eliminar articulo");
        btnEliminarArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarArticuloMouseClicked(evt);
            }
        });

        txtfCantidad.setPreferredSize(new java.awt.Dimension(64, 32));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Articulos");

        btnAnadirArticulo.setBackground(new java.awt.Color(248, 250, 252));
        btnAnadirArticulo.setFont(new java.awt.Font("Noto Sans", 1, 13)); // NOI18N
        btnAnadirArticulo.setForeground(new java.awt.Color(102, 112, 133));
        btnAnadirArticulo.setText("Anadir articulo");
        btnAnadirArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnadirArticuloMouseClicked(evt);
            }
        });

        cbItem.setPreferredSize(new java.awt.Dimension(70, 32));
        cbItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbItem, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtfCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtfPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(41, 41, 41)
                        .addComponent(btnAnadirArticulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarArticulo)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnAnadirArticulo)
                    .addComponent(btnEliminarArticulo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        cbEstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona un estatus", "Aprobado", "Rechazado", "Pendiente" }));
        cbEstatus.setPreferredSize(new java.awt.Dimension(101, 32));
        cbEstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEstatusActionPerformed(evt);
            }
        });

        cbClientes.setPreferredSize(new java.awt.Dimension(350, 32));
        cbClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbClientesActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombre del presupuesto");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Cliente");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Fecha");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Estatus");

        txtfNombrePresupuesto.setToolTipText("");
        txtfNombrePresupuesto.setPreferredSize(new java.awt.Dimension(110, 32));
        txtfNombrePresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfNombrePresupuestoActionPerformed(evt);
            }
        });

        cbDay.setPreferredSize(new java.awt.Dimension(72, 32));
        cbDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDayActionPerformed(evt);
            }
        });

        cbMonth.setPreferredSize(new java.awt.Dimension(72, 32));
        cbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMonthActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Empleado");

        cbYear.setPreferredSize(new java.awt.Dimension(100, 32));
        cbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbYearActionPerformed(evt);
            }
        });

        cbEmpleados.setPreferredSize(new java.awt.Dimension(350, 32));
        cbEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEmpleadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbDay, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(cbEstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(84, 84, 84)))
                    .addComponent(txtfNombrePresupuesto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbEmpleados, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtfNombrePresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(cbEstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
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
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnGuardarPresupuesto))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardarPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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

    private void btnGuardarPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPresupuestoActionPerformed
        Set<Component> exclude = new HashSet<>();
        exclude.add(txtfCantidad);
        exclude.add(txtfPrecioUnitario);
        exclude.add(cbItem);
        
        if (FormValidator.validarCampos(this.getContentPane().getComponents(), exclude)) {
            // todos los comboboxes y los textfield tienen algo asi que ok
            guardarPresupuesto();
            presupuestosPanel.cargarDatos();
            dispose(); // cierra el panel
        } else {
            JOptionPane.showMessageDialog(null, "Por favor llena todos los campos.");
        }
    }//GEN-LAST:event_btnGuardarPresupuestoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtfNombrePresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfNombrePresupuestoActionPerformed
    }//GEN-LAST:event_txtfNombrePresupuestoActionPerformed

    private void btnAnadirArticuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnadirArticuloMouseClicked
        Items item = (Items) cbItem.getSelectedItem();
        if (item == null) return;

        // Validate cantidad
        String cantidadStr = txtfCantidad.getText().trim();
        if (cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La cantidad no puede estar vacía.");
            return;
        }

        // Validate precio
        String precioStr = txtfPrecioUnitario.getText().trim();
        if (precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El precio unitario no puede estar vacío.");
            return;
        }

        int cantidad;
        float precio;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            precio = Float.parseFloat(precioStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad o precio inválido. Asegúrate de ingresar números válidos.");
            return;
        }

        float subtotal = cantidad * precio;

        modelo.addRow(new Object[]{
            item.getNombreItem(),
            item.getDescripcionItem(),
            cantidad,
            precio,
            subtotal,
        });
    }//GEN-LAST:event_btnAnadirArticuloMouseClicked

    private void btnGuardarPresupuestoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarPresupuestoMouseClicked
        
    }//GEN-LAST:event_btnGuardarPresupuestoMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        dispose();
    }//GEN-LAST:event_jButton2MouseClicked

    private void btnEliminarArticuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarArticuloMouseClicked
        int filaSeleccionada = tablaArticulos.getSelectedRow();
        if (filaSeleccionada != -1) {
            modelo.removeRow(filaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar.");
        }
    }//GEN-LAST:event_btnEliminarArticuloMouseClicked

    private void cbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbYearActionPerformed
        updateDays(cbYear, cbMonth, cbDay);
    }//GEN-LAST:event_cbYearActionPerformed

    private void cbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMonthActionPerformed
        updateDays(cbYear, cbMonth, cbDay);

    }//GEN-LAST:event_cbMonthActionPerformed

    private void cbDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDayActionPerformed
    }//GEN-LAST:event_cbDayActionPerformed

    private void cbEstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEstatusActionPerformed
    }//GEN-LAST:event_cbEstatusActionPerformed

    private void cbEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEmpleadosActionPerformed
    }//GEN-LAST:event_cbEmpleadosActionPerformed

    private void cbClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbClientesActionPerformed
    }//GEN-LAST:event_cbClientesActionPerformed

    private void cbItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbItemActionPerformed
    }//GEN-LAST:event_cbItemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadirArticulo;
    private javax.swing.JButton btnEliminarArticulo;
    private javax.swing.JButton btnGuardarPresupuesto;
    private javax.swing.JComboBox<Clientes> cbClientes;
    private javax.swing.JComboBox<Object> cbDay;
    private javax.swing.JComboBox<Empleados> cbEmpleados;
    private javax.swing.JComboBox<String> cbEstatus;
    private javax.swing.JComboBox<Items> cbItem;
    private javax.swing.JComboBox<Object> cbMonth;
    private javax.swing.JComboBox<Object> cbYear;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaArticulos;
    private javax.swing.JTextField txtfCantidad;
    private javax.swing.JTextField txtfNombrePresupuesto;
    private javax.swing.JTextField txtfPrecioUnitario;
    // End of variables declaration//GEN-END:variables
}
