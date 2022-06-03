package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ReadWriteServiceImpl<User,Long> {
    @Autowired
    public UserService(ReadWriteDao<User, Long> dao) {
        super(dao);
    }
}
