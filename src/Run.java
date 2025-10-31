import Interfaz.Menu;

public class Run {
    public static void main(String[] args) {
        // Iniciar la interfaz gráfica
        java.awt.EventQueue.invokeLater(() -> new Menu().setVisible(true));
    }
}
