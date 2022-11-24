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
import s3_gps_ivanti.business.review.CreateReviewUseCase;
import s3_gps_ivanti.business.review.DeleteReviewUseCase;
import s3_gps_ivanti.business.review.UpdateReviewUseCase;
import s3_gps_ivanti.dto.review.CreateReviewRequestDTO;
import s3_gps_ivanti.dto.review.CreateReviewResponseDTO;
import s3_gps_ivanti.dto.review.UpdateReviewDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateReviewUseCase createReviewService;
    @MockBean
    private DeleteReviewUseCase deleteReviewService;
    @MockBean
    private UpdateReviewUseCase updateReviewService;

    @Test
    @WithMockUser(username = "Yellow", roles = {"Customer"})
    void createReview() throws Exception{
        CreateReviewRequestDTO review = CreateReviewRequestDTO.builder()
                .applicationName("app")
                .customerName("customer")
                .description("desc")
                .rating(2)
                .title("title")
                .build();

        CreateReviewResponseDTO response = CreateReviewResponseDTO.builder()
                .id("1")
                .build();

        when(createReviewService.createReview(review)).thenReturn(response);

        mockMvc.perform(post("/review")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "customerName": "customer",
                                    "applicationName": "app",
                                    "rating": 2,
                                    "title": "title",
                                    "description": "desc"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            {
                                    "id": "1"
                            }
                        """));

        verify(createReviewService).createReview(review);
    }

    @Test
    @WithMockUser(username = "Yellow", roles = {"Customer"})
    void updateReview() throws Exception{
        mockMvc.perform(put("/review")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "id": "1",
                                    "rating": 1,
                                    "title": "title",
                                    "description": "desc"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isNoContent());

        UpdateReviewDTO review = UpdateReviewDTO.builder()
                .id("1")
                .rating(1)
                .description("desc")
                .title("title")
                .build();

        verify(updateReviewService).updateReview(review);
    }

    @Test
    @WithMockUser(username = "Yellow", roles = {"Customer"})
    void deleteReview() throws Exception{
        mockMvc.perform(delete("/review/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(deleteReviewService).deleteReview("1");
    }
}