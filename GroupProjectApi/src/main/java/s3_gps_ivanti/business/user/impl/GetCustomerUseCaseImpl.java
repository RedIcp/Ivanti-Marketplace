package s3_gps_ivanti.business.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.dtoconvertor.CustomerDTOConverter;
import s3_gps_ivanti.business.exception.CustomerNotFoundException;
import s3_gps_ivanti.business.user.GetCustomerUseCase;
import s3_gps_ivanti.dto.user.CustomerDetailedInfoDTO;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.User;

import javax.transaction.Transactional;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class GetCustomerUseCaseImpl implements GetCustomerUseCase {

    private final UserRepository userRepository;

    @Override
    public CustomerDetailedInfoDTO getCustomer(String username) {

        User result = userRepository.findUserByUsername(username);

       if(result == null){
           throw new CustomerNotFoundException();
       }

       return CustomerDTOConverter.convertToDetailedDTO(result);
    }
}