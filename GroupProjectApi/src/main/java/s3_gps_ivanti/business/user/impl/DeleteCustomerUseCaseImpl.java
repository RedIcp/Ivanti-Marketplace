package s3_gps_ivanti.business.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.exception.CustomerNotFoundException;
import s3_gps_ivanti.business.exception.InvalidCredentialsException;
import s3_gps_ivanti.business.user.DeleteCustomerUseCase;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.User;

import javax.transaction.Transactional;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class DeleteCustomerUseCaseImpl implements DeleteCustomerUseCase {

    private final UserRepository userRepository;
    private final AccessTokenDTO requestAccessToken;

    @Override
    public void DeleteCustomer(String customerID) {

        if(!requestAccessToken.getUserID().equals(customerID)){
            throw new InvalidCredentialsException();
        }

        User user = userRepository.findById(customerID).orElse(null);

        if(user == null) {
            throw new CustomerNotFoundException();
        }

        userRepository.deleteById(customerID);
    }
}
