package miu.ea.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentDto {
    private Long id;
    private CustomerDto customer;
    private CarDto car;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;


    public void setCustomerNumber(Long customerNumber) {
        this.customer.setCustomerNumber(customerNumber);
    }

    public void setCarLicensePlate(String carLicensePlate) {
        this.car.setLicensePlate(carLicensePlate);
    }

    public Long getCustomerNumber() {
        return customer.getCustomerNumber();
    }

    public String getCarLicensePlate() {
        return car.getLicensePlate();
    }


}
