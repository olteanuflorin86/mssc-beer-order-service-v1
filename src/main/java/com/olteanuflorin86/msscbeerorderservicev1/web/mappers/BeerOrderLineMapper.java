package com.olteanuflorin86.msscbeerorderservicev1.web.mappers;

import org.mapstruct.DecoratedWith; 
import org.mapstruct.Mapper;

import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrderLine;
import com.olteanuflorin86.msscbeerorderservicev1.web.model.BeerOrderLineDto;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerOrderLineMapperDecorator.class)
public interface BeerOrderLineMapper {

	BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);	
	
	BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto);
}
