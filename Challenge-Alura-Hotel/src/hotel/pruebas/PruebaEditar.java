package hotel.pruebas;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hotel.factory.ConnectionFactory;

public class PruebaEditar {

	public static void main(String[] args) {
		Date fechaEntrada = Date.valueOf("2023-05-22");
		Date fechaSalida = Date.valueOf("2023-06-21");
		BigDecimal valor = new BigDecimal(4000.00);
		String formaPago = "Tarjeta de credito";
		Integer id = 70;

		editarReserva(fechaEntrada, fechaSalida, valor, formaPago, id);
		
		String nombre = "Samanta";
		String apellido = "Carrey";
		Date fechaNacimiento = Date.valueOf("2000-05-23");
		String nacionalidad = "español-española";
		String telefono = "2387852364";
		Integer idHuesped = 71;
		
		editarHuesped(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idHuesped);
		
		
	}

	public static int editarReserva(Date fechaEntrada, Date fechaSalida, BigDecimal valor, String formaPago,
			Integer id) {
		System.out.println("Editar Reserva");

		ConnectionFactory connectionFactory = new ConnectionFactory();

		try (Connection conn = connectionFactory.recuperaConexion()) {

			String query = "UPDATE reserva SET FechaEntrada = ?, FechaSalida = ?, Valor = ?, FormaPago = ? WHERE ID = ?";

			try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
				preparedStatement.setDate(1, fechaEntrada);
				preparedStatement.setDate(2, fechaSalida);
				preparedStatement.setBigDecimal(3, valor);
				preparedStatement.setString(4, formaPago);
				preparedStatement.setInt(5, id);

				preparedStatement.execute();

				return preparedStatement.getUpdateCount();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static int editarHuesped(String nombre, String apellido, Date fechaNacimiento, String nacionalidad,
			String telefono, Integer id) {
		System.out.println("Editar Huesped");

		ConnectionFactory connectionFactory = new ConnectionFactory();

		try (Connection conn = connectionFactory.recuperaConexion()) {

			String query = "UPDATE huesped SET nombre = ?, apellido = ?, fechaNacimiento = ?, nacionalidad = ?, telefono = ? WHERE ID = ?";

			try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
				preparedStatement.setString(1, nombre);
				preparedStatement.setString(2, apellido);
				preparedStatement.setDate(3, fechaNacimiento);
				preparedStatement.setString(4, nacionalidad);
				preparedStatement.setString(5, telefono);
				preparedStatement.setInt(6, id);

				preparedStatement.execute();

				return preparedStatement.getUpdateCount();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
