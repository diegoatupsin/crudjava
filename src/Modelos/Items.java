/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

public class Items {
    
    private int idItems;
    private String NombreItem;
    private String DescripcionItem;
    private int Existencia;
    private float PrecioProducto;
    private String TipoItem;
    private String UnidadItem;
    private int Proveedores_idProveedores;
    private int EstatusItems;
    
    // muestra el nombre de el item cuando se usa en un combobox
    @Override
    public String toString() {
        return NombreItem;
    }

    public Items() {
    }

    public Items(int idItems, String NombreItem, String DescripcionItem, int Existencia, float PrecioProducto, String TipoItem, String UnidadItem, int Proveedores_idProveedores, int EstatusItems) {
        this.idItems = idItems;
        this.NombreItem = NombreItem;
        this.DescripcionItem = DescripcionItem;
        this.Existencia = Existencia;
        this.PrecioProducto = PrecioProducto;
        this.TipoItem = TipoItem;
        this.UnidadItem = UnidadItem;
        this.Proveedores_idProveedores = Proveedores_idProveedores;
        this.EstatusItems = EstatusItems;
    }

    public Items(String NombreItem, String DescripcionItem, int Existencia, float PrecioProducto, String TipoItem, String UnidadItem, int Proveedores_idProveedores, int EstatusItems) {
        this.NombreItem = NombreItem;
        this.DescripcionItem = DescripcionItem;
        this.Existencia = Existencia;
        this.PrecioProducto = PrecioProducto;
        this.TipoItem = TipoItem;
        this.UnidadItem = UnidadItem;
        this.Proveedores_idProveedores = Proveedores_idProveedores;
        this.EstatusItems = EstatusItems;
    }
    
    public int getEstatusItems() {
        return EstatusItems;
    }

    public void setEstatusItems(int EstatusItems) {
        this.EstatusItems = EstatusItems;
    }
    
    public int getIdItems() {
        return idItems;
    }

    public void setIdItems(int idItems) {
        this.idItems = idItems;
    }

    public String getNombreItem() {
        return NombreItem;
    }

    public void setNombreItem(String NombreItem) {
        this.NombreItem = NombreItem;
    }

    public String getDescripcionItem() {
        return DescripcionItem;
    }

    public void setDescripcionItem(String DescripcionItem) {
        this.DescripcionItem = DescripcionItem;
    }

    public int getExistencia() {
        return Existencia;
    }

    public void setExistencia(int Existencia) {
        this.Existencia = Existencia;
    }

    public float getPrecioProducto() {
        return PrecioProducto;
    }

    public void setPrecioProducto(float PrecioProducto) {
        this.PrecioProducto = PrecioProducto;
    }

    public String getTipoItem() {
        return TipoItem;
    }

    public void setTipoItem(String TipoItem) {
        this.TipoItem = TipoItem;
    }

    public String getUnidadItem() {
        return UnidadItem;
    }

    public void setUnidadItem(String UnidadItem) {
        this.UnidadItem = UnidadItem;
    }

    public int getProveedores_idProveedores() {
        return Proveedores_idProveedores;
    }

    public void setProveedores_idProveedores(int Proveedores_idProveedores) {
        this.Proveedores_idProveedores = Proveedores_idProveedores;
    }
}
