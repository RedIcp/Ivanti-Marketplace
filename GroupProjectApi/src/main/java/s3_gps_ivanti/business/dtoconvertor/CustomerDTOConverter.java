package s3_gps_ivanti.business.dtoconvertor;

import org.apache.commons.lang3.RandomStringUtils;
import s3_gps_ivanti.dto.user.*;
import s3_gps_ivanti.repository.entity.User;

import java.util.ArrayList;
import java.util.List;

public class CustomerDTOConverter {
    private CustomerDTOConverter(){}

    public static User convertToEntity(CreateCustomerRequestDTO customerRequestDTO) {
        return User.builder()
                .id(RandomStringUtils.randomAlphabetic(23))
                .username(customerRequestDTO.getUsername())
                .email(customerRequestDTO.getEmail())
                .password(customerRequestDTO.getPassword())
                .roles(List.of("Customer"))
                .permission("Customer")
                .build();
    }
    public static User convertToEntity(UpdateCustomerRequestDTO customerRequestDTO, User oldCustomer) {
        return User.builder()
                .id(customerRequestDTO.getId())
                .username(customerRequestDTO.getUsername())
                .email(customerRequestDTO.getEmail())
                .password(customerRequestDTO.getPassword())
                .roles(oldCustomer.getRoles())
                .permission(oldCustomer.getPermission())
                .build();
    }
    private static UserBasicInfoDTO convertToDTO(User user) {
        return UserBasicInfoDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
    public static List<UserBasicInfoDTO> convertToListDTO(List<User> all) {
        List<UserBasicInfoDTO> result = new ArrayList<>();

        for (User user: all) {
            result.add(CustomerDTOConverter.convertToDTO(user));
        }

        return result;
    }
    public static CreateCustomerResponseDTO convertToDTOCreateResponse(User user) {
        return CreateCustomerResponseDTO.builder()
                .username(user.getUsername())
                .build();
    }
    public static CustomerDetailedInfoDTO convertToDetailedDTO(User result) {
        return CustomerDetailedInfoDTO.builder()
                .username(result.getUsername())
                .email(result.getEmail())
                .permission(result.getPermission())
                .applications(ApplicationDTOConverter.convertListToDTO(result.getApplications()))
                .build();
    }
}
