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
	private JLabel		nameLabel_;
	private JLabel		contentLabel_;
	private JLabel		createdLabel_;
	private JLabel		modifiedLabel_;
	private JLabel		deadlineLabel_;
	private JLabel		priorityLabel_;
	private JLabel		createdByLabel_;
	private JLabel		editByLabel_;
	private JLabel		shareLabel_;

	private int			todoIndex_;

	private JButton		cancelButton_;
	
	private MyButtonListener	myButtonListener_;

	private Member member_;

	private ToDoListPanel toDoListPanel_;

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
		todoIndex_ = todo;
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
		middlePanel.setLayout(null);


		nameLabel_ = new JLabel("title: " + tmp.getTitle());
		nameLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		nameLabel_.setBounds(50, 20, 500, 20);
		middlePanel.add(nameLabel_);
		
		contentLabel_ = new JLabel("content: " + tmp.getContents());
		contentLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		contentLabel_.setBounds(50, 60, 500, 20);
		middlePanel.add(contentLabel_);
		
		createdLabel_ = new JLabel("created: " + tmp.getCreated());
		createdLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		createdLabel_.setBounds(50, 100, 500, 20);
		middlePanel.add(createdLabel_);

		modifiedLabel_ = new JLabel("modified: " + tmp.getModified());
		modifiedLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		modifiedLabel_.setBounds(50, 140, 500, 20);
		middlePanel.add(modifiedLabel_);

		deadlineLabel_ = new JLabel("deadline: " + tmp.getDeadline());
		deadlineLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		deadlineLabel_.setBounds(50, 180, 500, 20);
		middlePanel.add(deadlineLabel_);
		
		priorityLabel_ = new JLabel("priority: " + tmp.getPriority());
		priorityLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		priorityLabel_.setBounds(50, 220, 500, 20);
		middlePanel.add(priorityLabel_);
		
		createdByLabel_ = new JLabel("createdBy: " + tmp.getCreatedBy());
		createdByLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		createdByLabel_.setBounds(50, 260, 500, 20);
		middlePanel.add(createdByLabel_);

		editByLabel_ = new JLabel("editBy: " + tmp.getEditedBy());
		editByLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		editByLabel_.setBounds(50, 300, 500, 20);
		middlePanel.add(editByLabel_);

		String sentence = "shared: ";
		for (int i = 0; i < sharedList_.size() - 1; i++)
			sentence += (dictFromIndex.get(sharedList_.get(i)) + ", ");
		sentence += dictFromIndex.get(sharedList_.get(sharedList_.size() - 1));
		shareLabel_ = new JLabel(sentence);
		shareLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		shareLabel_.setBounds(50, 340, 500, 20);
		middlePanel.add(shareLabel_);

		JScrollPane scrollPane = new JScrollPane(middlePanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(600, 280));
		this.add(scrollPane, BorderLayout.CENTER);
		
		
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
