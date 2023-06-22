package miu.ea.carfleet.repository;

import miu.ea.carfleet.entity.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CarRepository extends MongoRepository<Car, String> {
    List<Car> findByType(String type);
    List<Car> findByBrand(String brand);
    List<Car> findByPricePerDayLessThan(double price);

    @Query("{$and: [ { $or: [ { 'type': { $regex: ?0, $options: 'i' } }, { ?0: { $exists: false } } ] }, "
            + "{ $or: [ { 'brand': { $regex: ?1, $options: 'i' } }, { ?1: { $exists: false } } ] }, "
            + "{ $or: [ { 'price': { $eq: ?2 } }, { ?2: { $exists: false } } ] } ]}")
    List<Car> searchCars(String type, String brand, Double price);

}
