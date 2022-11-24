package s3_gps_ivanti.business.user.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.dtoconvertor.CustomerDTOConverter;
import s3_gps_ivanti.business.exception.CustomerNotFoundException;
import s3_gps_ivanti.business.exception.EmailAlreadyExistsException;
import s3_gps_ivanti.business.exception.InvalidCredentialsException;
import s3_gps_ivanti.business.exception.UsernameAlreadyExistsException;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.dto.user.UpdateCustomerRequestDTO;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerUseCaseImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AccessTokenDTO requestAccessToken;
    @InjectMocks
    private UpdateCustomerUseCaseImpl updateCustomerUseCase;

    @Test
    void updateCustomer_UserIdNotMatch() {
        UpdateCustomerRequestDTO requestDTO = UpdateCustomerRequestDTO.builder()
                .id("id")
                .username("username")
                .email("email")
                .password("password")
                .build();

        when(requestAccessToken.getUserID())
                .thenReturn("WrongID");

        assertThrows(InvalidCredentialsException.class, () -> updateCustomerUseCase.updateCustomer(requestDTO));

        verify(requestAccessToken).getUserID();
    }

    @Test
    void updateCustomer_UsernameNotUnique() {
        UpdateCustomerRequestDTO requestDTO = UpdateCustomerRequestDTO.builder()
                .id("id")
                .username("username")
                .email("email")
                .password("password")
                .build();

        when(requestAccessToken.getUserID())
                .thenReturn("id");
        when(userRepository.findUserByUsername("username"))
                .thenReturn(User.builder().id("WrongID").build());

        assertThrows(UsernameAlreadyExistsException.class, () -> updateCustomerUseCase.updateCustomer(requestDTO));

        verify(requestAccessToken).getUserID();
        verify(userRepository,times(2)).findUserByUsername("username");
    }

    @Test
    void updateCustomer_EmailNotUnique() {
        UpdateCustomerRequestDTO requestDTO = UpdateCustomerRequestDTO.builder()
                .id("id")
                .username("username")
                .email("email")
                .password("password")
                .build();

        User user =  User.builder()
                .id("id")
                .build();

        when(requestAccessToken.getUserID())
                .thenReturn("id");
        when(userRepository.findUserByUsername("username"))
                .thenReturn(user);
        when(userRepository.findUserByEmail("email"))
                .thenReturn(User.builder().id("WrongID").build());

        assertThrows(EmailAlreadyExistsException.class, () -> updateCustomerUseCase.updateCustomer(requestDTO));

        verify(requestAccessToken).getUserID();
        verify(userRepository,times(2)).findUserByUsername("username");
        verify(userRepository,times(2)).findUserByEmail("email");

    }

    @Test
    void updateCustomer_CustomerNotFound() {
        UpdateCustomerRequestDTO requestDTO = UpdateCustomerRequestDTO.builder()
                .id("id")
                .username("username")
                .email("email")
                .password("password")
                .build();

      User user =  User.builder()
              .id("id")
              .build();

        when(requestAccessToken.getUserID())
                .thenReturn("id");
        when(userRepository.findUserByUsername("username"))
                .thenReturn(user);
        when(userRepository.findUserByEmail("email"))
                .thenReturn(user);

        assertThrows(CustomerNotFoundException.class, () -> updateCustomerUseCase.updateCustomer(requestDTO));

        verify(requestAccessToken).getUserID();
        verify(userRepository,times(2)).findUserByUsername("username");
        verify(userRepository,times(2)).findUserByEmail("email");
        verify(userRepository).findById("id");
    }

    @Test
    void updateCustomer_PermissionNotCustomer() {
        UpdateCustomerRequestDTO requestDTO = UpdateCustomerRequestDTO.builder()
                .id("id")
                .username("username")
                .email("email")
                .password("password")
                .build();

        User user = User.builder()
                .id("id")
                .username("creatorName")
                .email("email")
                .password("password")
                .roles(List.of("Customer"))
                .permission("permission")
                .build();

        when(requestAccessToken.getUserID())
                .thenReturn("id");
        when(userRepository.findUserByUsername("username"))
                .thenReturn(user);
        when(userRepository.findUserByEmail("email"))
                .thenReturn(user);
         when(userRepository.findById("id"))
                .thenReturn(Optional.ofNullable(user));

        assertThrows(CustomerNotFoundException.class, () -> updateCustomerUseCase.updateCustomer(requestDTO));

        verify(requestAccessToken).getUserID();
        verify(userRepository,times(2)).findUserByUsername("username");
        verify(userRepository,times(2)).findUserByEmail("email");
        verify(userRepository).findById("id");

    }

    @Test
    void updateCustomer_RolesNotCustomer() {
        UpdateCustomerRequestDTO requestDTO = UpdateCustomerRequestDTO.builder()
                .id("id")
                .username("username")
                .email("email")
                .password("password")
                .build();

        User user = User.builder()
                .id("id")
                .username("creatorName")
                .email("email")
                .password("password")
                .roles(List.of("roles"))
                .permission("Customer")
                .build();

        when(requestAccessToken.getUserID())
                .thenReturn("id");
        when(userRepository.findUserByUsername("username"))
                .thenReturn(user);
        when(userRepository.findUserByEmail("email"))
                .thenReturn(user);
        when(userRepository.findById("id"))
                .thenReturn(Optional.ofNullable(user));

        assertThrows(CustomerNotFoundException.class, () -> updateCustomerUseCase.updateCustomer(requestDTO));

        verify(requestAccessToken).getUserID();
        verify(userRepository,times(2)).findUserByUsername("username");
        verify(userRepository,times(2)).findUserByEmail("email");
        verify(userRepository).findById("id");

    }

    @Test
    void updateCustomer() {
        mockStatic(CustomerDTOConverter.class);

        UpdateCustomerRequestDTO requestDTO = UpdateCustomerRequestDTO.builder()
                .id("id")
                .username("username")
                .email("email")
                .password("password")
                .build();

        User user = User.builder()
                .id("id")
                .username("creatorName")
                .email("email")
                .password("password")
                .roles(List.of("Customer"))
                .permission("Customer")
                .build();

        User newUser = CustomerDTOConverter.convertToEntity(requestDTO,user);


        when(requestAccessToken.getUserID())
                .thenReturn("id");
        when(userRepository.findUserByUsername("username"))
                .thenReturn(user);
        when(userRepository.findUserByEmail("email"))
                .thenReturn(user);
        when(userRepository.findById("id"))
                .thenReturn(Optional.ofNullable(user));
        when(CustomerDTOConverter.convertToEntity(requestDTO,user))
                .thenReturn(newUser);

        updateCustomerUseCase.updateCustomer(requestDTO);

        verify(requestAccessToken).getUserID();
        verify(userRepository,times(2)).findUserByUsername("username");
        verify(userRepository,times(2)).findUserByEmail("email");
        verify(userRepository).findById("id");
        verify(userRepository).save(newUser);
    }
}