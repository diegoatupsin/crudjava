package dao;

import Modelos.Empleados;
import db.dbConnection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class EmpleadosDAO {
    
    public List<Empleados> obtenerEmpleados() {
        List<Empleados> empleados = new ArrayList<>();
        String sql = "{CALL obtener_empleados()}";

        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql);
            ResultSet rs = stmt.executeQuery()) {

            //utiliza un constructor que solo llena el id y el empleado en este caso no se necesitan los otros datos, nevermind
            while (rs.next()) {
                int id = rs.getInt("idEmpleados");
            
                Empleados emp = new Empleados(
                    id,
                    rs.getString("NombreEmp"),
                    rs.getString("ApellidoEmp"),
                    rs.getString("NumeroSS"),
                    rs.getString("Puesto"),
                    rs.getDate("FechaContrato"),
                    rs.getString("DireccionEmp"),
                    rs.getInt("EstatusEmpleado")
                );
                emp.setNumeroEmp(obtenerTelefonosPorEmpleado(id));
            
                emp.setCorreosEmp(obtenerCorreosPorEmpleado(id));
                
                empleados.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleados;
    }
    
    public boolean eliminarEmpleado(int idEmpleado) {
        String sql = "CALL eliminar_empleados(?)";
        
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, idEmpleado);
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean reactivarEmpleado(int idEmpleado) {
        String sql = "CALL reactivar_empleados(?)";
        
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, idEmpleado);
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Empleados obtenerEmpleadoPorId(int idEmpleado) {
        String sql = "CALL obtener_empleado_por_id(?)";
        Empleados empleado = new Empleados();
        
        try (Connection conn = dbConnection.getConnection(); CallableStatement stmt = conn.prepareCall(sql) ) {
            
            stmt.setInt(1, idEmpleado);
            ResultSet rs = stmt.executeQuery();
            
            //if en lugar de while porque solo es una
            if (rs.next()) {
                empleado.setIdEmpleados(rs.getInt("idEmpleados"));
                empleado.setNombreEmp(rs.getString("NombreEmp"));
                empleado.setApellidoEmp(rs.getString("ApellidoEmp"));
                empleado.setNumeroSS(rs.getString("NumeroSS"));
                empleado.setPuesto(rs.getString("Puesto"));
                empleado.setFechaContrato(rs.getDate("FechaContrato"));
                empleado.setNumeroEmp(obtenerTelefonosPorEmpleado(idEmpleado));
                empleado.setCorreosEmp(obtenerCorreosPorEmpleado(idEmpleado));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleado;
    }
 
    public List<String> obtenerTelefonosPorEmpleado(int idEmpleado) {
        List<String> telefonos = new ArrayList<>();
        String sql = "CALL obtener_telefonos_empleados_por_empleado(?)";
        
        try ( Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql) ) {
            
            stmt.setInt(1, idEmpleado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String tel = rs.getString("NumeroEmp");
                
                telefonos.add(tel);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
        return telefonos;
    }
    
    public List<String> obtenerCorreosPorEmpleado(int idEmpleado) {
        List<String> correos = new ArrayList<>();
        String sql = "CALL obtener_correos_empleados_por_empleado(?)";
        
        try (Connection conn = dbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, idEmpleado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String cor = rs.getString("CorreosEmp");
                correos.add(cor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return correos;
    }
    
    // el ultimo argumento de este procedimiento almacenado es un out con el lastID
    public void insertarEmpleado(Empleados empleado) {
        String sql = "{CALL insertar_empleados(?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = dbConnection.getConnection(); 
            CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setString(1, empleado.getNombreEmp());
            stmt.setString(2, empleado.getApellidoEmp());
            stmt.setString(3, empleado.getNumeroSS());
            stmt.setString(4, empleado.getPuesto());
            stmt.setDate(5, empleado.getFechaContrato());
            stmt.setString(6, empleado.getDireccionEmp());
            
            stmt.registerOutParameter(7, Types.INTEGER);
            
            stmt.execute();
            
            int idGenerado = stmt.getInt(7);
            insertarTelefonos(empleado.getNumeroEmp(), idGenerado);
            insertarCorreos(empleado.getCorreosEmp(), idGenerado);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void insertarCorreos(List<String> CorreoEmp, int idEmpleado) {
        String sql = "CALL insertar_correosempleados(?, ?)";

        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            for (String cor : CorreoEmp) {
                stmt.setString(1, cor);
                stmt.setInt(2, idEmpleado);
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void insertarTelefonos(List<String> NumeroEmp, int idEmpleado) {
        String sql = "CALL insertar_telefonosempleados(?, ?)";

        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

        for (String tel : NumeroEmp) {
            stmt.setString(1, tel);
            stmt.setInt(2, idEmpleado);
            stmt.addBatch();
        }

        stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void actualizarEmpleado(Empleados empleado) {
        String sql = "{CALL actualizar_empleados(?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, empleado.getIdEmpleados());
            stmt.setString(2, empleado.getNombreEmp());
            stmt.setString(3, empleado.getApellidoEmp());
            stmt.setString(4, empleado.getNumeroSS());
            stmt.setString(5, empleado.getPuesto());
            stmt.setDate(6, empleado.getFechaContrato());
            stmt.setString(7, empleado.getDireccionEmp());
            
            stmt.execute();
            
            actualizarTelefonos(empleado);
            actualizarCorreos(empleado);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void actualizarTelefonos(Empleados empleado) {        
        String eliminarSql = "DELETE FROM TelefonosEmpleados WHERE Empleados_idEmpleados = ?";
        String insertarSql = "CALL insertar_telefonosempleados(?, ?)";

        try (Connection conn = dbConnection.getConnection()) {

            // Eliminar todos los teléfonos actuales
            try (PreparedStatement stmtEliminar = conn.prepareStatement(eliminarSql)) {
                stmtEliminar.setInt(1, empleado.getIdEmpleados());
                stmtEliminar.executeUpdate();
            }

            // Insertar los nuevos teléfonos
            try (CallableStatement stmtInsertar = conn.prepareCall(insertarSql)) {
                for (String numero : empleado.getNumeroEmp()) {
                    stmtInsertar.setString(1, numero);
                    stmtInsertar.setInt(2, empleado.getIdEmpleados());
                    stmtInsertar.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void actualizarCorreos(Empleados empleado) {
        String eliminarSql = "DELETE FROM CorreoEmpleados WHERE Empleados_idEmpleados = ?";
        String insertarSql = "CALL insertar_correosempleados(?, ?)";

        try (Connection conn = dbConnection.getConnection()) {

            // Eliminar todos los correos actuales
            try (PreparedStatement stmtEliminar = conn.prepareStatement(eliminarSql)) {
                stmtEliminar.setInt(1, empleado.getIdEmpleados());
                stmtEliminar.executeUpdate();
            }

            // Insertar los nuevos correos
            try (CallableStatement stmtInsertar = conn.prepareCall(insertarSql)) {
                for (String correo : empleado.getCorreosEmp()) {
                    stmtInsertar.setString(1, correo);
                    stmtInsertar.setInt(2, empleado.getIdEmpleados());
                    stmtInsertar.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
