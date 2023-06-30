package com.olteanuflorin86.msscbeerorderservicev1.bootstrap;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.olteanuflorin86.msscbeerorderservicev1.domain.Customer;
import com.olteanuflorin86.msscbeerorderservicev1.repositories.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderBootStrap implements CommandLineRunner {
	public static final String TASTING_ROOM = "Tasting Room";
	public static final String BEER_1_UPC = "0631234200036";
	public static final String BEER_2_UPC = "0631234300019";
	public static final String BEER_3_UPC = "0083783375213";
	
	private final CustomerRepository customerRepository;

	@Override
	public void run(String... args) throws Exception {
		loadCustomerData();
	} 
	
	private void loadCustomerData() {
		if (customerRepository.findAllByCustomerNameLike(BeerOrderBootStrap.TASTING_ROOM).size() == 0) {
            Customer savedCustomer = customerRepository.saveAndFlush(Customer.builder()
				.customerName(TASTING_ROOM)
				.apiKey(UUID.randomUUID())
				.build());
		
		log.info("Tasting Room Customer Id " + savedCustomer.getId().toString());
//		System.out.println("Tasting Room Customer Id " + savedCustomer.getId().toString());

		}
	}
}
