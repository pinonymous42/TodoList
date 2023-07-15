import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddListPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private JLabel		title_;
	private JLabel		nameLabel_;
	private JLabel		contentLabel_;
	private JLabel		deadlineLabel_;
	private JLabel		priorityLabel_;
	private JLabel		shareLabel_;
	private JLabel		err_;
	
	private JTextField	name_;
	private JTextField	content_;

	SpinnerDateModel	model;
	JSpinner 			spinner;
	
	private int			todoIndex_;
	private	String[]	priorityList_ = {"1", "2", "3", "4", "5"};

	private JComboBox<String>	priorityBox_;

	private JButton		cancelButton_;
	private JButton		addButton_;
	private JButton		editButton_;
	private	JRadioButton	notShareButon_, shareButton_;
	private	ButtonGroup		shareGroup_;
	
	private MyButtonListener	myButtonListener_;

	private Member member_;

	private ToDoListPanel toDoListPanel_;
	private	ShareListPanel	shareListPanel;
	
	AddListPanel(){
		this.setLayout(null);
		this.setBackground(new Color(238, 238, 238));
	}
	
	public void prepareComponents(String ID) {
		title_ = new JLabel("Add a new list");
		title_.setFont(new Font("Dialog", Font.BOLD, 30));
		title_.setForeground(new Color(0, 0, 100));
		title_.setHorizontalAlignment(SwingConstants.CENTER);
		title_.setToolTipText("");
		title_.setBounds(150, 20, 300, 40);
		this.add(title_);
		
		err_ = new JLabel("error");
		err_.setFont(new Font("Dialog", Font.BOLD, 13));
		err_.setForeground(new Color(255, 0, 0));
		err_.setHorizontalAlignment(SwingConstants.CENTER);
		err_.setToolTipText("");
		err_.setBounds(150, 270, 300, 20);
		err_.setVisible(false);
		this.add(err_);
		
		nameLabel_ = new JLabel("title");
		nameLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		nameLabel_.setBounds(205, 80, 100, 10);
		this.add(nameLabel_);
		
		contentLabel_ = new JLabel("content");
		contentLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		contentLabel_.setBounds(205, 120, 100, 10);
		this.add(contentLabel_);
		
		deadlineLabel_ = new JLabel("deadline");
		deadlineLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		deadlineLabel_.setBounds(205, 160, 100, 10);
		this.add(deadlineLabel_);
		
		priorityLabel_ = new JLabel("priority");
		priorityLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		priorityLabel_.setBounds(205, 200, 100, 10);
		this.add(priorityLabel_);

		shareLabel_ = new JLabel("share");
		shareLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		shareLabel_.setBounds(205, 240, 100, 10);
		this.add(shareLabel_);
		
		

		name_ = new JTextField();
		name_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		name_.setBounds(200, 90, 200, 20);
		name_.setHorizontalAlignment(SwingConstants.LEFT);
		name_.setColumns(10);
		this.add(name_);
		
		content_ = new JTextField();
		content_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		content_.setBounds(200, 130, 200, 20);
		content_.setHorizontalAlignment(SwingConstants.LEFT);
		content_.setColumns(10);
		this.add(content_);


		//deadline
		model = new SpinnerDateModel();
		spinner = new JSpinner(model);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyy/MM/dd HH:mm:ss");
		spinner.setEditor(editor);
		spinner.setBounds(200, 170, 200, 20);
		this.add(spinner);

		
		priorityBox_ = new JComboBox<>(priorityList_);
		priorityBox_.setBounds(200, 210, 200, 20);
		this.add(priorityBox_);
		
		notShareButon_ = new JRadioButton("No", true);
		notShareButon_.setBounds(200, 250, 100, 20);
		this.add(notShareButon_);
		
		shareButton_ = new JRadioButton("Yes");
		shareButton_.setBounds(300, 250, 100, 20);
		this.add(shareButton_);
		
		shareGroup_ = new ButtonGroup();
		shareGroup_.add(notShareButon_);
		shareGroup_.add(shareButton_);
		
		
		
		cancelButton_ = new JButton("Cancel");
		cancelButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		cancelButton_.setBorderPainted(true);
		cancelButton_.setBackground(new Color(0, 255, 0));
		cancelButton_.setBounds(200, 330, 100, 30);
		this.add(cancelButton_);
		
		addButton_ = new JButton("Add");
		addButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		addButton_.setForeground(new Color(0, 255, 0));
		addButton_.setBorderPainted(true);
		addButton_.setBounds(300, 330, 100, 30);
		this.add(addButton_);

		try
		{
			member_ = new Member();
			member_.readFromDB(ID);
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e);
		}
		
		
		myButtonListener_ = new MyButtonListener();
		cancelButton_.addActionListener(myButtonListener_);
		addButton_.addActionListener(myButtonListener_);
		notShareButon_.addActionListener(myButtonListener_);
		shareButton_.addActionListener(myButtonListener_);
		
	}

	public void prepareComponents(int todo, String ID) {
		Todo tmp = null;
		todoIndex_ = todo;
		
		try
		{
			member_ = new Member();
			member_.readFromDB(ID);
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

		title_ = new JLabel("Edit a new list");
		title_.setFont(new Font("Dialog", Font.BOLD, 30));
		title_.setForeground(new Color(0, 0, 100));
		title_.setHorizontalAlignment(SwingConstants.CENTER);
		title_.setToolTipText("");
		title_.setBounds(150, 20, 300, 40);
		this.add(title_);
		
		err_ = new JLabel("error");
		err_.setFont(new Font("Dialog", Font.BOLD, 13));
		err_.setForeground(new Color(255, 0, 0));
		err_.setHorizontalAlignment(SwingConstants.CENTER);
		err_.setToolTipText("");
		err_.setBounds(150, 270, 300, 20);
		err_.setVisible(false);
		this.add(err_);
		
		nameLabel_ = new JLabel("title");
		nameLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		nameLabel_.setBounds(205, 80, 100, 10);
		this.add(nameLabel_);
		
		contentLabel_ = new JLabel("content");
		contentLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		contentLabel_.setBounds(205, 120, 100, 10);
		this.add(contentLabel_);
		
		deadlineLabel_ = new JLabel("deadline");
		deadlineLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		deadlineLabel_.setBounds(205, 160, 100, 10);
		this.add(deadlineLabel_);
		
		priorityLabel_ = new JLabel("priority");
		priorityLabel_.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		priorityLabel_.setBounds(205, 200, 100, 10);
		this.add(priorityLabel_);
		
		name_ = new JTextField(tmp.getTitle());
		name_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		name_.setBounds(200, 90, 200, 20);
		name_.setHorizontalAlignment(SwingConstants.LEFT);
		name_.setColumns(10);
		this.add(name_);
		
		content_ = new JTextField(tmp.getContents());
		content_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		content_.setBounds(200, 130, 200, 20);
		content_.setHorizontalAlignment(SwingConstants.LEFT);
		content_.setColumns(10);
		this.add(content_);
		



		//deadline
		model = new SpinnerDateModel();
		spinner = new JSpinner(model);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyy/MM/dd HH:mm:ss");
		spinner.setEditor(editor);
		spinner.setBounds(200, 170, 200, 20);
		this.add(spinner);
		
		priorityBox_ = new JComboBox<>(priorityList_);
		priorityBox_.setSelectedItem(tmp.getPriority());
		priorityBox_.setBounds(200, 210, 200, 20);
		this.add(priorityBox_);
		
		notShareButon_ = new JRadioButton("No", true);
		notShareButon_.setBounds(200, 250, 100, 20);
		this.add(notShareButon_);
		
		shareButton_ = new JRadioButton("Yes");
		shareButton_.setBounds(300, 250, 100, 20);
		this.add(shareButton_);
		
		shareGroup_ = new ButtonGroup();
		shareGroup_.add(notShareButon_);
		shareGroup_.add(shareButton_);
		
		cancelButton_ = new JButton("Cancel");
		cancelButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		cancelButton_.setBorderPainted(true);
		cancelButton_.setBackground(new Color(0, 255, 0));
		cancelButton_.setBounds(200, 330, 100, 30);
		this.add(cancelButton_);
		
		editButton_ = new JButton("Edit");
		editButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		editButton_.setForeground(new Color(0, 255, 0));
		editButton_.setBorderPainted(true);
		editButton_.setBounds(300, 330, 100, 30);
		this.add(editButton_);

		
		
		myButtonListener_ = new MyButtonListener();
		cancelButton_.addActionListener(myButtonListener_);
		editButton_.addActionListener(myButtonListener_);
		notShareButon_.addActionListener(myButtonListener_);
		shareButton_.addActionListener(myButtonListener_);
		
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
					err_.setVisible(false);
					toDoListPanel_ = new ToDoListPanel();
					toDoListPanel_.prepareComponents(member_.getID());
					Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
				}
				if (event.getSource() == addButton_) {
					int	id = 0;
					
					if (name_.getText().equals("") == true) {
						err_.setVisible(true);
					}
					else if (shareButton_.isSelected() == true){
						member_.writeToDB();
						shareListPanel = new ShareListPanel();
						shareListPanel.prepareComponents(id, member_.getID());
						Main.mainWindow_.add(shareListPanel, "shareListPanel");
						Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.SHARE_LIST, shareListPanel);
					}
					else {
					Calendar cl = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					index = member_.getMaxIndex() + 1;
					title = name_.getText();
					contents = content_.getText();
					created = sdf.format(cl.getTime());/*後で現在時刻を代入*/
					modified = null;
					deadline = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(model.getDate());
					priority = (String)priorityBox_.getSelectedItem();
					createdBy = member_.getName();
					editedBy = null;
					archive = 0;
					member_.setAddCount(member_.getAddCount() + 1);
					err_.setVisible(false);
					Todo todo = new Todo(index, title, contents, created, modified, deadline, priority, createdBy, editedBy, archive);
					member_.addTodo(todo);
					member_.writeToDB();
					toDoListPanel_ = new ToDoListPanel();
					toDoListPanel_.prepareComponents(member_.getID());
					Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
					}
				}
				if (event.getSource() == editButton_)
				{
					int	id = 0;
					
					if (name_.getText().equals("") == true) {
						err_.setVisible(true);
					}
					else if (shareButton_.isSelected() == true){
						member_.writeToDB();
						shareListPanel = new ShareListPanel();
						shareListPanel.prepareComponents(id, member_.getID());
						Main.mainWindow_.add(shareListPanel, "shareListPanel");
						Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.SHARE_LIST, shareListPanel);
					}
					else
					{
						Calendar cl = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						for (int i = 0; i < member_.getTodo().size(); i++)
						{
							if (member_.getTodo().get(i).getIndex() == todoIndex_)
							{
								member_.getTodo().get(i).setTitle(name_.getText());
								member_.getTodo().get(i).setContents(content_.getText());
								member_.getTodo().get(i).setDeadline(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(model.getDate()));
								member_.getTodo().get(i).setPriority((String)priorityBox_.getSelectedItem());
								member_.getTodo().get(i).setModified(sdf.format(cl.getTime()));
								member_.getTodo().get(i).setEditedBy(member_.getID());
								member_.setEditList(todoIndex_);
							}
						}
						// member_.printTodo();
						member_.writeToDB();
						toDoListPanel_ = new ToDoListPanel();
						toDoListPanel_.prepareComponents(member_.getID());
						Main.mainWindow_.add(toDoListPanel_, "toDoListPanel");
						Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, toDoListPanel_);
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
