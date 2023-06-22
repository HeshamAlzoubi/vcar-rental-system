package miu.ea.carfleet.service;

import miu.ea.carfleet.dto.CarEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsService {

    @Autowired
    private JmsTemplate jmsTemplate;


    public void sendCarEvent(CarEventDto carEventDto) {
        jmsTemplate.convertAndSend("carEventsQueue", carEventDto);
    }
}
