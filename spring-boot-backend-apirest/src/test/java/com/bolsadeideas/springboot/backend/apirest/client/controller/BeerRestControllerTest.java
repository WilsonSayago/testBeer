package com.bolsadeideas.springboot.backend.apirest.client.controller;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bolsadeideas.springboot.backend.apirest.data.entity.BeerItem;

@RunWith(SpringRunner.class)
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BeerRestControllerTest {

	private TestRestTemplate testRestTemplate = new TestRestTemplate();
	
	private HttpHeaders httpHeaders = new HttpHeaders(); 
	
	@LocalServerPort
	private Integer port;
	
	private static final String URL = "http://localhost:";
	
	private String getUrl() {
		return URL + port;
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	private ResponseEntity<Map> responseEntity(String uri, HttpMethod method, BeerItem beer) {
        HttpEntity<BeerItem> entity = new HttpEntity<BeerItem>(beer, httpHeaders);
        ResponseEntity<Map> response = testRestTemplate.exchange(
        	getUrl() + uri,
        	method, entity, Map.class);
        return response;
    }
	
	@SuppressWarnings({ "unused" })
	private ResponseEntity<?> responseEntityBeerItem(String uri, HttpMethod method) {
		BeerItem beer = new BeerItem();
        HttpEntity<BeerItem> entity = new HttpEntity<BeerItem>(beer, httpHeaders);
        return testRestTemplate.exchange(
        	getUrl() + uri,
        	method, entity, List.class);
    }
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchBeers() {
		ResponseEntity<?> response = responseEntityBeerItem("/beers", HttpMethod.GET);	
		List<BeerItem> data = (List<BeerItem>) response.getBody();
		assertTrue("FindAll no errors",!data.isEmpty());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testSearchBeerById() {
		//findById Success
		ResponseEntity<Map> response = responseEntity("/beers/1", HttpMethod.GET, new BeerItem());
		Map<String, Object> map = response.getBody();
		assertTrue("FindAll no errors",map.get("errors")==null);
		assertTrue("FindAll no errors",map.get("beerItem")!=null);
		//findById not found
		response = responseEntity("/beers/99", HttpMethod.GET, new BeerItem());
		map = response.getBody();
		assertTrue("FindAll no errors",map.get("errors")!=null);
		assertTrue("FindAll no errors",map.get("beerItem")==null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testAddBeers() {
		//BeerItem fail
		BeerItem beerItem = new BeerItem();
		ResponseEntity<Map> response = responseEntity("/beers", HttpMethod.POST, beerItem);
		Map<String, Object> map = response.getBody();
		assertTrue("FindAll no errors",map.get("errors")!=null);
		assertTrue("FindAll no errors",map.get("beerItem")==null);

		//BeerItem success
		beerItem.setName("ICE");
		beerItem.setCountry("Venezuela");
		beerItem.setBrewery("Polar");
		beerItem.setCurrency("BS");
		beerItem.setPrice(new BigDecimal(1000));
		response = responseEntity("/beers", HttpMethod.POST, beerItem);
		map = response.getBody();
		assertTrue("Save with errors",map.get("errors")==null);
		assertTrue("Save with no errors",map.get("beerItem")!=null);
	}
}
