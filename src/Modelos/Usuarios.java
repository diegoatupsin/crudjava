package Modelos;

public class Usuarios {
    
    private int idUsuarios;
    private String NombreUser;
    private String Password;
    private String TipoUsuario;
    private int Empleados_idEmpleados;
    private int EstatusUsuario;
    
    public Usuarios() {
        
    }

    public Usuarios(int idUsuarios, String NombreUser, String Password, String TipoUsuario, int Empleados_idEmpleados, int EstatusUsuario) {
        this.idUsuarios = idUsuarios;
        this.NombreUser = NombreUser;
        this.Password = Password;
        this.TipoUsuario = TipoUsuario;
        this.Empleados_idEmpleados = Empleados_idEmpleados;
        this.EstatusUsuario = EstatusUsuario;
    }

    public Usuarios(String NombreUser, String Password, String TipoUsuario, int Empleados_idEmpleados, int EstatusUsuario) {
        this.NombreUser = NombreUser;
        this.Password = Password;
        this.TipoUsuario = TipoUsuario;
        this.Empleados_idEmpleados = Empleados_idEmpleados;
        this.EstatusUsuario = EstatusUsuario;
    }

    public int getEstatusUsuario() {
        return EstatusUsuario;
    }

    public void setEstatusUsuario(int EstatusUsuario) {
        this.EstatusUsuario = EstatusUsuario;
    }
    
    public int getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public String getNombreUser() {
        return NombreUser;
    }

    public void setNombreUser(String NombreUser) {
        this.NombreUser = NombreUser;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getTipoUsuario() {
        return TipoUsuario;
    }

    public void setTipoUsuario(String TipoUsuario) {
        this.TipoUsuario = TipoUsuario;
    }

    public int getEmpleados_idEmpleados() {
        return Empleados_idEmpleados;
    }

    public void setEmpleados_idEmpleados(int Empleados_idEmpleados) {
        this.Empleados_idEmpleados = Empleados_idEmpleados;
    }
}
