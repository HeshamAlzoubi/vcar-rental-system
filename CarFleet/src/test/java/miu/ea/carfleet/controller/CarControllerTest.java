package miu.ea.carfleet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import miu.ea.carfleet.dto.CarDto;
import miu.ea.carfleet.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void testGetAllCars() throws Exception {
        List<CarDto> cars = Arrays.asList(
                new CarDto("ABC123", "Toyota", "Camry", 2015, "Black", 50.0),
                new CarDto("DEF456", "Honda", "Civic", 2017, "Red", 40.0)
        );
        given(carService.getAllCars()).willReturn(cars);

        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].licensePlate", is("ABC123")))
                .andExpect(jsonPath("$[0].brand", is("Toyota")))
                .andExpect(jsonPath("$[0].type", is("Camry")))
                .andExpect(jsonPath("$[0].modelYear", is(2015)))
                .andExpect(jsonPath("$[0].color", is("Black")))
                .andExpect(jsonPath("$[0].pricePerDay", is(50.0)))
                .andExpect(jsonPath("$[1].licensePlate", is("DEF456")))
                .andExpect(jsonPath("$[1].brand", is("Honda")))
                .andExpect(jsonPath("$[1].type", is("Civic")))
                .andExpect(jsonPath("$[1].modelYear", is(2017)))
                .andExpect(jsonPath("$[1].color", is("Red")))
                .andExpect(jsonPath("$[1].pricePerDay", is(40.0)));
    }

    @Test
    public void testGetCarById() throws Exception {
        String id = "ABC123";
        CarDto car = new CarDto(id, "Toyota", "Camry", 2015, "Black", 50.0);
        given(carService.getCarById(id)).willReturn(car);

        mockMvc.perform(get("/api/cars/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate", is(id)))
                .andExpect(jsonPath("$.brand", is("Toyota")))
                .andExpect(jsonPath("$.type", is("Camry")))
                .andExpect(jsonPath("$.modelYear", is(2015)))
                .andExpect(jsonPath("$.color", is("Black")))
                .andExpect(jsonPath("$.pricePerDay", is(50.0)));
    }

    @Test
    public void testAddCar() throws Exception {
        String id = "ABC123";
        CarDto carDto = new CarDto(null, "Toyota", "Camry", 2015, "Black", 50.0);
        CarDto savedCarDto = new CarDto(id, "Toyota", "Camry", 2015, "Black", 50.0);
        given(carService.addCar(carDto)).willReturn(savedCarDto);

        mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"brand\":\"Toyota\",\"type\":\"Camry\",\"modelYear\":2015,\"color\":\"Black\",\"pricePerDay\":50.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.licensePlate", is(id)))
                .andExpect(jsonPath("$.brand", is("Toyota")))
                .andExpect(jsonPath("$.type", is("Camry")))
                .andExpect(jsonPath("$.modelYear", is(2015)))
                .andExpect(jsonPath("$.color", is("Black")))
                .andExpect(jsonPath("$.pricePerDay", is(50.0)));
    }

    @Test
    public void testUpdateCar() throws Exception {
        // Prepare mock input data
        String carId = "ABC123";
        CarDto carDto = new CarDto("ABC123", "Ford", "Mustang", 2021, "red", 150.0);

        // Prepare mock output data
        CarDto updatedCarDto = new CarDto(carId, "Ford", "Mustang", 2021, "red", 150.0);

        // Set up the mock service method
        given(carService.updateCar(eq(carId), any(CarDto.class))).willReturn(updatedCarDto);

        // Perform the PUT request with the mock input data
        mockMvc.perform(put("/api/cars/{id}", carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(carDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(carId)))
                .andExpect(jsonPath("$.brand", is("Ford")))
                .andExpect(jsonPath("$.type", is("Mustang")))
                .andExpect(jsonPath("$.modelYear", is(2021)))
                .andExpect(jsonPath("$.color", is("red")))
                .andExpect(jsonPath("$.pricePerDay", is(150.0)));
    }



    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}