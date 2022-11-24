package s3_gps_ivanti.business.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.dtoconvertor.CustomerDTOConverter;
import s3_gps_ivanti.business.exception.CustomerNotFoundException;
import s3_gps_ivanti.business.exception.EmailAlreadyExistsException;
import s3_gps_ivanti.business.exception.InvalidCredentialsException;
import s3_gps_ivanti.business.exception.UsernameAlreadyExistsException;
import s3_gps_ivanti.business.user.UpdateCustomerUseCase;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.dto.user.UpdateCustomerRequestDTO;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.User;

import javax.transaction.Transactional;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class UpdateCustomerUseCaseImpl implements UpdateCustomerUseCase {

    private final UserRepository userRepository;
    private final AccessTokenDTO requestAccessToken;

    @Override
    public void updateCustomer(UpdateCustomerRequestDTO customerRequestDTO) {

        if(!requestAccessToken.getUserID().equals(customerRequestDTO.getId())){
            throw new InvalidCredentialsException();
        }

        if(userRepository.findUserByUsername(customerRequestDTO.getUsername()) != null
                && !(userRepository.findUserByUsername(customerRequestDTO.getUsername()).getId().equals(customerRequestDTO.getId()))) {
            throw new UsernameAlreadyExistsException();
        }

        if(userRepository.findUserByEmail(customerRequestDTO.getEmail()) != null
                && !(userRepository.findUserByEmail(customerRequestDTO.getEmail()).getId().equals(customerRequestDTO.getId()))) {
            throw new EmailAlreadyExistsException();
        }

        User oldCustomer = userRepository.findById(customerRequestDTO.getId()).orElse(null);

        if(oldCustomer == null) {
            throw new CustomerNotFoundException();
        }

        if(!oldCustomer.getPermission().equals("Customer") || !(oldCustomer.getRoles().contains("Customer"))) {
            throw new CustomerNotFoundException();
        }

        User user = CustomerDTOConverter.convertToEntity(customerRequestDTO,oldCustomer);

        userRepository.save(user);
    }
}