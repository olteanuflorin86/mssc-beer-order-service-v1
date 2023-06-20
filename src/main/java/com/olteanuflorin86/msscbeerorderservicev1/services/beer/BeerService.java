package com.olteanuflorin86.msscbeerorderservicev1.services.beer;

import java.util.Optional;
import java.util.UUID;

import com.olteanuflorin86.brewery.model.BeerDto;

public interface BeerService {
	
	Optional<BeerDto> getBeerById(UUID uuid);

	Optional<BeerDto> getBeerByUpc(String upc);

}
