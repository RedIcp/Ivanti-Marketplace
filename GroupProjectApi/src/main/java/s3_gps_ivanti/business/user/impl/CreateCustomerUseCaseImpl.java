package s3_gps_ivanti.business.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.dtoconvertor.CustomerDTOConverter;
import s3_gps_ivanti.business.exception.EmailAlreadyExistsException;
import s3_gps_ivanti.business.exception.UsernameAlreadyExistsException;
import s3_gps_ivanti.business.user.CreateCustomerUseCase;
import s3_gps_ivanti.dto.user.CreateCustomerRequestDTO;
import s3_gps_ivanti.dto.user.CreateCustomerResponseDTO;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.User;

import javax.transaction.Transactional;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateCustomerResponseDTO createCustomer(CreateCustomerRequestDTO customerRequestDTO) {

        if(userRepository.findUserByUsername(customerRequestDTO.getUsername()) != null) {
            throw new UsernameAlreadyExistsException();
        }

        if(userRepository.findUserByEmail(customerRequestDTO.getEmail()) != null) {
            throw new EmailAlreadyExistsException();
        }

        User user = CustomerDTOConverter.convertToEntity(customerRequestDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return CustomerDTOConverter.convertToDTOCreateResponse(user);
    }
}
