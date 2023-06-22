package miu.ea.carrental.repository;

import miu.ea.carrental.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCustomerNumber(long customerNumber);
}
