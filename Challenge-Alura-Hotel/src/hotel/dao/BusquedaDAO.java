package hotel.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import hotel.factory.ConnectionFactory;
import hotel.modelo.Huesped;
import hotel.modelo.Reserva;
import hotel.views.Busqueda;

public class BusquedaDAO {

	private final Connection conn;

	/**
	 * Constructor que sirve para inicializar los {@code PreparedStatement}
	 * 
	 * @param conn
	 */
	public BusquedaDAO(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Metodo que sirve para mostrar lo que hay en la tabla de Reserva de la DB en la interfaz
	 * 
	 * @return
	 */
	public List<Reserva> listarReservas() {
		List<Reserva> resultado = new ArrayList<>();

		try (PreparedStatement statement = this.conn
				.prepareStatement("SELECT Id, FechaEntrada, FechaSalida, Valor, FormaPago FROM Reserva")) {

			statement.execute();

			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					Reserva reserva = new Reserva(resultSet.getInt("ID"), resultSet.getDate("FechaEntrada"),
							resultSet.getDate("FechaSalida"), resultSet.getBigDecimal("Valor"),
							resultSet.getString("FormaPago"));

					resultado.add(reserva);
				}
			}
			return resultado;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo que sirve para mostrar lo que hay en la tabla Huesped de la DB en la interfaz
	 * 
	 * @return
	 */
	public List<Huesped> listarHuespedes() {
		List<Huesped> resultado = new ArrayList<>();

		try (PreparedStatement statement = this.conn.prepareStatement(
				"SELECT Id, Nombre, Apellido, FechaNacimiento, Nacionalidad, Telefono, IdReserva FROM Huesped")) {

			statement.execute();

			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					Huesped huesped = new Huesped(resultSet.getInt("Id"), resultSet.getString("Nombre"),
							resultSet.getString("Apellido"), resultSet.getDate("FechaNacimiento"),
							resultSet.getString("Nacionalidad"), resultSet.getString("Telefono"),
							resultSet.getInt("IdReserva"));

					resultado.add(huesped);
				}
			}
			return resultado;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo para buscar en la tabla Huesped de la DB los parametros ingresados en interfaz
	 * 
	 * @param search
	 * @return
	 */
	public List<Huesped> buscarHuesped(String search) {
		List<Huesped> resultados = new ArrayList<>();

		try (PreparedStatement statement = this.conn.prepareStatement(
				"SELECT Id, Nombre, Apellido, FechaNacimiento, Nacionalidad, Telefono, IdReserva FROM Huesped "
						+ "WHERE LOWER(Nombre) = ? OR LOWER(Apellido) = ? OR IdReserva = ? OR LOWER(Nacionalidad) = ?")) {

			statement.setString(1, search.toLowerCase());
			statement.setString(2, search.toLowerCase());

			try {
				int idReserva = Integer.parseInt(search);
				statement.setInt(3, idReserva);
			} catch (NumberFormatException e) {
				statement.setInt(3, 0); // Valor que no coincidirá con ningún IdReserva
			}

			statement.setString(4, search.toLowerCase());

			statement.execute();

			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					Huesped huesped = new Huesped(resultSet.getInt("Id"), resultSet.getString("Nombre"),
							resultSet.getString("Apellido"), resultSet.getDate("FechaNacimiento"),
							resultSet.getString("Nacionalidad"), resultSet.getString("Telefono"),
							resultSet.getInt("IdReserva"));

					resultados.add(huesped);
				}
			}
			return resultados;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo para buscar en la tabla Reserva de la DB los parametros ingresados en interfaz
	 *  
	 * @param search
	 * @return
	 */
	public List<Reserva> buscarReserva(String search) {
		List<Reserva> resultados = new ArrayList<>();

		try (PreparedStatement statement = this.conn
				.prepareStatement("SELECT Id, FechaEntrada, FechaSalida, Valor, FormaPago FROM Reserva "
						+ "WHERE Id = ? OR LOWER(FechaEntrada) = ? OR LOWER(FechaSalida) = ? OR LOWER(FormaPago) = ?")) {

			try {
				int id = Integer.parseInt(search);
				statement.setInt(1, id);
			} catch (NumberFormatException e) {
				statement.setInt(1, 0);
			}

			statement.setString(2, search.toLowerCase());
			statement.setString(3, search.toLowerCase());
			statement.setString(4, search.toLowerCase());

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int reservaId = resultSet.getInt("Id");
					Date fechaEntrada = resultSet.getDate("FechaEntrada");
					Date fechaSalida = resultSet.getDate("FechaSalida");
					BigDecimal valor = resultSet.getBigDecimal("Valor");
					String formaPago = resultSet.getString("FormaPago");

					Reserva reserva = new Reserva(reservaId, fechaEntrada, fechaSalida, valor, formaPago);
					resultados.add(reserva);
				}
			}

			return resultados;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo limpiar
	 * 
	 * @param model
	 */
	public void limpiarTabla(DefaultTableModel model) {
		model.getDataVector().clear();
	}

	/**
	 * Metodo que sirve para editar la tabla Reserva desde la interfaz de {@link Busqueda}
	 * 
	 * @param fechaEntrada
	 * @param fechaSalida
	 * @param valor
	 * @param formaPago
	 * @param id
	 * @return
	 */
	public int editarReserva(Date fechaEntrada, Date fechaSalida, BigDecimal valor, String formaPago, Integer id) {
		System.out.println("Editar Reserva");

		String query = "UPDATE reserva SET FechaEntrada = ?, FechaSalida = ?, Valor = ?, FormaPago = ? WHERE ID = ?";
		try (PreparedStatement preparedStatement = this.conn.prepareStatement(query)) {
			preparedStatement.setDate(1, fechaEntrada);
			preparedStatement.setDate(2, fechaSalida);
			preparedStatement.setBigDecimal(3, valor);
			preparedStatement.setString(4, formaPago);
			preparedStatement.setInt(5, id);

			preparedStatement.execute();

			return preparedStatement.getUpdateCount();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo que sirve para editar la tabla Huesped desde la interfaz {@link Busqueda}
	 * 
	 * @param nombre
	 * @param apellido
	 * @param fechaNacimiento
	 * @param nacionalidad
	 * @param telefono
	 * @param id
	 * @return
	 */
	public int editarHuesped(String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono,
			Integer id) {
		System.out.println("Editar Huesped");

		String query = "UPDATE huesped SET nombre = ?, apellido = ?, fechaNacimiento = ?, nacionalidad = ?, telefono = ? WHERE ID = ?";

		try (PreparedStatement preparedStatement = this.conn.prepareStatement(query)) {
			preparedStatement.setString(1, nombre);
			preparedStatement.setString(2, apellido);
			preparedStatement.setDate(3, fechaNacimiento);
			preparedStatement.setString(4, nacionalidad);
			preparedStatement.setString(5, telefono);
			preparedStatement.setInt(6, id);

			preparedStatement.execute();

			return preparedStatement.getUpdateCount();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo que sirve para eliminar la fila por id en la tabla Reserva desde la interfaz {@link Busqueda}
	 * 
	 * @param id
	 * @return
	 */
	public int eliminarReserva(Integer id) {
		String query = "DELETE FROM reserva WHERE ID = ?";

		try (PreparedStatement statement = this.conn.prepareStatement(query)) {
			statement.setInt(1, id);

			statement.execute();

			return statement.getUpdateCount();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo que sirve para eliminar la fila por id en la tabla Huespede desde la interfaz {@link Busqueda}
	 * 
	 * @param id
	 * @return
	 */
	public int eliminarHuesped(Integer id) {
		ConnectionFactory factory = new ConnectionFactory();

		try (Connection conn = factory.recuperaConexion()) {
			String obtenerIdReservaQuery = "SELECT IdReserva FROM huesped WHERE ID = ?";
			String eliminarHuespedQuery = "DELETE FROM huesped WHERE ID = ?";
			String verificarReservaQuery = "SELECT COUNT(*) AS Total FROM huesped WHERE IdReserva = ?";

			try (PreparedStatement obtenerIdReservaStatement = conn.prepareStatement(obtenerIdReservaQuery);
					PreparedStatement eliminarHuespedStatement = conn.prepareStatement(eliminarHuespedQuery);
					PreparedStatement verificarReservaStatement = conn.prepareStatement(verificarReservaQuery)) {

				conn.setAutoCommit(false);

				obtenerIdReservaStatement.setInt(1, id);

				ResultSet idReservaResultSet = obtenerIdReservaStatement.executeQuery();
				if (idReservaResultSet.next()) {
					int idReserva = idReservaResultSet.getInt("IdReserva");

					eliminarHuespedStatement.setInt(1, id);
					eliminarHuespedStatement.execute();

					verificarReservaStatement.setInt(1, idReserva);
					ResultSet totalResultSet = verificarReservaStatement.executeQuery();

					if (totalResultSet.next() && totalResultSet.getInt("Total") == 0) {
						String eliminarReservaQuery = "DELETE FROM Reserva WHERE ID = ?";
						try (PreparedStatement eliminarReservaStatement = conn.prepareStatement(eliminarReservaQuery)) {
							eliminarReservaStatement.setInt(1, idReserva);
							eliminarReservaStatement.execute();
						}
					}

					conn.commit();
					return 1; // Éxito
				} else {
					conn.rollback();
					return 0; // No se encontró el huesped
				}
			} catch (SQLException e) {
				conn.rollback();
				throw new RuntimeException(e);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
