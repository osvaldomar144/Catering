package com.catering.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.catering.demo.model.Chef;

public interface ChefRepository extends CrudRepository<Chef, Long> {

}
