package com.bolsadeideas.springboot.backend.apirest.data.repository;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.backend.apirest.data.entity.BeerItem;

public interface BeerItemRepository extends CrudRepository<BeerItem, Long> {

	public BeerItem findByName(String name);
}
