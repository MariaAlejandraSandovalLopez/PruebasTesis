package Conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

public class BD {

		/*
		String URL = "jdbc:mysql://localhost/";
		String bd = "android";
		String usuario = "root";
		String password = " ";
		Connection conn = null;
		conn = DriverManager.getConnection(URL + bd, usuario, password);
		PreparedStatement ps = null;
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("Select * from users");
		
		while(rs.next()) {
			System. out. println("Nombre\n" + rs.getNString("name"));
		}
		conn.close();
		
	}
	*/
		
	private String nombreBd = "android";
	private String usuario = "root";
	private String password = "";
	private String url="jdbc:mysql://localhost:3306/"+nombreBd+"?useUnicode=true&use"
			+ "JDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&"
			+ "serverTimezone=UTC";
	
	Connection conn = null;
	
	public String conectar() {
		String respuesta = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, usuario, password);
			
			if (conn!=null) {
				respuesta="conectado";
			}else{
				respuesta="NO SE PUDO CONECTAR "+nombreBd;
			}
			
		} 
		catch (ClassNotFoundException e) {
			respuesta="ocurre una ClassNotFoundException : "+e.getMessage();
		}
		catch (SQLSyntaxErrorException e) {
			respuesta="ocurre una SQLSyntaxErrorException: "+e.getMessage()+"\n";
			respuesta+="Verifique que se esté usando la base de datos y tablas correctas...";
		}
		catch (CommunicationsException e) {
			respuesta="ocurre una CommunicationsException: "+e.getMessage()+"\n";
			respuesta+="Verifique que la base de datos fué iniciada...";
		}
		catch (SQLException e) {
			respuesta="ocurre una SQLException: "+e.getMessage()+"\n";
			respuesta+="Este es un problema general de SQL, verifique con el administrador";
		}
		
		return respuesta;
	}
	
	public Connection getConnection(){
		return conn;
	}
	public void desconectar(){
		conn=null;
	}
	
}
