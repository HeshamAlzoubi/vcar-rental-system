package miu.ea.carfleet.repository;

import miu.ea.carfleet.entity.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Arrays;
import java.util.List;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataMongoTest
class CarRepositoryTests {
    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        Car car1 = new Car("ABC123", "Toyota", "Camry", 2020, "Blue", 50.0);
        Car car2 = new Car("DEF456", "Honda", "Civic", 2021, "Red", 45.0);
        carRepository.saveAll(Arrays.asList(car1, car2));
    }

    @Test
    void testFindAll() {
        List<Car> cars = carRepository.findAll();
        assertEquals(2, cars.size());
    }

    @Test
    void testFindById() {
        Car car = carRepository.findById("ABC123").orElse(null);
        assertNotNull(car);
        assertEquals("Toyota", car.getBrand());
        assertEquals("Camry", car.getType());
        assertEquals(2020, car.getModelYear());
        assertEquals("Blue", car.getColor());
        assertEquals(50.0, car.getPricePerDay(), 0.001);
    }

    @Test
    void testSave() {
        Car car = new Car("GHI789", "Ford", "Mustang", 2022, "Yellow", 60.0);
        carRepository.save(car);

        assertNotNull(car.getLicensePlate());
        assertEquals("GHI789", car.getLicensePlate());
        assertEquals("Ford", car.getBrand());
        assertEquals("Mustang", car.getType());
        assertEquals(2022, car.getModelYear());
        assertEquals("Yellow", car.getColor());
        assertEquals(60.0, car.getPricePerDay(), 0.001);

        Car savedCar = carRepository.findById("GHI789").orElse(null);
        assertNotNull(savedCar);
        assertEquals("Ford", savedCar.getBrand());
        assertEquals("Mustang", savedCar.getType());
        assertEquals(2022, savedCar.getModelYear());
        assertEquals("Yellow", savedCar.getColor());
        assertEquals(60.0, savedCar.getPricePerDay(), 0.001);
    }

    @Test
    void testDeleteById() {
        carRepository.deleteById("ABC123");
        assertEquals(1, carRepository.count());
    }
}
