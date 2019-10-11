package com.bolsadeideas.springboot.backend.apirest.data.entity;

import java.math.BigDecimal;

public class BeerBox {
	private BigDecimal total;

	public BeerBox() {
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal cambio, Integer quantity, BigDecimal price) {
		this.total = cambio.multiply(price.multiply(BigDecimal.valueOf(quantity.doubleValue())));
	}
}
