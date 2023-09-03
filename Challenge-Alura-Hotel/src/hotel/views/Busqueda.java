package hotel.views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import hotel.controller.BusquedaController;
import hotel.modelo.Huesped;
import hotel.modelo.Reserva;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	private BusquedaController busquedaController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public Busqueda() {
		// Crea conexion
		busquedaController = new BusquedaController();

		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/hotel/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 290, 42);
		contentPane.add(lblNewLabel_4);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/hotel/imagenes/reservado.png")),
				scroll_table, null);
		scroll_table.setVisible(true);

		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/hotel/imagenes/pessoas.png")),
				scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);

		// Agregar un evento al cambiar la pestaña seleccionada
		panel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int selectedTabIndex = panel.getSelectedIndex();

				if (selectedTabIndex == 0) {
					// System.out.println("Pestaña Reserva seleccionada");
					busquedaController.limpiarTabla(modelo);
					cargarTablaReserva();

				} else if (selectedTabIndex == 1) { // indice de la pestaña "Huespedes"
					busquedaController.limpiarTabla(modeloHuesped);
					cargarTablaHuesped();

				}

			}
		});

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/hotel/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			// Al usuario pasar el mouse por el botón este cambiará de color
			@Override
			public void mouseEntered(MouseEvent e) {
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			// Al usuario quitar el mouse por el botón este volverá al estado original
			@Override
			public void mouseExited(MouseEvent e) {
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// System.out.println("Buscar");
				int indexTab = panel.getSelectedIndex();

				if (indexTab == 0) {
					buscarReservas();
				} else if (indexTab == 1) {
					buscarHuespedes();
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexTab = panel.getSelectedIndex();
				if (indexTab == 0) {
					editarReservas();
				}

				if (indexTab == 1) {
					editarHuespedes();
				}
			}
		});

		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);

		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexTab = panel.getSelectedIndex();
				if (indexTab == 0) {
					eliminarReserva();
					busquedaController.limpiarTabla(modelo);
					cargarTablaReserva();
					busquedaController.limpiarTabla(modeloHuesped);
					cargarTablaHuesped();
				}

				if (indexTab == 1) {
					eliminarHuesped();
					busquedaController.limpiarTabla(modeloHuesped);
					cargarTablaHuesped();
					busquedaController.limpiarTabla(modelo);
					cargarTablaReserva();
				}
			}

		});

	}

//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}

	// Metodos para el funcionamiento de la aplicacion

	/**
	 * Muestra la tabla de Reserva en la interfaz y pestana de Reservas
	 */
	protected void cargarTablaReserva() {
		List<Reserva> reservas = this.busquedaController.listarReservas();

		reservas.forEach(reserva -> modelo.addRow(new Object[] { reserva.getId(), reserva.getFechaEntrada(),
				reserva.getFechaSalida(), reserva.getValor(), reserva.getFormaPago() }));
	}

	/**
	 * Muestra la tabla de Huesped en la interfa y pestana de Huespedes
	 */
	protected void cargarTablaHuesped() {
		List<Huesped> huespedes = this.busquedaController.listarHuesped();

		huespedes.forEach(huesped -> modeloHuesped.addRow(new Object[] { huesped.getId(), huesped.getNombre(),
				huesped.getApellido(), huesped.getFechaNacimiento(), huesped.getNacionalidad(), huesped.getTelefono(),
				huesped.getIdReserva() }));
	}

	/**
	 * Metodo que sirve para tener el registro de la fila elegida en interfaz y
	 * hacer uso de ella en los metodos que la implementan
	 * 
	 * @param tabla
	 * @return
	 */
	private boolean filaElegida(JTable tabla) {
		return tabla.getSelectedRowCount() == 0 || tabla.getSelectedColumnCount() == 0;
	}

	/**
	 * Sirve para eliminar la Reserva seleccionada
	 */
	private void eliminarReserva() {
		if (filaElegida(tbReservas)) {
			JOptionPane.showMessageDialog(this, "Por favor, elije un item");
			return;
		}

		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
				.ifPresentOrElse(fila -> {
					Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());

					var cantidadEliminada = this.busquedaController.eliminarReserva(id);

					modelo.removeRow(tbReservas.getSelectedRow());

					JOptionPane.showMessageDialog(this, cantidadEliminada + " Item eliminado con éxito!");

				}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
	}

	/**
	 * Sirve para eliminar el Huesped seleccionado
	 */
	private void eliminarHuesped() {
		if (filaElegida(tbHuespedes)) {
			JOptionPane.showMessageDialog(this, "Por favor, elije un item");
			return;
		}

		try {
			Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
					.ifPresentOrElse(fila -> {
						Integer id = Integer
								.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());

						var cantidadEliminada = this.busquedaController.eliminarHuesped(id);

						modeloHuesped.removeRow(tbHuespedes.getSelectedRow());

						JOptionPane.showMessageDialog(this, cantidadEliminada + " Item eliminado con éxito");
					}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Sirve para buscar por huesped en la interfaz
	 */
	private void buscarHuespedes() {
		if (txtBuscar.getText().isBlank() || txtBuscar.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "El campo de búsqueda está vacío.");
			this.busquedaController.limpiarTabla(modeloHuesped);
			cargarTablaHuesped();
			return;
		}

		String search = txtBuscar.getText().trim().toLowerCase();
		List<Huesped> resultados = busquedaController.buscarHuesped(search);

		this.busquedaController.limpiarTabla(modeloHuesped);

		for (Huesped huesped : resultados) {
			modeloHuesped.addRow(new Object[] { huesped.getId(), huesped.getNombre(), huesped.getApellido(),
					huesped.getFechaNacimiento(), huesped.getNacionalidad(), huesped.getTelefono(),
					huesped.getIdReserva() });
		}

		if (modeloHuesped.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this, "No se encontraron resultados para el valor especificado.");
			this.busquedaController.limpiarTabla(modeloHuesped);
			cargarTablaHuesped();
		}
	}

	/**
	 * Sirve para buscar por reserva en la interfaz
	 */
	private void buscarReservas() {
		if (txtBuscar.getText().isBlank() || txtBuscar.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "El campo de búsqueda está vacío.");
			this.busquedaController.limpiarTabla(modelo);
			cargarTablaReserva();
			return;
		}

		String search = txtBuscar.getText().trim().toLowerCase();
		List<Reserva> resultados = busquedaController.buscarReserva(search);

		this.busquedaController.limpiarTabla(modelo);

		for (Reserva reserva : resultados) {
			modelo.addRow(new Object[] { reserva.getId(), reserva.getFechaEntrada(), reserva.getFechaSalida(),
					reserva.getValor(), reserva.getFormaPago() });
		}

		if (modelo.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this, "No se encontraron resultados para el valor especificado.");
			this.busquedaController.limpiarTabla(modelo);
			cargarTablaReserva();
		}
	}

	/**
	 * Sirve para editar las reservas
	 */
	private void editarReservas() {
		if (filaElegida(tbReservas)) {
			JOptionPane.showMessageDialog(this, "Por favor, elije un item");
			return;
		}

		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
				.ifPresentOrElse(fila -> {
					Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());
					Date fechaEntrada = Date
							.valueOf((String) modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString());
					Date fechaSalida = Date
							.valueOf((String) modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString());

					String valorStr = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 3);
					BigDecimal valor = new BigDecimal(valorStr);

					String formaPago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);

					var filasModificadas = this.busquedaController.editarReserva(fechaEntrada, fechaSalida, valor,
							formaPago, id);

					JOptionPane.showMessageDialog(this,
							String.format("%d item modificado con éxito!", filasModificadas));
				}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
	}

	/**
	 * Sirve para editar los huespedes
	 */
	private void editarHuespedes() {
		if (filaElegida(tbHuespedes)) {
			JOptionPane.showMessageDialog(this, "Por favor, elije un item");
			return;
		}

		Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
				.ifPresentOrElse(fila -> {
					Integer id = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
					String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
					String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
					Date fechaNacimiento = Date
							.valueOf((String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString());
					String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
					String telefono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);

					var filasModificadas = this.busquedaController.editarHuesped(nombre, apellido, fechaNacimiento,
							nacionalidad, telefono, id);

					JOptionPane.showMessageDialog(this,
							String.format("%d item modificado con éxito!", filasModificadas));
				}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
	}
}
