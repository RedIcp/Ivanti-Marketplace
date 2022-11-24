package s3_gps_ivanti.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import s3_gps_ivanti.business.application.GetApplicationsAnalyticsPerMonthUseCase;
import s3_gps_ivanti.business.user.*;
import s3_gps_ivanti.dto.application.ApplicationAnalyticsRequestDTO;
import s3_gps_ivanti.dto.application.ApplicationAnalyticsResponseDTO;
import s3_gps_ivanti.dto.user.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateCustomerUseCase createCustomerUseCase;
    @MockBean
    private DeleteCustomerUseCase deleteCustomerUseCase;
    @MockBean
    private GetCustomersUseCase getCustomersUseCase;
    @MockBean
    private GetCustomerUseCase getCustomerUseCase;
    @MockBean
    private UpdateCustomerUseCase updateCustomerUseCase;
    @MockBean
    private GetApplicationsAnalyticsPerMonthUseCase getApplicationsAnalyticsPerMonthUseCase;

    @Test
    @WithMockUser(username = "Yellow", roles = {"Customer"})
    void createUser() throws Exception{
        CreateCustomerRequestDTO customer = CreateCustomerRequestDTO.builder()
                .username("name")
                .email("email")
                .password("password")
                .build();

        CreateCustomerResponseDTO response = CreateCustomerResponseDTO.builder()
                .username("name")
                .build();

        when(createCustomerUseCase.createCustomer(customer)).thenReturn(response);

        mockMvc.perform(post("/user")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "username": "name",
                                    "email": "email",
                                    "password": "password"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {
                                    "username": "name"
                            }
                        """));

        verify(createCustomerUseCase).createCustomer(customer);
    }

    @Test
    @WithMockUser(username = "Yellow", roles = {"Queen"})
    void getUsers() throws Exception{
        UserBasicInfoDTO user = UserBasicInfoDTO.builder()
                .email("email")
                .username("username")
                .build();

        List<UserBasicInfoDTO> users = new ArrayList<>();
        users.add(user);

        when(getCustomersUseCase.getAllCustomers()).thenReturn(users);

        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            [
                                {
                                    "email": "email",
                                    "username": "username"
                                }                           
                            ]
                        """));

        verify(getCustomersUseCase).getAllCustomers();

    }

    @Test
    @WithMockUser(username = "Yellow", roles = {"Customer"})
    void getUser() throws Exception{
        CustomerDetailedInfoDTO user = CustomerDetailedInfoDTO.builder()
                .email("email")
                .permission("CUSTOMER")
                .applications(new ArrayList<>())
                .build();

        when(getCustomerUseCase.getCustomer("name")).thenReturn(user);

        mockMvc.perform(get("/user/name"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                                    "email": "email",
                                    "permission": "CUSTOMER",
                                    "applications": []
                            }                           
                        """));
    }

    @Test
    @WithMockUser(username = "Yellow", roles = {"Customer"})
    void updateCustomer() throws Exception{
        mockMvc.perform(put("/user")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "id": "1",
                                    "email": "email",
                                    "username": "username"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk());

        UpdateCustomerRequestDTO user = UpdateCustomerRequestDTO.builder()
                .id("1")
                .email("email")
                .username("username")
                .build();

        verify(updateCustomerUseCase).updateCustomer(user);
    }

    @Test
    @WithMockUser(username = "Yellow", roles = {"Customer"})
    void deleteUser() throws Exception{
        mockMvc.perform(delete("/user/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(deleteCustomerUseCase).DeleteCustomer("1");
    }

    @Test
    @WithMockUser(username = "Yellow", roles = {"Creator"})
    void getAppAnalytics() throws Exception{
        ApplicationAnalyticsResponseDTO analytic = ApplicationAnalyticsResponseDTO.builder()
                .icon("icon")
                .name("name")
                .downloads(new ArrayList<>())
                .reviews(new ArrayList<>())
                .totalDownloads(22)
                .totalReviews(22)
                .build();
        List<ApplicationAnalyticsResponseDTO> analytics = new ArrayList<>();
        analytics.add(analytic);

        when(getApplicationsAnalyticsPerMonthUseCase.getApplicationAnalytics(ApplicationAnalyticsRequestDTO.builder()
                .creatorName("username")
                .build())).thenReturn(analytics);

        mockMvc.perform(get("/user/username/statistics"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            [
                                {
                                    "icon": "icon",
                                    "name": "name",
                                    "downloads": [],
                                    "reviews": [],
                                    "totalDownloads": 22,
                                    "totalReviews": 22
                                }                           
                            ]
                        """));
    }
}