

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel		loginLabel_;
	private JLabel		userIDLabel_;
	private JLabel		emailLabel_;
	private JLabel		passwordLabel_;
	private JLabel		loginErrLabel_;
	
	private JTextField	userNameField_;
	private JTextField	emailField_;
	private JPasswordField	passwordField_;
	
	private JButton		signinButton_;
	private JButton		createAccountButton_;
	private JButton		forgotPasswordButton_;
	private JButton		exitButton_;
	
	private MyButtonListener	myButtonListener_;

	private ToDoListPanel	toDoListPanel_;
	private CreateAccountPanel createAccountPanel_;

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

		userNameField_ = new JTextField();
		userNameField_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		userNameField_.setBounds(200, 160, 200, 20);
		userNameField_.setHorizontalAlignment(SwingConstants.LEFT);
		userNameField_.setColumns(10);
		this.add(userNameField_);
		
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

	public String userCheck(String username, String password) throws ClassNotFoundException
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
				if (username.compareTo(rs.getString(2)) == 0)
				{
					if (password.compareTo(rs.getString(5)) == 0)
						return (rs.getString(3));
				}
			}
			return (null);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
			System.exit(1);
        }
		finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
			try { connection.close(); } catch (Exception e) { /* Ignored */ }
			try { statement.close(); } catch (Exception e) { /* Ignored */ }
		}
		return (null);
	}
	
	private class MyButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			try
			{
				String	userID;
				if (event.getSource() == signinButton_) {
					if ((userID = userCheck(userNameField_.getText(), String.valueOf(passwordField_.getPassword()))) == null) {
						loginErrLabel_.setVisible(true);
						userNameField_.setText("");
						passwordField_.setText("");
					}
					else {
						toDoListPanel_ = new ToDoListPanel();
						toDoListPanel_.prepareComponents(userID);
						Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
						Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
					}
				}
				if (event.getSource() == createAccountButton_) {
					createAccountPanel_ = new CreateAccountPanel();
					createAccountPanel_.prepareComponents();
					Main.mainWindow_.add(createAccountPanel_, "createAccountPanel");
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.CREATE_ACCOUNT, createAccountPanel_);
				}
				if (event.getSource() == forgotPasswordButton_) {
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.FORGOT_PASSWORD, null);
				}
				if (event.getSource() == exitButton_) {
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
