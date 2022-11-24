package s3_gps_ivanti.business.validation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.exception.ApplicationNotFoundException;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.entity.Application;

@Service
@AllArgsConstructor
public class ApplicationNameValidation {
    private ApplicationRepository applicationRepository;

    public Application applicationIsValid(String name){
        if(!applicationRepository.existsByName(name)){
            throw new ApplicationNotFoundException();
        }
        else
            return applicationRepository.findByName(name);
    }
}
