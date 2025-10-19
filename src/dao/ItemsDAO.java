package dao;

import Modelos.Items;
import db.dbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsDAO {
    
    public List<Items> obtenerItems() {
        List<Items> items = new ArrayList<>();
        String sql = "{Call obtener_items()}";
        
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql);
            ResultSet rs= stmt.executeQuery()) {
            
            // poner los datos en un objeto de la clase Clientes
            while (rs.next()) {
                int id = rs.getInt("idItems");
                
                
                Items item = new Items(id, 
                    rs.getString("NombreItem"), rs.getString("DescripcionItem"),
                    rs.getInt("Existencia"), rs.getFloat("PrecioProducto"), rs.getString("TipoItem"), rs.getString("UnidadItem"),
                    rs.getInt("Proveedores_idProveedores"), rs.getInt("EstatusItems")
                );
                
                
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    public void insertarItem(Items item) {
        String sql = "{CALL insertar_items(?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setString(1, item.getNombreItem());
            stmt.setString(2, item.getDescripcionItem());
            stmt.setInt(3, item.getExistencia());
            stmt.setFloat(4, item.getPrecioProducto());
            stmt.setString(5, item.getTipoItem());
            stmt.setString(6, item.getUnidadItem());
            stmt.setInt(7, item.getProveedores_idProveedores());
            
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean eliminarItem(int idItem) {
        String sql = "CALL eliminar_items(?)";
        
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, idItem);
            
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        
    public boolean reactivarItem(int idItem) {
        String sql = "CALL reactivar_items(?)";
        
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, idItem);
            
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void actualizarItem(Items item) {
        String sql = "CALL actualizar_items(?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, item.getIdItems());
            stmt.setString(2, item.getNombreItem());
            stmt.setString(3, item.getDescripcionItem());
            stmt.setInt(4, item.getExistencia());
            stmt.setFloat(5, item.getPrecioProducto());
            stmt.setString(6, item.getTipoItem());
            stmt.setString(7, item.getUnidadItem());
            stmt.setInt(8, item.getProveedores_idProveedores());
            
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
        
    public int obtenerIdPorNombre(String nombre) {
        String sql = "CALL obtener_iditem_por_nombre(?)";
        try (Connection conn = dbConnection.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idItems");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public Items obtenerItemPorId(int idItem) {
        String sql = "CALL obtener_item_por_id(?)";
        Items item = new Items();
        
        try (Connection conn = dbConnection.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, idItem);
            ResultSet rs = stmt.executeQuery();
            
            //if en lugar de while porque solo es una
            if (rs.next()) {
                item.setIdItems(rs.getInt("idItems"));
                item.setNombreItem(rs.getString("NombreItem"));
                item.setDescripcionItem(rs.getString("DescripcionItem"));
                item.setExistencia(rs.getInt("Existencia"));
                item.setPrecioProducto(rs.getFloat("PrecioProducto"));
                item.setTipoItem(rs.getString("TipoItem"));
                item.setUnidadItem(rs.getString("UnidadItem"));
                item.setProveedores_idProveedores(rs.getInt("Proveedores_idProveedores"));
                item.setEstatusItems(rs.getInt("EstatusItems"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
    
    public List<Object[]> obtenerItemsMasVendidosPorMes(int mes, int año) {
        List<Object[]> resultados = new ArrayList<>();
        String sql = "CALL obtener_items_mas_vendidos_por_mes(?, ?)";

        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, mes);
            stmt.setInt(2, año);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nombreItem = rs.getString("NombreItem");
                int totalVendido = rs.getInt("TotalVendido");
                int existencia = rs.getInt("Existencia");
                float precio = rs.getFloat("PrecioProducto");

                resultados.add(new Object[]{nombreItem, totalVendido, existencia, precio});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }
    
    public int obtenerTotalItems() {
        String sql = "CALL obtener_total_items(?)";
        int total = 0;

        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.execute();
            total = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
    
    public int obtenerItemsConStockBajo() {
        String sql = "CALL obtener_items_stock_bajo(?)";
        int total = 0;

        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.execute();
            total = stmt.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
}
