package Modelos;

import java.util.List;

public class Proveedores {
    private int idProveedores;
    private String NombreProv;
    private String RFCProv;
    private String DireccionProv;
    private List<String> NumeroProv;
    private List<String> CorreoProv;
    private int EstatusProveedor;


    
    // este override es para cuando se intente anadir un objeto de esta clase a un combo box muestra el siguiente string
    @Override
    public String toString() {
        return NombreProv; // This is what will be shown in the JComboBox
    }
    
    public Proveedores() {
    }

    public Proveedores(int idProveedores, String NombreProv, String RFCProv, String DireccionProv, int EstatusProveedor) {
        this.idProveedores = idProveedores;
        this.NombreProv = NombreProv;
        this.RFCProv = RFCProv;
        this.DireccionProv = DireccionProv;
        this.EstatusProveedor = EstatusProveedor;
    }

    public Proveedores(String NombreProv, String RFCProv, String DireccionProv, int EstatusProveedor) {
        this.NombreProv = NombreProv;
        this.RFCProv = RFCProv;
        this.DireccionProv = DireccionProv;
        this.EstatusProveedor = EstatusProveedor;
    }


    
    public int getEstatusProveedor() {
        return EstatusProveedor;
    }

    public void setEstatusProveedor(int EstatusProveedor) {
        this.EstatusProveedor = EstatusProveedor;
    }

    public int getIdProveedores() {
        return idProveedores;
    }

    public void setIdProveedores(int idProveedores) {
        this.idProveedores = idProveedores;
    }

    public String getNombreProv() {
        return NombreProv;
    }

    public void setNombreProv(String NombreProv) {
        this.NombreProv = NombreProv;
    }

    public String getRFCProv() {
        return RFCProv;
    }

    public void setRFCProv(String RFCProv) {
        this.RFCProv = RFCProv;
    }

    public String getDireccionProv() {
        return DireccionProv;
    }

    public void setDireccionProv(String DireccionProv) {
        this.DireccionProv = DireccionProv;
    }

    public List<String> getNumeroProv() {
        return NumeroProv;
    }

    public void setNumeroProv(List<String> NumeroProv) {
        this.NumeroProv = NumeroProv;
    }

    public List<String> getCorreoProv() {
        return CorreoProv;
    }

    public void setCorreoProv(List<String> CorreoProv) {
        this.CorreoProv = CorreoProv;
    }
}