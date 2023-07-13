package tmp;


import java.util.*;

public class MainSystem {
    public static Systemapp system_;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        system_ = new Systemapp();
        while (true)
        {
            System.out.print("[0]: sign in, [1]: sign up : ");
            String input = scanner.nextLine();
            if (Integer.valueOf(input) == 0)
            {
                String ID;
                String password;
                while (true)
                {
                    System.out.print("ID: ");
                    ID = scanner.nextLine();
                    System.out.print("password: ");
                    password = scanner.nextLine();
                    if (!ID.isEmpty() && !password.isEmpty())
                    {
                        if (system_.userCheck(ID, password) == false)
                            continue ;
                        else
                            break ;
                    }
                    System.out.println("Invalid, please input again!");
                }
                system_.run(ID);
            }
            else if (Integer.valueOf(input) == 1)
            {
                String ID = system_.userRegister();
                system_.run(ID);
            }
            else
                System.out.println("Invalid option");
        }
    }
}
