package com.catering.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.catering.demo.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
