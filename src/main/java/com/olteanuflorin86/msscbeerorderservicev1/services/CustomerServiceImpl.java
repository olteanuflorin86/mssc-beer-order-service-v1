package com.olteanuflorin86.msscbeerorderservicev1.services;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.olteanuflorin86.brewery.model.CustomerPagedList;
import com.olteanuflorin86.msscbeerorderservicev1.domain.Customer;
import com.olteanuflorin86.msscbeerorderservicev1.repositories.CustomerRepository;
import com.olteanuflorin86.msscbeerorderservicev1.web.mappers.CustomerMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
	
	@Override
	public CustomerPagedList listCustomers(Pageable pageable) {

		Page<Customer> customerPage = customerRepository.findAll(pageable);

        return new CustomerPagedList(customerPage
                        .stream()
                        .map(customerMapper::customerToDto)
                        .collect(Collectors.toList()),
                    PageRequest.of(customerPage.getPageable().getPageNumber(),
                        customerPage.getPageable().getPageSize()),
                        customerPage.getTotalElements());
		
	}

}
