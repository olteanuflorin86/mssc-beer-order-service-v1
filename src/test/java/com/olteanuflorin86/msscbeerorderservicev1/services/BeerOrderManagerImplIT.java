package com.olteanuflorin86.msscbeerorderservicev1.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrder;
import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrderLine;
import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrderStatusEnum;
import com.olteanuflorin86.msscbeerorderservicev1.domain.Customer;
import com.olteanuflorin86.msscbeerorderservicev1.repositories.BeerOrderRepository;
import com.olteanuflorin86.msscbeerorderservicev1.repositories.CustomerRepository;

@SpringBootTest
public class BeerOrderManagerImplIT {

	@Autowired
	BeerOrderManager beerOrderManager;
	
	@Autowired
	BeerOrderRepository beerOrderRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	Customer testCustomer;
	
	UUID beerId = UUID.randomUUID();
	
	@BeforeEach
	void setUp() {
		testCustomer = customerRepository.save(Customer.builder()
				.customerName("Test Customer")
				.build());
	}
	
	@Test
	void testNewToAllocated() {
		BeerOrder beerOrder = createBeerOrder();
		
		BeerOrder savedBeerOrder = beerOrderManager.newBeerOrder(beerOrder);
		
		assertNotNull(savedBeerOrder);
		assertEquals(BeerOrderStatusEnum.ALLOCATED, savedBeerOrder.getOrderStatus());
	}

	public BeerOrder createBeerOrder(){
        BeerOrder beerOrder = BeerOrder.builder()
                .customer(testCustomer)
                .build();

        Set<BeerOrderLine> lines = new HashSet<>();
        lines.add(BeerOrderLine.builder()
                .beerId(beerId)
                .upc("12345")
                .orderQuantity(1)
                .beerOrder(beerOrder)
                .build());

        beerOrder.setBeerOrderLines(lines);

        return beerOrder;
    }
}






