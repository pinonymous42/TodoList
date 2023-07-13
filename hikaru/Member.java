

import java.util.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;


public class Member {
    private ArrayList<Todo> todo_;
    private int index_;
    private String name_;
    private String ID_;
    private String email_;
    private String password_;
    private int todoCount_ = 0;//size of todolist
    private int addCount_ = 0;//count the number of new adding to todolist
    private ArrayList<String> removeList_ = new ArrayList<String>();

    Member()
    {
        todo_ = new ArrayList<Todo>();
    }

    public void readFromDB(String ID) throws ClassNotFoundException
    {
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./test.db");
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT m.id, m.name, m.userID, m.email, m.password FROM Member m WHERE m.userID=" + ID);
            this.index_ = rs.getInt(1);
            this.name_ = rs.getString(2);
            this.ID_ = rs.getString(3);
            this.email_ = rs.getString(4);
            this.password_ = rs.getString(5);
            rs = statement.executeQuery("SELECT t.id, t.title, t.contents, t.created, t.modified, t.deadline, t.priority FROM Todo t, Rights r WHERE r.todo=t.id and r.member=" + rs.getString(1));
            while (rs.next())
                setTodo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            rs = statement.executeQuery("SELECT * FROM Todo");
            while (rs.next())
                this.todoCount_++;
            // rs.close();
			// statement.close();
			// connection.close();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
			try { connection.close(); } catch (Exception e) { /* Ignored */ }
			try { statement.close(); } catch (Exception e) { /* Ignored */ }
		}
    }

    public void writeToDB() throws ClassNotFoundException
    {
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<Integer> removeInt = new ArrayList<Integer>();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./test.db");
            statement = connection.createStatement();
            for (int i = 0; i < removeList_.size(); i++)
            {
                rs = statement.executeQuery("SELECT * FROM Todo WHERE title='" + removeList_.get(i) + "'");
                removeInt.add(rs.getInt(1));
                // System.out.println(removeList_.get(i));
                statement.executeUpdate("DELETE FROM Todo WHERE id=" +rs.getInt(1));
            }
            for (int i = 0; i < removeInt.size(); i++)
                statement.executeUpdate("DELETE FROM Rights WHERE todo=" + removeInt.get(i));
            for (int i = 0; i < todo_.size(); i++)
            {
                Todo tmp = this.todo_.get(i);
                // System.out.println(tmp.getTitle());
                // System.out.println();
                statement.executeUpdate("INSERT INTO Rights VALUES(" + tmp.getIndex() + ", " + this.index_ + ")");
                if (i >= todo_.size() - addCount_)
                    statement.executeUpdate("INSERT INTO Todo VALUES(" + tmp.getIndex() + ", '" + tmp.getTitle() + "', '" + tmp.getContents() + "', '" + tmp.getCreated() + "', '" + tmp.getModified() + "', '" + tmp.getDeadline() + "', '" + tmp.getPriority() + "')");
            }
			// statement.close();
			// connection.close();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
			try { connection.close(); } catch (Exception e) { /* Ignored */ }
			try { statement.close(); } catch (Exception e) { /* Ignored */ }
		}
    }

    public void removeTodo(String target)
    {
        int size = todo_.size();
        for (int i = 0; i < size; i++)
        {
            if (todo_.get(i).getTitle().compareTo(target) == 0)
            {
                todo_.remove(i);
                return ;
            }
        }
    }

    public ArrayList<String> getRemoveList()
    {
        return (removeList_);
    }

    public ArrayList<Todo> getTodo()
    {
        return (todo_);
    }

    public String getName()
    {
        return (name_);
    }

    public String getID()
    {
        return (ID_);
    }

    public String getEmail()
    {
        return (email_);
    }

    public String getPassword()
    {
        return (password_);
    }

    public int getTodoCount()
    {
        return (todoCount_);
    }

    public int getAddCount()
    {
        return (addCount_);
    }

    public void setTodo(int index, String title, String contents, String created, String modified, String deadline, String priority)
    {
        this.todo_.add(new Todo(index, title, contents, created, modified, deadline, priority));
    }

    public void addTodo(Todo todo)
    {
        this.todo_.add(todo);
    }

    public void setName(String name)
    {
        this.name_ = name;
    }

    public void setID(String ID)
    {
        this.ID_ = ID;
    }

    public void setEmail(String email)
    {
        this.email_ = email;
    }

    public void setPassword(String password)
    {
        this.password_ = password;
    }

    public void setTodoCount(int todoCount)
    {
        this.todoCount_ = todoCount;
    }

    public void setAddCount(int addCount)
    {
        this.addCount_ = addCount;
    }

    public void setRemoveList(String title)
    {
        this.removeList_.add(title);
    }

    public void printTodo()
    {
        for (int i = 0; i < this.todo_.size(); i++)
        {
            System.out.println();
            System.out.println("task " + i);
            System.out.println("title:" + this.todo_.get(i).getTitle());
            System.out.println("contents:" + this.todo_.get(i).getContents());
            System.out.println("created:" + this.todo_.get(i).getCreated());
            if (this.todo_.get(i).getModified() != null)
                System.out.println("modified:" + this.todo_.get(i).getModified());
            System.out.println("deadline:" + this.todo_.get(i).getDeadline());
            System.out.println("priority:" + this.todo_.get(i).getPriority());
        }
    }
}