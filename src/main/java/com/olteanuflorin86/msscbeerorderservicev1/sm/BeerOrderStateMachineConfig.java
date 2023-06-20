package com.olteanuflorin86.msscbeerorderservicev1.sm;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrderEventEnum;
import com.olteanuflorin86.msscbeerorderservicev1.domain.BeerOrderStatusEnum;

@Configuration
public class BeerOrderStateMachineConfig extends StateMachineConfigurerAdapter<BeerOrderStatusEnum, BeerOrderEventEnum>{

	@Override
	public void configure(StateMachineStateConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> states)
			throws Exception {
		states.withStates()
			.initial(BeerOrderStatusEnum.NEW)
			.states(EnumSet.allOf(BeerOrderStatusEnum.class))
			.end(BeerOrderStatusEnum.PICKED_UP)
            .end(BeerOrderStatusEnum.DELIVERED)
            .end(BeerOrderStatusEnum.DELIVERY_EXCEPTION)
            .end(BeerOrderStatusEnum.VALIDATION_EXCEPTION)
            .end(BeerOrderStatusEnum.ALLOCATION_EXCEPTION);		
	}
	
	@Override
    public void configure(StateMachineTransitionConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> transitions) throws Exception {
        transitions.withExternal()
                .source(BeerOrderStatusEnum.NEW).target(BeerOrderStatusEnum.NEW)
                .event(BeerOrderEventEnum.VALIDATE_ORDER)
                //todo add validation action here
           .and().withExternal()
                .source(BeerOrderStatusEnum.NEW).target(BeerOrderStatusEnum.VALIDATED)
                .event(BeerOrderEventEnum.VALIDATION_PASSED)
           .and().withExternal()
                .source(BeerOrderStatusEnum.NEW).target(BeerOrderStatusEnum.VALIDATION_EXCEPTION)
                .event(BeerOrderEventEnum.VALIDATION_FAILED);
    }

}
