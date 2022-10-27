package com.finalVariant.OnlineStore.model.dao.impl;

import com.finalVariant.OnlineStore.controller.constants.SQLConstants;
import com.finalVariant.OnlineStore.model.dao.SizeDao;
import com.finalVariant.OnlineStore.model.dao.mapper.SizeMapper;
import com.finalVariant.OnlineStore.model.entity.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCSizeDao implements SizeDao {
    private final Logger logger = LogManager.getLogger(JDBCSizeDao.class);
    private final Connection connection;
    private final SizeMapper sizeMapper = new SizeMapper();

    public JDBCSizeDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean create(Product.Size size) {
        boolean result = false;
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.CREATE_SIZE,
                PreparedStatement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, size.getSize());
            if(pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        size.setId(rs.getLong(1));
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
    public Optional<Product.Size> findById(long id) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_SIZE_BY_ID)){
            pstmt.setLong(1, id);
            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(sizeMapper.extractFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product.Size> findAll() {
        List<Product.Size> sizes = new ArrayList<>();
        try(
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.FIND_ALL_SIZES)
        ){
            while(rs.next()){
                sizes.add(sizeMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return sizes;
    }

    @Override
    public void delete(Product.Size size) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.DELETE_SIZE)){
            pstmt.setLong(1, size.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Product.Size size) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_SIZE)){
            int i = 0;
            pstmt.setString(++i, size.getSize());
            pstmt.setLong(++i, size.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
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
}
