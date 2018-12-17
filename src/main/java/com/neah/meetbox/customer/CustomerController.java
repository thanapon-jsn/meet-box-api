package com.neah.meetbox.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;
    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        logger.info("get customers");
        return customerService.getCustomerLists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Integer id) {
        logger.info("ID : " + id);
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (!customer.isPresent()) return ResponseEntity.notFound().build();


        return ResponseEntity.ok(customer);
    }

    @GetMapping(params = "name")
    public List<Customer> getCustomer(@RequestParam(value = "name") String name) {
        logger.info("Name : " + name);
        return customerService.getCustomerByFirstName(name);
    }

}
