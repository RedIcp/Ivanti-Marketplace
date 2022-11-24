package s3_gps_ivanti.business.user;

import s3_gps_ivanti.dto.user.CreateCustomerRequestDTO;
import s3_gps_ivanti.dto.user.CreateCustomerResponseDTO;

public interface CreateCustomerUseCase {
    CreateCustomerResponseDTO createCustomer(CreateCustomerRequestDTO customerRequestDTO);
}
