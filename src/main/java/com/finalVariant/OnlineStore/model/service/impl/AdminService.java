package com.finalVariant.OnlineStore.model.service.impl;

import com.finalVariant.OnlineStore.model.entity.*;
import com.finalVariant.OnlineStore.model.service.Service;

import java.util.List;
import java.util.Optional;

public class AdminService implements Service {
    private static AdminService adminService;

    public static AdminService getInstance(){
        if(adminService == null){
            synchronized (UserService.class){
                adminService = new AdminService();
            }
        }
        return adminService;
    }

    private AdminService(){}

    public List<User> getAllUsers(){
        return userDao.findAll();
    }

    public List<Order> getAllOrdersForUser(User user){
        return orderDao.findAllOrdersForUser(user);
    }

    public List<Order> getAllOrders(){
        return orderDao.findAll();
    }

    public Optional<Order> getOrderById(long orderId){
        return orderDao.findById(orderId);
    }

    public void updateOrder(Order order){
        orderDao.update(order);
    }

    public void updateProduct(Product product){
        productDao.update(product);
    }

    public Optional<User> getUserById(long userId){
        return userDao.findById(userId);
    }

    public void updateUser(User user){
        userDao.update(user);
    }

    public List<Product> getProductWithSortAndLimit(String sortBy, String method, String[] filterParams, int from, int to){
        return productDao.findProductWithSortLimitAndFilter(sortBy, method, filterParams, from, to);
    }

    public int getAmountOfProducts(String[] filterParams){
        return productDao.findAmountOfProductsWithFilter(filterParams);
    }
}
