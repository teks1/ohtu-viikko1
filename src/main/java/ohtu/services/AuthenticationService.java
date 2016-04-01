package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    private UserDao userDao;

    @Autowired
    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (logInCheck(user, username, password)) {
                return true;
            }
        }

        return false;
    }
    private boolean logInCheck(User user, String username, String password){
        return (user.getUsername().equals(username)
                    && user.getPassword().equals(password));
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {

        if (checkPassword(password)) {
            return false;
        }
        if (checkUsername(username)) {
            return true;
        }

        return false;
    }

    private boolean checkPassword(String password) {
        return (password.length() < 8 || !password.matches("(.*)(\\d+)(.*)") && !password.matches("(.*)(\\W+)(.*)"));
    }

    private boolean checkUsername(String username) {
        return (username.length() < 3 || username.matches("(.*)(\\d+)(.*)") || username.matches("(.*)(\\W+)(.*)"));
    }
}
