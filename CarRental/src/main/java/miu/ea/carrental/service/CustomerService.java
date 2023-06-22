package miu.ea.carrental.service;

import miu.ea.carrental.dto.CustomerDto;
import miu.ea.carrental.entity.Customer;
import miu.ea.carrental.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtos = customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
        return customerDtos;
    }

    public CustomerDto getCustomerByNumber(long customerNumber) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByCustomerNumber(customerNumber));
        return optionalCustomer.map(customer -> modelMapper.map(customer, CustomerDto.class)).orElse(null);
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDto savedCustomerDto = modelMapper.map(savedCustomer, CustomerDto.class);
        return savedCustomerDto;
    }

    public CustomerDto updateCustomer(long customerNumber, CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByCustomerNumber(customerNumber));
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            modelMapper.map(customerDto, customer);
            Customer updatedCustomer = customerRepository.save(customer);
            CustomerDto updatedCustomerDto = modelMapper.map(updatedCustomer, CustomerDto.class);
            return updatedCustomerDto;
        } else {
            return null;
        }
    }

    public void deleteCustomer(long customerNumber) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByCustomerNumber(customerNumber));
        optionalCustomer.ifPresent(customerRepository::delete);
    }
}
