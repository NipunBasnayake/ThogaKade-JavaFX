package service.custom;

import model.User;
import service.SuperService;

public interface LoginSignupService extends SuperService {

    boolean signup(User user);
    boolean login(String email, String password);
    boolean checkUser(String email);
}
