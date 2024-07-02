package com.wigell.wigellpadel.services;

import com.wigell.wigellpadel.entities.Customer;
import com.wigell.wigellpadel.repositories.CustomerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private static final Logger logger = Logger.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer saveCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        logger.info("Created new customer: " + savedCustomer);
        return savedCustomer;
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer != null) {
            existingCustomer.setUsername(customer.getUsername());
            existingCustomer.setName(customer.getName());
            existingCustomer.setAddress(customer.getAddress());
            Customer updatedCustomer = customerRepository.save(existingCustomer);
            logger.info("Updated customer: " + updatedCustomer);
            return updatedCustomer;
        }
        return null;
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
        logger.info("Deleted customer with id: " + id);
    }
}