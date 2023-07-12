

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgotPasswordPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	//Components
	private JLabel forgotPasswordLabel_;
	
	private JButton		exitButton_;
	
	
	private MyButtonListener	myButtonListener_;

	// private static Member member_;
	//Construct
	ForgotPasswordPanel(){
		this.setLayout(null);
		this.setBackground(new Color(238, 238, 238));
	}
	
	public void prepareComponents() {
		forgotPasswordLabel_ = new JLabel();
		forgotPasswordLabel_.setText("forgot password");
		forgotPasswordLabel_.setFont(new Font("Dialog", Font.BOLD, 30));
		forgotPasswordLabel_.setForeground(new Color(255, 0, 0));
		forgotPasswordLabel_.setHorizontalAlignment(SwingConstants.CENTER);
		forgotPasswordLabel_.setToolTipText("");
		forgotPasswordLabel_.setBounds(150, 60, 300, 40);
		this.add(forgotPasswordLabel_);
		
		
		
		exitButton_ = new JButton("Exit");
		exitButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		exitButton_.setForeground(UIManager.getColor("Button.disabledText"));
		exitButton_.setBorderPainted(false);
		exitButton_.setBounds(10, 10, 100, 30);
		this.add(exitButton_);
		
		myButtonListener_ = new MyButtonListener();
		exitButton_.addActionListener(myButtonListener_);
	}
	
	private class MyButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == exitButton_) {
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.LOGIN, null);
			}
		}
		
	}
}
