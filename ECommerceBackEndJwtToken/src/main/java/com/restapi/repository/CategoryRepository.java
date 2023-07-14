package com.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
