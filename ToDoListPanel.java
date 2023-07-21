import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToDoListPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private int TodoSize_;
	private	int	editCount_ = 0;

	private JLabel	title;
	private	JLabel	err;
	private JLabel	user_;

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
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(238, 238, 238));
	}
	public void prepareComponents(String ID) {
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

		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		topPanel.setPreferredSize(new Dimension(600, 100));
		
		title = new JLabel("ToDolist");
		title.setFont(new Font("Dialog", Font.BOLD, 30));
		title.setForeground(new Color(46, 139, 87));
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

		user_ = new JLabel(member_.getName());
		user_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		user_.setForeground(new Color(103, 181, 183));
		user_.setHorizontalAlignment(SwingConstants.CENTER);
		user_.setToolTipText("");
		user_.setBounds(500, 10, 100, 30);
		topPanel.add(user_);
		
		exitButton_ = new JButton("Logout");
		exitButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		// exitButton_.setContentAreaFilled(false);
		exitButton_.setMargin(new Insets(0, 0, 0, 0));
		exitButton_.setBackground(Color.white);
		exitButton_.setForeground(UIManager.getColor("Button.disabledText"));
		exitButton_.setBorderPainted(true);
		exitButton_.setBounds(10, 10, 90, 30);
		topPanel.add(exitButton_);

		goToArchiveButton_ = new JButton("<html><u>go to Archive List</u><html>");
		goToArchiveButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		goToArchiveButton_.setContentAreaFilled(false);
		goToArchiveButton_.setForeground(Color.red);
		goToArchiveButton_.setBorderPainted(false);
		goToArchiveButton_.setBounds(422, 30, 180, 30);
		topPanel.add(goToArchiveButton_);

		addButton_ = new JButton("+");
		addButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		// addButton_.setContentAreaFilled(false);
		addButton_.setMargin(new Insets(0, 0, 0, 0));
		addButton_.setBackground(Color.white);
		addButton_.setForeground(new Color(103, 181, 183));
		addButton_.setBounds(560, 70, 40, 30);
		topPanel.add(addButton_);

		JPanel middlePanel = new JPanel();
		if (TodoSize_ <= 8)
			middlePanel.setPreferredSize(new Dimension(400, 260));
		else
			middlePanel.setPreferredSize(new Dimension(400, 20+30*TodoSize_));
		middlePanel.setLayout(null);

		Object[][] data_ = new Object[member_.getTodo().size()][3];
		box_ = new JCheckBox[TodoSize_];
		int count = 0;
		for (int i = 0; i < member_.getTodo().size(); i++) {
			if (member_.getTodo().get(i).getArchive() == 0)
			{
				box_[count] = new JCheckBox(String.valueOf(member_.getTodo().get(i).getIndex()));
				box_[count].setOpaque(false);
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
		// removeButton_.setContentAreaFilled(false);
		removeButton_.setMargin(new Insets(0, 0, 0, 0));
		removeButton_.setBackground(Color.white);
		removeButton_.setBorderPainted(true);
		removeButton_.setForeground(Color.black);
		removeButton_.setBounds(150, 10, 80, 30);
		bottomPanel.add(removeButton_);
		
		archiveButton_ = new JButton("archive");
		archiveButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		// archiveButton_.setContentAreaFilled(false);
		archiveButton_.setMargin(new Insets(0, 0, 0, 0));
		archiveButton_.setBackground(Color.white);
		archiveButton_.setBorderPainted(true);
		archiveButton_.setForeground(Color.black);
		archiveButton_.setBounds(250, 10, 80, 30);
		bottomPanel.add(archiveButton_);
		
		detailButton_ = new JButton("detail");
		detailButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		// detailButton_.setContentAreaFilled(false);
		detailButton_.setMargin(new Insets(0, 0, 0, 0));
		detailButton_.setBackground(Color.white);
		detailButton_.setBorderPainted(true);
		detailButton_.setForeground(Color.black);
		detailButton_.setBounds(350, 10, 80, 30);
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
					}
				}
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