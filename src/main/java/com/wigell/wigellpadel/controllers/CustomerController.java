package com.wigell.wigellpadel.controllers;

import com.wigell.wigellpadel.entities.Customer;
import com.wigell.wigellpadel.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v5/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping ("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}