package com.cashier.stripe.adapter.in.web;

import com.cashier.stripe.domain.model.Customer;
import com.cashier.stripe.domain.port.in.CustomerUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerUseCase customerUseCase;

    public CustomerController(CustomerUseCase customerUseCase) {
        this.customerUseCase = customerUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Customer> createCustomer(@RequestBody Customer customer) {
        return customerUseCase.createCustomer(customer);
    }

    @GetMapping("/{customerId}")
    public Mono<Customer> getCustomer(@PathVariable Long customerId) {
        return customerUseCase.getCustomer(customerId);
    }

    @PutMapping("/{customerId}")
    public Mono<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer) {
        return customerUseCase.updateCustomer(customerId, customer);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCustomer(@PathVariable Long customerId) {
        return customerUseCase.deleteCustomer(customerId);
    }
}
