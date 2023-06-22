package miu.ea.CarRentalClient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
