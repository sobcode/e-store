package com.finalVariant.OnlineStore.model.dao;

import com.finalVariant.OnlineStore.model.entity.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User>{
    Optional<User> findByLogin(String login);
}
