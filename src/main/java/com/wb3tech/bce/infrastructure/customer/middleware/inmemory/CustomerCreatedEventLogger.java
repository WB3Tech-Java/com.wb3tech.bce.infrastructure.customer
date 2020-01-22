package com.wb3tech.bce.infrastructure.customer.middleware.inmemory;

import com.wb3tech.bce.domain.customer.create.CustomerCreatedEvent;
import com.wb3tech.bce.domain.customer.create.CustomerCreatedEventDispatcher;

import java.util.logging.Logger;

public class CustomerCreatedEventLogger implements CustomerCreatedEventDispatcher {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void Dispatch(CustomerCreatedEvent customerCreatedEvent) {
        logger.info(String.format("Customer Created Event: %s", customerCreatedEvent.toString()));
    }

}
