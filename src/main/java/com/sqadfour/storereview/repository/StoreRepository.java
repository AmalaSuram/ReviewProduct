package com.sqadfour.storereview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqadfour.storereview.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer>{

	Store findByStoreId(Integer storeId);

}
