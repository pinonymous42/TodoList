SRCS		:=	Main.java CreateAccountPanel.java ForgotPasswordPanel.java LoginPanel.java\
				MainWindow.java Member.java ScreenMode.java Todo.java ToDoListPanel.java\
				AddListPanel.java ArchiveListPanel.java DetailPanel.java
RM			:=	rm -rf

all: $(SRCS)
	javac -Xlint:deprecation $(SRCS) && java -classpath ".:./sqlite-jdbc-3.42.0.0.jar" $(basename $(SRCS))
	$(RM) *.class

fclean: 
	$(RM) *.class

.PHONY: all
