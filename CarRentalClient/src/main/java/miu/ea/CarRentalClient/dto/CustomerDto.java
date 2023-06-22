package miu.ea.CarRentalClient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

        private Long customerNumber;

        private String firstName;

        private String lastName;

        private String email;

        private String phone;

        private AddressDto address;

        public void setAddress(String s, String fairfield, String ia, String number) {
        }
}
