package com.wb3tech.bce.infrastructure.customer.database.inmemory;

import com.wb3tech.bce.domain.customer.CustomerEntity;
import com.wb3tech.bce.domain.customer.CustomerGateway;
import com.wb3tech.bce.infrastructure.customer.database.CustomerNotFound;
import com.wb3tech.kernel.entity.Identifiable;
import com.wb3tech.kernel.entity.Identity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

public class CustomerInMemoryDatabase implements CustomerGateway {

    private List<CustomerEntity> customers;

    public CustomerInMemoryDatabase() {
        this.customers = new ArrayList<>();
    }

    @Override
    public void Create(CustomerEntity customerEntity) {
        this.customers.add(customerEntity);
    }

    @Override
    public void Update(CustomerEntity customerEntity) {
        try {
            this.updateCustomer(customerEntity);
        } catch (NoSuchElementException nse) {
            this.throwCustomerNotFound(customerEntity);
        }
    }

    @Override
    public void Remove(Identifiable identifiable) {
        try {
            this.removeCustomer(identifiable);
        } catch (NoSuchElementException nse) {
            this.throwCustomerNotFound(identifiable);
        }
    }

    @Override
    public Optional<CustomerEntity> Find(Identity identity) {
        return this.customers.stream().filter(customer -> identity.equals(customer.getId())).findFirst();
    }

    private int getIndexOfCustomer(Identifiable identifiable) {
        return IntStream.range(0, this.customers.size())
                .filter(customerInd -> this.customers.get(customerInd).getId().equals(identifiable.getId()))
                .findFirst()
                .getAsInt();
    }

    private void updateCustomer(CustomerEntity customerEntity) {
        this.customers.set(this.getIndexOfCustomer(customerEntity), customerEntity);
    }

    private void removeCustomer(Identifiable identifiable) {
        this.customers.remove(this.getIndexOfCustomer(identifiable));
    }

    private void throwCustomerNotFound(Identifiable identifiable) {
        throw new CustomerNotFound(String.format("Could not find customer: %s", identifiable.getId().toString()));
    }

}
