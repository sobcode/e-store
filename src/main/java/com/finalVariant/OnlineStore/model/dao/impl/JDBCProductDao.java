package com.finalVariant.OnlineStore.model.dao.impl;

import com.finalVariant.OnlineStore.controller.constants.SQLConstants;
import com.finalVariant.OnlineStore.model.dao.ProductDao;
import com.finalVariant.OnlineStore.model.dao.exception.FieldNotPresent;
import com.finalVariant.OnlineStore.model.dao.mapper.ProductMapper;
import com.finalVariant.OnlineStore.model.entity.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class JDBCProductDao implements ProductDao {
    private final Logger logger = LogManager.getLogger(JDBCProductDao.class);
    private final Connection connection;
    private final ProductMapper productMapper = new ProductMapper();

    public JDBCProductDao(Connection connection){
        this.connection = connection;
    }
    @Override
    public boolean create(Product product) {
        boolean result = false;
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.CREATE_PRODUCT,
                                    PreparedStatement.RETURN_GENERATED_KEYS)){
            int i = 0;
            pstmt.setString(++i, product.getName());
            pstmt.setString(++i, product.getImg());
            pstmt.setInt(++i, product.getPrice());
            pstmt.setString(++i, product.getSex().toString());
            pstmt.setLong(++i, product.getCategory().getId());
            pstmt.setLong(++i, product.getSize().getId());
            pstmt.setLong(++i, product.getColor().getId());

            if(pstmt.executeUpdate() > 0){
                try(ResultSet rs = pstmt.getGeneratedKeys()){
                    if(rs.next()){
                        product.setId(rs.getLong(1));
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
    public Optional<Product> findById(long id) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_PRODUCT_BY_ID)){
            pstmt.setLong(1, id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return Optional.of(productMapper.extractFromResultSet(rs));
                }
            }
        } catch (SQLException | FieldNotPresent e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try(
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.FIND_ALL_PRODUCTS)
        ){
            while (rs.next()){
                try {
                    products.add(productMapper.extractFromResultSet(rs));
                }catch (FieldNotPresent fnp){
                    return products;
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return products;
    }

    @Override
    public void delete(Product product) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.DELETE_PRODUCT)){
            pstmt.setLong(1, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Product product) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_PRODUCT)){
            int i = 0;
            pstmt.setString(++i, product.getName());
            pstmt.setString(++i, product.getImg());
            pstmt.setLong(++i, product.getPrice());
            pstmt.setString(++i, product.getSex().toString());
            pstmt.setLong(++i, product.getCategory().getId());
            pstmt.setLong(++i, product.getSize().getId());
            pstmt.setLong(++i, product.getColor().getId());
            pstmt.setLong(++i, product.getId());
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findProductWithSortLimitAndFilter(String sortBy, String order, String[] filterParams, int from, int to) {
        List<Product> products = new ArrayList<>();
        ResultSet rs = null;
        StringBuilder fullStatement = new StringBuilder(SQLConstants.FIND_ALL_PRODUCTS);

        addFilterToQuery(filterParams, fullStatement);

        fullStatement
                .append(" ").append(SQLConstants.sortBy.getOrDefault(sortBy, "ORDER BY ID"))
                .append(" ").append(SQLConstants.order.getOrDefault(order, "ASC"))
                .append(" ").append(SQLConstants.LIMIT);

        if(filterParams.length > 0 && (!fullStatement.toString().contains("category_ID")
                                   || !fullStatement.toString().contains("color_ID")
                                   || !fullStatement.toString().contains("size_ID")
                                   || !fullStatement.toString().contains("sex"))){
            return products;
        }

        try(PreparedStatement pstmt = connection.prepareStatement(fullStatement.toString()
                .replaceAll(" OR \\)", ")").replaceAll(" AND  ", " "))){
            int i = 0;
            pstmt.setInt(++i, --from);
            pstmt.setInt(++i, to);
            rs = pstmt.executeQuery();
            while (rs.next()){
                products.add(productMapper.extractFromResultSet(rs));
            }
        } catch (SQLException | FieldNotPresent e) {
            logger.error(e.getMessage(), e);
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return products;
    }

    @Override
    public int findAmountOfProductsWithFilter(String[] filterParams) {
        StringBuilder statementWithFilter = new StringBuilder(SQLConstants.FIND_AMOUNT_OF_PRODUCTS);
        addFilterToQuery(filterParams, statementWithFilter);
        statementWithFilter.append(";");
        ResultSet rs = null;

        try(Statement stmt = connection.createStatement()){
            rs = stmt.executeQuery(statementWithFilter.toString()
                    .replaceAll(" OR \\)", ")").replaceAll(" AND ;", " "));
            if (rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return 0;
    }

    @Override
    public boolean updateAllProducts(Product... products) throws SQLException {
        connection.setAutoCommit(false);
        for(Product product : products){
            if(!update(product)){
                connection.rollback();
                return false;
            }
        }
        connection.commit();
        return true;
    }

    private void addFilterToQuery(String[] filterParams, StringBuilder statementWithFilter) {
        if(filterParams.length > 0){
            statementWithFilter.append(" WHERE ");

            HashMap<String, Set<String>> paramNameToValues = new HashMap<>();

            for(String s : filterParams){
                String[] paramToValues = s.split("=");
                if(paramNameToValues.containsKey(paramToValues[0])){
                    paramNameToValues.get(paramToValues[0]).add(paramToValues[1]);
                } else{
                    HashSet<String> set = new HashSet<>();
                    set.add(paramToValues[1]);
                    paramNameToValues.put(paramToValues[0], set);
                }
            }

            for(Map.Entry<String, Set<String>> entry : paramNameToValues.entrySet()){
                statementWithFilter.append("(");
                for(String s : entry.getValue()){
                    statementWithFilter.append(entry.getKey()).append("=").append(s).append(" OR ");
                }
                statementWithFilter.append(")").append(" AND ");
            }
        }
    }
}
