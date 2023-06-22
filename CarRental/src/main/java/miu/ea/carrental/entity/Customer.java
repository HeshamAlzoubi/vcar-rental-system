package miu.ea.carrental.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    private Long customerNumber;

    private String firstName;

    private String lastName;

    private String email;
    private String phone;

    @Embedded
    private Address address;

}