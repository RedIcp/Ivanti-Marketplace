package s3_gps_ivanti.controller;


import lombok.RequiredArgsConstructor;

import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3_gps_ivanti.business.application.*;
import s3_gps_ivanti.configuration.security.isauthenticated.IsAuthenticated;
import s3_gps_ivanti.dto.application.*;
import s3_gps_ivanti.dto.creator.CreatorApplicationListDTO;
import s3_gps_ivanti.business.application.impl.DriveQuickstart;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.io.*;
import java.security.GeneralSecurityException;


@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class ApplicationController {

    private final CreateApplicationUseCase createApplications;
    private final DeleteApplicationUseCase deleteApplications;
    private final GetApplicationDetailedInfoUseCase getApplicationDetailedInfo;
    private final GetApplicationsBasicInfoUseCase getApplicationsBasicInfo;
    private final GetApplicationByCreatorUseCase getApplicationByCreator;
    private final UpdateApplicationUseCase updateApplication;


    //All
    @GetMapping("/{applicationName}")
    public ResponseEntity<ApplicationDetailedInfoDTO> getApplicationDetails(@PathVariable("applicationName") String applicationName) {

        ApplicationDetailedInfoDTO applicationDetailedInfoDTO = getApplicationDetailedInfo.getApplicationInfo(applicationName);

        if (applicationDetailedInfoDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(applicationDetailedInfoDTO);
    }

    @GetMapping(path = "/download/{fileId}/{appName}")
    public ResponseEntity<DownloadApplicationResponseDTO> downloadApplication(@PathVariable("fileId")String fileId, @PathVariable("appName")String appName) throws GeneralSecurityException, IOException{



        DriveQuickstart dq = new DriveQuickstart();
        String home = System.getProperty("user.home");
        String path = home + "/Downloads" + "/" + appName + ".png";
        byte[] arrayOfBytes = dq.downloadApplication(path, fileId);
        DownloadApplicationResponseDTO downloadApplicationResponseDTO = new DownloadApplicationResponseDTO(arrayOfBytes);

        System.out.println("Success");

        return ResponseEntity.ok().body(downloadApplicationResponseDTO);

    }

    @GetMapping
    public ResponseEntity<GetAllApplicationsResponseDTO> getAllApplications(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "sort", required = false) String sort) {
        GetAllApplicationsRequestDTO request = new GetAllApplicationsRequestDTO();
        request.setName(name);
        request.setSort(sort);
        return ResponseEntity.ok(getApplicationsBasicInfo.getAllApplications(request));
    }

    //Content Creator
    @IsAuthenticated
    @RolesAllowed({"ROLE_Creator"})
    @GetMapping("/creator/{username}")
    public ResponseEntity<CreatorApplicationListDTO> getApplicationsByCreator(@PathVariable("username") String username) {
        CreatorApplicationListDTO creatorApplicationListDTO = getApplicationByCreator.getApplicationsByCreator(username);

        if (creatorApplicationListDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(creatorApplicationListDTO);
    }

    //Content Creator

    @IsAuthenticated
    @RolesAllowed({"ROLE_Creator"})
    @PostMapping()
    public ResponseEntity<CreateApplicationResponseDTO> createApplications(@RequestBody CreateApplicationRequestDTO application) {

        CreateApplicationResponseDTO createApplicationResponseDTO = createApplications.createApplications(application);

        if (createApplicationResponseDTO == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            String url = "Application/" + createApplicationResponseDTO.getId();
            URI uri = URI.create(url);
            return ResponseEntity.created(uri).build();
        }
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_Creator"})
    @PutMapping()
    public ResponseEntity<Object> updateApplications(@RequestBody UpdateApplicationRequestDTO application) {
        updateApplication.updateApplications(application);
        return ResponseEntity.noContent().build();
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_Creator"})
    @PutMapping({"{name}"})
    public ResponseEntity<Object> deleteApplications(@PathVariable("name") String name) {

        deleteApplications.deleteApplications(name);
        return ResponseEntity.ok().build();
    }
}
