package hotel.pruebas;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import hotel.factory.ConnectionFactory;

public class GuardarPrueba {
	
    public static void main(String[] args) {

        int a = 4;
        for (int i = 0; i < a; i++) {
            String fechaEntrada = "2023-02-01";
            String fechaSalida = "2023-05-15";
            BigDecimal valor = calcularValor(fechaEntrada, fechaSalida);
            String formaPago = "Tarjeta de credito";

            String resultado = guardarReserva(fechaEntrada, fechaSalida, valor, formaPago);

            System.out.println(resultado.toString());
            
            String nombre = "John";
            String apellido = "De pleur";
            String fechaNacimiento = "1987-01-30";
            String nacionalidad = "Frances";
            String telefono = "0321943029";

            // Obtiene el ultimo id guardado en tabla Reserva y lo implemnenta como el idReserva
            int idReserva = obtenerUltimoIdReserva();

            String result = guardarHuesped(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
            System.out.println(result.toString());
        }
    }

    public static int obtenerUltimoIdReserva() {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try (Connection conn = connectionFactory.recuperaConexion()) {
            String query = "SELECT MAX(id) FROM reserva";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1; // Manejar el caso de error
    }

	private static BigDecimal calcularValor(String fechaEntrada, String fechaSalida) {
        BigDecimal valorPorDia = new BigDecimal("20.00");

        LocalDate fechaInicio = LocalDate.parse(fechaEntrada);
        LocalDate fechaFin = LocalDate.parse(fechaSalida);

        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;

        BigDecimal totalValor = valorPorDia.multiply(BigDecimal.valueOf(dias));

        return totalValor;
    }

    public static String guardarReserva(String fechaEntrada, String fechaSalida, BigDecimal valor, String formaPago) {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try (Connection conn = connectionFactory.recuperaConexion()) {
            String query = "INSERT INTO reserva(fechaEntrada, fechaSalida, valor, formaPago) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setDate(1, Date.valueOf(fechaEntrada));
                preparedStatement.setDate(2, Date.valueOf(fechaSalida));
                preparedStatement.setBigDecimal(3, valor);
                preparedStatement.setString(4, formaPago);

                preparedStatement.executeUpdate();
            }
            return "Reserva guardada exitosamente.";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String guardarHuesped(String nombre, String apellido, String fechaNacimiento, String nacionalidad, String telefono, int idReserva) {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try (Connection conn = connectionFactory.recuperaConexion()) {
            String query = "INSERT INTO huesped(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, apellido);
                preparedStatement.setDate(3, Date.valueOf(fechaNacimiento));
                preparedStatement.setString(4, nacionalidad);
                preparedStatement.setString(5, telefono);
                preparedStatement.setInt(6, idReserva);
                
                preparedStatement.executeUpdate();
            }
            return "Huesped guardado exitosamente.";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
}
