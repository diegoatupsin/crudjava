package Formularios;

import Modelos.Clientes;
import Modelos.DetalleItemPres;
import Modelos.DetalleItemPresConNombre;
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

public class FormEditarPresupuesto extends javax.swing.JFrame {
    
    DefaultTableModel modelo = new DefaultTableModel(new String[]{"Id item", "Nombre", "Descripción", "Cantidad", "Precio Unitario", "Subtotal"}, 0);
    private Presupuestos presupuesto;
    private PresupuestosPanel presupuestosPanel;

    
    public FormEditarPresupuesto(Presupuestos presupuesto, PresupuestosPanel presupuestoPanel) {
        this.presupuesto = presupuesto;
        this.presupuestosPanel = presupuestoPanel;
        
        initComponents();
        
        // aplicar el limite a los jtextfields
        ((AbstractDocument) txtfNombrePresupuesto.getDocument()).setDocumentFilter(new InputFilter(45, false, false));
        ((AbstractDocument) txtfCantidad.getDocument()).setDocumentFilter(new InputFilter(10, false, true));
        ((AbstractDocument) txtfPrecioUnitario.getDocument()).setDocumentFilter(new PriceInputFilter(10, 2)); // 10 digitos 2 decimales
        
        Image icon = Toolkit.getDefaultToolkit().getImage(FormEditarPresupuesto.class.getResource("/Imagenes/logoFlor.png"));
        setIconImage(icon);
        setTitle("Editar presupuesto");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // para que solo se cierre este frame

        tablaArticulos.setModel(modelo);        
                
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
        
        // actionlistener para rellenar automaticamente el precio unitario
        cbItem.addActionListener(e -> {
        Items selectedItem = (Items) cbItem.getSelectedItem();
        if (selectedItem != null) {
            txtfPrecioUnitario.setText(String.valueOf(selectedItem.getPrecioProducto()));
        }
        });
        
        llenarCamposConDatos();
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
    
    private void llenarCamposConDatos() {
        txtfNombrePresupuesto.setText(presupuesto.getNombrePres());
        cbEstatus.setSelectedItem(presupuesto.getEstatus());
        
        // divide la fecha en year month day
        java.sql.Date fecha = presupuesto.getFechaMovimiento();
        LocalDate localDate = fecha.toLocalDate();
        
        cbYear.setSelectedItem(localDate.getYear());
        cbMonth.setSelectedIndex(localDate.getMonthValue());
        cbDay.setSelectedItem(localDate.getDayOfMonth());
        
        // no se puede obtener la lista de clientes directamente de la base de datos porque entonces el objeto que se anadio
        // y el nuevo generado son diferentes instacias se necesita  Override equals() and hashCode() en esos modelos
        // pone el cliente en el cb
        for (int i = 0; i < cbClientes.getItemCount(); i++) {
            Clientes c = cbClientes.getItemAt(i);
            if (c != null && c.getIdClientes()== presupuesto.getClientes_idClientes()) {
                cbClientes.setSelectedIndex(i);
                break;
            }
        }
        
        // pone el empleado en el cb
        for (int i = 0; i < cbEmpleados.getItemCount(); i++) {
            Empleados e = cbEmpleados.getItemAt(i);
            if (e != null && e.getIdEmpleados()== presupuesto.getEmpleados_idEmpleados()) {
                cbEmpleados.setSelectedIndex(i);
                break;
            }
        }
        
        
        // llena la tabla con los items
        PresupuestosDAO presupuestosDAO = new PresupuestosDAO();
        List<DetalleItemPresConNombre> detalles = presupuestosDAO.obtenerDetalleItemsPorPresupuestoConNombreIds(presupuesto.getIdPresupuesto());
        
        modelo.setRowCount(0); // limpia las columnas existentes
             
        for (DetalleItemPresConNombre detalle : detalles) {
            Object[] row = {
                detalle.getItems_idItems(),
                detalle.getNombreItem(),  // assuming you added this field
                detalle.getDescripcionItem(),
                (int) detalle.getCantidad(),
                (float) detalle.getPrecioUnitario(),
                detalle.getCantidad() * detalle.getPrecioUnitario()
            };
            modelo.addRow(row);
        }
    }

    private void guardarPresupuesto() {
        String NombrePres = txtfNombrePresupuesto.getText().trim();
        String Estatus = (String) cbEstatus.getSelectedItem();
        
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
            
        // insertar el presupuesto
        PresupuestosDAO presupuestosDAO = new PresupuestosDAO();
        presupuestosDAO.actualizarPresupuesto(presupuesto);
            
        // eliminar los items en detalleItemPres de el presupuesto
        DetalleItemDAO detalleDAO = new DetalleItemDAO();
        System.out.println(presupuesto.getIdPresupuesto());
        detalleDAO.eliminarDetallesPorPresupuesto(presupuesto.getIdPresupuesto());
            
        // obtener las columnas de la tabla de items y guardar en detalles item presupuesto
        for (int i = 0; i < modelo.getRowCount(); i++) {
            int cantidad = Integer.parseInt(modelo.getValueAt(i, 3).toString());
            float precioUnitario = Float.parseFloat(modelo.getValueAt(i, 4).toString());
                
            int idItem = (int) modelo.getValueAt(i, 0);
                
            DetalleItemPres detalle = new DetalleItemPres();
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setItems_idItems(idItem);
            detalle.setPresupuestos_idPresupuestos(presupuesto.getIdPresupuesto());
                
            detalleDAO.insertarDetalle(detalle);
        }
        JOptionPane.showMessageDialog(null, "Presupuesto guardado exitosamente.");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaArticulos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnGuardarPresupuesto = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtfPrecioUnitario = new javax.swing.JTextField();
        txtfCantidad = new javax.swing.JTextField();
        btnAnadirArticulo = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cbItem = new javax.swing.JComboBox<>();
        btnEliminarArticulo = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        cbEmpleados = new javax.swing.JComboBox<>();
        txtfNombrePresupuesto = new javax.swing.JTextField();
        cbYear = new javax.swing.JComboBox<>();
        cbDay = new javax.swing.JComboBox<>();
        cbMonth = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbEstatus = new javax.swing.JComboBox<>();
        cbClientes = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(870, 500));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(52, 199, 89));
        jLabel1.setText("Editar presupuesto");

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnGuardarPresupuesto)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        txtfPrecioUnitario.setPreferredSize(new java.awt.Dimension(64, 32));

        txtfCantidad.setPreferredSize(new java.awt.Dimension(64, 32));

        btnAnadirArticulo.setBackground(new java.awt.Color(248, 250, 252));
        btnAnadirArticulo.setFont(new java.awt.Font("Noto Sans", 1, 13)); // NOI18N
        btnAnadirArticulo.setForeground(new java.awt.Color(102, 112, 133));
        btnAnadirArticulo.setText("Anadir articulo");
        btnAnadirArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnadirArticuloMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Articulos");

        cbItem.setPreferredSize(new java.awt.Dimension(70, 32));
        cbItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbItemActionPerformed(evt);
            }
        });

        btnEliminarArticulo.setBackground(new java.awt.Color(248, 250, 252));
        btnEliminarArticulo.setFont(new java.awt.Font("Noto Sans", 1, 13)); // NOI18N
        btnEliminarArticulo.setForeground(new java.awt.Color(102, 112, 133));
        btnEliminarArticulo.setText("Eliminar articulo");
        btnEliminarArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarArticuloMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(41, 41, 41)
                        .addComponent(btnAnadirArticulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarArticulo))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cbItem, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtfCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtfPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnadirArticulo)
                    .addComponent(btnEliminarArticulo)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        cbEmpleados.setPreferredSize(new java.awt.Dimension(110, 32));
        cbEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEmpleadosActionPerformed(evt);
            }
        });

        txtfNombrePresupuesto.setToolTipText("");
        txtfNombrePresupuesto.setPreferredSize(new java.awt.Dimension(350, 32));
        txtfNombrePresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfNombrePresupuestoActionPerformed(evt);
            }
        });

        cbYear.setPreferredSize(new java.awt.Dimension(100, 32));
        cbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbYearActionPerformed(evt);
            }
        });

        cbDay.setPreferredSize(new java.awt.Dimension(100, 32));
        cbDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDayActionPerformed(evt);
            }
        });

        cbMonth.setPreferredSize(new java.awt.Dimension(100, 32));
        cbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMonthActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombre del presupuesto");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Fecha");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Empleado");

        cbEstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona un estatus", "Aprobado", "Rechazado", "Pendiente" }));
        cbEstatus.setPreferredSize(new java.awt.Dimension(110, 32));
        cbEstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEstatusActionPerformed(evt);
            }
        });

        cbClientes.setPreferredSize(new java.awt.Dimension(110, 32));
        cbClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbClientesActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Estatus");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Cliente");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(txtfNombrePresupuesto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cbEstatus, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(cbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(22, 22, 22)
                .addComponent(txtfNombrePresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel7)
                .addGap(14, 14, 14)
                .addComponent(cbEstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(25, 25, 25))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(1, Short.MAX_VALUE))
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
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtfNombrePresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfNombrePresupuestoActionPerformed
        // TODO add your handling code here:
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
            item.getIdItems(),
            item.getNombreItem(),
            item.getDescripcionItem(),
            cantidad,
            precio,
            subtotal,
        });
    }//GEN-LAST:event_btnAnadirArticuloMouseClicked

    private void btnGuardarPresupuestoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarPresupuestoMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnGuardarPresupuestoMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton2MouseClicked

    private void btnEliminarArticuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarArticuloMouseClicked
        // TODO add your handling code here:
        int filaSeleccionada = tablaArticulos.getSelectedRow();
        if (filaSeleccionada != -1) {
            modelo.removeRow(filaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar.");
        }
    }//GEN-LAST:event_btnEliminarArticuloMouseClicked

    private void cbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbYearActionPerformed
        // TODO add your handling code here:
        updateDays(cbYear, cbMonth, cbDay);
    }//GEN-LAST:event_cbYearActionPerformed

    private void cbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMonthActionPerformed
        // TODO add your handling code here:
        updateDays(cbYear, cbMonth, cbDay);

    }//GEN-LAST:event_cbMonthActionPerformed

    private void cbDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbDayActionPerformed

    private void cbEstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEstatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEstatusActionPerformed

    private void cbEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEmpleadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEmpleadosActionPerformed

    private void cbClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbClientesActionPerformed

    private void cbItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbItemActionPerformed
        // TODO add your handling code here:
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaArticulos;
    private javax.swing.JTextField txtfCantidad;
    private javax.swing.JTextField txtfNombrePresupuesto;
    private javax.swing.JTextField txtfPrecioUnitario;
    // End of variables declaration//GEN-END:variables
}
