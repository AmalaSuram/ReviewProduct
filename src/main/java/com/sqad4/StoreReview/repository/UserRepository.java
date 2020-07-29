package com.sqad4.StoreReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqad4.StoreReview.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
