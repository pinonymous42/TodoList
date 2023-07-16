import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArchiveListPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	int		TodoSize_;
	
	JLabel		title_;
	JLabel		user_;

	JButton		removeButton_;
	JButton		archiveButton_;
	JButton		goToTodolist_;
	JButton		exitButton_;
	
	MyButtonListener	myButtonListener;

	private LoginPanel loginPanel_;
	private ToDoListPanel toDoListPanel_;
	private	ArchiveListPanel	archiveListPanel_;

	private Member member_;

	private JTable		table_;
	String[] columns = {"title", "deadline", "priority"};
	private JCheckBox	box_[];
	
	ArchiveListPanel(){
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(238, 238, 238));
	}
	
	public void prepareComponents(String ID) {
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

		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		topPanel.setPreferredSize(new Dimension(600, 100));
		
		title_ = new JLabel("Archive List");
		title_.setFont(new Font("Dialog", Font.BOLD, 30));
		title_.setForeground(new Color(255, 140, 0));
		title_.setHorizontalAlignment(SwingConstants.CENTER);
		title_.setToolTipText("");
		title_.setBounds(150, 20, 300, 40);
		topPanel.add(title_);

		user_ = new JLabel(member_.getName());
		user_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		user_.setForeground(new Color(103, 181, 183));
		user_.setHorizontalAlignment(SwingConstants.CENTER);
		user_.setToolTipText("");
		user_.setBounds(500, 10, 100, 30);
		topPanel.add(user_);
		
		exitButton_ = new JButton("Logout");
		exitButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		exitButton_.setForeground(UIManager.getColor("Button.disabledText"));
		exitButton_.setBorderPainted(true);
		exitButton_.setBounds(10, 10, 90, 30);
		topPanel.add(exitButton_);
		
		goToTodolist_ = new JButton("<html><u>go to To Do List</u><html>");
		goToTodolist_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		goToTodolist_.setForeground(Color.red);
		goToTodolist_.setBorderPainted(false);
		goToTodolist_.setBounds(422, 30, 200, 30);
		topPanel.add(goToTodolist_);

		JPanel middlePanel = new JPanel();
		if (TodoSize_ <= 8)
			middlePanel.setPreferredSize(new Dimension(400, 240));
		else
			middlePanel.setPreferredSize(new Dimension(400, 10+30*TodoSize_));
		middlePanel.setLayout(null);

		Object[][] data_ = new Object[member_.getTodo().size()][3];
		box_ = new JCheckBox[TodoSize_];
		int count = 0;
		for (int i = 0; i < member_.getTodo().size(); i++) {
			if (member_.getTodo().get(i).getArchive() == 1)
			{
				box_[count] = new JCheckBox(String.valueOf(member_.getTodo().get(i).getIndex()));
				box_[count].setBounds(20, 10+30*count, 600, 30);
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
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setPreferredSize(new Dimension(100, 50));
		
		removeButton_ = new JButton("remove");
		removeButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		removeButton_.setBorderPainted(true);
		removeButton_.setForeground(Color.black);
		removeButton_.setBounds(200, 10, 100, 30);
		bottomPanel.add(removeButton_);
		
		archiveButton_ = new JButton("return");
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
							int	id = Integer.valueOf(box_[i].getText());
							member_.setEditList(id);
							for (int j = 0; j < member_.getTodo().size(); j++)
							{
								if (member_.getTodo().get(j).getIndex() == id)
									member_.getTodo().get(j).setArchive(0);
							}
							box_[i].setVisible(false);
						}
					}
					member_.writeToDB();
					archiveListPanel_ = new ArchiveListPanel();
					archiveListPanel_.prepareComponents(member_.getID());
					Main.mainWindow_.add(archiveListPanel_, "archiveListPanel");
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.ARCHIVE_LIST, archiveListPanel_);
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
					archiveListPanel_ = new ArchiveListPanel();
					archiveListPanel_.prepareComponents(member_.getID());
					Main.mainWindow_.add(archiveListPanel_, "archiveListPanel");
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.ARCHIVE_LIST, archiveListPanel_);
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
