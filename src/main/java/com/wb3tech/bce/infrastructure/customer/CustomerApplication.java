package com.wb3tech.bce.infrastructure.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 * TODO: Build a BDD Test Suite for this App; NOTE - Should have been done before building the App!!!
 * Given the full business logic has been tested in the domain layer,
 * testing at the infrastructure layer allows one to focus on how the infrastructure
 * components integrate and play with each other.
 * These types of tests are called "Large" tests.
 */
public class CustomerApplication {

        public static void main(String[] args) {
            SpringApplication.run(CustomerApplication.class, args);
        }
}
