package util;

import db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {

    private CrudUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static <T>T execute(String sql, Object... args) throws SQLException {
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            statement.setObject(i + 1, args[i]);
        }
        if (sql.toUpperCase().startsWith("SELECT")) {
            return (T) statement.executeQuery();
        }
        return (T) (Boolean) (statement.executeUpdate()>0);
    }
}
