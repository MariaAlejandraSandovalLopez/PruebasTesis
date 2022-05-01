package Controller;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.omg.CORBA.portable.OutputStream;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpsServer;
import com.sun.net.httpserver.HttpServer;

//import Conexion.HttpServer;

public class Http {
	
	public static void main (String []args) throws IOException{
		final int puerto = 8080;
		HttpServer httpd = HttpServer.create(new InetSocketAddress(puerto), 0);
		
		HttpContext ctx = httpd.createContext("/");
		ctx.setHandler(Http::gestionarSolicitud);
		
		httpd.start();
		
		System. out. println("Llego algo\n");
	
	}
	
	private static void gestionarSolicitud(HttpExchange exchange) throws IOException {
		final int CODIGO_RESPUESTA = 200;
		String contenido = "Respuesta desde el servidor HTTP";
		exchange.sendResponseHeaders(CODIGO_RESPUESTA, contenido.getBytes().length);
		
		java.io.OutputStream os = exchange.getResponseBody();
		os.write(contenido.getBytes());
		os.close();

		
	}
}
