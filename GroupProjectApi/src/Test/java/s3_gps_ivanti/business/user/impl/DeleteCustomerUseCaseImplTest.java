package s3_gps_ivanti.business.user.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.exception.CustomerNotFoundException;
import s3_gps_ivanti.business.exception.InvalidCredentialsException;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerUseCaseImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AccessTokenDTO requestAccessToken;
    @InjectMocks
    private DeleteCustomerUseCaseImpl deleteCustomerUseCase;

    @Test
    void deleteCustomer_UserIdNotMatch() {
        when(requestAccessToken.getUserID())
                .thenReturn("WrongID");

       assertThrows(InvalidCredentialsException.class, ()-> deleteCustomerUseCase.DeleteCustomer("id"));

        verify(requestAccessToken).getUserID();
    }
    @Test
    void deleteCustomer_UserNotFound() {
        when(requestAccessToken.getUserID())
                .thenReturn("id");

        assertThrows(CustomerNotFoundException.class, ()-> deleteCustomerUseCase.DeleteCustomer("id"));

        verify(requestAccessToken).getUserID();
        verify(userRepository).findById("id");

    }
    @Test
    void deleteCustomer() {
        when(requestAccessToken.getUserID())
                .thenReturn("id");
        when(userRepository.findById("id"))
                .thenReturn(Optional.ofNullable(User.builder().build()));

        deleteCustomerUseCase.DeleteCustomer("id");

        verify(requestAccessToken).getUserID();
        verify(userRepository).findById("id");
        verify(userRepository).deleteById("id");
    }
}