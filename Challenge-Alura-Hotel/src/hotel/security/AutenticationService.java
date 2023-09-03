package hotel.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

import hotel.controller.LoginController;

@SuppressWarnings("serial")
public class AutenticationService extends JFrame {

	private final Connection conn;

	/**
	 * Constructor para inicializar los {@code PreparedStatement}
	 * 
	 * @param conn
	 */
	public AutenticationService(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Metodo que sirve para guardar un nuevo usuario
	 * 
	 * compara mediante el metodo {@code esNombreUsarioValido()} para saber si el
	 * texto ingresado es valido, si no lo es muestra un mensaje en pantalla, sino
	 * continua con la logica que compara si ya hay un usuario existente con el
	 * mismo nombre. Posteriormente se implementa en {@link LoginController}
	 * 
	 * @param txtUsuario
	 * @param txtClave
	 * @throws Exception por si ocurre algun error
	 */
	public void guardarNuevoUser(String txtUsuario, String txtClave) {
		try {
			String user = txtUsuario.toString();
			String pw = txtClave.toString();

			if (!esNombreUsuarioValido(user)) {
				JOptionPane.showMessageDialog(this,
						"El nombre de usuario contiene caracteres no permitidos o esta en mayusculas");
			} else {
				String usuarioGuardado = obtenerContrasena(user);

				if (usuarioGuardado != null) {
					JOptionPane.showMessageDialog(this, "Ya hay un cliente con este usuario");
				} else {
					guardar(user, pw);
					JOptionPane.showMessageDialog(this, "Guardado con éxito!");
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Ya hay un cliente con este usuario");
			System.err.println("Error: " + e);
		}
	}

	/**
	 * Metodo que sirve para validar el texto del usuario, que tenga minusculas,
	 * numeros del 0 al 9 y que pueda contener guiones (-), guiones bajos (_) y
	 * puntos (.). No se pueden usar mayusculas
	 * 
	 * @param usuario
	 * @return el usuario con el patron validando
	 */
	private boolean esNombreUsuarioValido(String usuario) {
		// Define una expresión regular que describe el patrón permitido
		String patron = "^[a-z0-9_.-]+$";

		// Comprueba si el nombre de usuario coincide con el patrón
		return usuario.matches(patron);
	}

	/**
	 * Metodo que sirve para buscar en la base de datos la contraseña ingresada, este metodo se usa en {@link LoginController}
	 * 
	 * @param usuario
	 * @return
	 */
	public String obtenerContrasena(String usuario) {
		String contrasenaGuardada = null;

		String query = "SELECT Clave FROM login WHERE Usuario = ?";

		try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
			preparedStatement.setString(1, usuario);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					contrasenaGuardada = resultSet.getString("Clave");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return contrasenaGuardada;
	}

	/**
	 * Guarda en la base de datos un nuevo usuario con el hash de {@code BCrypt}
	 * 
	 * @param usuario
	 * @param clave
	 */
	private void guardar(String usuario, String clave) {
		try {
			// Contraseña proporcionada por el usuario
			String password = clave;

			// Generar un hash BCrypt de la contraseña
			String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

			String query = "INSERT INTO login(Usuario, Clave) VALUES (?, ?)";

			try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
				preparedStatement.setString(1, usuario);
				preparedStatement.setString(2, hashPassword);

				preparedStatement.execute();

			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

		} catch (Exception e) {
			System.err.print("Error inseperado: " + e);
			JOptionPane.showMessageDialog(this, "Ya hay un cliente con este usuario");
		}
	}

}
