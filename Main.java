

public class Main {
	public static MainWindow mainWindow_;
	// public static Todo bufferTodo_;
	public static void main(String[] args) {
		mainWindow_ = new MainWindow();
		// bufferTodo_ = new Todo();
		mainWindow_.preparePanels();
		mainWindow_.prepareComponents();
		mainWindow_.setFrontScreenAndFocus(ScreenMode.LOGIN, null);
		mainWindow_.setVisible(true);
		mainWindow_.setLocationRelativeTo(null);
	}
}
