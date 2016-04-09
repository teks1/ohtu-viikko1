package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ohtu.domain.User;

public class FileUserDAO implements UserDao {

    private final String fileName;
    private Scanner sc;
    private FileWriter writer;
    private List<User> users;

    public FileUserDAO(String fileName) {
        this.fileName = fileName;
        users = new ArrayList<User>();
        users = getUsersList();
    }

    @Override
    public List<User> listAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return new User(user.getUsername(), user.getPassword());
            }
        }
        return null;
    }

    @Override
    public void add(User user) {
        try {
            writer = new FileWriter(this.fileName, true);
            writer.write(user.getUsername() + " " + user.getPassword() + "\n");
            writer.close();
            users.add(user);
        } catch (IOException ex) {
            System.exit(-1);
        }
    }

    private Scanner createScanner() {
        try {
            sc = new Scanner(new File(this.fileName));
        } catch (FileNotFoundException ex) {
            System.out.println("Error " + ex);
            System.exit(-1);
        }
        return sc;
    }

    private List<User> getUsersList() {
        sc = createScanner();
        while (sc.hasNext()) {
            String username = sc.next();
            String password = sc.next();
            users.add(new User(username, password));
        }
        sc.close();
        return users;
    }
}
