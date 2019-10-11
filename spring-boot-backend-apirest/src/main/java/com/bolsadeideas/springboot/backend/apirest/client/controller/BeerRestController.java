package com.bolsadeideas.springboot.backend.apirest.client.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.backend.apirest.bussines.service.BeerItemService;
import com.bolsadeideas.springboot.backend.apirest.bussiness.service.currency.CurrencylayerService;
import com.bolsadeideas.springboot.backend.apirest.data.entity.BeerBox;
import com.bolsadeideas.springboot.backend.apirest.data.entity.BeerItem;

@CrossOrigin
@RestController
@RequestMapping("/beers")
public class BeerRestController {

	@Autowired
	private BeerItemService beerService;
	
	@Autowired
	private CurrencylayerService currencyService;

	@GetMapping
	public ResponseEntity<?> searchBeers() {
		return new ResponseEntity<List<BeerItem>>(beerService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/{beerID}")
	public ResponseEntity<?> searchBeerById(@PathVariable Long beerID) {
		BeerItem beerItem = null;
		Map<String, Object> response = new HashMap<>();
		List<String> errors = new ArrayList<>();
		try {
			beerItem = beerService.findById(beerID);
		} catch(DataAccessException e) {
			response.put("message", "Error al realizar la busqueda en la base de datos");
			response.put("errors", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(beerItem == null) {
			response.put("message", "El Id de la cerveza no existe");
			errors.add("El Id de la cerveza no existe");
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		response.put("message", "Operacion exitosa");
		response.put("beerItem", beerItem);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> addBeers(@Valid @RequestBody BeerItem beerItem, BindingResult result) {
		BeerItem beerItemNew = null;
		Map<String, Object> response = new HashMap<>();
		List<String> errors = new ArrayList<>();
		if(result.hasErrors()) {
			errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			response.put("message", "Request invalida");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if(beerService.findByName(beerItem.getName()) != null) {
			errors.add("La cerveza ya existe");
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
		}
		try {
			beerItemNew = beerService.save(beerItem);
		} catch(DataAccessException e) {
			response.put("message", "Error al realizar el insert en la base de datos");
			response.put("errors", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "Beer createds");
		response.put("beerItem", beerItemNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/{beerID}/boxprice")
	public ResponseEntity<?> boxBeerPriceById(@PathVariable Long beerID, @RequestParam(name = "currency") String currency, 
			@RequestParam(name = "quantity", defaultValue = "6") Integer quantity) {
		BeerItem beerItem = null;
		Map<String, Object> response = new HashMap<>();
		List<String> errors = new ArrayList<>();
		try {
			beerItem = beerService.findById(beerID);
		} catch(DataAccessException e) {
			response.put("message", "Error al realizar la busqueda en la base de datos");
			response.put("errors", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(beerItem == null) {
			response.put("message", "El Id de la cerveza no existe");
			errors.add("El Id de la cerveza no existe");
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		BigDecimal cambio = currencyService.getBeerBox(currency);
		response.put("message", "Operacion exitosa");
		BeerBox beerBox = new BeerBox();
		beerBox.setTotal(cambio, quantity, beerItem.getPrice());
		response.put("beerBox", beerBox);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
}
