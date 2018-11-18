package com.neah.meetbox.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomerLists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Integer id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (!customer.isPresent()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(customer);
    }

    @GetMapping(params = "name")
    public List<Customer> getCustomer(@RequestParam(value = "name") String name) {
        return customerService.getCustomerByFirstName(name);
    }

}
