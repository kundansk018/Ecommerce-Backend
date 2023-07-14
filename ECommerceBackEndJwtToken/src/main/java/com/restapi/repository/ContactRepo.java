package com.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.entity.Contact;

public interface ContactRepo extends JpaRepository<Contact , Integer> {

}
