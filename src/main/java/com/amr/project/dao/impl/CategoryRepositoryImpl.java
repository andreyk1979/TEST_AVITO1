package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.CategoryRepository;
import com.amr.project.model.entity.Category;

import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl extends ReadWriteDaoImpl<Category, Long> implements CategoryRepository<Category, Long> {

}
