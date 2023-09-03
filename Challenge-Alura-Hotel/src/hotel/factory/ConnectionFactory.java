package hotel.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	private final DataSource dataSource;

	/**
	 * Crea la conexion a la base de datos
	 */
	public ConnectionFactory() {
		var pooledDataSource = new ComboPooledDataSource();

		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/hotel_alura?useTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("root123");

		// pooledDataSource.setMaxPoolSize(10);
		
		this.dataSource = pooledDataSource;
	}

	/**
	 * Metodo para poder crear una conexion instanciando ConnetionFactory
	 * 
	 * @return
	 */
	public Connection recuperaConexion() {
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException();
		}

	}

}
