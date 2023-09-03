package hotel.modelo;

import java.math.BigDecimal;
import java.sql.Date;

public class Reserva {
	public Huesped getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Huesped idReserva) {
		this.idReserva = idReserva;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	private Huesped idReserva;
    public void setId(int id) {
		this.id = id;
	}

	private int id;
    private Date fechaEntrada;
    private Date fechaSalida;
    private BigDecimal valor;
    private String formaPago;

    public Reserva(int id, Date fechaEntrada, Date fechaSalida, BigDecimal valor, String formaPago) {
        this.id = id;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.valor = valor;
        this.formaPago = formaPago;
    }

	public Reserva(Date fechaEntrada, Date fechaSalida, BigDecimal valor, String formaPago) {
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.formaPago = formaPago;
	}

	public Reserva() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
        return id;
    }
    
	public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public Date getFechaSalida() {
        return fechaSalida;
    }

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public BigDecimal getValor() {
        return valor;
    }

    public String getFormaPago() {
        return formaPago;
    }

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
    
    @Override
    public String toString() {
        return String.format("{id: %s, FechaEntrada: %s, FechaSalida: %s, Valor: %s, FormaPago: %s}", this.id, this.fechaEntrada, this.fechaSalida,
				this.valor, this.formaPago);
    }
}