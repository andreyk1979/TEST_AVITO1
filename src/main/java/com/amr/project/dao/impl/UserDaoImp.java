package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.exception.Util;
import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Roles;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserDaoImp extends ReadWriteDaoImpl<User,Long> implements UserDao {

    @Override
    public boolean activateUser(String secret) {
        TypedQuery<User> query = em.createQuery("from User where secret = :secret", User.class);
        query.setParameter("secret", secret);
        User user = Util.getSingleResult(query);
        if (user == null) {
            return false;
        }
        user.setSecret(null);
        user.setActivate(true);
        em.merge(user);
        return true;
    }

    @Override
    public boolean addUser(User user) {
        TypedQuery<User> query = em.createQuery("from User where email = :email", User.class);
        query.setParameter("email", user.getEmail());
        if(Util.getSingleResult(query) != null) {
            return false;
        }
        user.setRole(Roles.USER);
        em.persist(user);
        return true;
    }

    @Override
    public User findByUserName(String username) {
       return Util.getSingleResult(em.createQuery("select u from User u where u.username=:username",User.class)
                .setParameter("username",username));
    }

    @Override
    public boolean existByUserName(String userName) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = : e", User.class);
        query.setParameter("e", userName);
        if (Util.getSingleResult(query) == null) {
            return false;
        }
        return true;
    }
}
