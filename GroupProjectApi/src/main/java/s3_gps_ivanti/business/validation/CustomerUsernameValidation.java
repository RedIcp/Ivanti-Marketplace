package s3_gps_ivanti.business.validation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.exception.CustomerNotFoundException;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.User;

@Service
@AllArgsConstructor
public class CustomerUsernameValidation {
    private UserRepository userRepository;

    public User customerIsValid(String name){
        if(!userRepository.existsByUsername(name)){
            throw new CustomerNotFoundException();
        }
        return
                userRepository.findUserByUsername(name);
    }
}
