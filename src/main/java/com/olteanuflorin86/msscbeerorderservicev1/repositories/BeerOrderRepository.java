package com.olteanuflorin86.msscbeerorderservicev1.repositories;

import java.util.List;
import java.util.UUID;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrder;
import com.olteanuflorin86.msscbeerorderservicev1.domain.Customer;
import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrderStatusEnum;

@Repository
public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {

	Page<BeerOrder> findAllByCustomer(Customer customer, Pageable pageable);
	
	List<BeerOrder> findAllByOrderStatus(BeerOrderStatusEnum orderStatusEnum);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	BeerOrder findOneById(UUID id);
}
