

public class Main {
	public static MainWindow mainWindow_;
	public static void main(String[] args) {
		mainWindow_ = new MainWindow();
		mainWindow_.preparePanels();
		mainWindow_.prepareComponents();
		mainWindow_.setVisible(true);
		mainWindow_.setLocationRelativeTo(null);
	}
}
