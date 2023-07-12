package test;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToDoList extends JPanel{
	private static final long serialVersionUID = 1L;
	//Components
	//表示したいリストとそのリスト数を指定、ここを変更する
	String[] list = {"list1", "list2", "list3", "list4", "list5", "list6", "list7", "list8", "list9", "list10"};
	int		n = 10;
	
	
	JCheckBox	box[];
	
	JLabel	title;

	JButton		add_button;
	JButton		remove_button;
	JButton		archive_button;
	JButton		go_to_archive;
	JButton		exit_button;
	
	MyButtonListener	myButtonListener;
	
	
	//Construct
	ToDoList(){
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(238, 238, 238));
	}
	
	public void prepareComponents() {
		//画面上部
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		topPanel.setPreferredSize(new Dimension(600, 70));
		
		title = new JLabel("To Do list");
		title.setFont(new Font("Dialog", Font.BOLD, 30));
		title.setForeground(new Color(0, 0, 0));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setToolTipText("");
		title.setBounds(150, 20, 300, 40);
		topPanel.add(title);
		
		exit_button = new JButton("Exit");
		exit_button.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		exit_button.setForeground(UIManager.getColor("Button.disabledText"));
		exit_button.setBorderPainted(false);
		exit_button.setBounds(10, 10, 100, 30);
		topPanel.add(exit_button);
		
		go_to_archive = new JButton("<html><u>go to Archive List</u><html>");
		go_to_archive.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		go_to_archive.setForeground(Color.red);
		go_to_archive.setBorderPainted(false);
		go_to_archive.setBounds(400, 10, 200, 30);
		topPanel.add(go_to_archive);
		this.add(topPanel, BorderLayout.NORTH);
		
		//画面中央　リストを表示する部分　ここを変更
		JPanel middlePanel = new JPanel();
		if (n <= 8)
			middlePanel.setPreferredSize(new Dimension(400, 240));
		else
			middlePanel.setPreferredSize(new Dimension(400, 30*n));
		middlePanel.setLayout(null);
		box = new JCheckBox[n];
		for (int i = 0; i < n; i++) {
			box[i] = new JCheckBox(list[i]);
			box[i].setBounds(100, 30*i, 400, 30);
			middlePanel.add(box[i]);
		}
		JScrollPane scrollPane = new JScrollPane(middlePanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(600, 280));
		this.add(scrollPane, BorderLayout.CENTER);
//		this.add(middlePanel, BorderLayout.CENTER); これをなくすと上手くいく、理由不明
		
		
		
		//画面下部（ボタン）
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setPreferredSize(new Dimension(100, 50));
		
		remove_button = new JButton("remove");
		remove_button.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		remove_button.setBorderPainted(true);
		remove_button.setForeground(Color.black);
		remove_button.setBounds(100, 10, 100, 30);
		bottomPanel.add(remove_button);
		
		archive_button = new JButton("archive");
		archive_button.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		archive_button.setBorderPainted(true);
		archive_button.setForeground(Color.black);
		archive_button.setBounds(250, 10, 100, 30);
		bottomPanel.add(archive_button);
		
		add_button = new JButton("add");
		add_button.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		add_button.setBorderPainted(true);
		add_button.setForeground(Color.black);
		add_button.setBounds(400, 10, 100, 30);
		bottomPanel.add(add_button);
		
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		
		myButtonListener = new MyButtonListener();
		exit_button.addActionListener(myButtonListener);
		remove_button.addActionListener(myButtonListener);
		archive_button.addActionListener(myButtonListener);
		add_button.addActionListener(myButtonListener);
		go_to_archive.addActionListener(myButtonListener);
	}
	
	private class MyButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == exit_button) {
				Main.mainWindow.setFrontScreenAndFocus(ScreenMode.LOGIN);
			}
			if (event.getSource() == remove_button) {
				for (int i = 0; i < n; i++) {
					if (box[i].isSelected()) {
						System.out.println(box[i].getText() + " is removed");
						box[i].setVisible(false);
					}					
				}
				Main.mainWindow.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST);
			}
			if (event.getSource() == archive_button) {
				for (int i = 0; i < n; i++) {
					if (box[i].isSelected()) {
						System.out.println(box[i].getText() + " is removed");
						box[i].setVisible(false);
					}
				}
				Main.mainWindow.setFrontScreenAndFocus(ScreenMode.TO_DO_LIST);
			}
			if (event.getSource() == add_button) {
				Main.mainWindow.setFrontScreenAndFocus(ScreenMode.ADD_LIST);
			}
			if (event.getSource() == go_to_archive) {
				Main.mainWindow.setFrontScreenAndFocus(ScreenMode.ARCHIVE_LIST);
			}
		}
	}
}
