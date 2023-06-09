package com.olteanuflorin86.brewery.model;

import java.time.OffsetDateTime;   
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
//import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerOrderDto {

	@JsonProperty("id")
    private UUID id = null;

    @JsonProperty("version")
    private Integer version = null;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    @JsonProperty("createdDate")
    private OffsetDateTime createdDate = null;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    @JsonProperty("lastModifiedDate")
    private OffsetDateTime lastModifiedDate = null;
	
	private UUID customerId;
	private String customerRef;
	private List<BeerOrderLineDto> beerOrderLines;
	private String orderStatus;
	private String orderStatusCallbackUrl;
}
