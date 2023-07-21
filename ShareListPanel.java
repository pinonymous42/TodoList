import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ShareListPanel extends JPanel{

	private JLabel		title_;
	private JButton		cancelButton_;
	private JButton		addButton_;

	private JCheckBox	box_[];

	private int			todoIndex_;
	
	private Member member_;

	private MyButtonListener	myButtonListener_;

	private ToDoListPanel toDoListPanel_;

	private ArrayList<String> sharedList_ = new ArrayList<String>();
	private int memberCount_ = 0;
	private HashMap<String, String> dictFromIndex = new HashMap<String, String>();
	private HashMap<String, String> dictFromName = new HashMap<String, String>();

	ShareListPanel(){
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
			{
				memberCount_++;
				dictFromIndex.put(rs.getString(1), rs.getString(2));
				dictFromName.put(rs.getString(2), rs.getString(1));
				sharedList_.add(rs.getString(1));
			}
			rs.close();
			rs = statement.executeQuery("SELECT * FROM Rights WHERE todo=" + todo);
			while (rs.next())
			{
				if (sharedList_.contains(rs.getString(2)))
					sharedList_.remove(rs.getString(2));
			}
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

	public void writeToRights()
	{
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:./test.db");
            statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM Rights WHERE todo=" + todoIndex_);
			for (int i = 0; i < sharedList_.size(); i++)
				statement.executeUpdate("INSERT INTO Rights values(" + todoIndex_ + ", " + Integer.valueOf(dictFromName.get(sharedList_.get(i))) + ")");
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
		todoIndex_ = todo;
		getFromRights(todo);
		try
		{
			member_ = new Member();
			member_.readFromDB(ID);
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e);
		}

		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		topPanel.setPreferredSize(new Dimension(600, 70));

		title_ = new JLabel("Share List");
		title_.setFont(new Font("Dialog", Font.BOLD, 30));
		title_.setForeground(new Color(0, 0, 100));
		title_.setHorizontalAlignment(SwingConstants.CENTER);
		title_.setToolTipText("");
		title_.setBounds(150, 20, 300, 40);
		topPanel.add(title_);

		this.add(topPanel, BorderLayout.NORTH);

		JPanel middlePanel = new JPanel();
		if (sharedList_.size() <= 8)
			middlePanel.setPreferredSize(new Dimension(400, 240));
		else
			middlePanel.setPreferredSize(new Dimension(400, 30*sharedList_.size()));
		middlePanel.setLayout(null);

		box_ = new JCheckBox[memberCount_];
		for (int i = 0; i < memberCount_; i++) {
				if (sharedList_.contains(String.valueOf(i)))
					box_[i] = new JCheckBox(dictFromIndex.get(String.valueOf(i)), false);
				else
					box_[i] = new JCheckBox(dictFromIndex.get(String.valueOf(i)), true);
				box_[i].setBounds(50, 10+30*i, 400, 30);
				middlePanel.add(box_[i]);
		}
		sharedList_.clear();
		JScrollPane scrollPane = new JScrollPane(middlePanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(600, 280));
		this.add(scrollPane, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setPreferredSize(new Dimension(100, 50));

		cancelButton_ = new JButton("Cancel");
		cancelButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		// cancelButton_.setContentAreaFilled(false);
		cancelButton_.setMargin(new Insets(0, 0, 0, 0));
		cancelButton_.setBackground(Color.white);
		cancelButton_.setBorderPainted(true);
		cancelButton_.setBackground(new Color(0, 255, 0));
		cancelButton_.setBounds(200, 10, 80, 30);
		bottomPanel.add(cancelButton_);
		
		addButton_ = new JButton("Change");
		addButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		// addButton_.setContentAreaFilled(false);
		addButton_.setMargin(new Insets(0, 0, 0, 0));
		addButton_.setBackground(Color.white);
		addButton_.setForeground(new Color(0, 255, 0));
		addButton_.setBorderPainted(true);
		addButton_.setBounds(300, 10, 80, 30);
		bottomPanel.add(addButton_);
		this.add(bottomPanel, BorderLayout.SOUTH);


		myButtonListener_ = new MyButtonListener();
		cancelButton_.addActionListener(myButtonListener_);
		addButton_.addActionListener(myButtonListener_);
	}

	private class MyButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == cancelButton_) {
				toDoListPanel_ = new ToDoListPanel();
				toDoListPanel_.prepareComponents(member_.getID());
				Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
			}
			if (event.getSource() == addButton_) {
				for (int i = 0; i < memberCount_; i++) {
					if (box_[i].isSelected()) {
						sharedList_.add(box_[i].getText());
					}
				}
				writeToRights();
				toDoListPanel_ = new ToDoListPanel();
				toDoListPanel_.prepareComponents(member_.getID());
				Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
				Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
			}
		}
	}
}
