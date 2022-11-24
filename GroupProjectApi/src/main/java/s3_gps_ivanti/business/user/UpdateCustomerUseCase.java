package s3_gps_ivanti.business.user;

import s3_gps_ivanti.dto.user.UpdateCustomerRequestDTO;

public interface UpdateCustomerUseCase {

    void updateCustomer(UpdateCustomerRequestDTO customerRequestDTO);
}
