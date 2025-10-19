package dao;

import Modelos.DetalleItemPresConNombre;
import Modelos.Presupuestos;
import db.dbConnection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class PresupuestosDAO {

    public List<Presupuestos> obtenerPresupuestos() {
        List<Presupuestos> lista = new ArrayList<>();
        String sql = "CALL obtener_presupuestos()";

        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql);
            ResultSet rs = stmt.executeQuery()) {

            // poner los datos en el modelo (un objeto de la clase Presupuestos)
            while (rs.next()) {
                Presupuestos p = new Presupuestos();
                p.setIdPresupuesto(rs.getInt("idPresupuesto"));
                p.setNombrePres(rs.getString("NombrePres"));
                p.setFechaMovimiento(rs.getDate("FechaMovimiento"));
                p.setEstatus(rs.getString("Estatus"));
                p.setClientes_idClientes(rs.getInt("Clientes_idClientes")); // aqui poner el nombre en vez de el id //omg
                p.setEmpleados_idEmpleados(rs.getInt("Empleados_idEmpleados")); // optional: join with Empleados
                p.setEstatusPresupuestos(rs.getInt("EstatusPresupuestos"));

                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public List<Presupuestos> obtenerUltimosPresupuestos() {
        List<Presupuestos> lista = new ArrayList<>();
        String sql = "CALL ultimos_presupuestos()";

        try  (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Presupuestos p = new Presupuestos();
                p.setIdPresupuesto(rs.getInt("idPresupuesto"));
                p.setNombrePres(rs.getString("NombrePres"));
                p.setEstatus(rs.getString("Estatus"));
                p.setFechaMovimiento(rs.getDate("FechaMovimiento"));
                lista.add(p);
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


    public boolean eliminarPresupuesto(int id) {
        String sql = "{CALL eliminar_presupuestos(?)}";
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            System.out.println(id);
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean reactivarPresupuesto(int id) {
        String sql = "{CALL reactivar_presupuestos(?)}";
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            System.out.println(id);
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<DetalleItemPresConNombre> obtenerDetalleItemsPorPresupuestoConNombreIds(int idPresupuesto) {
        List<DetalleItemPresConNombre> detalles = new ArrayList<>();

        String sql = "CALL obtener_detalleitem_por_presupuesto_connombre(?)";

        try (Connection conn = dbConnection.getConnection();
                CallableStatement stmt = conn.prepareCall(sql)) {
        
                stmt.setInt(1, idPresupuesto);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    DetalleItemPresConNombre detalle = new DetalleItemPresConNombre(
                    rs.getInt("idDetalleItemPres"),
                    rs.getFloat("PrecioUnitario"),
                    rs.getInt("Cantidad"),
                    rs.getInt("Items_idItems"),
                    rs.getString("NombreItem"),
                   rs.getString("DescripcionItem")
                );
                detalles.add(detalle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detalles;
    }
    
    public int insertarPresupuesto(Presupuestos presupuesto) {
        String sql = "CALL insertar_presupuestos(?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
        
            stmt.setString(1, presupuesto.getNombrePres());
            stmt.setString(2, presupuesto.getEstatus());
            stmt.setDate(3, new java.sql.Date(presupuesto.getFechaMovimiento().getTime()));
            stmt.setInt(4, presupuesto.getClientes_idClientes());
            stmt.setInt(5, presupuesto.getEmpleados_idEmpleados());

            boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet rs = stmt.getResultSet();
                if (rs.next()) {
                    generatedId = rs.getInt("idPresupuesto"); // usar el alias de el SELECT en el SP
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public boolean actualizarPresupuesto(Presupuestos p) {
        String sql = "{CALL actualizar_presupuestos(?, ?, ?, ?, ?, ?)}";
        boolean actualizado = false;

        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, p.getIdPresupuesto());
            stmt.setString(2, p.getNombrePres());
            stmt.setString(3, p.getEstatus());
            stmt.setDate(4, new java.sql.Date(p.getFechaMovimiento().getTime()));
            stmt.setInt(5, p.getClientes_idClientes());
            stmt.setInt(6, p.getEmpleados_idEmpleados());

            actualizado = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actualizado;
    }
    
    public int obtenerTotalPresupuestos() {
        String sql = "CALL obtener_total_presupuestos(?)";
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
    
    public int obtenerPresupuestosAprobadosMes() {
        String sql = "CALL obtener_presupuestos_aprobados_mes(?)";
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
    
    public int obtenerPresupuestosPendientes() {
        String sql = "CALL obtener_presupuestos_pendientes(?)";
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