package miu.ea.carrental.controller;

import miu.ea.carrental.dto.CustomerDto;
import miu.ea.carrental.entity.Customer;
import miu.ea.carrental.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customers = customerService.getAllCustomers();
        List<CustomerDto> customerDtos = customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerDtos);
    }

    @GetMapping("/{customerNumber}")
    public ResponseEntity<CustomerDto> getCustomerByNumber(@PathVariable long customerNumber) {
        CustomerDto c = customerService.getCustomerByNumber(customerNumber);
        if (c != null) {
            return ResponseEntity.ok( modelMapper.map(c, CustomerDto.class));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto savedCustomer = customerService.createCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @PutMapping("/{customerNumber}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable long customerNumber,
                                                      @RequestBody CustomerDto customerDto) {
        CustomerDto c = customerService.updateCustomer(customerNumber, customerDto);
        if (c != null) {
            return ResponseEntity.ok(c);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{customerNumber}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long customerNumber) {
        customerService.deleteCustomer(customerNumber);
        return ResponseEntity.noContent().build();
    }
}
