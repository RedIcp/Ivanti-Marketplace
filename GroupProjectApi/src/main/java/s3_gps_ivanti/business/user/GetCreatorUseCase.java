package s3_gps_ivanti.business.user;

import s3_gps_ivanti.repository.entity.User;

public interface GetCreatorUseCase {
    User getCreator(String name);
}
