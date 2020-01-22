package com.wb3tech.bce.infrastructure.customer.middleware.inmemory;

import com.wb3tech.bce.domain.customer.update.CustomerUpdatedEvent;
import com.wb3tech.bce.domain.customer.update.CustomerUpdatedEventDispatcher;

import java.util.logging.Logger;

public class CustomerUpdatedEventLogger implements CustomerUpdatedEventDispatcher {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void Dispatch(CustomerUpdatedEvent customerUpdatedEvent) {
        logger.info(String.format("Customer Updated Event: %s", customerUpdatedEvent.toString()));
    }

}
