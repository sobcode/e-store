package com.finalVariant.OnlineStore.model.dao;

import com.finalVariant.OnlineStore.model.entity.Order;
import com.finalVariant.OnlineStore.model.entity.Product;
import com.finalVariant.OnlineStore.model.entity.User;
import com.finalVariant.OnlineStore.model.entity.emums.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends GenericDao<Order> {
    List<Order> findOrdersForUserByOrderStatus(User user, OrderStatus status);

    Optional<Order> findOrderForUserByOrderStatusAndProduct(User user, Product product, OrderStatus unregistered);

    List<Order> findAllOrdersForUser(User user);
}
