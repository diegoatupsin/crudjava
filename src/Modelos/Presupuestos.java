package Modelos;

import java.sql.Date;

public class Presupuestos {

    private int idPresupuesto;
    private String NombrePres;
    private String Estatus;
    private Date FechaMovimiento;
    private int Clientes_idClientes;
    private int Empleados_idEmpleados;
    private int EstatusPresupuestos;


    public Presupuestos() {
    }

    public Presupuestos(int idPresupuesto, String NombrePres, String Estatus, Date FechaMovimiento, int Clientes_idClientes, int Empleados_idEmpleados, int EstatusPresupuestos) {
        this.idPresupuesto = idPresupuesto;
        this.NombrePres = NombrePres;
        this.Estatus = Estatus;
        this.FechaMovimiento = FechaMovimiento;
        this.Clientes_idClientes = Clientes_idClientes;
        this.Empleados_idEmpleados = Empleados_idEmpleados;
        this.EstatusPresupuestos = EstatusPresupuestos;
    }

    public Presupuestos(String NombrePres, String Estatus, Date FechaMovimiento, int Clientes_idClientes, int Empleados_idEmpleados, int EstatusPresupuestos) {
        this.NombrePres = NombrePres;
        this.Estatus = Estatus;
        this.FechaMovimiento = FechaMovimiento;
        this.Clientes_idClientes = Clientes_idClientes;
        this.Empleados_idEmpleados = Empleados_idEmpleados;
        this.EstatusPresupuestos = EstatusPresupuestos;
    }
    
    
    public int getEstatusPresupuestos() {
        return EstatusPresupuestos;
    }

    public void setEstatusPresupuestos(int EstatusPresupuestos) {
        this.EstatusPresupuestos = EstatusPresupuestos;
    }
    
    public int getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(int idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public String getNombrePres() {
        return NombrePres;
    }

    public void setNombrePres(String NombrePres) {
        this.NombrePres = NombrePres;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String Estatus) {
        this.Estatus = Estatus;
    }

    public Date getFechaMovimiento() {
        return FechaMovimiento;
    }

    public void setFechaMovimiento(Date FechaMovimiento) {
        this.FechaMovimiento = FechaMovimiento;
    }

    public int getClientes_idClientes() {
        return Clientes_idClientes;
    }

    public void setClientes_idClientes(int Clientes_idClientes) {
        this.Clientes_idClientes = Clientes_idClientes;
    }

    public int getEmpleados_idEmpleados() {
        return Empleados_idEmpleados;
    }

    public void setEmpleados_idEmpleados(int Empleados_idEmpleados) {
        this.Empleados_idEmpleados = Empleados_idEmpleados;
    }
}
