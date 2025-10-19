package Modelos;

public class DetalleItemPresConNombre {
    private int idDetalleItemPres;
    private float precioUnitario;
    private int cantidad;
    private int items_idItems;
    private String nombreItem;
    private String descripcionItem;

    // Constructor
    public DetalleItemPresConNombre(int idDetalleItemPres, float precioUnitario, int cantidad,
                                    int items_idItems, String nombreItem, String descripcionItem) {
        this.idDetalleItemPres = idDetalleItemPres;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.items_idItems = items_idItems;
        this.nombreItem = nombreItem;
        this.descripcionItem = descripcionItem;
    }

    public int getIdDetalleItemPres() {
        return idDetalleItemPres;
    }

    public void setIdDetalleItemPres(int idDetalleItemPres) {
        this.idDetalleItemPres = idDetalleItemPres;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getItems_idItems() {
        return items_idItems;
    }

    public void setItems_idItems(int items_idItems) {
        this.items_idItems = items_idItems;
    }

    public String getNombreItem() {
        return nombreItem;
    }

    public void setNombreItem(String nombreItem) {
        this.nombreItem = nombreItem;
    }

    public String getDescripcionItem() {
        return descripcionItem;
    }

    public void setDescripcionItem(String descripcionItem) {
        this.descripcionItem = descripcionItem;
    }  
}
