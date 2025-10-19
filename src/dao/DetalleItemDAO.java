package dao;

import Modelos.DetalleItemPres;
import db.dbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DetalleItemDAO {
    
    public void insertarDetalle(DetalleItemPres detalle) {
        String sql = "CALL insertar_detallesitempresupuesto(?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setFloat(1, detalle.getPrecioUnitario());
            stmt.setInt(2, detalle.getCantidad());
            stmt.setInt(3, detalle.getItems_idItems());
            stmt.setInt(4, detalle.getPresupuestos_idPresupuestos());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void eliminarDetallesPorPresupuesto(int idPresupuesto) {
        String sql = "CALL eliminar_detallesporpresupuesto(?)";
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, idPresupuesto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<DetalleItemPres> obtenerDetallePorPresupuesto(int idPresupuesto) {
        List<DetalleItemPres> lista = new ArrayList<>();
        String sql = "CALL obtener_detalle_por_presupuesto(?)";
    
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idPresupuesto);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DetalleItemPres detalle = new DetalleItemPres();
                detalle.setIdDetalleItemPres(rs.getInt("idDetalleItemPres"));
                detalle.setPrecioUnitario(rs.getFloat("PrecioUnitario"));
                detalle.setCantidad(rs.getInt("Cantidad"));
                detalle.setItems_idItems(rs.getInt("Items_idItems"));
                detalle.setPresupuestos_idPresupuestos(rs.getInt("Presupuestos_idPresupuesto"));
                lista.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
