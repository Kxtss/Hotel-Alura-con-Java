package hotel.modelo;

import java.sql.Date;

public class Huesped {

	private int id;
	private String nombre;
	private String apellido;
	private Date fechaNacimiento;
	private String nacionalidad;
	private String telefono;
	private int idReserva;

	public Huesped(int id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono,
			int idReserva) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
	}

	public Huesped(String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono, int idReserva) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public String getTelefono() {
		return telefono;
	}

	public int getIdReserva() {
		return idReserva;
	}

	@Override
	public String toString() {
		return String.format(
				"{id: %s, Nombre: %s, Apellido: %s, FechaNacimiento: %s, Nacionalidad: %s, Telefono: %s, IdReserva: %s}",
				this.id, this.nombre, this.apellido, this.fechaNacimiento, this.nacionalidad, this.telefono,
				this.idReserva);

	}

}
