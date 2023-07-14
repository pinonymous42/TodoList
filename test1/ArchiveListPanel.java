import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class ArchiveListPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	int		TodoSize_;
	
	
	JCheckBox	box_[];
	
	JLabel	title_;

	JButton		removeButton_;
	JButton		archiveButton_;
	JButton		goToTodolist_;
	JButton		exitButton_;
	
	MyButtonListener	myButtonListener;

	private LoginPanel loginPanel_;
	private ToDoListPanel toDoListPanel_;

	private Member member_;
	
	
	/*Construct*/
	ArchiveListPanel(){
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(238, 238, 238));
	}
	
	public void prepareComponents(String ID) {
		//画面上部
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		topPanel.setPreferredSize(new Dimension(600, 70));
		
		title_ = new JLabel("Archive List");
		title_.setFont(new Font("Dialog", Font.BOLD, 30));
		title_.setForeground(new Color(0, 0, 0));
		title_.setHorizontalAlignment(SwingConstants.CENTER);
		title_.setToolTipText("");
		title_.setBounds(150, 20, 300, 40);
		topPanel.add(title_);
		
		exitButton_ = new JButton("Exit");
		exitButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		exitButton_.setForeground(UIManager.getColor("Button.disabledText"));
		exitButton_.setBorderPainted(false);
		exitButton_.setBounds(10, 10, 100, 30);
		topPanel.add(exitButton_);
		
		goToTodolist_ = new JButton("<html><u>go to To Do List</u><html>");
		goToTodolist_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		goToTodolist_.setForeground(Color.red);
		goToTodolist_.setBorderPainted(false);
		goToTodolist_.setBounds(400, 10, 200, 30);
		topPanel.add(goToTodolist_);
		this.add(topPanel, BorderLayout.NORTH);

		try
		{
			member_ = new Member();
			member_.readFromDB(ID);
			TodoSize_ = member_.getArchiveCount();
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e);
		}
		
		/*画面中央　リストを表示する部分　ここを変更*/
		JPanel middlePanel = new JPanel();
		if (TodoSize_ <= 8)
			middlePanel.setPreferredSize(new Dimension(400, 240));
		else
			middlePanel.setPreferredSize(new Dimension(400, 30*TodoSize_));
		middlePanel.setLayout(null);
		box_ = new JCheckBox[TodoSize_];
		int count = 0;
		for (int i = 0; i < member_.getTodo().size(); i++) {
			if (member_.getTodo().get(i).getArchive() == 1)
			{
				//
				// System.out.println(member_.getTodo().get(i).getTitle());
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
		removeButton_.setBounds(200, 10, 100, 30);
		bottomPanel.add(removeButton_);
		
		archiveButton_ = new JButton("add list");
		archiveButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		archiveButton_.setBorderPainted(true);
		archiveButton_.setForeground(Color.black);
		archiveButton_.setBounds(300, 10, 100, 30);
		bottomPanel.add(archiveButton_);

		
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		
		myButtonListener = new MyButtonListener();
		exitButton_.addActionListener(myButtonListener);
		removeButton_.addActionListener(myButtonListener);
		archiveButton_.addActionListener(myButtonListener);
		goToTodolist_.addActionListener(myButtonListener);
	}
	
	private class MyButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			try
			{
				if (event.getSource() == exitButton_) {
					loginPanel_ = new LoginPanel();
					loginPanel_.prepareComponents();
					Main.mainWindow_.add(loginPanel_, "loginPanel");
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.LOGIN, loginPanel_);
				}
				if (event.getSource() == archiveButton_) {
					for (int i = 0; i < TodoSize_; i++) {
						if (box_[i].isSelected()) {
							for (int j = 0; j < member_.getTodo().size(); j++)
							{
								if (member_.getTodo().get(j).getTitle().compareTo(box_[i].getText()) == 0)
								{
									member_.setEditList(member_.getTodo().get(j).getIndex());
									member_.getTodo().get(j).setArchive(0);
								}
							}
							box_[i].setVisible(false);
						}					
					}
					member_.writeToDB();
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.ARCHIVE_LIST, ArchiveListPanel.this);
				}
				if (event.getSource() == removeButton_) {
					for (int i = 0; i < TodoSize_; i++) {
						if (box_[i].isSelected()) {
							member_.removeTodo(Integer.valueOf(box_[i].getText()));
							member_.setRemoveList(Integer.valueOf(box_[i].getText()));
							box_[i].setVisible(false);
						}
					}
					member_.writeToDB();
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.ARCHIVE_LIST, ArchiveListPanel.this);
				}
				if (event.getSource() == goToTodolist_) {
					toDoListPanel_ = new ToDoListPanel();
					toDoListPanel_.prepareComponents(member_.getID());
					Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
				}
			}
			catch (ClassNotFoundException e)
			{
				System.out.println(e);
			}
		}
	}
}
