package s3_gps_ivanti.business.user;

import s3_gps_ivanti.dto.user.UserBasicInfoDTO;

import java.util.List;

public interface GetCustomersUseCase {

    List<UserBasicInfoDTO> getAllCustomers();
}
