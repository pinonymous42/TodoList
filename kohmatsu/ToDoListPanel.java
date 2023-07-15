

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
	private	JButton		detailButton_;

	private AddListPanel	addListPanel_;
	private LoginPanel loginPanel_;
	private ArchiveListPanel archiveListPanel_;
	private ToDoListPanel toDoListPanel_;
	private	DetailPanel		detailPanel_;
	
	private MyButtonListener	myButtonListener_;

	private Member member_;

	private JTable		table_;
	String[] columns = {"title", "deadline", "priority"};
	private JCheckBox	box_[];

	ToDoListPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(new Color(238, 238, 238));
	}
	public void prepareComponents(String ID) {

		/*画面上部*/
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		topPanel.setPreferredSize(new Dimension(600, 110));
		
		title = new JLabel("ToDolist");
		title.setFont(new Font("Dialog", Font.BOLD, 30));
		title.setForeground(new Color(0, 0, 0));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setToolTipText("");
		title.setBounds(150, 20, 300, 40);
		topPanel.add(title);

		err = new JLabel("choose only one list");
		err.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		err.setForeground(Color.red);
		err.setHorizontalAlignment(SwingConstants.CENTER);
		err.setBounds(150, 50, 300, 20);
		err.setVisible(false);
		topPanel.add(err);
		
		exitButton_ = new JButton("Logout");
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

		// this.add(topPanel, BorderLayout.NORTH);

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
			middlePanel.setPreferredSize(new Dimension(400, 10+30*TodoSize_));
		middlePanel.setLayout(null);

		Object[][] data_ = new Object[member_.getTodo().size()][3];
		box_ = new JCheckBox[TodoSize_];
		int count = 0;
		// System.out.println(member_.getTodoCount());
		for (int i = 0; i < member_.getTodo().size(); i++) {
			if (member_.getTodo().get(i).getArchive() == 0)
			{
				box_[count] = new JCheckBox(String.valueOf(member_.getTodo().get(i).getIndex()));
				box_[count].setBounds(20, 10+30*count, 400, 30);
				middlePanel.add(box_[count]);
				box_[count].setForeground(new Color(238, 238, 238));
				data_[count][0] = member_.getTodo().get(i).getTitle();
				data_[count][1] = member_.getTodo().get(i).getDeadline();
				data_[count][2] = member_.getTodo().get(i).getPriority();
				count++;
			}
		}
		table_ = new JTable(data_, columns);
		table_.setAutoCreateRowSorter(true);
		table_.setFillsViewportHeight(true);
		table_.setShowVerticalLines(true);
		table_.setGridColor(Color.black);
		table_.getColumn("title").setPreferredWidth(200);
		table_.getColumn("deadline").setPreferredWidth(200);
		table_.getColumn("priority").setPreferredWidth(100);
		table_.setRowHeight(30);
		table_.getTableHeader().setBounds(62, 70, 500, 30);
		table_.getTableHeader().setBackground(new Color(103, 181, 183));
		table_.getTableHeader().setForeground(Color.white);
		topPanel.add(table_.getTableHeader());
		table_.setBounds(60, 10, 500, 30*count);
		middlePanel.add(table_);
		JScrollPane scrollPane = new JScrollPane(middlePanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(600, 280));
		this.add(topPanel, BorderLayout.NORTH);
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

		detailButton_ = new JButton("detail");
		detailButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		detailButton_.setBorderPainted(true);
		detailButton_.setForeground(Color.black);
		detailButton_.setBounds(400, 10, 100, 30);
		bottomPanel.add(detailButton_);
		
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		
		myButtonListener_ = new MyButtonListener();
		exitButton_.addActionListener(myButtonListener_);
		removeButton_.addActionListener(myButtonListener_);
		archiveButton_.addActionListener(myButtonListener_);
		addButton_.addActionListener(myButtonListener_);
		goToArchiveButton_.addActionListener(myButtonListener_);
		detailButton_.addActionListener(myButtonListener_);
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
						member_.removeTodo(Integer.valueOf(box_[i].getText()));
						member_.setRemoveList(Integer.valueOf(box_[i].getText()));
						box_[i].setVisible(false);
					}					
				}
				member_.writeToDB();
				toDoListPanel_ = new ToDoListPanel();
				toDoListPanel_.prepareComponents(member_.getID());
				Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
			}
			if (event.getSource() == archiveButton_) {
				System.out.println(TodoSize_);
				for (int i = 0; i < TodoSize_; i++) {
					if (box_[i].isSelected()) {
						int	id = Integer.valueOf(box_[i].getText());
						member_.setEditList(id);
						for (int j = 0; j < member_.getTodo().size(); j++)
						{
							if (member_.getTodo().get(j).getIndex() == id)
								member_.getTodo().get(j).setArchive(1);
						}
						box_[i].setVisible(false);
					}
				}
				member_.writeToDB();
				toDoListPanel_ = new ToDoListPanel();
				toDoListPanel_.prepareComponents(member_.getID());
				Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
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
			if (event.getSource() == detailButton_){
				int	id = 0;
				editCount_ = 0;
				for (int i = 0; i < TodoSize_; i++) {
					if (box_[i].isSelected()) {
						id = Integer.valueOf(box_[i].getText());
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
					detailPanel_ = new DetailPanel();
					detailPanel_.prepareComponents(id, member_.getID());
					Main.mainWindow_.add(detailPanel_, "detailPanel");
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.DETAIL, detailPanel_);
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