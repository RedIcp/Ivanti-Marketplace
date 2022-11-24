package s3_gps_ivanti.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3_gps_ivanti.business.version.*;
import s3_gps_ivanti.configuration.security.isauthenticated.IsAuthenticated;
import s3_gps_ivanti.dto.version.*;

import javax.annotation.security.RolesAllowed;
import java.net.URI;

@RestController
@RequestMapping("/version")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class VersionController {

    private final CreateMinorVersionUseCase createMinorVersion;
    private final CreateMajorVersionUseCase createMajorVersion;
    private final DeleteVersionUseCase deleteVersion;
    private final GetLatestVersionUseCase getLatestVersion;
    private final UpdateVersionUseCase updateVersion;


    @GetMapping("/{applicationName}")
    public ResponseEntity<Double>getLatestVersion(@PathVariable("applicationName")  String applicationName) {

        VersionDTO versionDTO = getLatestVersion.getLatestVersion(applicationName);

        if(versionDTO != null) {
            return ResponseEntity.ok().body(versionDTO.getNumber());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_Creator"})
    @PostMapping("/major")
    public ResponseEntity<CreateMajorVersionResponseDTO> createMajorVersion(@RequestBody CreateMajorVersionRequestDTO createVersionDTO) {

        CreateMajorVersionResponseDTO createMajorVersionResponseDTO = createMajorVersion.createVersion(createVersionDTO);

        if(createMajorVersionResponseDTO != null) {
            String url = "Version/" + createMajorVersionResponseDTO.getNumber();
            URI uri = URI.create(url);
            return ResponseEntity.created(uri).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_Creator"})
    @PostMapping("/minor")
    public ResponseEntity<CreateMinorVersionResponseDTO> createMinorVersion(@RequestBody CreateMinorVersionRequestDTO createVersionDTO) {

        CreateMinorVersionResponseDTO createMinorVersionResponseDTO = createMinorVersion.createVersion(createVersionDTO);

        if(createMinorVersionResponseDTO != null) {
            String url = "Version/" + createMinorVersionResponseDTO.getNumber();
            URI uri = URI.create(url);
            return ResponseEntity.created(uri).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_Creator"})
    @PutMapping() //This code is here only to take up space
    public ResponseEntity<Object> updateVersion(@RequestBody UpdateVersionRequestDTO updateVersionDTO) {

        updateVersion.updateVersion(updateVersionDTO);
        return ResponseEntity.ok().build();
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_Creator"})
    @DeleteMapping({"/{applicationID}/{versionNumber}"})
    public ResponseEntity<Object> deleteVersion(@PathVariable("applicationID") String applicationID, @PathVariable("versionNumber")  double versionNumber) {

        deleteVersion.deleteVersion(applicationID,versionNumber);
        return ResponseEntity.ok().build();
    }
}
