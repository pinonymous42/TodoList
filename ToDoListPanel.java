

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToDoListPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	//Components
	// private JLabel toDoListLabel_;
	// private JLabel	listLabel_;

	private int TodoSize_;//TodoList size

	private JLabel	title;

	private JButton		addButton_;
	private JButton		removeButton_;
	private JButton		archiveButton_;
	private JButton		exitButton_;

	private AddListPanel	addListPanel_;
	
	private MyButtonListener	myButtonListener_;

	private Member member_;

	private JCheckBox	box_[];

	//Construct
	ToDoListPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(new Color(238, 238, 238));
	}
	
	public void prepareComponents(String ID) {
		
// 		this.add(Box.createRigidArea(new Dimension(10, 10)));

// 		exitButton_ = new JButton("Exit");
// 		exitButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
// 		exitButton_.setForeground(UIManager.getColor("Button.disabledText"));
// 		exitButton_.setBorderPainted(false);
// //		exitButton_.setBounds(10, 10, 100, 30);
// 		exitButton_.setAlignmentX(Component.RIGHT_ALIGNMENT);
// 		this.add(exitButton_);
		
// 		this.add(Box.createRigidArea(new Dimension(10, 20)));
		
// 		toDoListLabel_ = new JLabel();
// 		toDoListLabel_.setText("To Do List");
// 		toDoListLabel_.setFont(new Font("Dialog", Font.BOLD, 30));
// 		toDoListLabel_.setForeground(new Color(0, 0, 0));
// 		toDoListLabel_.setHorizontalAlignment(SwingConstants.CENTER);
// 		toDoListLabel_.setToolTipText("");
// 		toDoListLabel_.setAlignmentX(Component.CENTER_ALIGNMENT);
// 		this.add(toDoListLabel_);
		//画面上部
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		topPanel.setPreferredSize(new Dimension(600, 70));
		
		title = new JLabel("ToDolist");
		title.setFont(new Font("Dialog", Font.BOLD, 30));
		title.setForeground(new Color(0, 0, 0));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setToolTipText("");
		title.setBounds(150, 20, 300, 40);
		topPanel.add(title);
		
		exitButton_ = new JButton("Exit");
		exitButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		exitButton_.setForeground(UIManager.getColor("Button.disabledText"));
		exitButton_.setBorderPainted(false);
		exitButton_.setBounds(10, 10, 100, 30);
		topPanel.add(exitButton_);
		
		this.add(topPanel, BorderLayout.NORTH);

		try
		{
			member_ = new Member();
			member_.readFromDB(ID);
			TodoSize_ = member_.getTodo().size();
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e);
		}
		
// 		for(int i = 0; i < member_.getTodo().size(); i++) {
// 			listLabel_ = new JLabel(member_.getTodo().get(i).getTitle());
// 			listLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
// //			listLabel_.setAlignmentX(Component.CENTER_ALIGNMENT);
// 			this.add(listLabel_);
// 		}
		//画面中央　リストを表示する部分
		JPanel middlePanel = new JPanel();
		if (TodoSize_ <= 8)
			middlePanel.setPreferredSize(new Dimension(400, 240));
		else
			middlePanel.setPreferredSize(new Dimension(400, 30*TodoSize_));
		middlePanel.setLayout(null);
		box_ = new JCheckBox[TodoSize_];
		for (int i = 0; i < TodoSize_; i++) {
			box_[i] = new JCheckBox(member_.getTodo().get(i).getTitle());
			box_[i].setBounds(100, 30*i, 400, 30);
			middlePanel.add(box_[i]);
		}
		JScrollPane scrollPane = new JScrollPane(middlePanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(600, 280));
		this.add(scrollPane, BorderLayout.CENTER);
		// this.add(middlePanel, BorderLayout.CENTER); これをなくすと上手くいく、理由不明

		//画面下部（ボタン）
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setPreferredSize(new Dimension(100, 50));
		
		removeButton_ = new JButton("remove");
		removeButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		removeButton_.setBorderPainted(true);
		removeButton_.setForeground(Color.black);
		removeButton_.setBounds(100, 10, 100, 30);
		bottomPanel.add(removeButton_);
		
		archiveButton_ = new JButton("archive");
		archiveButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		archiveButton_.setBorderPainted(true);
		archiveButton_.setForeground(Color.black);
		archiveButton_.setBounds(250, 10, 100, 30);
		bottomPanel.add(archiveButton_);
		
		addButton_ = new JButton("add");
		addButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		addButton_.setBorderPainted(true);
		addButton_.setForeground(Color.black);
		addButton_.setBounds(400, 10, 100, 30);
		bottomPanel.add(addButton_);
		
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		
		myButtonListener_ = new MyButtonListener();
		exitButton_.addActionListener(myButtonListener_);
		removeButton_.addActionListener(myButtonListener_);
		archiveButton_.addActionListener(myButtonListener_);
		addButton_.addActionListener(myButtonListener_);
	}
	
	private class MyButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
		try
		{
			if (event.getSource() == exitButton_) {
				// member_.printTodo();
				member_.writeToDB();
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.LOGIN, null);
			}
			if (event.getSource() == removeButton_) {
				for (int i = 0; i < TodoSize_; i++) {
					if (box_[i].isSelected()) {
						member_.removeTodo(box_[i].getText());
						member_.setRemoveList(box_[i].getText());
						System.out.println(box_[i].getText() + " is removed");
						box_[i].setVisible(false);
					}					
				}
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, ToDoListPanel.this);
			}
			if (event.getSource() == archiveButton_) {
				for (int i = 0; i < TodoSize_; i++) {
					if (box_[i].isSelected()) {
						System.out.println(box_[i].getText() + " is removed");
						box_[i].setVisible(false);
					}
				}
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, null);
			}
			if (event.getSource() == addButton_) {
				member_.writeToDB();
				addListPanel_ = new AddListPanel();
				addListPanel_.prepareComponents();
				// member_.setAddCount(member_.getAddCount() + 1);//addcount increment
				// Main.bufferTodo_.setIndex(member_.getAddCount());
				Main.mainWindow_.add(addListPanel_, "addListPanel");
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.ADD_LIST, addListPanel_);
			}
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e);
		}
	}
	}
}