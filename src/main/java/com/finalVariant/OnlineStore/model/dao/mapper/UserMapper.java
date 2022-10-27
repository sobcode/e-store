package com.finalVariant.OnlineStore.model.dao.mapper;

import com.finalVariant.OnlineStore.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements Mapper<User>{
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return User.createUser(rs.getLong("ID"),
                               rs.getString("login"),
                               rs.getString("password"),
                               User.Role.valueOf(rs.getString("role")),
                               User.UserStatus.valueOf(rs.getString("status")));
    }
}
