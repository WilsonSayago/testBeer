package com.bolsadeideas.springboot.backend.apirest.bussiness.service.currency;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bolsadeideas.springboot.backend.apirest.data.entity.BeerBox;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CurrencylayerService {

	@Value("${currency.key}")
	private String key;
	//private static final String KEY = "d18abcc25bb6d4008756893237d9eb80";
	
	@Value("${currency.url}")
	private String url;
	//private static final String URL = "http://www.apilayer.net/api/live?currencies={ocurrency}&access_key={key}";

	@SuppressWarnings("unchecked")
	public BigDecimal getBeerBox(String ocurrency) {
		RestTemplate restTemplate = new RestTemplate();
		String url_base = url.replace("{ocurrency}", ocurrency);
		String data = restTemplate.getForObject(url_base, String.class);
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, Map<String, ?>> response = (Map<String, Map<String, ?>>) mapper.readValue(data, Object.class);
			Map<String, ?> quotes = response.get("quotes");
			return BigDecimal.valueOf(Double.parseDouble(quotes.get(quotes.keySet().toArray()[0]).toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
