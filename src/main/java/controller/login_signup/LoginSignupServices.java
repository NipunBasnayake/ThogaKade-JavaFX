package controller.login_signup;

import model.User;

public interface LoginSignupServices {

    boolean signup(User user);
    boolean login(String email, String password);
    boolean checkUser(String email);
    String getKey();
}
