package test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.awt.event.FocusListener;

public class AddList extends JPanel{
	private static final long serialVersionUID = 1L;
	//Components
	JLabel		title;
	JLabel		name_label;
	JLabel		content_label;
	JLabel		deadline_label;
	JLabel		priority_label;
	JLabel		err;
	
	JTextField	name;
	JTextField	content;
	JTextField	deadline;
	JTextField	priority;
	
	String		list;
	
	JButton		cancel_button;
	JButton		add_button;
	
	MyButtonListener	myButtonListener;
	
	//Construct
	AddList(){
		this.setLayout(null);
		this.setBackground(new Color(238, 238, 238));
	}
	
	public void prepareComponents() {
		title = new JLabel("Add a new list");
		title.setFont(new Font("Dialog", Font.BOLD, 30));
		title.setForeground(new Color(0, 0, 100));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setToolTipText("");
		title.setBounds(150, 60, 300, 40);
		this.add(title);
		
		err = new JLabel("Error");
		err.setFont(new Font("Dialog", Font.BOLD, 13));
		err.setForeground(new Color(255, 0, 0));
		err.setHorizontalAlignment(SwingConstants.CENTER);
		err.setToolTipText("");
		err.setBounds(150, 270, 300, 20);
		err.setVisible(false);
		this.add(err);
		
		name_label = new JLabel("title");
		name_label.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		name_label.setBounds(205, 110, 100, 10);
		this.add(name_label);
		
		content_label = new JLabel("content");
		content_label.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		content_label.setBounds(205, 150, 100, 10);
		this.add(content_label);
		
		deadline_label = new JLabel("deadline");
		deadline_label.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		deadline_label.setBounds(205, 190, 100, 10);
		this.add(deadline_label);
		
		priority_label = new JLabel("priority");
		priority_label.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		priority_label.setBounds(205, 230, 100, 10);
		this.add(priority_label);
		
		

		name = new JTextField("");
		name.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		name.setBounds(200, 120, 200, 20);
		name.setHorizontalAlignment(SwingConstants.LEFT);
		name.setColumns(10);
		this.add(name);
		
		content = new JTextField();
		content.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		content.setBounds(200, 160, 200, 20);
		content.setHorizontalAlignment(SwingConstants.LEFT);
		content.setColumns(10);
		this.add(content);
		
		deadline = new JTextField();
		deadline.setText("2022/9/12");
		deadline.setForeground(Color.LIGHT_GRAY);
		deadline.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		deadline.setBounds(200, 200, 200, 20);
		deadline.setHorizontalAlignment(SwingConstants.LEFT);
		deadline.setColumns(10);
		deadline.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e){
				deadline.setText("");
				deadline.setForeground(Color.black);
		    }
		    @Override
		    public void focusLost(FocusEvent e){
		        if (deadline.getText().length() == 0){
		        	deadline.setText("2022/9/12");
		        	deadline.setForeground(Color.LIGHT_GRAY);
		        }
		    }
		});
		this.add(deadline);
		
		priority = new JTextField();
		priority.setText("1→優先度:低　5→優先度:高");
		priority.setForeground(Color.LIGHT_GRAY);
		priority.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		priority.setBounds(200, 240, 200, 20);
		priority.setHorizontalAlignment(SwingConstants.LEFT);
		priority.setColumns(10);
		priority.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e){;
				priority.setText("");
				priority.setForeground(Color.black);
		    }
		    @Override
		    public void focusLost(FocusEvent e){
		        if (priority.getText().length() == 0) {
		        	priority.setText("1→優先度:低　5→優先度:高");
		        	priority.setForeground(Color.LIGHT_GRAY);
		        }
		    }
		});
		this.add(priority);
		
		
		
		cancel_button = new JButton("Cancel");
		cancel_button.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		cancel_button.setBorderPainted(true);
		cancel_button.setBackground(new Color(0, 255, 0));
		cancel_button.setBounds(200, 300, 100, 30);
		this.add(cancel_button);
		
		add_button = new JButton("Add");
		add_button.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		add_button.setForeground(new Color(0, 255, 0));
		add_button.setBorderPainted(true);
		add_button.setBounds(300, 300, 100, 30);
		this.add(add_button);
		
		
		myButtonListener = new MyButtonListener();
		cancel_button.addActionListener(myButtonListener);
		add_button.addActionListener(myButtonListener);
		
	}
	
	private class MyButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == cancel_button) {
				name.setText("");
				err.setVisible(false);
				Main.mainWindow.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST);
			}
			if (event.getSource() == add_button) {
				list = name.getText();
				if (list.equals("") == true) {
					err.setVisible(true);
				}
				else {
				name.setText("");
				err.setVisible(false);
				System.out.println(list);
				Main.mainWindow.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST);
				}
			}
		}
	}
}
