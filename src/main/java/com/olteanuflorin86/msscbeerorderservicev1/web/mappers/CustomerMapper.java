package com.olteanuflorin86.msscbeerorderservicev1.web.mappers;

import org.mapstruct.Mapper;

import com.olteanuflorin86.brewery.model.CustomerDto;
import com.olteanuflorin86.msscbeerorderservicev1.domain.Customer;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {
	
	CustomerDto customerToDto(Customer customer);

    Customer dtoToCustomer(Customer dto);

}
