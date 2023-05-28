package com.olteanuflorin86.msscbeerorderservicev1.web.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerDto extends BaseItem {

	private String name;
	
	@Builder
	public CustomerDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, String name) {
		super(id, version, createdDate, lastModifiedDate);
		this.name = name;		
	}
}
