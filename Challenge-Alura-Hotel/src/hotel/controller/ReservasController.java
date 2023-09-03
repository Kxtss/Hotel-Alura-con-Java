package hotel.controller;

import hotel.dao.ReservaDAO;
import hotel.factory.ConnectionFactory;
import hotel.modelo.Huesped;
import hotel.modelo.Reserva;
import hotel.views.RegistroHuesped;
import hotel.views.ReservasView;

public class ReservasController {
	private final ReservaDAO reservaDAO;
	
	/**
	 * Constructor que inicializa la conexion con la base de datos
	 */
	public ReservasController() {
		this.reservaDAO = new ReservaDAO(new ConnectionFactory().recuperaConexion());
	}
	
	/**
	 * Metodo que sirve para guardar la reserva de {@link ReservasView}
	 * 
	 * @param reserva
	 */
	// Guardar
	public void guardarReserva(Reserva reserva) {
		reservaDAO.guardarReserva(reserva);
	}

	/**
	 * Metodo que sirve para guardar el huesped de {@link RegistroHuesped}
	 * 
	 * @param huesped
	 */
	public void guardarHuesped(Huesped huesped) {
		reservaDAO.guardarHuesped(huesped);
	}
}
