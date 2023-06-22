package miu.ea.carrental.service;

import miu.ea.carrental.dto.RentDto;
import miu.ea.carrental.entity.Car;
import miu.ea.carrental.entity.Customer;
import miu.ea.carrental.entity.Rent;
import miu.ea.carrental.repository.CarRepository;
import miu.ea.carrental.repository.CustomerRepository;
import miu.ea.carrental.repository.RentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentService {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<RentDto> getAllRents() {
        List<Rent> rents = rentRepository.findAll();
        return rents.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<RentDto> getRentsByCustomerNumber(Long customerNumber) {
        List<Rent> rents = rentRepository.findByCustomerCustomerNumber(customerNumber);
        return rents.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<RentDto> getRentsByCarLicensePlate(String carLicensePlate) {
        List<Rent> rents = rentRepository.findByCar_LicensePlate(carLicensePlate);
        return rents.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public RentDto addRent(RentDto rentDto) {
        Rent rent = convertToEntity(rentDto);
        rent.setCustomer(customerRepository.findById(rentDto.getCustomerNumber()).orElse(null));
        rent.setCar(carRepository.findById(rentDto.getCarLicensePlate()).orElse(null));
        Rent savedRent = rentRepository.save(rent);
        return convertToDto(savedRent);
    }

    public void deleteRent(Long id) {
        rentRepository.deleteById(id);
    }

    private RentDto convertToDto(Rent rent) {
        RentDto rentDto = modelMapper.map(rent, RentDto.class);
        rentDto.setCustomerNumber(rent.getCustomer().getCustomerNumber());
        rentDto.setCarLicensePlate(rent.getCar().getLicensePlate());
        return rentDto;
    }

    private Rent convertToEntity(RentDto rentDto) {
        return modelMapper.map(rentDto, Rent.class);
    }
}
