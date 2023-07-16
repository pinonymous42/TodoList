SRCS		:=	Main.java CreateAccountPanel.java LoginPanel.java\
				MainWindow.java Member.java ScreenMode.java Todo.java ToDoListPanel.java\
				AddListPanel.java ArchiveListPanel.java DetailPanel.java ShareListPanel.java\
				
RM			:=	rm -rf

all: $(SRCS)
	javac Main.java CreateAccountPanel.java LoginPanel.java MainWindow.java Member.java ScreenMode.java Todo.java ToDoListPanel.java AddListPanel.java ArchiveListPanel.java DetailPanel.java ShareListPanel.java  && java -classpath ".:./sqlite-jdbc-3.42.0.0.jar" Main CreateAccountPanel LoginPanel MainWindow Member ScreenMode Todo ToDoListPanel AddListPanel ArchiveListPanel DetailPanel ShareListPanel
	$(RM) *.class

fclean: 
	$(RM) *.class

.PHONY: all fclean
