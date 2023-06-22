package miu.ea.carfleet.dto;

import lombok.Data;
import miu.ea.carfleet.config.EventType;


@Data
public class CarEventDto {
    private EventType eventType;
    private CarDto carDto;

    public CarEventDto(EventType eventType, CarDto carDto) {
        this.eventType = eventType;
        this.carDto = carDto;
    }
}


