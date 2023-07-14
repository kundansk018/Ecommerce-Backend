package com.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.entity.Role;


public interface RoleRepo  extends JpaRepository<Role, Integer> {

}
