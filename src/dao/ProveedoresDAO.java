package dao;

import db.dbConnection;
import Modelos.Proveedores;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedoresDAO {

    public List<Proveedores> obtenerProveedores() {
        List<Proveedores> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM Proveedores";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idProveedores");

                Proveedores proveedor = new Proveedores(
                    id,
                    rs.getString("NombreProv"),
                    rs.getString("RFCProv"),
                    rs.getString("DireccionProv"),
                    rs.getInt("EstatusProveedor")
                );

                proveedor.setNumeroProv(obtenerTelefonosPorProveedor(id));
                proveedor.setCorreoProv(obtenerCorreosPorProveedor(id));

                proveedores.add(proveedor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proveedores;
    }

    public List<String> obtenerTelefonosPorProveedor(int idProveedor) {
        List<String> telefonos = new ArrayList<>();
        String sql = "SELECT NumeroProv FROM TelefonosProveedores WHERE Proveedores_idProveedores = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProveedor);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                telefonos.add(rs.getString("NumeroProv"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return telefonos;
    }

    public List<String> obtenerCorreosPorProveedor(int idProveedor) {
        List<String> correos = new ArrayList<>();
        String sql = "SELECT CorreoProv FROM CorreosProveedores WHERE Proveedores_idProveedores = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProveedor);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                correos.add(rs.getString("CorreoProv"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return correos;
    }

    public void insertarProveedor(Proveedores proveedor) {
        String sql = "CALL insertar_proveedores(?, ?, ?)";
    
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, proveedor.getNombreProv());
            stmt.setString(2, proveedor.getRFCProv());
            stmt.setString(3, proveedor.getDireccionProv());

            boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet rs = stmt.getResultSet();
                if (rs.next()) {
                    int idGenerado = rs.getInt("idProveedor");  // el alias de el select
                    insertarTelefonos(proveedor.getNumeroProv(), idGenerado);
                    insertarCorreos(proveedor.getCorreoProv(), idGenerado);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertarTelefonos(List<String> telefonos, int idProveedor) {
        String sql = "CALL insertar_telefonosproveedores(?, ?)";

        try (Connection conn = dbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            for (String tel : telefonos) {
                stmt.setString(1, tel);
                stmt.setInt(2, idProveedor);
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertarCorreos(List<String> correos, int idProveedor) {
        String sql = "CALL insertar_correosproveedores(?, ?)";

        try (Connection conn = dbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            for (String correo : correos) {
                stmt.setString(1, correo);
                stmt.setInt(2, idProveedor);
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarProveedor(Proveedores proveedor) {
        String sql = "CALL actualizar_proveedores(?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, proveedor.getIdProveedores());
            stmt.setString(2, proveedor.getNombreProv());
            stmt.setString(3, proveedor.getRFCProv());
            stmt.setString(4, proveedor.getDireccionProv());
            stmt.executeUpdate();

            actualizarTelefonos(proveedor);
            actualizarCorreos(proveedor);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarTelefonos(Proveedores proveedor) {
        String eliminarSQL = "DELETE FROM TelefonosProveedores WHERE Proveedores_idProveedores = ?";
        String insertarSQL = "CALL insertar_telefonosproveedores(?, ?)";

        try (Connection conn = dbConnection.getConnection()) {
            try (PreparedStatement stmtEliminar = conn.prepareStatement(eliminarSQL)) {
                stmtEliminar.setInt(1, proveedor.getIdProveedores());
                stmtEliminar.executeUpdate();
            }

            try (CallableStatement stmtInsertar = conn.prepareCall(insertarSQL)) {
                for (String tel : proveedor.getNumeroProv()) {
                    stmtInsertar.setString(1, tel);
                    stmtInsertar.setInt(2, proveedor.getIdProveedores());
                    stmtInsertar.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarCorreos(Proveedores proveedor) {
        String eliminarSQL = "DELETE FROM CorreosProveedores WHERE Proveedores_idProveedores = ?";
        String insertarSQL = "CALL insertar_correosproveedores(?, ?)";

        try (Connection conn = dbConnection.getConnection()) {
            try (PreparedStatement stmtEliminar = conn.prepareStatement(eliminarSQL)) {
                stmtEliminar.setInt(1, proveedor.getIdProveedores());
                stmtEliminar.executeUpdate();
            }

            try (CallableStatement stmtInsertar = conn.prepareCall(insertarSQL)) {
                for (String correo : proveedor.getCorreoProv()) {
                    stmtInsertar.setString(1, correo);
                    stmtInsertar.setInt(2, proveedor.getIdProveedores());
                    stmtInsertar.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean eliminarProveedor(int idProveedor) {
        String sql = "CALL eliminar_proveedores(?)";
        
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, idProveedor);
            
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean reactivarProveedor(int idProveedor) {
        String sql = "CALL reactivar_proveedores(?)";
        
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, idProveedor);
            
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}