package s3_gps_ivanti.business.dtoconvertor;

import s3_gps_ivanti.dto.version.*;
import s3_gps_ivanti.repository.entity.Version;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VersionDTOConverter {

    private VersionDTOConverter(){}

    public static Version convertToEntityForCreate(CreateMajorVersionRequestDTO majorVersionRequestDTO){
        return Version.builder()
                .number(majorVersionRequestDTO.getNumber())
                .totalReviews(0)
                .totalDownloads(0)
                .appLocation(majorVersionRequestDTO.getAppLocation())
                .build();
    }
    public static Version convertToEntityForCreate(CreateMinorVersionRequestDTO minorVersionRequestDTO){
        return Version.builder()
                .number(minorVersionRequestDTO.getNumber())
                .totalReviews(0)
                .totalDownloads(0)
                .appLocation(minorVersionRequestDTO.getAppLocation())
                .build();
    }

    public static CreateMajorVersionResponseDTO convertToDTOForMajorResponse(Version version){
        return CreateMajorVersionResponseDTO.builder()
                .number(version.getNumber())
                .build();
    }
    public static CreateMinorVersionResponseDTO convertToDTOForMinorResponse(Version version){
        return CreateMinorVersionResponseDTO.builder()
                .number(version.getNumber())
                .build();
    }

    public static Version convertToEntityForUpdate(UpdateVersionRequestDTO updateVersionRequestDTO, Version oldVersion){
        return Version.builder()
                .number(updateVersionRequestDTO.getNumber())
                .totalDownloads(oldVersion.getTotalDownloads())
                .totalReviews(oldVersion.getTotalReviews())
                .appLocation(updateVersionRequestDTO.getAppLocation())
                .build();
    }
    public static VersionDTO convertToDTO(Version version){
        return VersionDTO.builder()
                .number(version.getNumber())
                .totalDownloads(version.getTotalDownloads())
                .totalReviews(version.getTotalReviews())
                .appLocation(version.getAppLocation())
                .build();
    }
    public static List<VersionDTO> convertToListOfDTO(List<Version> versions) {

        List<VersionDTO> result = new ArrayList<>();
        for (Version v :versions) {
            result.add(convertToDTO(v));
        }
        return result;
    }

    private static VersionAnalyticsDTO convertToVersionAnalytics(Version version){
        return VersionAnalyticsDTO.builder()
                .number(version.getNumber())
                .totalDownloads(version.getTotalDownloads())
                .totalReviews(version.getTotalReviews())
                .build();
    }

    public static List<VersionAnalyticsDTO> convertToVersionAnalyticsList(List<Version> versions){
        List<VersionAnalyticsDTO> result = new ArrayList<>();
        for (Version v :versions) {
            result.add(convertToVersionAnalytics(v));
        }
        return result;
    }
}
