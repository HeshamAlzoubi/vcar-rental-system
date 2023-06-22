package miu.ea.carrental.controller;

import miu.ea.carrental.dto.RentDto;
import miu.ea.carrental.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rents")
public class RentController {
    @Autowired
    private RentService rentService;

    @PostMapping
    public ResponseEntity<RentDto> addRent(@RequestBody RentDto rentDto) {
        RentDto addedRent = rentService.addRent(rentDto);
        return ResponseEntity.ok(addedRent);
    }

    @GetMapping("/customer/{customerNumber}")
    public ResponseEntity<List<RentDto>> getRentsByCustomerNumber(@PathVariable Long customerNumber) {
        List<RentDto> rentDtos = rentService.getRentsByCustomerNumber(customerNumber);
        return ResponseEntity.ok(rentDtos);
    }

    @GetMapping("/car/{licensePlate}")
    public ResponseEntity<List<RentDto>> getRentsByCarLicensePlate(@PathVariable String licensePlate) {
        List<RentDto> rentDtos = rentService.getRentsByCarLicensePlate(licensePlate);
        return ResponseEntity.ok(rentDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRent(@PathVariable Long id) {
        rentService.deleteRent(id);
        return ResponseEntity.noContent().build();
    }
}
