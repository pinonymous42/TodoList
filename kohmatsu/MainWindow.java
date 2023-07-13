

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.CardLayout;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ScreenMode screenMode_;
	private final int width = 600;
	private final int height = 400;
	
	private CardLayout layout_ = new CardLayout();
	private LoginPanel loginPanel_;
	private CreateAccountPanel createAccountPanel_;
	private ForgotPasswordPanel forgotPasswordPanel_;
	
	MainWindow(){
		this.setTitle("TODO");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(layout_);
		this.setPreferredSize(new Dimension(width, height));
		this.pack();
		this.setLocation(0, 0);;
	}
	public void preparePanels() {
		loginPanel_ = new LoginPanel();
		this.add(loginPanel_, "loginPanel");
		createAccountPanel_ = new CreateAccountPanel();
		this.add(createAccountPanel_, "createAccountPanel");
		forgotPasswordPanel_ = new ForgotPasswordPanel();
		this.add(forgotPasswordPanel_, "forgotPasswordPanel");
	}
	
	public void prepareComponents() {
		loginPanel_.prepareComponents();
		createAccountPanel_.prepareComponents();
		forgotPasswordPanel_.prepareComponents();
		Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.LOGIN, loginPanel_);
	}
	
	public void setFrontScreenAndFocus(ScreenMode s, JPanel Panel) {
		screenMode_ = s;
		switch(screenMode_) {
		case LOGIN:
			layout_.show(this.getContentPane(), "loginPanel");
			Panel.requestFocus();
			break ;
		case CREATE_ACCOUNT:
			layout_.show(this.getContentPane(), "createAccountPanel");
			Panel.requestFocus();
			break ;
		case FORGOT_PASSWORD:
			layout_.show(this.getContentPane(), "forgotPasswordPanel");
			Panel.requestFocus();
			break ;
		case TO_DO_LIST:
			layout_.show(this.getContentPane(), "toDoListPanel");
			Panel.requestFocus();
			break ;
		case ADD_LIST:
			layout_.show(this.getContentPane(), "addListPanel");
			Panel.requestFocus();
			break ;
		case ARCHIVE_LIST:
			layout_.show(this.getContentPane(), "archiveListPanel");
			Panel.requestFocus();
		}
	}
}
