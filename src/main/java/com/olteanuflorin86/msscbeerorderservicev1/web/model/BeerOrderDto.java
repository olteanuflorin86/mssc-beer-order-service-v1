package com.olteanuflorin86.msscbeerorderservicev1.web.model;

import java.time.OffsetDateTime;  
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerOrderDto extends BaseItem {

	public BeerOrderDto() {
		
	}
	
	@Builder
	public BeerOrderDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
			UUID customerId, String customerRef, List<BeerOrderLineDto> beerOrderLines, String orderStatus, String orderStatusCallbackUrl) {
		super(id, version, createdDate, lastModifiedDate);
		
		this.customerId = customerId;
		this.customerRef = customerRef;
		this.beerOrderLines = beerOrderLines;
		this.orderStatus = orderStatus;
		this.orderStatusCallbackUrl = orderStatusCallbackUrl;
	}
	
	private UUID customerId;
	private String customerRef;
	private List<BeerOrderLineDto> beerOrderLines;
	private String orderStatus;
	private String orderStatusCallbackUrl;
}
