import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	//Components
	private JLabel		loginLabel_;
	private JLabel		userIDLabel_;
	private JLabel		passwordLabel_;
	private JLabel		loginErrLabel_;
	
	private JTextField	userIDField_;
	private JPasswordField	passwordField_;
	
	private JButton		signinButton_;
	private JButton		createAccountButton_;
	private JButton		forgotPasswordButton_;
	private JButton		exitButton_;
	
	private MyButtonListener	myButtonListener_;
	//panel
	// private CreateAccountPanel createAccountPanel_;
	// private ForgotPasswordPanel forgotPasswordPanel_;
	private ToDoListPanel	toDoListPanel_;
	private CreateAccountPanel createAccountPanel_;

	// private MainWindow mainWindow_;

	// private static Member member_;
	
	// LoginPanel(MainWindow mainWindow){
	// 	mainWindow_ = mainWindow;
	// 	this.setLayout(null);
	// 	this.setBackground(new Color(238, 238, 238));
	// }
	LoginPanel(){
		this.setLayout(null);
		this.setBackground(new Color(238, 238, 238));
	}
	
	public void prepareComponents() {
		
		userIDLabel_ = new JLabel("userID");
		userIDLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		userIDLabel_.setBounds(200, 150, 100, 10);
		this.add(userIDLabel_);
		
		passwordLabel_ = new JLabel("password");
		passwordLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		passwordLabel_.setBounds(205, 190, 100, 10);
		this.add(passwordLabel_);
		
		loginLabel_ = new JLabel("Login");
		loginLabel_.setFont(new Font("Dialog", Font.BOLD, 30));
		loginLabel_.setForeground(new Color(0, 255, 0));
		loginLabel_.setHorizontalAlignment(SwingConstants.CENTER);
		loginLabel_.setToolTipText("");
		loginLabel_.setBounds(250, 60, 100, 40);
		this.add(loginLabel_);
		
		loginErrLabel_ = new JLabel("Login failed");
		loginErrLabel_.setFont(new Font("Dialog", Font.PLAIN, 10));
		loginErrLabel_.setForeground(new Color(255, 0, 0));
		loginErrLabel_.setToolTipText("");
		loginErrLabel_.setBounds(200, 220, 100, 10);
		loginErrLabel_.setVisible(false);
		this.add(loginErrLabel_);

		userIDField_ = new JTextField();
		userIDField_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		userIDField_.setBounds(200, 160, 200, 20);
		userIDField_.setHorizontalAlignment(SwingConstants.LEFT);
		userIDField_.setColumns(10);
		this.add(userIDField_);
		
		passwordField_ = new JPasswordField();
		passwordField_.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		passwordField_.setColumns(10);
		passwordField_.setBounds(200, 200, 200, 20);
		this.add(passwordField_);
		
		signinButton_ = new JButton("Sign in");
		signinButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		signinButton_.setBorderPainted(false);
		signinButton_.setBackground(new Color(0, 255, 0));
		signinButton_.setBounds(250, 240, 100, 30);
		this.add(signinButton_);
		
		createAccountButton_ = new JButton("Create an account");
		createAccountButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		createAccountButton_.setForeground(new Color(0, 255, 0));
		createAccountButton_.setBorderPainted(false);
		createAccountButton_.setBounds(220, 290, 160, 30);
		this.add(createAccountButton_);
		
		forgotPasswordButton_ = new JButton("Forgot password?");
		forgotPasswordButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		forgotPasswordButton_.setForeground(UIManager.getColor("Button.disabledText"));
		forgotPasswordButton_.setBorderPainted(false);
		forgotPasswordButton_.setBounds(220, 320, 160, 30);
		this.add(forgotPasswordButton_);
		
		exitButton_ = new JButton("Exit");
		exitButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		exitButton_.setForeground(UIManager.getColor("Button.disabledText"));
		exitButton_.setBorderPainted(false);
		exitButton_.setBounds(10, 10, 100, 30);
		this.add(exitButton_);
		
		myButtonListener_ = new MyButtonListener();
		signinButton_.addActionListener(myButtonListener_);
		createAccountButton_.addActionListener(myButtonListener_);
		forgotPasswordButton_.addActionListener(myButtonListener_);
		exitButton_.addActionListener(myButtonListener_);
		
	}

	public boolean userCheck(String ID, String password) throws ClassNotFoundException
	{	
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:./test.db");
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Member");
			while (rs.next())
			{
				if (ID.compareTo(rs.getString(3)) == 0)
				{
					if (password.compareTo(rs.getString(5)) == 0)
						return (true);
				}
			}
			// rs.close();
			// statement.close();
			// connection.close();
			return (false);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
			System.exit(1);
        }
		finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
			try { connection.close(); } catch (Exception e) { /* Ignored */ }
			try { statement.close(); } catch (Exception e) { /* Ignored */ }
		}
		return (false);
	}

	// public void removeTodo() throws ClassNotFoundException
	// {
	// 	Connection connection = null;
	// 	Statement statement1 = null;
	// 	Statement statement2 = null;
	// 	ResultSet rsTodo = null;
	// 	ResultSet rsRights = null;
	// 	int index = 0;
	// 	ArrayList<Integer> removeList = new ArrayList<Integer>();
	// 	int notFoundFlag = 0;
	// 	try
	// 	{
	// 		Class.forName("org.sqlite.JDBC");
    //         connection = DriverManager.getConnection("jdbc:sqlite:./test.db");
    //         statement1 = connection.createStatement();
    //         statement2 = connection.createStatement();
	// 		rsTodo = statement1.executeQuery("SELECT * FROM Todo");
	// 		// int x = 0;
	// 		// while (rsTodo.next())
	// 		// {
	// 		// 	int tmp = rsTodo.getInt(1);
	// 		// 	System.out.println(tmp);
	// 		// 	System.out.println(x);
	// 		// 	x++;
	// 		// }
	// 		// System.out.println(x);
	// 		// System.exit(0);
	// 		while (rsTodo.next())
	// 		{
	// 			notFoundFlag = 0;
	// 			index = rsTodo.getInt(1);
	// 			System.out.println("index: " + index);
	// 			rsRights = statement2.executeQuery("SELECT * FROM Rights");
	// 			while (rsRights.next())
	// 			{
	// 				// System.out.println(rsRights.getInt(1));
	// 				if (rsRights.getInt(1) == index)
	// 				{
	// 					notFoundFlag = 1;
	// 					break;
	// 				}
	// 			}
	// 			if (notFoundFlag == 0)
	// 				removeList.add(Integer.valueOf(index));
	// 			rsRights.close();
	// 			// System.out.println("show: " + rsTodo.getInt(1));
	// 		}
	// 		rsTodo.close();
	// 		// rsTodo = statement.executeQuery("SELECT * FROM Todo");
	// 		for (int i = 0; i < removeList.size(); i++)
	// 			statement1.executeUpdate("DELETE FROM Todo WHERE id=" + removeList.get(i));
	// 	}
	// 	catch (SQLException e)
	// 	{
	// 		System.out.println(e);
	// 	}
	// 	finally
	// 	{
	// 		// try { rsTodo.close(); } catch (Exception e) { /* Ignored */ }
	// 		// try { rsRights.close(); } catch (Exception e) { /* Ignored */ }
	// 		try { connection.close(); } catch (Exception e) { /* Ignored */ }
	// 		try { statement1.close(); } catch (Exception e) { /* Ignored */ }
	// 		try { statement2.close(); } catch (Exception e) { /* Ignored */ }
	// 	}
	// }
	
	private class MyButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			try
			{
				String	userID, password;
				// String email;
				if (event.getSource() == signinButton_) {
					userID = userIDField_.getText();
					password = new String(passwordField_.getPassword());
					userIDField_.setText("");
					passwordField_.setText("");
					if (userCheck(userID, password) == false) {
						loginErrLabel_.setVisible(true);
					}
					else {
						loginErrLabel_.setVisible(false);
						// System.out.println('a');
						toDoListPanel_ = new ToDoListPanel();
						// System.out.println(userID);
						toDoListPanel_.prepareComponents(userID);
						// System.out.println('a');
						Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
						Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
					}
				}
				if (event.getSource() == createAccountButton_) {
					userIDField_.setText("");
					passwordField_.setText("");
					loginErrLabel_.setVisible(false);
					createAccountPanel_ = new CreateAccountPanel();
					createAccountPanel_.prepareComponents();
					Main.mainWindow_.add(createAccountPanel_, "createAccountPanel");
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.CREATE_ACCOUNT, null);
				}
				if (event.getSource() == forgotPasswordButton_) {
					userIDField_.setText("");
					passwordField_.setText("");
					loginErrLabel_.setVisible(false);
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.FORGOT_PASSWORD, null);
				}
				if (event.getSource() == exitButton_) {
					// removeTodo();
					System.exit(0);
				}
			}
			catch(ClassNotFoundException e)
			{
				System.err.println(e);
				System.exit(1);
			}
		}
		
	}
}
