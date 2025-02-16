package service.custom.impl;

import dto.User;
import org.jasypt.util.text.BasicTextEncryptor;
import service.custom.LoginSignupService;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginSignupServiceImpl implements LoginSignupService {
    private static LoginSignupService loginSignupServices;

    public static LoginSignupService getInstance() {
        if (loginSignupServices == null) {
            loginSignupServices = new LoginSignupServiceImpl();
        }
        return loginSignupServices;
    }

    @Override
    public boolean signup(User user) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?,?,?)";
        try {
            return CrudUtil.execute(sql, user.getUsername(), user.getEmail(), user.getPassword());
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean checkUser(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return CrudUtil.execute(sql, email);
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(sql, email);
            if (resultSet.next()) {
                BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
                String key = "@bCd3f";
                basicTextEncryptor.setPassword(key);

                return basicTextEncryptor.decrypt(resultSet.getString("password")).equals(password);
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }
}
