package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static UserService service = new UserServiceImpl();

    public static void main(String[] args) {
        service.createUsersTable();
        service.saveUser("Djon", "Pupkin", (byte) 22);
        service.saveUser("Richard", "Ozarenok", (byte) 23);
        service.saveUser("Valera", "Lukashonok", (byte) 43);
        List<User> users = service.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
