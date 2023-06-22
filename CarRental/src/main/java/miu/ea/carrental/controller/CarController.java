package miu.ea.carrental.controller;

import miu.ea.carfleet.dto.CarDto;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @GetMapping("/search")
    public ResponseEntity<List<CarDto>> searchCars(@RequestParam(name = "type", required = false) String type,
                                                   @RequestParam(name = "brand", required = false) String brand,
                                                   @RequestParam(name = "price", required = false) Double price) {
        RestTemplate restTemplate = new RestTemplate();

        // Set the URL
        String url = "http://localhost:8089/api/cars/search";

        // Set the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

         // Set the request entity
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Send the GET request and get the response
        ResponseEntity<CarDto[]> responseEntity = restTemplate.exchange(
                url + "?type={type}&brand={brand}&price={price}",
                HttpMethod.GET,
                requestEntity,
                CarDto[].class,
                type, brand, price);

        // Get the response body
        List<CarDto> cars = Arrays.asList(responseEntity.getBody());
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }


}
