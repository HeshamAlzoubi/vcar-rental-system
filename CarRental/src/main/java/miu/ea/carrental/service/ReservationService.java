package miu.ea.carrental.service;

import miu.ea.carrental.dto.CarDto;
import miu.ea.carrental.dto.CustomerDto;
import miu.ea.carrental.dto.RentDto;
import miu.ea.carrental.dto.ReservationDto;
import miu.ea.carrental.entity.Car;
import miu.ea.carrental.entity.Customer;
import miu.ea.carrental.entity.Reservation;
import miu.ea.carrental.repository.ReservationRepository;
import miu.ea.carrental.service.RentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ModelMapper modelMapper;
    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return convertToDtoList(reservations);
    }

    public ReservationDto getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(null);
        return convertToDto(reservation);
    }

    public ReservationDto addReservation(ReservationDto reservationDto) {
        Reservation reservation = convertToEntity(reservationDto);
        Reservation savedReservation = reservationRepository.save(reservation);
        return convertToDto(savedReservation);
    }

    public ReservationDto updateReservation(Long id, ReservationDto reservationDto) {
        Reservation reservation = reservationRepository.findById(id)
                .orElse(null);
        reservation.setStartDate(reservationDto.getPickupDate());
        reservation.setEndDate(reservationDto.getReturnDate());
        Reservation savedReservation = reservationRepository.save(reservation);
        return convertToDto(savedReservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElse(null);
        reservationRepository.delete(reservation);
    }

    private ReservationDto convertToDto(Reservation reservation) {
        return new ReservationDto(reservation.getId(),
                modelMapper.map(reservation.getCustomer(), CustomerDto.class),
                modelMapper.map(reservation.getCar(), CarDto.class),
                reservation.getStartDate(),
                reservation.getEndDate()
              );
    }

    private List<ReservationDto> convertToDtoList(List<Reservation> reservations) {
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDtos.add(convertToDto(reservation));
        }
        return reservationDtos;
    }

    private Reservation convertToEntity(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        Customer customer = new Customer();
        customer.setCustomerNumber(reservationDto.getCustomerId());
        Car car = new Car();
        car.setLicensePlate(reservationDto.getCarId());
        reservation.setCustomer(customer);
        reservation.setCar(car);
        reservation.setStartDate(reservationDto.getPickupDate());
        reservation.setEndDate(reservationDto.getReturnDate());
        return reservation;
    }

}
