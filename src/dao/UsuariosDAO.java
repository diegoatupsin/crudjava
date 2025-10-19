package dao;

import Modelos.Usuarios;
import db.dbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAO {
    
    public List<Usuarios> obtenerUsuarios() {
        List<Usuarios> lista = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{call obtener_usuarios()}");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Usuarios(
                        rs.getInt("idUsuarios"),
                        rs.getString("NombreUser"),
                        rs.getString("Password"),
                        rs.getString("TipoUsuario"),
                        rs.getInt("Empleados_idEmpleados"),
                        rs.getInt("EstatusUsuarios")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public Usuarios obtenerUsuarioPorNombre(String nombreUsuario) {
        String sql = "CALL obtener_usuario_por_nombre(?)";
        Usuarios usuario = null;

        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, nombreUsuario);
            boolean hasResult = stmt.execute();

            if (hasResult) {
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        usuario = new Usuarios();
                        usuario.setIdUsuarios(rs.getInt("idUsuarios"));
                        usuario.setNombreUser(rs.getString("NombreUser"));
                        usuario.setPassword(rs.getString("Password"));
                        usuario.setTipoUsuario(rs.getString("TipoUsuario"));
                        usuario.setEstatusUsuario(rs.getInt("EstatusUsuarios"));
                        usuario.setEmpleados_idEmpleados(rs.getInt("empleados_idEmpleados"));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
             
    
    public boolean insertar(Usuarios usuarios) {
        String sql = "{CALL insertar_usuarios(?, ?, ?, ?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, usuarios.getNombreUser());
            stmt.setString(2, usuarios.getPassword());
            stmt.setString(3, usuarios.getTipoUsuario());
            stmt.setInt(4, usuarios.getEmpleados_idEmpleados());
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Regresa uno si el usuario se elimino correctamente, 0 si no
    public boolean eliminar(int id) {
        String sql = "{CALL eliminar_usuarios(?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            System.out.println(id);
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            System.out.println(affectedRows);
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean reactivarUsuario(int id) {
        String sql = "{CALL reactivar_usuarios(?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            System.out.println(id);
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            System.out.println(affectedRows);
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void actualizar(Usuarios usuarios) {
        String sql = "{CALL actualizar_usuarios(?, ?, ?, ?, ?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, usuarios.getIdUsuarios());
            stmt.setString(2, usuarios.getNombreUser());
            stmt.setString(3, usuarios.getPassword());
            stmt.setString(4, usuarios.getTipoUsuario());
            stmt.setInt(5, usuarios.getEmpleados_idEmpleados());
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean actualizarPassword(int id, String hashedPassword) {
        String sql = "{call actualizar_password(?, ?)}";
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, id);
            stmt.setString(2, hashedPassword);
            int columnasAfectadas = stmt.executeUpdate();
            return columnasAfectadas > 0; // regresa true si es que se hizo algo
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean existeUsuarioPorEmpleado(int idEmpleado) {
        String sql = "CALL existe_usuario_por_empleado(?)";

        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idEmpleado);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}