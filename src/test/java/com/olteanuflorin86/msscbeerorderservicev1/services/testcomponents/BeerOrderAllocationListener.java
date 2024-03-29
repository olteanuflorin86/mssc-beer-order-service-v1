package com.olteanuflorin86.msscbeerorderservicev1.services.testcomponents;

import org.springframework.jms.annotation.JmsListener; 
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import com.olteanuflorin86.brewery.model.events.AllocateOrderRequest;
import com.olteanuflorin86.brewery.model.events.AllocateOrderResult;
import com.olteanuflorin86.msscbeerorderservicev1.config.JmsConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderAllocationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(Message msg){
        AllocateOrderRequest request = (AllocateOrderRequest) msg.getPayload();
        boolean pendingInventory = false;
        boolean allocationError = false;
        
        //set pending inventory
//        if (request.getBeerOrderDto().getCustomerRef() != null && request.getBeerOrderDto().getCustomerRef().equals("partial-allocation")){
//            pendingInventory = true;
//        }
        boolean sendResponse = true;
        

        //set allocation error
//        if (request.getBeerOrderDto().getCustomerRef() != null && request.getBeerOrderDto().getCustomerRef().equals("fail-allocation")){
//            allocationError = true;
//        }
        
        if (request.getBeerOrderDto().getCustomerRef() != null) {
        	if (request.getBeerOrderDto().getCustomerRef().equals("fail-allocation")){
                allocationError = true;
            } else if (request.getBeerOrderDto().getCustomerRef().equals("partial-allocation")) {
                pendingInventory = true;
            } else if (request.getBeerOrderDto().getCustomerRef().equals("dont-allocate")){
                sendResponse = false;
            }
        }

        boolean finalPendingInventory = pendingInventory;

        request.getBeerOrderDto().getBeerOrderLines().forEach(beerOrderLineDto -> {
        	if (finalPendingInventory) {
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity() - 1);
            } else {
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
            }
        });

//        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE,
//                AllocateOrderResult.builder()
//                .beerOrderDto(request.getBeerOrderDto())
//                .pendingInventory(pendingInventory)
//                .allocationError(allocationError)
//                .build());
        
        if (sendResponse) {
            jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE,
                    AllocateOrderResult.builder()
                            .beerOrderDto(request.getBeerOrderDto())
                            .pendingInventory(pendingInventory)
                            .allocationError(allocationError)
                            .build());
        }
    }
}