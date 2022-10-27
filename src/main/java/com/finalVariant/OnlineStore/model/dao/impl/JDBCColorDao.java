package com.finalVariant.OnlineStore.model.dao.impl;

import com.finalVariant.OnlineStore.controller.constants.SQLConstants;
import com.finalVariant.OnlineStore.model.dao.ColorDao;
import com.finalVariant.OnlineStore.model.dao.mapper.ColorMapper;
import com.finalVariant.OnlineStore.model.entity.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCColorDao implements ColorDao {
    private final Logger logger = LogManager.getLogger(JDBCColorDao.class);
    private final Connection connection;
    private final ColorMapper colorMapper = new ColorMapper();

    public JDBCColorDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean create(Product.Color color) {
        boolean result = false;
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.CREATE_COLOR,
                                        PreparedStatement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, color.getColor());
            if(pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        color.setId(rs.getLong(1));
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
    public Optional<Product.Color> findById(long id) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_COLOR_BY_ID)){
            pstmt.setLong(1, id);
            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(colorMapper.extractFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product.Color> findAll() {
        List<Product.Color> colors = new ArrayList<>();
        try(
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.FIND_ALL_COLORS)
        ){
            while(rs.next()){
                colors.add(colorMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return colors;
    }

    @Override
    public void delete(Product.Color color) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.DELETE_COLOR)){
            pstmt.setLong(1, color.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Product.Color color) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_COLOR)){
            int i = 0;
            pstmt.setString(++i, color.getColor());
            pstmt.setLong(++i, color.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void close() {
        try{
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
