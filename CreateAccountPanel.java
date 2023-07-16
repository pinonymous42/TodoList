

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.*;

public class CreateAccountPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private JLabel		title_;
	private JLabel		userNameLabel_;
	private JLabel		emailLabel_;
	private JLabel		passwordLabel_;
	private	JLabel		confirmLabel_;
	private JLabel		errLabel_;
	
	private JTextField	usernameField_;
	private JTextField	emailField_;
	private JPasswordField	passwordField_;
	private	JPasswordField	confirmField_;
	
	private JButton		createAccountButton_;
	private JButton		exitButton_;
	
	private MyButtonListener	myButtonListener_;

	private ToDoListPanel toDoListPanel_;
	private LoginPanel loginPanel_;

	CreateAccountPanel(){
		this.setLayout(null);
		this.setBackground(new Color(238, 238, 238));
	}
	
	public void prepareComponents() {
		
		title_ = new JLabel();
		title_.setText("Create an account");
		title_.setFont(new Font("Dialog", Font.BOLD, 30));
		title_.setForeground(new Color(0, 255, 0));
		title_.setHorizontalAlignment(SwingConstants.CENTER);
		title_.setToolTipText("");
		title_.setBounds(150, 40, 300, 40);
		this.add(title_);
		
		userNameLabel_ = new JLabel("user_name");
		userNameLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		userNameLabel_.setBounds(200, 110, 100, 10);
		this.add(userNameLabel_);
		
		emailLabel_ = new JLabel("email");
		emailLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		emailLabel_.setBounds(200, 150, 100, 10);
		this.add(emailLabel_);
		
		passwordLabel_ = new JLabel("password");
		passwordLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		passwordLabel_.setBounds(205, 190, 100, 10);
		this.add(passwordLabel_);
		
		confirmLabel_ = new JLabel("confirm password");
		confirmLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		confirmLabel_.setBounds(205, 230, 100, 10);
		this.add(confirmLabel_);

		errLabel_ = new JLabel("failed");
		errLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		errLabel_.setForeground(Color.red);
		errLabel_.setBounds(200, 265, 100, 10);
		errLabel_.setVisible(false);
		this.add(errLabel_);

		usernameField_ = new JTextField();
		usernameField_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		usernameField_.setBounds(200, 120, 200, 20);
		usernameField_.setHorizontalAlignment(SwingConstants.LEFT);
		usernameField_.setColumns(10);
		this.add(usernameField_);
		
		emailField_ = new JTextField();
		emailField_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		emailField_.setBounds(200, 160, 200, 20);
		emailField_.setHorizontalAlignment(SwingConstants.LEFT);
		emailField_.setColumns(10);
		this.add(emailField_);
		
		passwordField_ = new JPasswordField();
		passwordField_.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		passwordField_.setColumns(10);
		passwordField_.setBounds(200, 200, 200, 20);
		this.add(passwordField_);

		confirmField_ = new JPasswordField();
		confirmField_.setHorizontalAlignment(SwingConstants.LEFT);
		confirmField_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		confirmField_.setColumns(10);
		confirmField_.setBounds(200, 240, 200, 20);
		this.add(confirmField_);
		
		createAccountButton_ = new JButton("<html><u>Create an account</u><html>");
		createAccountButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		createAccountButton_.setForeground(new Color(0, 255, 0));
		createAccountButton_.setBorderPainted(false);
		createAccountButton_.setBounds(200, 280, 200, 30);
		this.add(createAccountButton_);
		
		exitButton_ = new JButton("Cancel");
		exitButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		exitButton_.setForeground(UIManager.getColor("Button.disabledText"));
		exitButton_.setBorderPainted(true);
		exitButton_.setBounds(250, 320, 100, 30);
		this.add(exitButton_);
		
		myButtonListener_ = new MyButtonListener();
		createAccountButton_.addActionListener(myButtonListener_);
		exitButton_.addActionListener(myButtonListener_);
		
	}

	public static String createNewAccount(String name, String email, String password) throws ClassNotFoundException
    {
        int num = 0;
        String ID;
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
		ResultSet rs = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./test.db");
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Member");
            while (rs.next())
			{
				if (rs.getString(2).equals(name) == true && rs.getString(4).equals(email) == true && rs.getString(5).equals(password) == true)
					return (null);
                num++;
			}
			Calendar cl = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			ID = sdf.format(cl.getTime()).substring(2, 4) + Integer.valueOf(num).toString();
            statement.executeUpdate("INSERT INTO Member VALUES(" + num + ", '" + name + "', '" + ID + "', '" + email + "', '" + password + "')");
			return (ID);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
		try { rs.close(); } catch (Exception e) { /* Ignored */ }
		try { connection.close(); } catch (Exception e) { /* Ignored */ }
		}
		return (null);
    }
	
	private class MyButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String	username, email, password, ID, confirm;
			try
			{
				if (event.getSource() == createAccountButton_) {
					username = usernameField_.getText();
					email = emailField_.getText();
					password = new String(passwordField_.getPassword());
					confirm = new String(confirmField_.getPassword());
					errLabel_.setVisible(true);
					usernameField_.setText("");
					emailField_.setText("");
					passwordField_.setText("");
					confirmField_.setText("");
					if (username.equals("") == true || email.equals("") == true ||  password.equals("") == true || confirm.equals("") == true || password.equals(confirm) == false)
						errLabel_.setVisible(true);
					else
					{
						ID = createNewAccount(username, email, password);
						if (ID == null)
							errLabel_.setVisible(true);
						else
						{
							toDoListPanel_ = new ToDoListPanel();
							toDoListPanel_.prepareComponents(ID);
							errLabel_.setVisible(false);
							Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
							Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
						}
					}
				}
				if (event.getSource() == exitButton_) {
					errLabel_.setVisible(false);
					loginPanel_ = new LoginPanel();
					loginPanel_.prepareComponents();
					Main.mainWindow_.add(loginPanel_, "loginPanel");
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.LOGIN, loginPanel_);
				}
			}
			catch (ClassNotFoundException e)
			{
				System.out.println(e);
			}
		}
		
	}
}
