package com.finalVariant.OnlineStore.model.dao.impl;

import com.finalVariant.OnlineStore.controller.constants.SQLConstants;
import com.finalVariant.OnlineStore.model.dao.OrderDao;
import com.finalVariant.OnlineStore.model.dao.exception.FieldNotPresent;
import com.finalVariant.OnlineStore.model.dao.mapper.OrderMapper;
import com.finalVariant.OnlineStore.model.entity.Order;
import com.finalVariant.OnlineStore.model.entity.Product;
import com.finalVariant.OnlineStore.model.entity.User;
import com.finalVariant.OnlineStore.model.entity.emums.OrderStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCOrderDao implements OrderDao {
    private final Logger logger = LogManager.getLogger(JDBCOrderDao.class);
    private final Connection connection;
    private final OrderMapper orderMapper = new OrderMapper();

    public JDBCOrderDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean create(Order order) {
        boolean result = false;
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.ADD_ORDER_FOR_USER,
                                        PreparedStatement.RETURN_GENERATED_KEYS)){
            if(findOrderForUserByOrderStatusAndProduct(order.getUser(), order.getProduct(), order.getStatus()).isPresent()){
                return false;
            }

            int i = 0;
            pstmt.setLong(++i, order.getUser().getId());
            pstmt.setLong(++i, order.getProduct().getId());
            pstmt.setString(++i, order.getStatus().toString());
            pstmt.setInt(++i, order.getQuantity());
            if(pstmt.executeUpdate() > 0){
                try(ResultSet rs = pstmt.getGeneratedKeys()){
                    if(rs.next()){
                        order.setId(rs.getLong(1));
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
    public Optional<Order> findById(long id) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_ORDER_BY_ID)){
            pstmt.setLong(1, id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return Optional.of(orderMapper.extractFromResultSet(rs));
                }
            }
        } catch (SQLException | FieldNotPresent e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try(
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.FIND_ALL_ORDERS)
        ){
            while (rs.next()){
                orders.add(orderMapper.extractFromResultSet(rs));
            }
        } catch (SQLException | FieldNotPresent e) {
            logger.error(e.getMessage(), e);
        }
        return orders;
    }

    @Override
    public void delete(Order order) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.DELETE_ORDER)) {
            int i = 0;
            pstmt.setLong(++i, order.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(Order order) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_ORDER)) {
            int i = 0;
            pstmt.setInt(++i, order.getQuantity());
            pstmt.setLong(++i, order.getUser().getId());
            pstmt.setLong(++i, order.getProduct().getId());
            pstmt.setString(++i, order.getStatus().toString());
            pstmt.setLong(++i, order.getId());
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

    @Override
    public List<Order> findOrdersForUserByOrderStatus(User user, OrderStatus status) {
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_ORDERS_FOR_USER_ORDER_STATUS)){
            int i = 0;
            pstmt.setLong(++i, user.getId());
            pstmt.setString(++i, status.toString());
            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()){
                    orders.add(orderMapper.extractFromResultSet(rs));
                }
            }
        } catch (SQLException | FieldNotPresent e) {
            logger.error(e.getMessage(), e);
        }
        return orders;
    }

    @Override
    public Optional<Order> findOrderForUserByOrderStatusAndProduct(User user, Product product, OrderStatus status) {
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_ORDER_FOR_USER_ORDER_STATUS_PRODUCT)){
            int i = 0;
            pstmt.setLong(++i, user.getId());
            pstmt.setLong(++i, product.getId());
            pstmt.setString(++i, status.toString());
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return Optional.of(orderMapper.extractFromResultSet(rs));
                }
            }
        } catch (SQLException | FieldNotPresent e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findAllOrdersForUser(User user) {
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_ALL_ORDERS_FOR_USER)){
            pstmt.setLong(1, user.getId());
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    orders.add(orderMapper.extractFromResultSet(rs));
                }
            }
        } catch (SQLException | FieldNotPresent e) {
            logger.error(e.getMessage(), e);
        }
        return orders;
    }
}
