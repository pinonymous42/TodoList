import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArchiveListPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	//Components
	private int TodoSize_;//TodoList size
	JCheckBox	box_[];
	
	JLabel	title;

	JButton		removeButton_;
	JButton		archiveButton_;
	JButton		goToListButton_;
	JButton		exitButton_;

	private ToDoListPanel	toDoListPanel_;
	
	MyButtonListener	myButtonListener;

	private Member member_;
	
	
	//Construct
	ArchiveListPanel(){
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(238, 238, 238));
	}
	
	public void prepareComponents(String ID) {
		//画面上部
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		topPanel.setPreferredSize(new Dimension(600, 70));
		
		title = new JLabel("Archive List");
		title.setFont(new Font("Dialog", Font.BOLD, 30));
		title.setForeground(new Color(0, 0, 0));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setToolTipText("");
		title.setBounds(150, 20, 300, 40);
		topPanel.add(title);
		
		exitButton_ = new JButton("Exit");
		exitButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		exitButton_.setForeground(UIManager.getColor("Button.disabledText"));
		exitButton_.setBorderPainted(false);
		exitButton_.setBounds(10, 10, 100, 30);
		topPanel.add(exitButton_);
		
		goToListButton_ = new JButton("<html><u>go to To Do List</u><html>");
		goToListButton_.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		goToListButton_.setForeground(Color.red);
		goToListButton_.setBorderPainted(false);
		goToListButton_.setBounds(400, 10, 200, 30);
		topPanel.add(goToListButton_);
		this.add(topPanel, BorderLayout.NORTH);

		try
		{
			member_ = new Member();
			member_.readFromDB(ID);
			TodoSize_ = member_.getTodo().size();
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e);
		}
		
		//画面中央　リストを表示する部分　ここを変更
		JPanel middlePanel = new JPanel();
		if (TodoSize_ <= 8)
			middlePanel.setPreferredSize(new Dimension(400, 240));
		else
			middlePanel.setPreferredSize(new Dimension(400, 30*TodoSize_));
		middlePanel.setLayout(null);
		box_ = new JCheckBox[TodoSize_];
		for (int i = 0; i < TodoSize_; i++) {
			box_[i] = new JCheckBox(member_.getTodo().get(i).getTitle());
			box_[i].setBounds(100, 30*i, 400, 30);
			middlePanel.add(box_[i]);
		}
		JScrollPane scrollPane = new JScrollPane(middlePanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(600, 270));
		this.add(scrollPane, BorderLayout.CENTER);
		// this.add(middlePanel, BorderLayout.CENTER); これをなくすと上手くいく、理由不明
		
		
		
		//画面下部（ボタン）
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
		goToListButton_.addActionListener(myButtonListener);
	}
	
	private class MyButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			try
			{
				if (event.getSource() == exitButton_) {
					// member_.printTodo();
					member_.writeToDB();
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.LOGIN, null);
				}

				//removeButtonが押されたらアーカイブから削除、完全削除
				// if (event.getSource() == removeButton_) {
				// 	for (int i = 0; i < TodoSize_; i++) {
				// 		if (box_[i].isSelected()) {
				// 			member_.removeTodo(box_[i].getText());
				// 			member_.setRemoveList(box_[i].getText());
				// 			System.out.println(box_[i].getText() + " is removed");
				// 			box_[i].setVisible(false);
				// 		}					
				// 	}
				// 	Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, ToDoListPanel.this);
				// }
				if (event.getSource() == archiveButton_) {
					for (int i = 0; i < TodoSize_; i++) {
						if (box_[i].isSelected()) {
							System.out.println(box_[i].getText() + " is removed");
							box_[i].setVisible(false);
						}
					}
					Main.mainWindow_.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST, null);
				}
				if (event.getSource() == goToListButton_) {
					Main.ID_ = member_.getID();
					member_.writeToDB();
					toDoListPanel_ = new ToDoListPanel();
					toDoListPanel_.prepareComponents(Main.ID_);
					// member_.setAddCount(member_.getAddCount() + 1);//addcount increment
					// Main.bufferTodo_.setIndex(member_.getAddCount());
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
