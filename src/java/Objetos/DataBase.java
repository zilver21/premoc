package Objetos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author SRAD
 */
public class DataBase
{
    private final String ip;
    private final String bd;
    private final String usuarioBD;
    private final String passwordBD;
    private Connection conexion;
    
    public DataBase(String ip, String bd, String usuarioBD, String passwordBD)
    {
        this.ip         = ip;
        this.bd         = bd;
        this.usuarioBD  = usuarioBD;
        this.passwordBD = passwordBD;
    }
    
    public ResultSet consulta(String sql)
    {
        ResultSet resultado = null;
        if (!sql.trim().isEmpty() && conectar())
        {
            try
            {
                Statement comando = conexion.createStatement();
                resultado         = comando.executeQuery(sql);
            }
            catch(SQLException e)
            {
                mostrarExcepcion(e);
            }
        }
        return resultado;
    }
    
    private boolean conectar()
    {
        boolean regresar = false;
        try
        {
            String servidor = "jdbc:mysql://" + ip + "/" + bd;
            conexion        = DriverManager.getConnection(servidor, usuarioBD, passwordBD);
            regresar        = true;
        }
        catch(SQLException e)
        {
            mostrarExcepcion(e);
        }
        return regresar;
    }
    
    private boolean desconectar()
    {
        boolean regresar = false;
        try
        {
            conexion.close();
            regresar = true;
        }
        catch(SQLException e)
        {
            mostrarExcepcion(e);
        }
        return regresar;
    }
    
    private void mostrarExcepcion(Exception e)
    {
        JOptionPane.showMessageDialog(null, e, "Error en la Conexión con la BD " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
    }
}
