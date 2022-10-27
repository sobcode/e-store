package com.finalVariant.OnlineStore.model.dao.impl;

import com.finalVariant.OnlineStore.controller.constants.SQLConstants;
import com.finalVariant.OnlineStore.model.dao.CategoryDao;
import com.finalVariant.OnlineStore.model.dao.mapper.CategoryMapper;
import com.finalVariant.OnlineStore.model.entity.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCCategoryDao implements CategoryDao {
    private final Logger logger = LogManager.getLogger(JDBCCategoryDao.class);
    private final Connection connection;
    private final CategoryMapper categoryMapper = new CategoryMapper();

    public JDBCCategoryDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean create(Product.Category category) {
        boolean result = false;
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.CREATE_CATEGORY,
                                        PreparedStatement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, category.getName());

            if(pstmt.executeUpdate() > 0){
                try(ResultSet resultSet = pstmt.getGeneratedKeys()){
                    if(resultSet.next()){
                        category.setId(resultSet.getLong(1));
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
    public Optional<Product.Category> findById(long id) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_CATEGORY_BY_ID)){
            pstmt.setLong(1, id);
            try(ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(categoryMapper.extractFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product.Category> findAll() {
        List<Product.Category> categories = new ArrayList<>();
        try(
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.FIND_ALL_CATEGORIES)
        ) {
            while (rs.next()){
                categories.add(categoryMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return categories;
    }

    @Override
    public void delete(Product.Category category) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.DELETE_CATEGORY)){
            pstmt.setLong(1, category.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Product.Category category) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_CATEGORY)){
            int i = 0;
            pstmt.setString(++i, category.getName());
            pstmt.setLong(++i, category.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
