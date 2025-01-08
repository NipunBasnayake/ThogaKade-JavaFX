package controller.login_signup;

import db.DBConnection;
import lombok.Getter;
import model.User;
import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginSignupController implements LoginSignupServices{

    private final static String key = "@bCd3f";
    private static LoginSignupServices loginSignupServices;

    public String getKey() {
        return key;
    }

    public static LoginSignupServices getInstance() {
        return loginSignupServices==null?loginSignupServices=new LoginSignupController():loginSignupServices;
    }

    @Override
    public boolean signup(User user) {
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

    public boolean checkUser(String email) {
        try {
            return DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM users WHERE email ='" + email + "'").next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean login(String email, String password) {
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM users WHERE email ='" + email + "'");
            if (resultSet.next()) {
                BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
                basicTextEncryptor.setPassword(key);

                if (basicTextEncryptor.decrypt(resultSet.getString("password")).equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
