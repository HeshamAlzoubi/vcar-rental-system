package miu.ea.carrental.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Car {
    @Id
    private String licensePlate;
    private String brand;
    private String type;
    private int modelYear;
    private String color;
    private double pricePerDay;

    @OneToMany
    @JoinColumn(name="car_id")
    List<Rent> rents;


}