package s3_gps_ivanti.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingAnalytics {
    private int oneStar;
    private int twoStar;
    private int threeStar;
    private int fourStar;
    private int fiveStar;

    public double totalAmount(){
        return oneStar+twoStar+threeStar+fourStar+fiveStar;
    }

    public double avgStar(){
        if(totalAmount()!=0) {
            return (oneStar + 2 * twoStar + 3 * threeStar + 4 * fourStar + 5 * fiveStar) / totalAmount();
        }
        return 0;
    }
}
