package miu.ea.carfleet.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Car {
    @Id
    private String licensePlate;
    private String brand;
    private String type;
    private int modelYear;
    private String color;
    private double pricePerDay;

}
