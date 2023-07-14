

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

	private int TodoSize_;/*TodoList size*/
	private	int	editCount_ = 0;//編集は一個ずつしかできない、何個リストが選択されているか確認用

	private JLabel	title;
	private	JLabel	err;

	private JButton		addButton_;
	private JButton		removeButton_;
	private JButton		archiveButton_;
	private	JButton		goToArchiveButton_;
	private JButton		exitButton_;
	private JButton		editButton_;

	private AddListPanel	addListPanel_;
	private LoginPanel loginPanel_;
	private ArchiveListPanel archiveListPanel_;
	
	private MyButtonListener	myButtonListener_;

	private Member member_;

	private JCheckBox	box_[];

	ToDoListPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(new Color(238, 238, 238));
	}
	public void prepareComponents(String ID) {

		/*画面上部*/
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

		err = new JLabel("choose one list to edit");
		err.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		err.setForeground(Color.red);
		err.setHorizontalAlignment(SwingConstants.CENTER);
		err.setBounds(150, 50, 300, 20);
		err.setVisible(false);
		topPanel.add(err);
		
		exitButton_ = new JButton("Exit");
		exitButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		exitButton_.setForeground(UIManager.getColor("Button.disabledText"));
		exitButton_.setBorderPainted(false);
		exitButton_.setBounds(10, 10, 100, 30);
		topPanel.add(exitButton_);

		goToArchiveButton_ = new JButton("<html><u>go to Archive List</u><html>");
		goToArchiveButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		goToArchiveButton_.setForeground(Color.red);
		goToArchiveButton_.setBorderPainted(false);
		goToArchiveButton_.setBounds(400, 10, 200, 30);
		topPanel.add(goToArchiveButton_);

		this.add(topPanel, BorderLayout.NORTH);
		
		this.add(topPanel, BorderLayout.NORTH);

		try
		{
			member_ = new Member();
			member_.readFromDB(ID);
			TodoSize_ = member_.getNotArchiveCount();
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e);
		}

		/*画面中央　リストを表示する部分*/
		JPanel middlePanel = new JPanel();
		if (TodoSize_ <= 8)
			middlePanel.setPreferredSize(new Dimension(400, 240));
		else
			middlePanel.setPreferredSize(new Dimension(400, 30*TodoSize_));
		middlePanel.setLayout(null);
		box_ = new JCheckBox[TodoSize_];
		int count = 0;
		// System.out.println(member_.getTodoCount());
		for (int i = 0; i < member_.getTodo().size(); i++) {
			if (member_.getTodo().get(i).getArchive() == 0)
			{
				box_[count] = new JCheckBox(member_.getTodo().get(i).getTitle());
				box_[count].setBounds(100, 30*count, 400, 30);
				middlePanel.add(box_[count]);
				count++;
			}
		}
		JScrollPane scrollPane = new JScrollPane(middlePanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(600, 280));
		this.add(scrollPane, BorderLayout.CENTER);

		/*画面下部（ボタン）*/
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
		archiveButton_.setBounds(200, 10, 100, 30);
		bottomPanel.add(archiveButton_);
		
		addButton_ = new JButton("add");
		addButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		addButton_.setBorderPainted(true);
		addButton_.setForeground(Color.black);
		addButton_.setBounds(300, 10, 100, 30);
		bottomPanel.add(addButton_);
		
		editButton_ = new JButton("edit");
		editButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		editButton_.setBorderPainted(true);
		editButton_.setForeground(Color.black);
		editButton_.setBounds(400, 10, 100, 30);
		bottomPanel.add(editButton_);
		
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		
		myButtonListener_ = new MyButtonListener();
		exitButton_.addActionListener(myButtonListener_);
		removeButton_.addActionListener(myButtonListener_);
		archiveButton_.addActionListener(myButtonListener_);
		addButton_.addActionListener(myButtonListener_);
		goToArchiveButton_.addActionListener(myButtonListener_);
		editButton_.addActionListener(myButtonListener_);
	}
	
	private class MyButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
		try
		{
			if (event.getSource() == exitButton_) {
				member_.writeToDB();
				loginPanel_ = new LoginPanel();
				loginPanel_.prepareComponents();
				Main.mainWindow_.add(loginPanel_, "loginPanel");
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.LOGIN, loginPanel_);
			}
			if (event.getSource() == removeButton_) {
				for (int i = 0; i < TodoSize_; i++) {
					if (box_[i].isSelected()) {
						member_.removeTodo(box_[i].getText());
						member_.setRemoveList(box_[i].getText());
						box_[i].setVisible(false);
					}					
				}
				member_.writeToDB();
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, ToDoListPanel.this);
			}
			if (event.getSource() == archiveButton_) {
				for (int i = 0; i < TodoSize_; i++) {
					if (box_[i].isSelected()) {
						for (int j = 0; j < member_.getTodo().size(); j++)
						{
							if (member_.getTodo().get(j).getTitle().compareTo(box_[i].getText()) == 0)
							{
								member_.setEditList(member_.getTodo().get(j).getIndex());
								member_.getTodo().get(j).setArchive(1);
							}
						}
						box_[i].setVisible(false);
					}
				}
				member_.writeToDB();
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, ToDoListPanel.this);
			}
			if (event.getSource() == addButton_) {
				addListPanel_ = new AddListPanel();
				addListPanel_.prepareComponents(member_.getID());
				Main.mainWindow_.add(addListPanel_, "addListPanel");
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.ADD_LIST, addListPanel_);
			}
			if (event.getSource() == goToArchiveButton_) {
				archiveListPanel_ = new ArchiveListPanel();
				archiveListPanel_.prepareComponents(member_.getID());
				Main.mainWindow_.add(archiveListPanel_, "archiveListPanel");
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.ARCHIVE_LIST, archiveListPanel_);
			}
			if (event.getSource() == editButton_) {
				editCount_ = 0;
				for (int i = 0; i < TodoSize_; i++) {
					if (box_[i].isSelected()) {
						editCount_++;
						// System.out.println(box_[i].getText() + " is edit");
					}
				}
				// System.out.println(editCount_);
				if (editCount_ != 1) {
					err.setVisible(true);
				}
				else {
					err.setVisible(false);
					member_.writeToDB();
					addListPanel_ = new AddListPanel();
					addListPanel_.prepareComponents(member_.getID());
					Main.mainWindow_.add(addListPanel_, "addListPanel");
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.ADD_LIST, addListPanel_);
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