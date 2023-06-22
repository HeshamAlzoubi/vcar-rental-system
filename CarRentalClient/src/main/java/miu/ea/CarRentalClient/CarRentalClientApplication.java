package miu.ea.CarRentalClient;

import miu.ea.CarRentalClient.dto.CustomerDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class CarRentalClientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Create a new RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		// Get all customers
		ResponseEntity<CustomerDto[]> responseEntity = restTemplate.getForEntity("http://localhost:8090/api/customers", CustomerDto[].class);
		List<CustomerDto> customers = Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
		System.out.println("All customers: " + customers);

		// Add a new customer
		CustomerDto newCustomer = new CustomerDto();
		newCustomer.setCustomerNumber(6789L);
		newCustomer.setFirstName("John");
		newCustomer.setLastName("Doe");
		newCustomer.setEmail("johndoe@example.com");
		newCustomer.setAddress("123 Main St", "Fairfield", "IA", "52556");
		ResponseEntity<CustomerDto> response = restTemplate.postForEntity("http://localhost:8090/api/customers", newCustomer, CustomerDto.class);
		CustomerDto savedCustomer = response.getBody();
		System.out.println("Added customer: " + savedCustomer);


		// Get all customers
		responseEntity = restTemplate.getForEntity("http://localhost:8090/api/customers", CustomerDto[].class);
		customers = Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
		System.out.println("All customers: " + customers);


		// Get a customer by customer number

		long customerNumber = 6789L;
		CustomerDto customer = restTemplate.getForObject("http://localhost:8090/api/customers/" + customerNumber, CustomerDto.class);
		System.out.println("Customer with customer number " + customerNumber + ": " + customer);

		// Update a customer
		customerNumber = 6789L;
		assert customer != null;
		customer.setFirstName("Jane");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CustomerDto> entity = new HttpEntity<>(customer, headers);
		ResponseEntity<CustomerDto> updatedResponse = restTemplate.exchange("http://localhost:8090/api/customers/" + customerNumber, HttpMethod.PUT, entity, CustomerDto.class);
		CustomerDto updatedCustomer = updatedResponse.getBody();
		System.out.println("Updated customer: " + updatedCustomer);

		// Delete a customer
		customerNumber = 6789L;
		restTemplate.delete("http://localhost:8090/api/customers/" + customerNumber);
		System.out.println("Customer with customer number " + customerNumber + " deleted.");


		// Get all customers
		responseEntity = restTemplate.getForEntity("http://localhost:8090/api/customers", CustomerDto[].class);
		 customers = Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
		System.out.println("All customers: " + customers);
	}
}