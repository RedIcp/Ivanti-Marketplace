package s3_gps_ivanti.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import s3_gps_ivanti.business.version.*;
import s3_gps_ivanti.dto.version.CreateMinorVersionRequestDTO;
import s3_gps_ivanti.dto.version.UpdateVersionRequestDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class VersionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateMinorVersionUseCase createMinorVersion;
    @MockBean
    private CreateMajorVersionUseCase createMajorVersion;
    @MockBean
    private DeleteVersionUseCase deleteVersion;
    @MockBean
    private GetLatestVersionUseCase getLatestVersion;
    @MockBean
    private UpdateVersionUseCase updateVersion;

    @Test
    void getLatestVersion() {
    }

    @Test
    void createMajorVersion() {
    }

    @Test
    void createMinorVersion() {

    }

    @Test
    @WithMockUser(username = "Yellow", roles = {"Creator"})
    void updateVersion() throws Exception{
        mockMvc.perform(put("/version")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "applicationID": "1",
                                    "appLocation": "location",
                                    "number": 2.2
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk());

        UpdateVersionRequestDTO request = UpdateVersionRequestDTO.builder()
                .applicationID("1")
                .appLocation("location")
                .number(2.2)
                .build();

        verify(updateVersion).updateVersion(request);
    }

    @Test
    @WithMockUser(username = "Yellow", roles = {"Creator"})
    void deleteVersion() throws Exception{
        mockMvc.perform(delete("/version/1/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(deleteVersion).deleteVersion("1",1);
    }
}