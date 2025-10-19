package Modelos;

public class DetalleItemPres {

    private int idDetalleItemPres;
    private float PrecioUnitario;
    private int Cantidad;
    private int Items_idItems;
    private int Presupuestos_idPresupuestos;
    
    public int getIdDetalleItemPres() {
        return idDetalleItemPres;
    }

    public int getItems_idItems() {
        return Items_idItems;
    }

    public void setItems_idItems(int Items_idItems) {
        this.Items_idItems = Items_idItems;
    }

    public int getPresupuestos_idPresupuestos() {
        return Presupuestos_idPresupuestos;
    }

    public void setPresupuestos_idPresupuestos(int Presupuestos_idPresupuestos) {
        this.Presupuestos_idPresupuestos = Presupuestos_idPresupuestos;
    }

    public void setIdDetalleItemPres(int idDetalleItemPres) {
        this.idDetalleItemPres = idDetalleItemPres;
    }

    public float getPrecioUnitario() {
        return PrecioUnitario;
    }

    public void setPrecioUnitario(float PrecioUnitario) {
        this.PrecioUnitario = PrecioUnitario;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }
}
