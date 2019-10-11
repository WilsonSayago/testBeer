package com.bolsadeideas.springboot.backend.apirest.bussines.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.backend.apirest.data.entity.BeerItem;
import com.bolsadeideas.springboot.backend.apirest.data.repository.BeerItemRepository;

@Service
public class BeerItemService {

	@Autowired
	private BeerItemRepository beerItemDao;
	
	@Transactional(readOnly = true)
	public List<BeerItem> findAll(){
		return  (List<BeerItem>) beerItemDao.findAll();
	}
	
	@Transactional
	public BeerItem save(BeerItem beer) {
		return beerItemDao.save(beer);
	}
	
	@Transactional
	public void delete(Long id) {
		beerItemDao.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public BeerItem findById(Long id) {
		return beerItemDao.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public BeerItem findByName(String name) {
		return beerItemDao.findByName(name);
	}
}
