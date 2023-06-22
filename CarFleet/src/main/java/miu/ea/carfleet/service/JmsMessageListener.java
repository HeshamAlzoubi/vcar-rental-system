package miu.ea.carfleet.service;

import miu.ea.carfleet.config.EventType;
import miu.ea.carfleet.dto.CarDto;
import miu.ea.carfleet.dto.CarEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsMessageListener {
    @Autowired
     private CarService carService;

    @JmsListener(destination = "carEventsQueue")
    public void receiveCarEvent(CarEventDto carEventDto) {
        if(carEventDto.getEventType() == EventType.CAR_RENTED){
            CarDto c = carEventDto.getCarDto();
            c.setRented(true);
            carService.updateCar(c.getLicensePlate(), c);
        }
    }
}
