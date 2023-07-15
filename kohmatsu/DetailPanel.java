import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private int TodoSize_;/*TodoList size*/

	private JLabel		title_;
	// private JLabel		nameLabel_;
	// private JLabel		contentLabel_;
	// private JLabel		createdLabel_;
	// private JLabel		modifiedLabel_;
	// private JLabel		deadlineLabel_;
	// private JLabel		priorityLabel_;
	// private JLabel		createdByLabel_;
	// private JLabel		editByLabel_;
	// private JLabel		shareLabel_;

	// private int			todoIndex_;

	private JButton		cancelButton_;
	
	private MyButtonListener	myButtonListener_;

	private Member member_;

	private ToDoListPanel toDoListPanel_;

	String[] columns = {"elements", "content"};
	private	JTable		table_;

	// private int memberCount_ = 0;
	private ArrayList<String> sharedList_ = new ArrayList<String>();
	private HashMap<String, String> dictFromIndex = new HashMap<String, String>();
	
	DetailPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(new Color(238, 238, 238));
	}

	public void getFromRights(int todo)
	{
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:./test.db");
            statement = connection.createStatement();
			rs = statement.executeQuery("SELECT * FROM Member");
			while (rs.next())
				dictFromIndex.put(rs.getString(1), rs.getString(2));
			rs.close();
			rs = statement.executeQuery("SELECT * FROM Rights WHERE todo=" + todo);
			while (rs.next())
				sharedList_.add(rs.getString(2));
			rs.close();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } catch(ClassNotFoundException e) {
			System.out.println(e);
		} finally {
			try { connection.close(); } catch (Exception e) { /* Ignored */ }
			try { statement.close(); } catch (Exception e) { /* Ignored */ }
		}
	}

	public void prepareComponents(int todo, String ID) {
		Todo tmp = null;
		// todoIndex_ = todo;
		getFromRights(todo);
		
		try
		{
			member_ = new Member();
			member_.readFromDB(ID);
			TodoSize_ = member_.getNotArchiveCount();
			for (int i = 0; i < member_.getTodo().size(); i++)
			{
				if (member_.getTodo().get(i).getIndex() == todo)
					tmp = member_.getTodo().get(i);
			}
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e);
		}
		//
		// tmp.print();

		//画面上部
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		topPanel.setPreferredSize(new Dimension(600, 70));

		title_ = new JLabel("Detail");
		title_.setFont(new Font("Dialog", Font.BOLD, 30));
		title_.setForeground(new Color(0, 0, 100));
		title_.setHorizontalAlignment(SwingConstants.CENTER);
		title_.setToolTipText("");
		title_.setBounds(150, 20, 300, 40);
		topPanel.add(title_);

		this.add(topPanel, BorderLayout.NORTH);
		

		//画面中央
		JPanel middlePanel = new JPanel();
		if (TodoSize_ >= 0) //shareする人数によって縦の長さが変わる
			middlePanel.setPreferredSize(new Dimension(400, 400));
		else
			middlePanel.setPreferredSize(new Dimension(400, 400+30*TodoSize_));
		middlePanel.setLayout(new BorderLayout());

		Object[][] data_ = new Object[9][2];
		data_[0][0] = "title";
		data_[0][1] = tmp.getTitle();
		data_[1][0] = "content";
		data_[1][1] = tmp.getContents();
		data_[2][0] = "created";
		data_[2][1] = tmp.getCreated();
		data_[3][0] = "modified";
		data_[3][1] = tmp.getModified();
		data_[4][0] = "deadline";
		data_[4][1] = tmp.getDeadline();
		data_[5][0] = "priority";
		data_[5][1] = tmp.getPriority();
		data_[6][0] = "created by";
		data_[6][1] = tmp.getCreatedBy();
		data_[7][0] = "edit by";
		data_[7][1] = tmp.getEditedBy();
		data_[8][0] = "share";
		String sentence = "";
		for (int i = 0; i < sharedList_.size() - 1; i++)
			sentence += (dictFromIndex.get(sharedList_.get(i)) + ", ");
		sentence += dictFromIndex.get(sharedList_.get(sharedList_.size() - 1));
		data_[8][1] = sentence;
		table_ = new JTable(data_, columns);
		table_.setAutoCreateRowSorter(true);
		table_.setFillsViewportHeight(true);
		table_.setShowVerticalLines(false);
		table_.setGridColor(Color.black);
		table_.getColumn("elements").setPreferredWidth(80);
		table_.getColumn("content").setPreferredWidth(420);
		table_.setRowHeight(30);
		middlePanel.add(table_.getTableHeader(), BorderLayout.NORTH);
		middlePanel.add(table_);

		JScrollPane scrollPane = new JScrollPane(middlePanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(600, 280));
		this.add(scrollPane, BorderLayout.CENTER);

		// String sentence = "";
		// for (int i = 0; i < sharedList_.size() - 1; i++)
		// 	sentence += (dictFromIndex.get(sharedList_.get(i)) + ", ");
		// sentence += dictFromIndex.get(sharedList_.get(sharedList_.size() - 1));
		// shareLabel_ = new JLabel(sentence);
		// shareLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		// shareLabel_.setBounds(50, 340, 500, 20);
		// middlePanel.add(shareLabel_);

		// JScrollPane scrollPane = new JScrollPane(middlePanel);
		// scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// scrollPane.setPreferredSize(new Dimension(600, 280));
		// this.add(scrollPane, BorderLayout.CENTER);
		
		
		//画面下部
		cancelButton_ = new JButton("Back");
		cancelButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		cancelButton_.setBorderPainted(true);
		cancelButton_.setBackground(new Color(0, 0, 0));
		cancelButton_.setBounds(250, 330, 100, 30);
		this.add(cancelButton_, BorderLayout.SOUTH);
		
		
		myButtonListener_ = new MyButtonListener();
		cancelButton_.addActionListener(myButtonListener_);
	}
	
	private class MyButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == cancelButton_) {
				toDoListPanel_ = new ToDoListPanel();
				toDoListPanel_.prepareComponents(member_.getID());
				Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
			}
		}
	}
}