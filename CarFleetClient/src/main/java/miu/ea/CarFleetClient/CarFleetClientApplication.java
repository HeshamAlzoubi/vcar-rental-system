package miu.ea.CarFleetClient;

import miu.ea.CarFleetClient.dto.CarDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class CarFleetClientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CarFleetClientApplication.class, args);
	}


		@Override
		public void run(String... args) throws Exception {
			// Create a new RestTemplate
			RestTemplate restTemplate = new RestTemplate();

			// Add a new car
			CarDto newCar = new CarDto();
			newCar.setLicensePlate("1");
			newCar.setType("Sedan");
			newCar.setBrand("Toyota");
			newCar.setModelYear(2020);
			newCar.setPricePerDay(25000.0);
			ResponseEntity<CarDto> response = restTemplate.postForEntity("http://localhost:8089/api/cars", newCar, CarDto.class);
			CarDto savedCar = response.getBody();
			System.out.println("Added car: " + savedCar);

			newCar = new CarDto();
			newCar.setLicensePlate("2");
			newCar.setType("Sedan");
			newCar.setBrand("Toyota");
			newCar.setModelYear(2020);
			newCar.setPricePerDay(25000.0);
			response = restTemplate.postForEntity("http://localhost:8089/api/cars", newCar, CarDto.class);
			savedCar = response.getBody();
			System.out.println("Added car: " + savedCar);

			// Get all cars
			ResponseEntity<CarDto[]> responseEntity = restTemplate.getForEntity("http://localhost:8089/api/cars", CarDto[].class);
			List<CarDto> cars = Arrays.asList(responseEntity.getBody());
			System.out.println("All cars: " + cars);

			// Get a car by ID
			String id = "1";
			CarDto car = restTemplate.getForObject("http://localhost:8089/api/cars/" + id, CarDto.class);
			System.out.println("Car with ID " + id + ": " + car);

			// Update a car
			id = "1";
			car.setPricePerDay(30000.0);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<CarDto> entity = new HttpEntity<>(car, headers);
			ResponseEntity<CarDto> updatedResponse = restTemplate.exchange("http://localhost:8089/api/cars/" + id, HttpMethod.PUT, entity, CarDto.class);
			CarDto updatedCar = updatedResponse.getBody();
			System.out.println("Updated car: " + updatedCar);

			// Search for cars
			String type = "SUV";
			String brand = "Ford";
			Double price = 20000.0;
			ResponseEntity<CarDto[]> searchResponseEntity = restTemplate.getForEntity("http://localhost:8089/api/cars/search?type=" + type + "&brand=" + brand + "&price=" + price, CarDto[].class);
			List<CarDto> searchResults = Arrays.asList(searchResponseEntity.getBody());
			System.out.println("Search results: " + searchResults);

			// Delete a car
			id = "1";
			restTemplate.delete("http://localhost:8089/api/cars/" + id);
			System.out.println("Car with ID " + id + " deleted.");
		}
}


