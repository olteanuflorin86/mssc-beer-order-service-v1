package com.olteanuflorin86.msscbeerorderservicev1.services;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.olteanuflorin86.brewery.model.BeerOrderDto;
import com.olteanuflorin86.brewery.model.BeerOrderPagedList;

public interface BeerOrderService {

	BeerOrderPagedList listOrders(UUID customerId, Pageable pageable);
	
	BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto);
	
	BeerOrderDto getOrderById(UUID customerId, UUID orderId);
	
	void pickupOrder(UUID customerId, UUID orderId);
}
