package miu.ea.carrental.repository;

import miu.ea.carrental.entity.Rent;
import miu.ea.carrental.entity.Customer;
import miu.ea.carrental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> findByCustomer(Customer customer);
    List<Rent> findByCar_LicensePlate(String licensePlate);
   List<Rent> findByCustomerCustomerNumber(Long customerNumber);

}
