package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.User;

public interface UserDao extends ReadWriteDao<User, Long>{
    boolean activateUser(String secret);
    boolean addUser(User user);
    User findByUserName(String username);
    boolean existByUserName(String userName);
}