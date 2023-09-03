package hotel.controller;

import hotel.factory.ConnectionFactory;
import hotel.security.AutenticationService;
import hotel.views.LoginView;

public class LoginController {
	private final AutenticationService autenticacionService;
	
	/**
	 * Constructor que se usa para inicializar la conexion con la base de datos
	 */
	public LoginController() {
		this.autenticacionService = new AutenticationService(new ConnectionFactory().recuperaConexion());
	}
	
	/**
	 * Metodo que sirve para guardar un nuevo usuario en la base de datos que se implementa en {@link LoginView}
	 * 
	 * @param txtUser
	 * @param txtClave
	 */
	public void guardar(String txtUser, String txtClave) {
		this.autenticacionService.guardarNuevoUser(txtUser, txtClave);
	}
	
	/**
	 * Metodo que sirve para obtener la contrasena encriptada de la base de datos, se implementa en {@link LoginView}
	 * 
	 * @param string
	 * @return
	 */
	public String obtenerClave(String string) {
		return this.autenticacionService.obtenerContrasena(string);
	}
}
