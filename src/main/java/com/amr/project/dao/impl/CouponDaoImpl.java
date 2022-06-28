package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.CouponDao;
import com.amr.project.model.entity.Coupon;
import org.springframework.stereotype.Repository;

@Repository
public class CouponDaoImpl extends ReadWriteDaoImpl<Coupon, Long> implements CouponDao {

}
