package s3_gps_ivanti.repository.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatingAnalyticsTest {

    @Test
    void avgStar() {
        RatingAnalytics analytics = RatingAnalytics.builder()
                .oneStar(2)
                .twoStar(2)
                .threeStar(1)
                .fourStar(2)
                .fiveStar(1)
                .build();

        assertEquals(analytics.avgStar(),2.75);
    }

    @Test
    void total() {
        RatingAnalytics analytics = RatingAnalytics.builder()
                .oneStar(2)
                .twoStar(2)
                .threeStar(1)
                .fourStar(2)
                .fiveStar(1)
                .build();

        assertEquals(analytics.totalAmount(),8);
    }
}