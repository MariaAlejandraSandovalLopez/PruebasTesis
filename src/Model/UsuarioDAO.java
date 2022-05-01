package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Conection.BD;

public class UsuarioDAO {
	
	Connection connection = null;
	BD conexion = null;
	PreparedStatement preStatement = null;
	
	private String conectar() {
		conexion = new BD();
		String resultado = conexion.conectar();
		if (resultado.equals("conectado")) {
			connection = conexion.getConnection();
			
		}else {
			JOptionPane.showMessageDialog(null, resultado,"Error",JOptionPane.ERROR_MESSAGE);
		}
		return resultado;
	}
	
	public String registrarUsuario(Usuario usuario) throws SQLException {
		String resultado = "";
		if (!conectar().equals("conectado")) {
			return "error";
		}
		
		String consulta = "INSERT INTO users (id, name, email, password, phone)"
				+ "  VALUES (?,?,?,?,?)";
		try {
			
			preStatement = connection.prepareStatement(consulta);
			preStatement.setLong(1, usuario.getId());
			preStatement.setString(2, usuario.getName());
			preStatement.setString(3,usuario.getEmail());
			preStatement.setString(4,usuario.getPassword());
			preStatement.setString(5,usuario.getPhone());
			preStatement.execute();
			
			resultado = "El usuario se registro exitosamente";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("No se pudo registrar el estudiante, verifique que el documento no exista: " + e.getMessage());
			resultado = "error";
		}
		finally {
			preStatement.close();
			connection.close();
			conexion.desconectar();
		}
		
		
		return resultado;
		
	}
	
	public Usuario buscarUsuario(String correo) throws SQLException {
		Usuario usuarioPedido = null;
		if (!conectar().equals("conectado")) {
			return usuarioPedido;
		}
		
		ResultSet result = null;	
		String consulta = "SELECT id, name, email, password, phone"
				+ " FROM users WHERE email = ?";
		
		try {
			preStatement = connection.prepareStatement(consulta);
			preStatement.setString(1, correo);
			result = preStatement.executeQuery();
			
			if(result.next()){
				usuarioPedido = new Usuario();
				usuarioPedido.setId(result.getInt("id"));
				usuarioPedido.setName(result.getString("name"));
				usuarioPedido.setEmail(result.getString("email"));
				usuarioPedido.setPassword(result.getString("password"));
				usuarioPedido.setPhone(result.getString("phone"));
				
			}	
		} catch (SQLException e) {
			System.out.println("Error en la consulta de la persona: "+e.getMessage());
		}
		finally {
			
			result.close();
			preStatement.close();
			connection.close();
			conexion.desconectar();
		}
		return usuarioPedido;
		
		
	}
	
	
	/*public String actualizarUsuario(Usuario usuario) throws SQLException {
		String resultado = "";
		if (!conectar().equals("conectado")) {
			return "error";
		}
		
		
		String consulta="UPDATE users "
				+ "SET id = ? , "
				+ "name = ? , "
				+ "email = ? , "
				+ "password = ? , "
				+ "phone = ?  "
				+ "WHERE email = ?;";
		
		try {
			preStatement = connection.prepareStatement(consulta);
			preStatement.setLong(1, usuario.getId());
			preStatement.setString(2, usuario.getName());
			preStatement.setString(3,usuario.getEmail());
			preStatement.setString(4,usuario.getPassword());
			preStatement.setString(5,usuario.getPhone());
			preStatement.execute();
			resultado = "ok me actualice";
		} 
		catch (SQLException e) {
			System.out.println("Ocurrió una excepcion de SQL "
            		+ "al momento de actualizar: "+e);
			resultado = "error";
		}
		finally {
			
			preStatement.close();
			connection.close();
			conexion.desconectar();
		}
		
		return resultado;
	}*/
	
	public String eliminarUsuario(String correo) throws SQLException {
		String resultado = "";
		if (!conectar().equals("conectado")) {
			return "error";
		}
		
		String consulta = "DELETE FROM users WHERE email = ? ";
		
		PreparedStatement statement;
		try {
			preStatement = connection.prepareStatement(consulta);
			preStatement.setString(1, correo);
			preStatement.executeUpdate();
			resultado = "Se elimino el usuario";
		} catch (SQLException e) {
			System.out.println("Ocurrió una excepcion de SQL "
            		+ "al momento de eliminar "+e);
			resultado="error";
		}
		finally {
			preStatement.close();
			connection.close();
			conexion.desconectar();
		}
		
		return "Usuario eliminado";
	}

}
