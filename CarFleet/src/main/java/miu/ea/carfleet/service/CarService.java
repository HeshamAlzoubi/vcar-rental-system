package miu.ea.carfleet.service;

import com.mongodb.MongoException;
import miu.ea.carfleet.config.EventType;
import miu.ea.carfleet.dto.CarDto;
import miu.ea.carfleet.dto.CarEventDto;
import miu.ea.carfleet.entity.Car;
import miu.ea.carfleet.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JmsService jmsService;
    public List<CarDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(car -> modelMapper.map(car, CarDto.class))
                .collect(Collectors.toList());
    }

    public CarDto getCarById(String id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        return optionalCar.map(car -> modelMapper.map(car, CarDto.class)).orElse(null);
    }

    public CarDto addCar(CarDto carDto) {
        Car car = modelMapper.map(carDto, Car.class);
        Car savedCar = carRepository.save(car);
        CarDto d = modelMapper.map(savedCar, CarDto.class);
        jmsService.sendCarEvent(new CarEventDto(EventType.CAR_ADDED, d));
        return d;

    }

    public CarDto updateCar(String id, CarDto carDto) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setType(carDto.getType());
            car.setBrand(carDto.getBrand());
            car.setPricePerDay(carDto.getPricePerDay());
            carRepository.save(car);
            jmsService.sendCarEvent(new CarEventDto(EventType.CAR_UPDATED, carDto));
            return modelMapper.map(car, CarDto.class);
        }
        return null;
    }

    public void deleteCar(String id) {
        carRepository.deleteById(id);
    }

    public List<CarDto> searchCars(String type, String brand, Double price) {
        List<Car> cars = carRepository.searchCars(type, brand, price );
        return cars.stream()
                .map(car -> modelMapper.map(car, CarDto.class))
                .collect(Collectors.toList());
    }
}
