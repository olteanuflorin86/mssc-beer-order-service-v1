package com.olteanuflorin86.msscbeerorderservicev1.sm;

import java.util.Optional;
import java.util.UUID;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrder;
import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrderEventEnum;
import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrderStatusEnum;
import com.olteanuflorin86.msscbeerorderservicev1.repositories.BeerOrderRepository;
import com.olteanuflorin86.msscbeerorderservicev1.services.BeerOrderManagerImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerOrderStateChangeInterceptor extends StateMachineInterceptorAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;

    @Transactional
    @Override
    public void preStateChange(State<BeerOrderStatusEnum, BeerOrderEventEnum> state, Message<BeerOrderEventEnum> message, Transition<BeerOrderStatusEnum, BeerOrderEventEnum> transition, StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachine) {
    	log.info("Pre-State Change");
    	
    	Optional.ofNullable(message)
                .flatMap(msg -> Optional.ofNullable((String) msg.getHeaders().getOrDefault(BeerOrderManagerImpl.ORDER_ID_HEADER, " ")))
                .ifPresent(orderId -> {
                    log.info("Saving state for order id: " + orderId + " Status: " + state.getId());

                    Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(orderId));
                    
                    beerOrderOptional.ifPresentOrElse(beerOrder -> {
                    	beerOrder.setOrderStatus(state.getId());
                    	beerOrderRepository.saveAndFlush(beerOrder);                    	
                    }, () -> log.error("Beer Order Not Found!"));                    
                });
    }
}
