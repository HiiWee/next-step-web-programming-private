package next.dao;

import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import next.exception.DataAccessException;
import next.model.User;

public class UserDao {
    public void insert(User user) {
        JdbcTemplate insertTemplate = new JdbcTemplate() {
            @Override
            protected void setValue(final PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };
        insertTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)");
    }

    public void update(final User user) {
        JdbcTemplate updateTemplate = new JdbcTemplate() {
            @Override
            protected void setValue(final PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }
        };
        updateTemplate.update("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?");
    }

    public User findByUserId(String userId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }

            return user;
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public List<User> findAll() {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS";
            Statement st = con.createStatement();

            rs = st.executeQuery(sql);

            List<User> users = new ArrayList<>();
            User user;
            while (rs.next()) {
                user = new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                users.add(user);
            }
            if (rs != null) {
                rs.close();
            }
            if (con != null) {
                con.close();
            }
            return users;
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }
}
