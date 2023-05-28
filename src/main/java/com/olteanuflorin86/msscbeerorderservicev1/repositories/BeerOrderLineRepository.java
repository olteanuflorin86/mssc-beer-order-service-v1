package com.olteanuflorin86.msscbeerorderservicev1.repositories;

import java.util.UUID; 

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrderLine;

@Repository
public interface BeerOrderLineRepository extends PagingAndSortingRepository<BeerOrderLine, UUID>{

}
