package Frames;

//import Paneles.PresupuestosPanel;
//import Paneles.MainPanel;
import Modelos.Usuarios;
import Paneles.MainPanel;
import Paneles.PresupuestosPanel;
import Paneles.ClientesPanel;
import Paneles.AdministracionPanel;
import Paneles.CatalogoPanel;
import Paneles.EmpleadosPanel;
import Paneles.ProveedoresPanel;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import utils.manejadorDePermisos;

public class MainFrame extends javax.swing.JFrame {

    private Usuarios usuario;
    
    CardLayout cardLayout;
    MainPanel mainPanel;
    public PresupuestosPanel presupuestosPanel;
    public CatalogoPanel catalogoPanel;
    public EmpleadosPanel empleadosPanel;
    public ProveedoresPanel proveedoresPanel;
    public ClientesPanel clientesPanel;
    AdministracionPanel administracionPanel;
    
    public MainFrame(Usuarios usuario) {
        this.usuario = usuario;
        manejadorDePermisos permisos = new manejadorDePermisos(usuario);
        
        setTitle("BudgetBloom - " + usuario.getTipoUsuario());
        Image icon = Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/Imagenes/logoFlor.png"));
        setIconImage(icon);

        initComponents();
        
        setLocationRelativeTo(null);
        setVisible(true);
        
        btnAdministracion.setEnabled(permisos.puedeEntrarAdministracion());
        btnCatalogo.setEnabled(permisos.puedeEntrarCatalogo());
        btnClientes.setEnabled(permisos.puedeEntrarClientes());
        btnEmpleados.setEnabled(permisos.puedeEntrarEmpleados());
        btnInicio.setEnabled(permisos.puedeEntrarInicio());
        btnPresupuestos.setEnabled(permisos.puedeEntrarPresupuestos());
        btnProveedores.setEnabled(permisos.puedeEntrarProveedores());
        
        // Las siguientes lineas hacen que funcione el cardLayout
        cardLayout = (CardLayout) mainPanelInFrame.getLayout(); // Use the existing layout
        
        // Creacion objetos de los otros jpanels
        mainPanel = new MainPanel(usuario, this);
        presupuestosPanel = new PresupuestosPanel(usuario);
        catalogoPanel = new CatalogoPanel(usuario);
        empleadosPanel = new EmpleadosPanel(usuario);
        proveedoresPanel = new ProveedoresPanel(usuario);    
        clientesPanel = new ClientesPanel(usuario);
        administracionPanel = new AdministracionPanel(usuario);

        // Anade esos jpanel al cardlayout
        mainPanelInFrame.add(mainPanel, "Pagina Principal");
        mainPanelInFrame.add(presupuestosPanel, "Presupuestos");
        mainPanelInFrame.add(catalogoPanel, "Catalogo");
        mainPanelInFrame.add(empleadosPanel, "Empleados");
        mainPanelInFrame.add(proveedoresPanel, "Proveedores");
        mainPanelInFrame.add(clientesPanel, "Clientes");
        mainPanelInFrame.add(administracionPanel, "Administracion");
        
        // Optional: show a default panel
        cardLayout.show(mainPanelInFrame, "Pagina Principal");
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanelInFrame = new javax.swing.JPanel();
        Sidebar = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        Separador1 = new javax.swing.JPanel();
        Inicio = new javax.swing.JPanel();
        btnInicio = new javax.swing.JButton();
        Presupuestos = new javax.swing.JPanel();
        btnPresupuestos = new javax.swing.JButton();
        Catalogo = new javax.swing.JPanel();
        btnCatalogo = new javax.swing.JButton();
        Empleados = new javax.swing.JPanel();
        btnEmpleados = new javax.swing.JButton();
        Proveedores = new javax.swing.JPanel();
        btnProveedores = new javax.swing.JButton();
        Clientes = new javax.swing.JPanel();
        btnClientes = new javax.swing.JButton();
        Administracion = new javax.swing.JPanel();
        btnAdministracion = new javax.swing.JButton();
        Separador2 = new javax.swing.JPanel();
        btnCerrarSesion = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        mainPanelInFrame.setBackground(new java.awt.Color(255, 255, 255));
        mainPanelInFrame.setPreferredSize(new java.awt.Dimension(900, 740));
        mainPanelInFrame.setLayout(new java.awt.CardLayout());

        Sidebar.setBackground(new java.awt.Color(255, 255, 255));
        Sidebar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(234, 236, 240), 3));
        Sidebar.setPreferredSize(new java.awt.Dimension(300, 740));

        lblLogo.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(0, 204, 0));
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logoDEF (1) (3).png"))); // NOI18N

        Separador1.setPreferredSize(new java.awt.Dimension(0, 3));

        javax.swing.GroupLayout Separador1Layout = new javax.swing.GroupLayout(Separador1);
        Separador1.setLayout(Separador1Layout);
        Separador1Layout.setHorizontalGroup(
            Separador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        Separador1Layout.setVerticalGroup(
            Separador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        Inicio.setBackground(new java.awt.Color(255, 255, 255));

        btnInicio.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnInicio.setForeground(new java.awt.Color(102, 112, 133));
        btnInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-casa-20.png"))); // NOI18N
        btnInicio.setText(" Inicio");
        btnInicio.setBorder(null);
        btnInicio.setMargin(new java.awt.Insets(2, 0, 2, 0));
        btnInicio.setPreferredSize(new java.awt.Dimension(50, 17));
        btnInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInicioMouseClicked(evt);
            }
        });
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout InicioLayout = new javax.swing.GroupLayout(Inicio);
        Inicio.setLayout(InicioLayout);
        InicioLayout.setHorizontalGroup(
            InicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        InicioLayout.setVerticalGroup(
            InicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InicioLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(btnInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Presupuestos.setBackground(new java.awt.Color(255, 255, 255));
        Presupuestos.setPreferredSize(new java.awt.Dimension(255, 43));

        btnPresupuestos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPresupuestos.setForeground(new java.awt.Color(102, 112, 133));
        btnPresupuestos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-presupuesto-20.png"))); // NOI18N
        btnPresupuestos.setText(" Presupuestos");
        btnPresupuestos.setBorder(null);
        btnPresupuestos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPresupuestosMouseClicked(evt);
            }
        });
        btnPresupuestos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPresupuestosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PresupuestosLayout = new javax.swing.GroupLayout(Presupuestos);
        Presupuestos.setLayout(PresupuestosLayout);
        PresupuestosLayout.setHorizontalGroup(
            PresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PresupuestosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btnPresupuestos)
                .addContainerGap(82, Short.MAX_VALUE))
        );
        PresupuestosLayout.setVerticalGroup(
            PresupuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PresupuestosLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(btnPresupuestos)
                .addGap(11, 11, 11))
        );

        Catalogo.setBackground(new java.awt.Color(255, 255, 255));
        Catalogo.setPreferredSize(new java.awt.Dimension(211, 45));

        btnCatalogo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnCatalogo.setForeground(new java.awt.Color(102, 112, 133));
        btnCatalogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-planta-en-maceta-20.png"))); // NOI18N
        btnCatalogo.setText(" Catálogo");
        btnCatalogo.setBorder(null);
        btnCatalogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCatalogoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CatalogoLayout = new javax.swing.GroupLayout(Catalogo);
        Catalogo.setLayout(CatalogoLayout);
        CatalogoLayout.setHorizontalGroup(
            CatalogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CatalogoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btnCatalogo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CatalogoLayout.setVerticalGroup(
            CatalogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CatalogoLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(btnCatalogo)
                .addGap(11, 11, 11))
        );

        Empleados.setBackground(new java.awt.Color(255, 255, 255));
        Empleados.setPreferredSize(new java.awt.Dimension(211, 45));

        btnEmpleados.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnEmpleados.setForeground(new java.awt.Color(102, 112, 133));
        btnEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-farmer-20 (1).png"))); // NOI18N
        btnEmpleados.setText(" Empleados");
        btnEmpleados.setBorder(null);
        btnEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpleadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EmpleadosLayout = new javax.swing.GroupLayout(Empleados);
        Empleados.setLayout(EmpleadosLayout);
        EmpleadosLayout.setHorizontalGroup(
            EmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEmpleados)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        EmpleadosLayout.setVerticalGroup(
            EmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEmpleados)
                .addContainerGap())
        );

        Proveedores.setBackground(new java.awt.Color(255, 255, 255));
        Proveedores.setPreferredSize(new java.awt.Dimension(211, 45));

        btnProveedores.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnProveedores.setForeground(new java.awt.Color(102, 112, 133));
        btnProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-proveedor-20.png"))); // NOI18N
        btnProveedores.setText(" Proveedores");
        btnProveedores.setBorder(null);
        btnProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedoresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ProveedoresLayout = new javax.swing.GroupLayout(Proveedores);
        Proveedores.setLayout(ProveedoresLayout);
        ProveedoresLayout.setHorizontalGroup(
            ProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProveedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnProveedores)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ProveedoresLayout.setVerticalGroup(
            ProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ProveedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnProveedores)
                .addContainerGap())
        );

        Clientes.setBackground(new java.awt.Color(255, 255, 255));
        Clientes.setPreferredSize(new java.awt.Dimension(211, 45));

        btnClientes.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnClientes.setForeground(new java.awt.Color(102, 112, 133));
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-clientes-20.png"))); // NOI18N
        btnClientes.setText(" Clientes");
        btnClientes.setBorder(null);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ClientesLayout = new javax.swing.GroupLayout(Clientes);
        Clientes.setLayout(ClientesLayout);
        ClientesLayout.setHorizontalGroup(
            ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnClientes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ClientesLayout.setVerticalGroup(
            ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnClientes)
                .addContainerGap())
        );

        Administracion.setBackground(new java.awt.Color(255, 255, 255));
        Administracion.setPreferredSize(new java.awt.Dimension(211, 45));

        btnAdministracion.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnAdministracion.setForeground(new java.awt.Color(102, 112, 133));
        btnAdministracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8-ajustes-20.png"))); // NOI18N
        btnAdministracion.setText(" Administración");
        btnAdministracion.setBorder(null);
        btnAdministracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAdministracionMouseClicked(evt);
            }
        });
        btnAdministracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdministracionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AdministracionLayout = new javax.swing.GroupLayout(Administracion);
        Administracion.setLayout(AdministracionLayout);
        AdministracionLayout.setHorizontalGroup(
            AdministracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdministracionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdministracion)
                .addContainerGap(135, Short.MAX_VALUE))
        );
        AdministracionLayout.setVerticalGroup(
            AdministracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdministracionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdministracion)
                .addContainerGap())
        );

        Separador2.setPreferredSize(new java.awt.Dimension(0, 3));

        javax.swing.GroupLayout Separador2Layout = new javax.swing.GroupLayout(Separador2);
        Separador2.setLayout(Separador2Layout);
        Separador2Layout.setHorizontalGroup(
            Separador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        Separador2Layout.setVerticalGroup(
            Separador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btnCerrarSesion.setBackground(new java.awt.Color(255, 85, 85));
        btnCerrarSesion.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrarSesion.setText("Cerrar sesion");
        btnCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCerrarSesionMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout SidebarLayout = new javax.swing.GroupLayout(Sidebar);
        Sidebar.setLayout(SidebarLayout);
        SidebarLayout.setHorizontalGroup(
            SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Separador1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
            .addGroup(SidebarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Administracion, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SidebarLayout.createSequentialGroup()
                        .addGroup(SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Empleados, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, SidebarLayout.createSequentialGroup()
                                .addGroup(SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(Inicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Presupuestos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(Proveedores, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                            .addComponent(Clientes, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                            .addComponent(Catalogo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))
                        .addGap(20, 20, 20))
                    .addGroup(SidebarLayout.createSequentialGroup()
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(90, Short.MAX_VALUE))))
            .addComponent(Separador2, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
            .addGroup(SidebarLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(btnCerrarSesion)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        SidebarLayout.setVerticalGroup(
            SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SidebarLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblLogo)
                .addGap(15, 15, 15)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(Inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Presupuestos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Catalogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Empleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Proveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Administracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Separador2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Sidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 24, Short.MAX_VALUE)
                .addComponent(mainPanelInFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Sidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainPanelInFrame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPresupuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPresupuestosActionPerformed
        // TODO add your handling code here:
         if ("admin".equals(usuario.getTipoUsuario()) || "vendedor".equals(usuario.getTipoUsuario())) {
            cardLayout.show(mainPanelInFrame, "Presupuestos");
        } else {
            JOptionPane.showMessageDialog(this, "No tienes permisos. " + usuario.getNombreUser());

        }
    }//GEN-LAST:event_btnPresupuestosActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        // TODO add your handling code here:
        cardLayout.show(mainPanelInFrame, "Clientes");
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        // TODO add your handling code here:
        cardLayout.show(mainPanelInFrame, "Pagina Principal");
        
        // actualizar el contenido de inicio
        mainPanel.cargarDatos();
        mainPanel.cargarItemsMasVendidos(SOMEBITS, HIDE_ON_CLOSE);
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresActionPerformed
        // TODO add your handling code here:
        cardLayout.show(mainPanelInFrame, "Proveedores");
    }//GEN-LAST:event_btnProveedoresActionPerformed

    private void btnCatalogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCatalogoActionPerformed
        // TODO add your handling code here:
        cardLayout.show(mainPanelInFrame, "Catalogo");
    }//GEN-LAST:event_btnCatalogoActionPerformed

    private void btnAdministracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdministracionActionPerformed
        // TODO add your handling code here:
        if ("admin".equals(usuario.getTipoUsuario())) {
            cardLayout.show(mainPanelInFrame, "Administracion");
        } else {
            JOptionPane.showMessageDialog(this, "No tienes permisos. " + usuario.getNombreUser());

        }
    }//GEN-LAST:event_btnAdministracionActionPerformed

    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
        // TODO add your handling code here:
        cardLayout.show(mainPanelInFrame, "Empleados");
    }//GEN-LAST:event_btnEmpleadosActionPerformed

    private void btnAdministracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdministracionMouseClicked

    }//GEN-LAST:event_btnAdministracionMouseClicked

    private void btnInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInicioMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_btnInicioMouseClicked

    private void btnPresupuestosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPresupuestosMouseClicked

    }//GEN-LAST:event_btnPresupuestosMouseClicked

    private void btnCerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCerrarSesionMouseClicked
        // TODO add your handling code here:
        LoginFrame login = new LoginFrame();
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCerrarSesionMouseClicked
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Administracion;
    private javax.swing.JPanel Catalogo;
    private javax.swing.JPanel Clientes;
    private javax.swing.JPanel Empleados;
    private javax.swing.JPanel Inicio;
    private javax.swing.JPanel Presupuestos;
    private javax.swing.JPanel Proveedores;
    private javax.swing.JPanel Separador1;
    private javax.swing.JPanel Separador2;
    private javax.swing.JPanel Sidebar;
    private javax.swing.JButton btnAdministracion;
    private javax.swing.JButton btnCatalogo;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnPresupuestos;
    private javax.swing.JButton btnProveedores;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JPanel mainPanelInFrame;
    // End of variables declaration//GEN-END:variables
}
