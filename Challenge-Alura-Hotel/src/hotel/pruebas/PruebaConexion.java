package hotel.pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import hotel.factory.ConnectionFactory;

public class PruebaConexion {
    @SuppressWarnings("unused")
	public static void main(String[] args) throws SQLException {
        // Simular multiples conexiones
        ConnectionFactory connectionFactory = new ConnectionFactory();

        // Itera 20 conexiones
        for(int i = 0; i < 20; i++) {
            Connection conn = connectionFactory.recuperaConexion();

            System.out.println("Abriendo la conexion numero: " + (i + 1));
        }
    }
}
