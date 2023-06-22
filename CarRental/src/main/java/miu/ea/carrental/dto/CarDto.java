package miu.ea.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private String licensePlate;
    private String brand;
    private String type;
    private int modelYear;
    private String color;
    private double pricePerDay;
}
