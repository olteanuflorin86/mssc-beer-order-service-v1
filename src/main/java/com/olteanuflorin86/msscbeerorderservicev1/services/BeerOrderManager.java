package com.olteanuflorin86.msscbeerorderservicev1.services;

import java.util.UUID;

import com.olteanuflorin86.brewery.model.BeerOrderDto;
import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrder;

public interface BeerOrderManager {
	
	BeerOrder newBeerOrder(BeerOrder beerOrder);
	
	void processValidationResult(UUID beerOrderId, Boolean isValid);
	
	void beerOrderAllocationPassed(BeerOrderDto beerOrder);

    void beerOrderAllocationPendingInventory(BeerOrderDto beerOrder);

    void beerOrderAllocationFailed(BeerOrderDto beerOrder);

}
