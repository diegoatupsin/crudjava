package dao;

import db.dbConnection;
import Modelos.Clientes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientesDAO {
    
    public List<Clientes> obtenerClientes() {
        List<Clientes> clientes = new ArrayList<>();
        String sql = "{Call obtener_clientes()}";
        
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql);
            ResultSet rs= stmt.executeQuery()) {
            
            // poner los datos en un objeto de la clase Clientes
            while (rs.next()) {
                int id = rs.getInt("idClientes");
                
                
                Clientes cli = new Clientes(id, 
                    rs.getString("NombreClie"), rs.getString("ApellidosClie"),
                    rs.getString("DireccionClie"), rs.getString("RFC_Clie"), rs.getInt("EstatusCliente")
                );
                cli.setNumeroClie(obtenerTelefonosPorCliente(id)); // parece que no se puede meter el telefono al constructor, porque no funciona
                //System.out.println("→ " + cli.getNombreClie() + " → Teléfonos: " + cli.getNumeroClie());
                
                cli.setCorreoClie(obtenerCorreosPorCliente(id));
                //System.out.println("→ " + cli.getNombreClie() + " → Correos: " + cli.getCorreoClie());
                
                clientes.add(cli);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
    
    public boolean eliminarCliente(int idCliente) {
        String sql = "CALL eliminar_clientes(?)";
        
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, idCliente);
            
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean reactivarCliente(int idCliente) {
        String sql = "CALL reactivar_clientes(?)";
        
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, idCliente);
            
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Clientes obtenerClientePorId(int idCliente) {
        String sql = "CALL obtener_cliente_porid(?)";
        Clientes cliente = new Clientes();
        
        try (Connection conn = dbConnection.getConnection(); CallableStatement stmt = conn.prepareCall(sql) ) {
            
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            
            //if en lugar de while porque solo es una
            if (rs.next()) {
                cliente.setIdClientes(rs.getInt("idClientes"));
                cliente.setNombreClie(rs.getString("NombreClie"));
                cliente.setApellidosClie(rs.getString("ApellidosClie"));
                cliente.setDireccionClie((rs.getString("DireccionClie")));
                cliente.setRFC_Clie(rs.getString("RFC_Clie"));
                cliente.setNumeroClie(obtenerTelefonosPorCliente(idCliente));
                cliente.setCorreoClie(obtenerCorreosPorCliente(idCliente));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }
    
    public List<String> obtenerCorreosPorCliente(int idCliente) {
        List<String> correos = new ArrayList<>();
        String sql = "CALL obtener_correos_porcliente(?)";
        
        try (Connection conn = dbConnection.getConnection(); 
            CallableStatement stmt = conn.prepareCall(sql) ) {
            
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String cor = rs.getString("CorreoClie");
                correos.add(cor);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return correos;
    }
    
    public List<String> obtenerTelefonosPorCliente(int idCliente) {
        List<String> telefonos = new ArrayList<>();
        String sql = "CALL obtener_telefonos_porcliente(?)";
        
        try (Connection conn = dbConnection.getConnection(); 
            CallableStatement stmt = conn.prepareCall(sql) ) {
            
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String tel = rs.getString("NumeroClie");
                telefonos.add(tel);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return telefonos;
    }
    
    // para poder meter multiples telefonos al cliente se hacen cosas raras
    public void insertarCliente(Clientes cliente) {
        String sql = "{CALL insertar_clientes(?, ?, ?, ?, ?)}"; // El ultimo parametro es out (ultimo insert id)
        
        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setString(1, cliente.getNombreClie());
            stmt.setString(2, cliente.getApellidosClie());
            stmt.setString(3, cliente.getDireccionClie());
            stmt.setString(4, cliente.getRFC_Clie());
            stmt.registerOutParameter(5, Types.INTEGER); //obtienes el id que se genero para luego insertar los telefonos
            
            stmt.execute();
            
            int idGenerado = stmt.getInt(5);
            insertarTelefonos(cliente.getNumeroClie(), idGenerado);
            insertarCorreos(cliente.getCorreoClie(), idGenerado);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void insertarCorreos(List<String> CorreoClie, int idCliente) {
        String sql = "CALL insertar_correosclientes(?, ?)";

        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            for (String cor : CorreoClie) {
                stmt.setString(1, cor);
                stmt.setInt(2, idCliente);
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void insertarTelefonos(List<String> NumeroClie, int idCliente) {
        String sql = "CALL insertar_telefonosclientes(?, ?)";

        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

        for (String tel : NumeroClie) {
            stmt.setString(1, tel);
            stmt.setInt(2, idCliente);
            stmt.addBatch();
        }

        stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarCliente(Clientes cliente) {
        String sql = "{CALL actualizar_clientes(?, ?, ?, ?, ?)}";

        try (Connection conn = dbConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, cliente.getIdClientes());
            stmt.setString(2, cliente.getNombreClie());
            stmt.setString(3, cliente.getApellidosClie());
            stmt.setString(4, cliente.getDireccionClie());
            stmt.setString(5, cliente.getRFC_Clie());

            stmt.executeUpdate();

            actualizarTelefonos(cliente);
            actualizarCorreos(cliente);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void actualizarTelefonos(Clientes cliente) {        
        String eliminarSql = "DELETE FROM TelefonosClientes WHERE Clientes_idClientes = ?";
        String insertarSql = "CALL insertar_telefonosclientes(?, ?)";

        try (Connection conn = dbConnection.getConnection()) {

            // Eliminar todos los teléfonos actuales
            try (PreparedStatement stmtEliminar = conn.prepareStatement(eliminarSql)) {
                stmtEliminar.setInt(1, cliente.getIdClientes());
                stmtEliminar.executeUpdate();
            }

            // Insertar los nuevos teléfonos
            try (CallableStatement stmtInsertar = conn.prepareCall(insertarSql)) {
                for (String numero : cliente.getNumeroClie()) {
                    stmtInsertar.setString(1, numero);
                    stmtInsertar.setInt(2, cliente.getIdClientes());
                    stmtInsertar.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void actualizarCorreos(Clientes cliente) {
        String eliminarSql = "DELETE FROM CorreosClie WHERE Clientes_idClientes = ?";
        String insertarSql = "CALL insertar_correosclientes(?, ?)";

        try (Connection conn = dbConnection.getConnection()) {

            // Eliminar todos los correos actuales
            try (PreparedStatement stmtEliminar = conn.prepareStatement(eliminarSql)) {
                stmtEliminar.setInt(1, cliente.getIdClientes());
                stmtEliminar.executeUpdate();
            }

            // Insertar los nuevos correos
            try (CallableStatement stmtInsertar = conn.prepareCall(insertarSql)) {
                for (String correo : cliente.getCorreoClie()) {
                    stmtInsertar.setString(1, correo);
                    stmtInsertar.setInt(2, cliente.getIdClientes());
                    stmtInsertar.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int obtenerTotalClientes() {
        String sql = "CALL obtener_total_clientes(?)";
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
    
    public int obtenerClientesActivosEsteMes() {
        String sql = "CALL obtener_clientes_activos_mes(?)";
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
