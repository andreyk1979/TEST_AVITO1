package com.amr.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amr.project.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
