package com.bolsadeideas.springboot.backend.apirest.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "beer_item")
public class BeerItem extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	@NotNull(message = "can´t be empty")
	private String name;
	
	@NotNull(message = "can´t be empty")
	private String brewery;
	
	@NotNull(message = "can´t be empty")
	private String country;
	
	@NotNull(message = "can´t be empty")
	private BigDecimal price;
	
	@NotNull(message = "can´t be empty")
	private String currency;

	@Transient
	private BeerBox beerBox;

	public BeerItem() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrewery() {
		return brewery;
	}

	public void setBrewery(String brewery) {
		this.brewery = brewery;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BeerBox getBeerBox() {
		return beerBox;
	}

	public void setBeerBox(BeerBox beerBox) {
		this.beerBox = beerBox;
	}

}
