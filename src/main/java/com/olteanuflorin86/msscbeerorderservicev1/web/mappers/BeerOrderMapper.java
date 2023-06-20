package com.olteanuflorin86.msscbeerorderservicev1.web.mappers;

import org.mapstruct.Mapper; 
import org.mapstruct.Mapping;

import com.olteanuflorin86.brewery.model.BeerOrderDto;
import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrder;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

	@Mapping(target = "customerId", source = "customer.id")
	BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto dto);
}
