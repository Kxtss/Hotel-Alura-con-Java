package hotel.pruebas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import hotel.factory.ConnectionFactory;

public class PruebaLogin {
	public static void main(String[] args) {

		guardar();
		
		String usuario = "ADmiN";
		String userInputPassword = "admin";

		try {
			// Obtener la contraseña almacenada en la base de datos para el usuario
			String storedHashedPassword = obtenerContrasena(usuario);

			// Compara la contraseña proporcionada por el usuario con la contraseña
			// almacenada en la base de datos
			if (BCrypt.checkpw(userInputPassword, storedHashedPassword)) {
				// Contraseña válida, permite el inicio de sesión
				System.out.println("Login\n");
				System.out.println("Stored pw: " + storedHashedPassword + "\n");
			} else {
				System.out.println("contraseña invalida");
			}

		} catch (Exception e) {
			System.err.println("Error al autenticar el usuario");
			throw new RuntimeException(e);
		}
	}

	public static void guardar() {
		try {
			String user = "carrlo.s";
			String pw = "1234";
	        
	        if (!esNombreUsuarioValido(user)) {
	            System.out.println("El nombre de usuario contiene caracteres no permitidos o esta en mayusculas\n");
	        } else {
	            // Llamar a guardarContraseña con el usuario en minusculas
	            guardarContrasena(user, pw);
	        }
			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Ya hay un cliente con este usuario");
		}
	}
	
	public static boolean esNombreUsuarioValido(String usuario) {
	    // Define una expresión regular que describe el patron permitido
	    String patron = "^[a-z0-9_.-]+$";
	    
	    // Comprueba si el nombre de usuario coincide con el patrón
	    return usuario.matches(patron);
	}
	
	public static String obtenerContrasena(String usuario) {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		String storedHashedPassword = null;

		try (Connection conn = connectionFactory.recuperaConexion()) {
			String query = "SELECT Clave FROM login WHERE Usuario = ?";

			try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
				preparedStatement.setString(1, usuario);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						storedHashedPassword = resultSet.getString("Clave");
						System.out.println("obtener contraseña: " + storedHashedPassword);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return storedHashedPassword;
	}

	public static void guardarContrasena(String usuario, String clave) {
		// Contraseña proporcionada por el usuario
		String password = clave;

		// Generar un hash BCrypt de la contraseña
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

		// Almacenar hashedPassword en la base de datos junto con el nombre de usuario u
		// otra información relevante
		// Por ejemplo, puedes usar JDBC para realizar una inserción en la base de datos
		ConnectionFactory connectionFactory = new ConnectionFactory();

		try (Connection conn = connectionFactory.recuperaConexion()) {
			String query = "INSERT INTO login(Usuario, Clave) VALUES (?, ?)";

			try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
				preparedStatement.setString(1, usuario);
				preparedStatement.setString(2, hashedPassword);

				preparedStatement.execute();
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
