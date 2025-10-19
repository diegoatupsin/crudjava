package Modelos;

import java.util.List;

public class Clientes {
    private int idClientes;
    private String NombreClie;
    private String ApellidosClie;
    private String DireccionClie;
    private String RFC_Clie;
    private List<String> NumeroClie;
    private List<String> CorreoClie;
    private int EstatusCliente;
    
    // este override es para cuando se intente anadir un objeto de esta clase a un combo box muestra el siguiente string
    @Override
    public String toString() {
        return NombreClie + " " + ApellidosClie; // This is what will be shown in the JComboBox
    }

    
    public Clientes() {
        
    }

    public Clientes(int idClientes, String NombreClie, String ApellidosClie, String DireccionClie, String RFC_Clie, int EstatusCliente) {
        this.idClientes = idClientes;
        this.NombreClie = NombreClie;
        this.ApellidosClie = ApellidosClie;
        this.DireccionClie = DireccionClie;
        this.RFC_Clie = RFC_Clie;
        this.EstatusCliente = EstatusCliente;
    }
    
    
    // Las listas creo que no se pueden pasar por medio del constructor **revisar esto**
    public Clientes(String NombreClie, String ApellidosClie, String DireccionClie, String RFC_Clie, int EstatusCliente) {
        this.NombreClie = NombreClie;
        this.ApellidosClie = ApellidosClie;
        this.DireccionClie = DireccionClie;
        this.RFC_Clie = RFC_Clie;
        this.EstatusCliente = EstatusCliente;
    }

    public int getEstatusCliente() {
        return EstatusCliente;
    }

    public void setEstatusCliente(int EstatusCliente) {
        this.EstatusCliente = EstatusCliente;
    }
    
    public int getIdClientes() {
        return idClientes;
    }

    public void setIdClientes(int idClientes) {
        this.idClientes = idClientes;
    }

    public String getNombreClie() {
        return NombreClie;
    }

    public void setNombreClie(String NombreClie) {
        this.NombreClie = NombreClie;
    }

    public String getApellidosClie() {
        return ApellidosClie;
    }

    public void setApellidosClie(String ApellidosClie) {
        this.ApellidosClie = ApellidosClie;
    }

    public String getDireccionClie() {
        return DireccionClie;
    }

    public void setDireccionClie(String DireccionClie) {
        this.DireccionClie = DireccionClie;
    }

    public String getRFC_Clie() {
        return RFC_Clie;
    }

    public void setRFC_Clie(String RFC_Clie) {
        this.RFC_Clie = RFC_Clie;
    }

    public List<String> getNumeroClie() {
        return NumeroClie;
    }

    public void setNumeroClie(List<String> NumeroClie) {
        this.NumeroClie = NumeroClie;
    }

    public List<String> getCorreoClie() {
        return CorreoClie;
    }

    public void setCorreoClie(List<String> CorreoClie) {
        this.CorreoClie = CorreoClie;
    }
}
