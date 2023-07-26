package com.olteanuflorin86.msscbeerorderservicev1.services.testcomponents;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.olteanuflorin86.brewery.model.events.ValidateOrderRequest;
import com.olteanuflorin86.brewery.model.events.ValidateOrderResult;
import com.olteanuflorin86.msscbeerorderservicev1.config.JmsConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {

	private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void list(Message msg){
    	boolean isValid = true;
    	boolean sendResponse = true;

        ValidateOrderRequest request = (ValidateOrderRequest) msg.getPayload();

        System.out.println("########### I RAN ########");
       //condition to fail validation
//        if (request.getBeerOrder().getCustomerRef() != null && 
//        	request.getBeerOrder().getCustomerRef().equals("fail-validation")){
//            	isValid = false;
//        }
        if (request.getBeerOrder().getCustomerRef() != null) {
            if (request.getBeerOrder().getCustomerRef().equals("fail-validation")){
                isValid = false;
            } else if (request.getBeerOrder().getCustomerRef().equals("dont-validate")){
                sendResponse = false;
            }
        }

//        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
//                ValidateOrderResult.builder()
////                .isValid(true)
//                .isValid(isValid)
//                .orderId(request.getBeerOrder().getId())
//                .build());
        if (sendResponse) {
            jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                    ValidateOrderResult.builder()
                            .isValid(isValid)
                            .orderId(request.getBeerOrder().getId())
                            .build());
        }
    }
}
