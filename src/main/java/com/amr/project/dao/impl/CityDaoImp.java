package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.CityDao;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.City;
import com.amr.project.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class CityDaoImp extends ReadWriteDaoImpl<City, Long> implements CityDao {
}
