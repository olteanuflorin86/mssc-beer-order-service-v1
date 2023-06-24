package com.olteanuflorin86.msscbeerorderservicev1.sm.actions;

import java.util.Optional;
import java.util.UUID;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.olteanuflorin86.brewery.model.events.ValidateOrderRequest;
import com.olteanuflorin86.msscbeerorderservicev1.config.JmsConfig;
import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrder;
import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrderEventEnum;
import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrderStatusEnum;
import com.olteanuflorin86.msscbeerorderservicev1.repositories.BeerOrderRepository;
import com.olteanuflorin86.msscbeerorderservicev1.services.BeerOrderManagerImpl;
import com.olteanuflorin86.msscbeerorderservicev1.web.mappers.BeerOrderMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        
        Optional<BeerOrder> beerOrderOpional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

        beerOrderOpional.ifPresentOrElse(beerOrder -> {
        	jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE, ValidateOrderRequest.builder()
        			.beerOrder(beerOrderMapper.beerOrderToDto(beerOrder))
        			.build());
        	
        }, () -> log.error("Order Not Found. Id: " + beerOrderId));
    
    log.debug("Sent Validation request to queue for order id " + beerOrderId);
	}
}