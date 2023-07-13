package tmp;


import java.util.*;

import Member;

public class Systemapp {
    private static Database database_;

    Systemapp()
    {
        database_ = new Database();
    }

    public boolean userCheck(String ID, String password)
    {
        for (int i = 0; i < database_.getMemberinfo().size(); i++)
        {
            if (ID.compareTo(database_.getMemberinfo().get(i).getID()) == 0)
            {
                if (password.compareTo(database_.getMemberinfo().get(i).getPassword()) == 0)
                {
                    // String name = database_.getMemberinfo().get(i).getName();
                    // String email = database_.getMemberinfo().get(i).getEmail();
                    System.out.println("Login success!");
                    return (true);
                }
                else
                {
                    System.out.println("password is wrong");
                    return (false);
                }
            }
        }
        System.out.println("ID is wrong");
        return (false);
    }

    public String userRegister()
    {
        String name;
        String ID = Integer.valueOf(database_.getMemberinfo().size() + 1).toString();
        String email;
        String password;
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.print("name: ");
            name = scanner.nextLine();
            System.out.print("email: ");
            email = scanner.nextLine();
            System.out.print("password: ");
            password = scanner.nextLine();
            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty())
                break ;
            System.out.println("Invalid, please input again!");
        }
        System.out.println("register success! your ID is " + ID);
        Member member = new Member(name, ID, email, password);
        database_.setMemberinfo(member);
        return (ID);
    }

    public void run(String ID)
    {
        Member member = null;
        for (int i = 0; i < database_.getMemberinfo().size(); i++)
        {
            if (ID.compareTo(database_.getMemberinfo().get(i).getID()) == 0)
                member = database_.getMemberinfo().get(i);
        }
        System.out.println("Welcome " + member.getName());
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println("[0]: print Todolist [1]: add Todolist, [2]: log out([2]: share)");
            String input = scanner.nextLine();
            if (Integer.valueOf(input) == 0)
            {
                member.printTodo();
            }
            else if (Integer.valueOf(input) == 1)
            {
                member.setTodo();
            }
            else if (Integer.valueOf(input) == 2)
            {
                System.out.println("log out");
                return ;
            }
            else
                System.out.println("Invalid input");
        }
    }
}
