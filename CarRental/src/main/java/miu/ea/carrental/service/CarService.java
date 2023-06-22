package miu.ea.carrental.service;

import miu.ea.carrental.dto.CarDto;
import miu.ea.carrental.entity.Car;
import miu.ea.carrental.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CarDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(car -> modelMapper.map(car, CarDto.class))
                .collect(Collectors.toList());
    }

    public CarDto getCarByLicensePlate(String licensePlate) {
        Optional<Car> optionalCar = carRepository.findById(licensePlate);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            return modelMapper.map(car, CarDto.class);
        } else {
            return null;
        }
    }

    public Car addCar(Car carDto) {
        Car car = modelMapper.map(carDto, Car.class);
        carRepository.save(car);
        return car;
    }

    public void updateCar(String licensePlate, CarDto carDto) {
        Optional<Car> optionalCar = carRepository.findById(licensePlate);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            modelMapper.map(carDto, car);
            carRepository.save(car);
        }
    }

    public boolean deleteCar(String licensePlate) {
        carRepository.deleteById(licensePlate);
        return false;
    }
}
