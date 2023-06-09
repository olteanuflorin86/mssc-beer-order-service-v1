package com.olteanuflorin86.msscbeerorderservicev1.services.beer;

import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.olteanuflorin86.brewery.model.BeerDto;

@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
@Service
public class BeerServiceImpl implements BeerService {
	public final static String BEER_PATH_V1 = "/api/v1/beer/";
	public final static String BEER_UPC_PATH_V1 = "/api/v1/beerUpc/";
	private final RestTemplate restTemplate;
	
	private String beerServiceHost;
	
	public BeerServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public void setBeerServiceHost(String beerServiceHost) {
		this.beerServiceHost = beerServiceHost;
	}

	@Override
	public Optional<BeerDto> getBeerById(UUID uuid) {
		return Optional.of(restTemplate.getForObject(beerServiceHost + BEER_PATH_V1 + uuid.toString(), BeerDto.class));
	}

	@Override
	public Optional<BeerDto> getBeerByUpc(String upc) {
		return Optional.of(restTemplate.getForObject(beerServiceHost + BEER_UPC_PATH_V1 + upc, BeerDto.class));
	}

}
