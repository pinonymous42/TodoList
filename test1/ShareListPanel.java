import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

public class ShareListPanel extends JPanel{
	private int TodoSize_;/*TodoList size*/

	private JLabel		title_;
	private	JLabel		err_;

	private JButton		cancelButton_;
	private JButton		addButton_;

	private JCheckBox	box_[];

	private int			todoIndex_;
	private int			editCount_;
	
	private Member member_;

	private MyButtonListener	myButtonListener_;

	private ToDoListPanel toDoListPanel_;

	ShareListPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(new Color(238, 238, 238));
	}
	public void prepareComponents(int todo, String ID) {
		Todo tmp = null;
		todoIndex_ = todo;
		
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

		//画面上部
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

		err_ = new JLabel("choose one at least");
		err_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		err_.setForeground(Color.red);
		err_.setHorizontalAlignment(SwingConstants.CENTER);
		err_.setBounds(150, 50, 300, 20);
		err_.setVisible(false);
		topPanel.add(err_);

		this.add(topPanel, BorderLayout.NORTH);

		//画面中央
		JPanel middlePanel = new JPanel();
		if (TodoSize_ <= 8)
			middlePanel.setPreferredSize(new Dimension(400, 240));
		else
			middlePanel.setPreferredSize(new Dimension(400, 30*TodoSize_));
		middlePanel.setLayout(null);

		box_ = new JCheckBox[TodoSize_];
		int count = 0;
		for (int i = 0; i < member_.getTodo().size(); i++) {
			if (member_.getTodo().get(i).getArchive() == 0)
			{
				box_[count] = new JCheckBox(String.valueOf(member_.getTodo().get(i).getIndex()));
				box_[count].setBounds(50, 10+30*count, 400, 30);
				middlePanel.add(box_[count]);
				count++;
			}
		}
		JScrollPane scrollPane = new JScrollPane(middlePanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(600, 280));
		this.add(scrollPane, BorderLayout.CENTER);



		//画面下部
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setPreferredSize(new Dimension(100, 50));

		cancelButton_ = new JButton("Cancel");
		cancelButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		cancelButton_.setBorderPainted(true);
		cancelButton_.setBackground(new Color(0, 255, 0));
		cancelButton_.setBounds(200, 10, 100, 30);
		bottomPanel.add(cancelButton_);
		
		addButton_ = new JButton("Add");
		addButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		addButton_.setForeground(new Color(0, 255, 0));
		addButton_.setBorderPainted(true);
		addButton_.setBounds(300, 10, 100, 30);
		bottomPanel.add(addButton_);
		this.add(bottomPanel, BorderLayout.SOUTH);


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
				String createdBy;
				String editedBy;
				int archive;
				if (event.getSource() == cancelButton_) {
					toDoListPanel_ = new ToDoListPanel();
					toDoListPanel_.prepareComponents(member_.getID());
					Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
				}
				if (event.getSource() == addButton_) {
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
					if (editCount_ == 0) {
						err_.setVisible(true);
					}
					else {
						err_.setVisible(false);
						member_.writeToDB();
						toDoListPanel_ = new ToDoListPanel();
						toDoListPanel_.prepareComponents(member_.getID());
						Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
						Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.DETAIL, toDoListPanel_);
					}
					// else {
					// 	Calendar cl = Calendar.getInstance();
					// 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					// 	index = member_.getMaxIndex() + 1;
					// 	title = name_.getText();
					// 	contents = content_.getText();
					// 	created = sdf.format(cl.getTime());/*後で現在時刻を代入*/
					// 	modified = null;
					// 	deadline = deadline_.getText();
					// 	priority = (String)priorityBox_.getSelectedItem();
					// 	createdBy = member_.getName();
					// 	editedBy = null;
					// 	archive = 0;
					// 	member_.setAddCount(member_.getAddCount() + 1);
					// 	err_.setVisible(false);
					// 	Todo todo = new Todo(index, title, contents, created, modified, deadline, priority, createdBy, editedBy, archive);
					// 	member_.addTodo(todo);
					// 	member_.writeToDB();
					// 	toDoListPanel_ = new ToDoListPanel();
					// 	toDoListPanel_.prepareComponents(member_.getID());
					// 	Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
					// 	Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
					// }
				}
				
			}
			catch (ClassNotFoundException e)
			{
				System.out.println(e);
			}
		}
	}
}
