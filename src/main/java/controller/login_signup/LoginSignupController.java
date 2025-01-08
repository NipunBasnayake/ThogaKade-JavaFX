package controller.login_signup;

import db.DBConnection;
import model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginSignupController implements LoginSignupServices{
    private static LoginSignupServices loginSignupServices;

    public static LoginSignupServices getInstance() {
        return loginSignupServices==null?loginSignupServices=new LoginSignupController():loginSignupServices;
    }

    @Override
    public boolean signup(User user) {
        System.out.println(user.toString());
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO users (username, email, password) VALUES (?,?,?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean login(String email, String password) {
        try {
            return DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM users WHERE email ='" + email + "' AND password ='" + password + "'").next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
