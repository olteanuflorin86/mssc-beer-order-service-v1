package com.olteanuflorin86.msscbeerorderservicev1.services;

import org.springframework.data.domain.Pageable;

import com.olteanuflorin86.brewery.model.CustomerPagedList;

public interface CustomerService {

	CustomerPagedList listCustomers(Pageable pageable);
}
