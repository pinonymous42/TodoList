import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

public class AddListPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private JLabel		title_;
	private JLabel		nameLabel_;
	private JLabel		contentLabel_;
	private JLabel		deadlineLabel_;
	private JLabel		priorityLabel_;
	private JLabel		err_;
	
	private JTextField	name_;
	private JTextField	content_;
	private JTextField	deadline_;
	private JTextField	priority_;
	
	private JButton		cancelButton_;
	private JButton		addButton_;
	
	private MyButtonListener	myButtonListener_;

	private Member member_;

	private ToDoListPanel toDoListPanel_;
	
	AddListPanel(){
		this.setLayout(null);
		this.setBackground(new Color(238, 238, 238));
	}
	
	public void prepareComponents(String ID) {
		title_ = new JLabel("Add a new list");
		title_.setFont(new Font("Dialog", Font.BOLD, 30));
		title_.setForeground(new Color(0, 0, 100));
		title_.setHorizontalAlignment(SwingConstants.CENTER);
		title_.setToolTipText("");
		title_.setBounds(150, 60, 300, 40);
		this.add(title_);
		
		err_ = new JLabel("error");
		err_.setFont(new Font("Dialog", Font.BOLD, 13));
		err_.setForeground(new Color(255, 0, 0));
		err_.setHorizontalAlignment(SwingConstants.CENTER);
		err_.setToolTipText("");
		err_.setBounds(150, 240, 300, 20);
		err_.setVisible(false);
		this.add(err_);
		
		nameLabel_ = new JLabel("title");
		nameLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		nameLabel_.setBounds(205, 110, 100, 10);
		this.add(nameLabel_);
		
		contentLabel_ = new JLabel("content");
		contentLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		contentLabel_.setBounds(205, 150, 100, 10);
		this.add(contentLabel_);
		
		deadlineLabel_ = new JLabel("deadline");
		deadlineLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		deadlineLabel_.setBounds(205, 190, 100, 10);
		this.add(deadlineLabel_);
		
		priorityLabel_ = new JLabel("priority");
		priorityLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		priorityLabel_.setBounds(205, 230, 100, 10);
		this.add(priorityLabel_);
		
		

		name_ = new JTextField();
		name_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		name_.setBounds(200, 120, 200, 20);
		name_.setHorizontalAlignment(SwingConstants.LEFT);
		name_.setColumns(10);
		this.add(name_);
		
		content_ = new JTextField();
		content_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		content_.setBounds(200, 160, 200, 20);
		content_.setHorizontalAlignment(SwingConstants.LEFT);
		content_.setColumns(10);
		this.add(content_);
		
		deadline_ = new JTextField();
		deadline_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		deadline_.setBounds(200, 200, 200, 20);
		deadline_.setHorizontalAlignment(SwingConstants.LEFT);
		deadline_.setColumns(10);
		this.add(deadline_);
		
		priority_ = new JTextField();
		priority_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		priority_.setBounds(200, 240, 200, 20);
		priority_.setHorizontalAlignment(SwingConstants.LEFT);
		priority_.setColumns(10);
		this.add(priority_);
		
		
		
		cancelButton_ = new JButton("Cancel");
		cancelButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		cancelButton_.setBorderPainted(true);
		cancelButton_.setBackground(new Color(0, 255, 0));
		cancelButton_.setBounds(200, 300, 100, 30);
		this.add(cancelButton_);
		
		addButton_ = new JButton("Add");
		addButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		addButton_.setForeground(new Color(0, 255, 0));
		addButton_.setBorderPainted(true);
		addButton_.setBounds(300, 300, 100, 30);
		this.add(addButton_);

		try
		{
			member_ = new Member();
			member_.readFromDB(ID);
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e);
		}
		
		
		myButtonListener_ = new MyButtonListener();
		cancelButton_.addActionListener(myButtonListener_);
		addButton_.addActionListener(myButtonListener_);
		
		
	}

	public void prepareComponents(Todo todo, String ID) {
		title_ = new JLabel("Edit a new list");
		title_.setFont(new Font("Dialog", Font.BOLD, 30));
		title_.setForeground(new Color(0, 0, 100));
		title_.setHorizontalAlignment(SwingConstants.CENTER);
		title_.setToolTipText("");
		title_.setBounds(150, 60, 300, 40);
		this.add(title_);
		
		err_ = new JLabel("error");
		err_.setFont(new Font("Dialog", Font.BOLD, 13));
		err_.setForeground(new Color(255, 0, 0));
		err_.setHorizontalAlignment(SwingConstants.CENTER);
		err_.setToolTipText("");
		err_.setBounds(150, 240, 300, 20);
		err_.setVisible(false);
		this.add(err_);
		
		nameLabel_ = new JLabel("title");
		nameLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		nameLabel_.setBounds(205, 110, 100, 10);
		this.add(nameLabel_);
		
		contentLabel_ = new JLabel("content");
		contentLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		contentLabel_.setBounds(205, 150, 100, 10);
		this.add(contentLabel_);
		
		deadlineLabel_ = new JLabel("deadline");
		deadlineLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		deadlineLabel_.setBounds(205, 190, 100, 10);
		this.add(deadlineLabel_);
		
		priorityLabel_ = new JLabel("priority");
		priorityLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		priorityLabel_.setBounds(205, 230, 100, 10);
		this.add(priorityLabel_);
		
		name_ = new JTextField();
		name_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		name_.setBounds(200, 120, 200, 20);
		name_.setHorizontalAlignment(SwingConstants.LEFT);
		name_.setColumns(10);
		this.add(name_);
		
		content_ = new JTextField();
		content_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		content_.setBounds(200, 160, 200, 20);
		content_.setHorizontalAlignment(SwingConstants.LEFT);
		content_.setColumns(10);
		this.add(content_);
		
		deadline_ = new JTextField();
		deadline_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		deadline_.setBounds(200, 200, 200, 20);
		deadline_.setHorizontalAlignment(SwingConstants.LEFT);
		deadline_.setColumns(10);
		this.add(deadline_);
		
		priority_ = new JTextField();
		priority_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		priority_.setBounds(200, 240, 200, 20);
		priority_.setHorizontalAlignment(SwingConstants.LEFT);
		priority_.setColumns(10);
		this.add(priority_);
		
		
		
		cancelButton_ = new JButton("Cancel");
		cancelButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		cancelButton_.setBorderPainted(true);
		cancelButton_.setBackground(new Color(0, 255, 0));
		cancelButton_.setBounds(200, 300, 100, 30);
		this.add(cancelButton_);
		
		addButton_ = new JButton("Add");
		addButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		addButton_.setForeground(new Color(0, 255, 0));
		addButton_.setBorderPainted(true);
		addButton_.setBounds(300, 300, 100, 30);
		this.add(addButton_);

		try
		{
			member_ = new Member();
			member_.readFromDB(ID);
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e);
		}
		
		
		myButtonListener_ = new MyButtonListener();
		cancelButton_.addActionListener(myButtonListener_);
		addButton_.addActionListener(myButtonListener_);
		
		
	}
	
	private class MyButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			try
			{
				int index;
				String title;
				String contents;
				String created;
				String modified;
				String deadline;
				String priority;
				int archive;
				if (event.getSource() == cancelButton_) {
					err_.setVisible(false);
					toDoListPanel_ = new ToDoListPanel();
					toDoListPanel_.prepareComponents(member_.getID());
					Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
				}
				if (event.getSource() == addButton_) {
					if (name_.getText().equals("") == true && content_.getText().equals("") == true && deadline_.getText().equals("") == true && priority_.getText().equals("") == true) {
						err_.setVisible(true);
					}
					else {
					index = member_.getMaxIndex() + 1;
					title = name_.getText();
					contents = content_.getText();
					created = null;/*後で現在時刻を代入*/
					modified = null;
					deadline = deadline_.getText();
					priority = priority_.getText();
					archive = 0;
					member_.setAddCount(member_.getAddCount() + 1);
					err_.setVisible(false);
					Todo todo = new Todo(index, title, contents, created, modified, deadline, priority, archive);
					member_.addTodo(todo);
					member_.writeToDB();
					toDoListPanel_ = new ToDoListPanel();
					toDoListPanel_.prepareComponents(member_.getID());
					Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
					}
				}
			}
			catch (ClassNotFoundException e)
			{
				System.out.println(e);
			}
		}
	}
}