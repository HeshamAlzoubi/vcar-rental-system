package miu.ea.carfleet.controller;

import miu.ea.carfleet.dto.CarDto;
import miu.ea.carfleet.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<CarDto> cars = carService.getAllCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable("id") String id) {
        CarDto car = carService.getCarById(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarDto> addCar(@RequestBody CarDto CarDto) {
        CarDto savedCar = carService.addCar(CarDto);
        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable("id") String id, @RequestBody CarDto CarDto) {
        CarDto updatedCar = carService.updateCar(id, CarDto);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") String id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CarDto>> searchCars(@RequestParam(name = "type", required = false) String type,
                                                   @RequestParam(name = "brand", required = false) String brand,
                                                   @RequestParam(name = "price", required = false) Double price) {
        List<CarDto> cars = carService.searchCars(type, brand, price);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
}
