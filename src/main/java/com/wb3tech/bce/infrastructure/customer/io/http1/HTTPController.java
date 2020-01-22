package com.wb3tech.bce.infrastructure.customer.io.http1;

import com.wb3tech.bce.domain.customer.CustomerGateway;
import com.wb3tech.bce.domain.customer.create.CreateCustomerRequest;
import com.wb3tech.bce.domain.customer.create.CreateCustomerUseCase;
import com.wb3tech.bce.domain.customer.create.CustomerCreatedEventDispatcher;
import com.wb3tech.bce.domain.customer.find.FindCustomerRequest;
import com.wb3tech.bce.domain.customer.find.FindCustomerResponse;
import com.wb3tech.bce.domain.customer.find.FindCustomerUseCase;
import com.wb3tech.bce.domain.customer.remove.CustomerRemovedEventDispatcher;
import com.wb3tech.bce.domain.customer.remove.RemoveCustomerRequest;
import com.wb3tech.bce.domain.customer.remove.RemoveCustomerUseCase;
import com.wb3tech.bce.domain.customer.update.CustomerUpdatedEventDispatcher;
import com.wb3tech.bce.domain.customer.update.UpdateCustomerRequest;
import com.wb3tech.bce.domain.customer.update.UpdateCustomerUseCase;
import com.wb3tech.bce.infrastructure.customer.database.CustomerNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class HTTPController {

    private CustomerGateway customerGateway;
    private CustomerCreatedEventDispatcher customerCreatedEventDispatcher;
    private CustomerRemovedEventDispatcher customerRemovedEventDispatcher;
    private CustomerUpdatedEventDispatcher customerUpdatedEventDispatcher;

    @Autowired
    public HTTPController(CustomerGateway customerGateway,
                          CustomerCreatedEventDispatcher customerCreatedEventDispatcher,
                          CustomerRemovedEventDispatcher customerRemovedEventDispatcher,
                          CustomerUpdatedEventDispatcher customerUpdatedEventDispatcher) {
        this.customerGateway = customerGateway;
        this.customerCreatedEventDispatcher = customerCreatedEventDispatcher;
        this.customerRemovedEventDispatcher = customerRemovedEventDispatcher;
        this.customerUpdatedEventDispatcher = customerUpdatedEventDispatcher;
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public ResponseEntity<CreateCustomerRequest> Create(@RequestBody CustomerHTTPBody httpBody) {

        var request = new CreateCustomerRequest(httpBody.firstName, httpBody.lastName);
        new CreateCustomerUseCase(this.customerGateway, this.customerCreatedEventDispatcher).execute(request);
        return new ResponseEntity<>(request, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<FindCustomerResponse> Find(@PathVariable UUID customerId) {

        var response = new FindCustomerUseCase(this.customerGateway).execute(new FindCustomerRequest(customerId));
        if(!response.doesExist()) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
        return new ResponseEntity<>(response, HttpStatus.FOUND);

    }

    @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.DELETE)
    public ResponseEntity Remove(@PathVariable UUID customerId) {

        try {
            new RemoveCustomerUseCase(this.customerGateway, this.customerRemovedEventDispatcher).execute(new RemoveCustomerRequest(customerId));
        } catch (CustomerNotFound cnf) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.PUT)
    public ResponseEntity<UpdateCustomerRequest> Update(@PathVariable UUID customerId,
                                 @RequestBody CustomerHTTPBody httpBody) {

        var request = new UpdateCustomerRequest(customerId, httpBody.firstName, httpBody.lastName);
        try {
            new UpdateCustomerUseCase(this.customerGateway, this.customerUpdatedEventDispatcher).execute(request);
        } catch (CustomerNotFound cnf) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(request, HttpStatus.ACCEPTED);

    }

}
