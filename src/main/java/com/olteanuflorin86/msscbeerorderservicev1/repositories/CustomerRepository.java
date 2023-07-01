package com.olteanuflorin86.msscbeerorderservicev1.repositories;

import java.util.List; 
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.olteanuflorin86.msscbeerorderservicev1.domain.Customer;

@Repository
//public interface CustomerRepository extends PagingAndSortingRepository<Customer, UUID>{
public interface CustomerRepository extends JpaRepository<Customer, UUID>{
	
	List<Customer> findAllByCustomerNameLike(String customerName);
}
