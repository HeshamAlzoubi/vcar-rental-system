package miu.ea.carfleet;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class CarFleetApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarFleetApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper()
    {
        return  new ModelMapper();
    }
}


