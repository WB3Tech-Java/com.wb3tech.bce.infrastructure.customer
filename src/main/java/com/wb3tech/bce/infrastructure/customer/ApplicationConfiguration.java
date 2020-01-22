package com.wb3tech.bce.infrastructure.customer;


import com.wb3tech.bce.domain.customer.CustomerGateway;
import com.wb3tech.bce.domain.customer.create.CustomerCreatedEventDispatcher;
import com.wb3tech.bce.domain.customer.remove.CustomerRemovedEventDispatcher;
import com.wb3tech.bce.domain.customer.update.CustomerUpdatedEventDispatcher;
import com.wb3tech.bce.infrastructure.customer.middleware.inmemory.CustomerCreatedEventLogger;
import com.wb3tech.bce.infrastructure.customer.database.inmemory.CustomerInMemoryDatabase;
import com.wb3tech.bce.infrastructure.customer.middleware.inmemory.CustomerRemovedEventLogger;
import com.wb3tech.bce.infrastructure.customer.middleware.inmemory.CustomerUpdatedEventLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/***
 * The Application Configuration is the explicit place to set all beans for the infrastructure components.
 */
public class ApplicationConfiguration {

    @Bean
    public CustomerGateway customerGateway() {
        return new CustomerInMemoryDatabase();
    }

    @Bean
    public CustomerCreatedEventDispatcher customerCreatedEventDispatcher() {
        return new CustomerCreatedEventLogger();
    }

    @Bean
    public CustomerUpdatedEventDispatcher customerUpdatedEventDispatcherEventDispatcher() {
        return new CustomerUpdatedEventLogger();
    }

    @Bean
    public CustomerRemovedEventDispatcher customerRemovedEventDispatcher() {
        return new CustomerRemovedEventLogger();
    }

}
