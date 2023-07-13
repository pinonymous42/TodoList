SRCS		:=	Main.java CreateAccountPanel.java ForgotPasswordPanel.java LoginPanel.java\
				MainWindow.java Member.java ScreenMode.java Todo.java ToDoListPanel.java\
				AddListPanel.java ArchiveListPanel.java
RM			:=	rm -rf

all: $(SRCS)
	javac $(SRCS) && java -classpath ".:./sqlite-jdbc-3.42.0.0.jar" $(basename $(SRCS))
	$(RM) *.class
	# rm test.db
	# mv 'testcopy.db' test.db
	# cp test.db testcopy.db

fclean: 
	$(RM) *.class

.PHONY: all
