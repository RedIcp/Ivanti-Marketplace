package s3_gps_ivanti.business.dtoconvertor;

import org.junit.jupiter.api.Test;
import s3_gps_ivanti.dto.application.ApplicationAnalyticsResponseDTO;
import s3_gps_ivanti.repository.entity.Application;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationDTOConverterTest {

    @Test
    void convertToDTOForAnalytics() {
        Application app = Application.builder()
                .name("app")
                .icon("icon")
                .build();

        ApplicationAnalyticsResponseDTO expected = ApplicationAnalyticsResponseDTO.builder()
                .name("app")
                .icon("icon")
                .build();
        ApplicationAnalyticsResponseDTO actual = ApplicationDTOConverter.convertToDTOForAnalytics(app);

        assertEquals(expected, actual);
    }
}