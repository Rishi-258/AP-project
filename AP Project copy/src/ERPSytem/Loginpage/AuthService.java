package ERPSytem.Loginpage;

import ERPSytem.Databaseconnector.database;
import ERPSytem.UserSession;

import java.sql.*;

public class AuthService {

    public boolean authenticate(String username, String password) {
        try (Connection conn = database.getConnection()) {

            String query = "SELECT * FROM users_auth WHERE username = ? AND password_hash = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UserSession.userId = rs.getInt("id");
                UserSession.username = rs.getString("username");
                UserSession.role = rs.getString("role");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
