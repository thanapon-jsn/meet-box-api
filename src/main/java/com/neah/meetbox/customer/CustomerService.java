package com.neah.meetbox.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    List<Customer> getCustomerLists() {
        return (List<Customer>) customerRepository.findAll();
    }

    Optional<Customer> getCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    List<Customer> getCustomerByFirstName(String name) {
        return customerRepository.findAllByFirstName(name);
    }

}
