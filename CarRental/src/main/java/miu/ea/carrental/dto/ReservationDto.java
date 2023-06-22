package miu.ea.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.ea.carrental.dto.CarDto;
import miu.ea.carrental.dto.CustomerDto;
import miu.ea.carrental.entity.Car;
import miu.ea.carrental.entity.Customer;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private CustomerDto customer;
    private CarDto car;
    private LocalDate pickupDate;
    private LocalDate returnDate;


    public String getCarId() {
        return  car.getLicensePlate();
    }

    public Long getCustomerId() {
        return customer.getCustomerNumber();
    }
}
