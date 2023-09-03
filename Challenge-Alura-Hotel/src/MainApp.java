import java.sql.SQLException;

import javax.swing.JFrame;

import hotel.views.MenuPrincipal;

public class MainApp {

    public static void main(String[] args) throws SQLException {
        // Crea una instancia de MenuPrincipal
        MenuPrincipal menuPrincipalFrame = new MenuPrincipal();
        
        // Configura la operación de cierre
        menuPrincipalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Hace visible la ventana
        menuPrincipalFrame.setVisible(true);
    }
	
}