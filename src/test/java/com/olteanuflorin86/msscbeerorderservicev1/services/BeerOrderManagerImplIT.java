package com.olteanuflorin86.msscbeerorderservicev1.services;

import static org.junit.Assert.assertEquals;  
import static org.junit.Assert.assertNotNull;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import static org.awaitility.Awaitility.await;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.olteanuflorin86.brewery.model.BeerDto;
import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrder;
import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrderLine;
import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrderStatusEnum;
import com.olteanuflorin86.msscbeerorderservicev1.domain.Customer;
import com.olteanuflorin86.msscbeerorderservicev1.repositories.BeerOrderRepository;
import com.olteanuflorin86.msscbeerorderservicev1.repositories.CustomerRepository;
import com.olteanuflorin86.msscbeerorderservicev1.services.beer.BeerServiceImpl;

@ExtendWith(WireMockExtension.class)
@SpringBootTest
public class BeerOrderManagerImplIT {

	@Autowired
	BeerOrderManager beerOrderManager;
	
	@Autowired
	BeerOrderRepository beerOrderRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	WireMockServer wireMockServer;
	
	@Autowired
	ObjectMapper objectMapper;
	
	Customer testCustomer;
	
	UUID beerId = UUID.randomUUID();
	
	@TestConfiguration
	static class RestTemplateBuilderProvider {
		
		@Bean(destroyMethod = "stop")
		public WireMockServer wireMockServer() {
			WireMockServer server = with(wireMockConfig().port(8083));
			server.start();
			return server;
		}
	}
	
	@BeforeEach
	void setUp() {
		testCustomer = customerRepository.save(Customer.builder()
				.customerName("Test Customer")
				.build());
	}
	
	@Test
	void testNewToAllocated() throws JsonProcessingException {
		BeerDto beerDto = BeerDto.builder().id(beerId).upc("12345").build();
		
		wireMockServer.stubFor(get(BeerServiceImpl.BEER_UPC_PATH_V1 + "12345")
		.willReturn(okJson(objectMapper.writeValueAsString(beerDto))));
		
		BeerOrder beerOrder = createBeerOrder();
		
		BeerOrder savedBeerOrder = beerOrderManager.newBeerOrder(beerOrder);
		
		await().untilAsserted(() -> {
            BeerOrder foundOrder = beerOrderRepository.findById(beerOrder.getId()).get();

            //todo - ALLOCATED STATUS
//            assertEquals(BeerOrderStatusEnum.ALLOCATION_PENDING, foundOrder.getOrderStatus());
            assertEquals(BeerOrderStatusEnum.ALLOCATED, foundOrder.getOrderStatus());
        });
		
		await().untilAsserted(() -> {
            BeerOrder foundOrder = beerOrderRepository.findById(beerOrder.getId()).get();
            BeerOrderLine line = foundOrder.getBeerOrderLines().iterator().next();
            assertEquals(line.getOrderQuantity(), line.getQuantityAllocated());
        });

        BeerOrder savedBeerOrder2 = beerOrderRepository.findById(savedBeerOrder.getId()).get();

        assertNotNull(savedBeerOrder2);
        assertEquals(BeerOrderStatusEnum.ALLOCATED, savedBeerOrder2.getOrderStatus());
        savedBeerOrder2.getBeerOrderLines().forEach(line -> {
            assertEquals(line.getOrderQuantity(), line.getQuantityAllocated());
        });
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






