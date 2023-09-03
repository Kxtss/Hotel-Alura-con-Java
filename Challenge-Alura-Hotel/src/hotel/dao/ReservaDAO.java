package hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import hotel.modelo.Huesped;
import hotel.modelo.Reserva;

public class ReservaDAO {
	private final Connection conn;

	/**
	 * Constructor que sirve para inicializar los {@code PreparedStatement}
	 * 
	 * @param conn
	 */
	public ReservaDAO(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Metodo que sirve para guardar las reservas dentro de la base de datos, si se
	 * descomenta el codigo este podra ser visto en consola al agregar una nueva
	 * reserva
	 * 
	 * @param reserva
	 */
	// Guardar
	public void guardarReserva(Reserva reserva) {
		String query = "INSERT INTO reserva(FechaEntrada, FechaSalida, Valor, FormaPago) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			statement.setDate(1, reserva.getFechaEntrada());
			statement.setDate(2, reserva.getFechaSalida());
			statement.setBigDecimal(3, reserva.getValor());
			statement.setString(4, reserva.getFormaPago());

			statement.execute();

//			try (ResultSet resultSet = statement.getGeneratedKeys()) {
//				while (resultSet.next()) {
//					reserva.setId(resultSet.getInt(1));
//					System.out.println("Fue insertado con exito la reserva de %s" + reserva);
//				}
//			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Sirve para guardar los nuevos huespedes dentro de la base de datos, si se
	 * descomenta el codigo este podra ser visto en consola al agregar un nuevo
	 * huesped
	 * 
	 * @param huesped
	 */
	public void guardarHuesped(Huesped huesped) {
		String query = "INSERT INTO huesped(Nombre, Apellido, FechaNacimiento, Nacionalidad, Telefono, IdReserva) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement preparedStatement = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, huesped.getNombre());
			preparedStatement.setString(2, huesped.getApellido());
			preparedStatement.setDate(3, huesped.getFechaNacimiento());
			preparedStatement.setString(4, huesped.getNacionalidad());
			preparedStatement.setString(5, huesped.getTelefono());
			preparedStatement.setInt(6, huesped.getIdReserva());

			preparedStatement.execute();

//			try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
//				while (resultSet.next()) {
//					huesped.setId(resultSet.getInt(1));
//					System.out.printf("Fue insertado el huesped de %s", huesped);
//				}
//			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
