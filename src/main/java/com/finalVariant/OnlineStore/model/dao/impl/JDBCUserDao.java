package com.finalVariant.OnlineStore.model.dao.impl;

import com.finalVariant.OnlineStore.controller.constants.SQLConstants;
import com.finalVariant.OnlineStore.model.dao.UserDao;
import com.finalVariant.OnlineStore.model.dao.mapper.UserMapper;
import com.finalVariant.OnlineStore.model.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao implements UserDao {
    private final Logger logger = LogManager.getLogger(JDBCUserDao.class);
    private final Connection connection;
    private final UserMapper userMapper = new UserMapper();

    public JDBCUserDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean create(User user) {
        boolean result = false;
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.CREATE_NEW_USER,
                                      PreparedStatement.RETURN_GENERATED_KEYS)){
            int i = 0;

            pstmt.setString(++i, user.getLogin());
            pstmt.setString(++i, user.getPassword());
            pstmt.setString(++i, user.getRole().toString());

            if(pstmt.executeUpdate() > 0){
                try(ResultSet rs = pstmt.getGeneratedKeys()){
                    if(rs.next()){
                        user.setId(rs.getLong(1));
                    }
                    result = true;
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Optional<User> findById(long id) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_USER_BY_ID)) {
            pstmt.setLong(1, id);

            try(ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    return Optional.of(userMapper.extractFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try(
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.FIND_ALL_USERS)
        ){
            while(rs.next()){
                users.add(userMapper.extractFromResultSet(rs));
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return users;
    }

    @Override
    public void delete(User user) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.DELETE_USER)){
            pstmt.setLong(1, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(User user) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_USER)){
            int i = 0;
            pstmt.setString(++i, user.getLogin());
            pstmt.setString(++i, user.getPassword());
            pstmt.setString(++i, user.getRole().toString());
            pstmt.setString(++i, user.getStatus().toString());
            pstmt.setLong(++i, user.getId());
            return pstmt.executeUpdate() > 0;
        }catch (SQLException e){
            logger.error(e.getMessage(), e);
        }
        return false;  //logger
    }

    @Override
    public void close() {
        try{
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try(PreparedStatement prtmt = connection.prepareStatement(SQLConstants.FIND_USER_BY_LOGIN)){
            prtmt.setString(1, login);
            try(ResultSet rs = prtmt.executeQuery()){
                if(rs.next()){
                    return Optional.of(userMapper.extractFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
