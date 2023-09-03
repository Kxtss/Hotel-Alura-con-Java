package hotel.pruebas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hotel.factory.ConnectionFactory;

public class BusquedaPrueba {

	public static void main(String[] args) {
//		buscarHuesped();
		buscarReserva();
	}
	
	public static void buscarHuesped() {
		// Busca por nombre y apellido o solo uno de ellos
		String busqueda = "";

		// Busca solo por id
		Integer busquedaIDReserva = 64;

		String[] nombresYApellidos = busqueda.split("\\s+");

		if (nombresYApellidos.length == 1) {
			// Búsqueda por nombre, apellido o ID
			Integer idReserva = busquedaIDReserva;
			String nombre = nombresYApellidos[0];
			String apellido = nombresYApellidos[0];

			String resultado = pruebaBusquedaHuesped(nombre, apellido, idReserva);
			System.out.println("Búsqueda: " + resultado);
		} else if (nombresYApellidos.length == 2) {
			// Búsqueda por nombre y apellido
			Integer id = busquedaIDReserva;
			String nombre = nombresYApellidos[0];
			String apellido = nombresYApellidos[1];

			String resultado = pruebaBusquedaHuesped(nombre, apellido, id);
			System.out.println("Búsqueda: " + resultado);
		} else {
			System.out.println("Entrada no válida para búsqueda.");
		}
	}
	
	public static void buscarReserva() {
		// Busca por nombre y apellido o solo uno de ellos
		String busqueda = "";

		// Busca solo por id
		Integer busquedaID = 70;

		String[] fechaEntradaYFechaSalida = busqueda.split("\\s+");

		if (fechaEntradaYFechaSalida.length == 1) {
			// Búsqueda por nombre, apellido o ID
			Integer id = busquedaID;
			String fechaEntrada = fechaEntradaYFechaSalida[0];
			String fechaSalida = fechaEntradaYFechaSalida[0];

			String resultado = pruebaBusquedaReserva(fechaEntrada, fechaSalida, id);
			System.out.println("Búsqueda: " + resultado);
			
		} else if (fechaEntradaYFechaSalida.length == 2) {
			// Búsqueda por nombre y apellido
			Integer id = busquedaID;
			String fechaEntrada = fechaEntradaYFechaSalida[0];
			String fechaSalida = fechaEntradaYFechaSalida[1];

			String resultado = pruebaBusquedaReserva(fechaEntrada, fechaSalida, id);
			System.out.println("Búsqueda: " + resultado);
		} else {
			System.out.println("Entrada no válida para búsqueda.");
		}
	}

	public static String pruebaBusquedaHuesped(String nombre, String apellido, Integer IdReserva) {
		ConnectionFactory connectionFactory = new ConnectionFactory();

		try (Connection conn = connectionFactory.recuperaConexion()) {
			String query = "SELECT Nombre, Apellido FROM Huesped WHERE Nombre = ? OR Apellido = ?";

			// Si IdReserva también debe ser utilizado en la búsqueda
			if (IdReserva != null) {
				query += " OR IdReserva = ?";
			}

			try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
				preparedStatement.setString(1, nombre);
				preparedStatement.setString(2, apellido);

				// Si IdReserva también debe ser configurado en el PreparedStatement
				if (IdReserva != null) {
					preparedStatement.setInt(3, IdReserva);
				}

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					StringBuilder resultBuilder = new StringBuilder();

					while (resultSet.next()) {
						String foundNombre = resultSet.getString("Nombre");
						String foundApellido = resultSet.getString("Apellido");
						resultBuilder.append(foundNombre).append(" ").append(foundApellido).append(", ");
					}

					if (resultBuilder.length() > 0) {
						// Elimina la última coma y el espacio
						resultBuilder.delete(resultBuilder.length() - 2, resultBuilder.length());
					} else {
						resultBuilder.append("No se encontraron resultados");
					}

					return resultBuilder.toString();
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String pruebaBusquedaReserva(String fechaEntrada, String fechaSalida, Integer id) {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		
		try (Connection conn = connectionFactory.recuperaConexion()) {
			String query = "SELECT id, fechaEntrada, fechaSalida FROM Reserva WHERE fechaEntrada = ? OR fechaSalida = ?";
			
			if (id != null) {
				query += " OR Id = ?";
			}
			
			try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
				preparedStatement.setString(1, fechaEntrada);
				preparedStatement.setString(2, fechaSalida);

				if (id != null) {
					preparedStatement.setInt(3, id);
				}
				
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					StringBuilder builder = new StringBuilder();
					
					while (resultSet.next()) {
						String foundFechaEntrada = resultSet.getString("FechaEntrada");
						String foundFechaSalida = resultSet.getString("FechaSalida");
						builder.append(foundFechaEntrada).append(" ").append(foundFechaSalida).append(", ");
					}
					
					if (builder.length() > 0) {
						// Elimina la última coma y el espacio
						builder.delete(builder.length() - 2, builder.length());
					} else {
						builder.append("No se encontraron resultados");
					}

					return builder.toString();
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
