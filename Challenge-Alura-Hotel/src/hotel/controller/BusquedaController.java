package hotel.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import hotel.dao.BusquedaDAO;
import hotel.factory.ConnectionFactory;
import hotel.modelo.Huesped;
import hotel.modelo.Reserva;
import hotel.views.Busqueda;

public class BusquedaController {
	private final BusquedaDAO busquedaDAO;

	/**
	 * Constructor que inicializa la conexion a la base de datos
	 */
	public BusquedaController() {
		this.busquedaDAO = new BusquedaDAO(new ConnectionFactory().recuperaConexion());
	}

	/**
	 * Metodo que sirve para mostrar en la interfaz las reservas que hay en base de
	 * datos que se implementa en {@link Busqueda}
	 * 
	 * @return
	 */
	// Mostrar
	public List<Reserva> listarReservas() {
		return busquedaDAO.listarReservas();
	}

	/**
	 * Metodo que sirve para mostrar en la interfaz los huespedes que hay en la base
	 * de datos que se implementa en {@link Busqueda}
	 * 
	 * @return
	 */
	public List<Huesped> listarHuesped() {
		return busquedaDAO.listarHuespedes();
	}

	/**
	 * Metodo que sirve para buscar por huesped en la vista de {@link Busqueda}
	 * 
	 * @param search
	 * @return
	 */
	// Buscar
	public List<Huesped> buscarHuesped(String search) {
		return busquedaDAO.buscarHuesped(search);
	}

	/**
	 * Metodo que sirve para buscar por reserva en la vista de {@link Busqueda}
	 * 
	 * @param search
	 * @return
	 */
	public List<Reserva> buscarReserva(String search) {
		return busquedaDAO.buscarReserva(search);
	}

	/**
	 * Metodo que sirve para limpiar la tabla o reiniciarla y dejarla limpia y clara
	 * para el siguiente metodo a usar despues de que se implementa
	 * 
	 * @param model
	 */
	// Limpiar metodo
	public void limpiarTabla(DefaultTableModel model) {
		busquedaDAO.limpiarTabla(model);
	}

	/**
	 * Metodo que sirve para editar la reserva en la vista {@link Busqueda} en la pestana de Reservas
	 * 
	 * @param fechaEntrada
	 * @param fechaSalida
	 * @param valor
	 * @param formaPago
	 * @param id
	 * @return
	 */
	// Modificar
	public int editarReserva(Date fechaEntrada, Date fechaSalida, BigDecimal valor, String formaPago, Integer id) {
		return busquedaDAO.editarReserva(fechaEntrada, fechaSalida, valor, formaPago, id);
	}

	/**
	 * Metodo que sirve para editar el huesped en la vista {@link Busqueda} en la pestana de Huespedes
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
		return busquedaDAO.editarHuesped(nombre, apellido, fechaNacimiento, nacionalidad, telefono, id);
	}

	/**
	 * Metodo para eliminar la Reserva seleccionada en la vista de {@link Busqueda} en la pestana de Reservas
	 * 
	 * @param id
	 * @return
	 */
	// Eliminar
	public int eliminarReserva(Integer id) {
		return busquedaDAO.eliminarReserva(id);
	}

	/**
	 * Metodo para eliminar el Huesped seleccionado en la vista de {@link Busqueda} en la pestana de Huespedes
	 * 
	 * @param id
	 * @return
	 */
	public int eliminarHuesped(Integer id) {
		return busquedaDAO.eliminarHuesped(id);
	}
}
