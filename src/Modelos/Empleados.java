package Modelos;

import java.sql.Date;
import java.util.List;

public class Empleados {

    private int idEmpleados;
    private String NombreEmp;
    private String ApellidoEmp;
    private String NumeroSS;
    private String Puesto;
    private Date FechaContrato;
    private String DireccionEmp;
    private List<String> CorreosEmp;
    private List<String> NumeroEmp;
    private int EstatusEmpleado;


    // muestra el nombre de el empleado cuando se usa en un combobox
    @Override
    public String toString() {
        return NombreEmp + " " + ApellidoEmp;
    }
    
    public Empleados() {
        
    }

    public Empleados(int idEmpleados, String NombreEmp, String ApellidoEmp, String NumeroSS, String Puesto, Date FechaContrato, String DireccionEmp, int EstatusEmpleado) {
        this.idEmpleados = idEmpleados;
        this.NombreEmp = NombreEmp;
        this.ApellidoEmp = ApellidoEmp;
        this.NumeroSS = NumeroSS;
        this.Puesto = Puesto;
        this.FechaContrato = FechaContrato;
        this.DireccionEmp = DireccionEmp;
        this.EstatusEmpleado = EstatusEmpleado;
    }

    public Empleados(String NombreEmp, String ApellidoEmp, String NumeroSS, String Puesto, Date FechaContrato, String DireccionEmp, int EstatusEmpleado) {
        this.NombreEmp = NombreEmp;
        this.ApellidoEmp = ApellidoEmp;
        this.NumeroSS = NumeroSS;
        this.Puesto = Puesto;
        this.FechaContrato = FechaContrato;
        this.DireccionEmp = DireccionEmp;
        this.EstatusEmpleado = EstatusEmpleado;
    }
    
    public int getEstatusEmpleado() {
        return EstatusEmpleado;
    }

    public void setEstatusEmpleado(int EstatusEmpleado) {
        this.EstatusEmpleado = EstatusEmpleado;
    }
    
    
    public List<String> getCorreosEmp() {
        return CorreosEmp;
    }

    public void setCorreosEmp(List<String> CorreosEmp) {
        this.CorreosEmp = CorreosEmp;
    }

    public List<String> getNumeroEmp() {
        return NumeroEmp;
    }

    public void setNumeroEmp(List<String> NumeroEmp) {
        this.NumeroEmp = NumeroEmp;
    }
    
    public int getIdEmpleados() {
        return idEmpleados;
    }

    public void setIdEmpleados(int idEmpleados) {
        this.idEmpleados = idEmpleados;
    }

    public String getNombreEmp() {
        return NombreEmp;
    }

    public void setNombreEmp(String NombreEmp) {
        this.NombreEmp = NombreEmp;
    }

    public String getApellidoEmp() {
        return ApellidoEmp;
    }

    public void setApellidoEmp(String ApellidoEmp) {
        this.ApellidoEmp = ApellidoEmp;
    }

    public String getNumeroSS() {
        return NumeroSS;
    }

    public void setNumeroSS(String NumeroSS) {
        this.NumeroSS = NumeroSS;
    }

    public String getPuesto() {
        return Puesto;
    }

    public void setPuesto(String Puesto) {
        this.Puesto = Puesto;
    }

    public Date getFechaContrato() {
        return FechaContrato;
    }

    public void setFechaContrato(Date FechaContrato) {
        this.FechaContrato = FechaContrato;
    }

    public String getDireccionEmp() {
        return DireccionEmp;
    }

    public void setDireccionEmp(String DireccionEmp) {
        this.DireccionEmp = DireccionEmp;
    }   
}
