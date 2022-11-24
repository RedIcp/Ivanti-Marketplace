package s3_gps_ivanti.business.review.impl;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.exception.InvalidRatingException;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.entity.Application;

import javax.transaction.Transactional;

@Service
@Primary
@AllArgsConstructor
@Transactional
public class UpdateRating {
    private ApplicationRepository applicationRepository;

    public void addAppRating(String appName, int rating){
        Application application = applicationRepository.findByName(appName);

        if(rating==1){
            application.getRating().setOneStar(application.getRating().getOneStar()+1);
        }
        else if(rating==2){
            application.getRating().setTwoStar(application.getRating().getTwoStar()+1);
        }
        else if(rating==3){
            application.getRating().setThreeStar(application.getRating().getThreeStar()+1);
        }
        else if(rating==4){
            application.getRating().setFourStar(application.getRating().getFourStar()+1);
        }
        else if(rating==5){
            application.getRating().setFiveStar(application.getRating().getFiveStar()+1);
        }
        else if(rating>5 || rating<0){
            throw new InvalidRatingException();
        }
        applicationRepository.save(application);
    }
    public void subtractAppRating(String appName, int rating){
        Application application = applicationRepository.findByName(appName);

        if(rating==1){
            application.getRating().setOneStar(application.getRating().getOneStar()-1);
        }
        else if(rating==2){
            application.getRating().setTwoStar(application.getRating().getTwoStar()-1);
        }
        else if(rating==3){
            application.getRating().setThreeStar(application.getRating().getThreeStar()-1);
        }
        else if(rating==4){
            application.getRating().setFourStar(application.getRating().getFourStar()-1);
        }
        else if(rating==5){
            application.getRating().setFiveStar(application.getRating().getFiveStar()-1);
        }
        else if(rating>5 || rating<0){
            throw new InvalidRatingException();
        }
        applicationRepository.save(application);
    }
}
