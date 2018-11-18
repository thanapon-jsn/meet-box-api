package com.neah.meetbox.customer;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    List<Customer> findAllByFirstName(String firstName);
}
