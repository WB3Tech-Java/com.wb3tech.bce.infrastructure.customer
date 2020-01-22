package com.wb3tech.bce.infrastructure.customer.middleware.inmemory;

import com.wb3tech.bce.domain.customer.remove.CustomerRemovedEvent;
import com.wb3tech.bce.domain.customer.remove.CustomerRemovedEventDispatcher;

import java.util.logging.Logger;

public class CustomerRemovedEventLogger implements CustomerRemovedEventDispatcher {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void Dispatch(CustomerRemovedEvent customerRemovedEvent) {
        logger.info(String.format("Customer Removed Event: %s", customerRemovedEvent.toString()));
    }
}
