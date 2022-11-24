package s3_gps_ivanti.business.user;

import s3_gps_ivanti.dto.user.CustomerDetailedInfoDTO;

public interface GetCustomerUseCase {
    CustomerDetailedInfoDTO getCustomer(String username);
}
