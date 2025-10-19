package utils;

import Modelos.Usuarios;

public class manejadorDePermisos {
    private Usuarios usuario;

    public manejadorDePermisos(Usuarios usuario) {
        this.usuario = usuario;
    }

    private String tipo() {
        return usuario.getTipoUsuario().toLowerCase();
    }

    // Administración
    public boolean puedeEntrarAdministracion() {
        return tipo().equals("admin");
    }

    // Clientes
    public boolean puedeEntrarClientes() {
        return tipo().equals("admin") || tipo().equals("vendedor");
    }

    public boolean puedeInsertarClientes() {
        return tipo().equals("admin") || tipo().equals("vendedor");
    }

    public boolean puedeEliminarClientes() {
        return tipo().equals("admin");
    }

    public boolean puedeActualizarClientes() {
        return tipo().equals("admin") || tipo().equals("vendedor");
    }

    public boolean puedeConsultarClientes() {
        return tipo().equals("admin") || tipo().equals("vendedor");
    }

    // Proveedores
    public boolean puedeEntrarProveedores() {
        return tipo().equals("admin") || tipo().equals("vendedor");
    }

    public boolean puedeConsultarProveedores() {
        return tipo().equals("admin") || tipo().equals("vendedor");
    }

    public boolean puedeInsertarProveedores() {
        return tipo().equals("admin");
    }

    public boolean puedeActualizarProveedores() {
        return tipo().equals("admin");
    }

    public boolean puedeEliminarProveedores() {
        return tipo().equals("admin");
    }

    // Empleados
    public boolean puedeEntrarEmpleados() {
        return tipo().equals("admin");
    }
    
    public boolean puedeConsultarEmpleados() {
        return tipo().equals("admin");
    }
    
    public boolean puedeInsertarEmpleados() {
        return tipo().equals("admin");
    }
    
    public boolean puedeActualizarEmpleados() {
        return tipo().equals("admin");
    }
        
    public boolean puedeEliminarEmpleados() {
        return tipo().equals("admin");
    }
            

    // Catálogo (Items)
    public boolean puedeEntrarCatalogo() {
        return tipo().equals("admin") || tipo().equals("vendedor") || tipo().equals("jardinero");
    }

    public boolean puedeConsultarItems() {
        return tipo().equals("admin") || tipo().equals("vendedor") || tipo().equals("jardinero");
    }

    public boolean puedeInsertarItems() {
        return tipo().equals("admin") || tipo().equals("vendedor");
    }

    public boolean puedeActualizarItems() {
        return tipo().equals("admin") || tipo().equals("vendedor") || tipo().equals("jardinero");
    }

    public boolean puedeEliminarItems() {
        return tipo().equals("admin");
    }

    // Presupuestos
    public boolean puedeEntrarPresupuestos() {
        return tipo().equals("admin") || tipo().equals("vendedor");
    }

    public boolean puedeConsultarPresupuestos() {
        return tipo().equals("admin") || tipo().equals("vendedor");
    }

    public boolean puedeInsertarPresupuestos() {
        return tipo().equals("admin") || tipo().equals("vendedor");
    }

    public boolean puedeActualizarPresupuestos() {
        return tipo().equals("admin") || tipo().equals("vendedor");
    }

    public boolean puedeEliminarPresupuestos() {
        return tipo().equals("admin");
    }

    // Inicio
    public boolean puedeEntrarInicio() {
        return true; // todos pueden entrar a inicio
    }
}