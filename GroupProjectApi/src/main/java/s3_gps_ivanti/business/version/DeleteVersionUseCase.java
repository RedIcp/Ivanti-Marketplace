package s3_gps_ivanti.business.version;

public interface DeleteVersionUseCase {
    void deleteVersion(String applicationID, double number);
}
