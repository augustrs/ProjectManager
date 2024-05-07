package com.example.projectmanagerkea.repository;

import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String db_username;
    @Value("${spring.datasource.password}")
    private String db_password;

    public User findUser(String username) throws SQLException {

        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT * FROM USER WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        User user = null;

        if (rs.next()) {
            user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setRealName(rs.getString("real_name"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRoleId(rs.getInt("role_id"));
        }
        return user;
    }

    public User verifyUser(String username, String password) throws SQLException {
        User user = findUser(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    public void createUser(User newUser) throws SQLException {

        Connection connection = ConnectionManager.getConnection(db_url,db_username,db_password);
        String SQL ="INSERT INTO USER(real_name, username, password, role_id) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(SQL);

        ps.setString(1, newUser.getRealName());
        ps.setString(2, newUser.getUsername());
        ps.setString(3, newUser.getPassword());
        ps.setInt(4,newUser.getRoleId());
        ps.executeUpdate();

    }

    public int findManagerId(int userId) throws SQLException {

        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT M.manager_id FROM USER U JOIN MANAGER M ON U.user_id = M.user_id WHERE U.user_id = ?";
        PreparedStatement ps = connection.prepareStatement(SQL);

        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("manager_id");
        }
        return 0;
    }

}
