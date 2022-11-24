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
import s3_gps_ivanti.business.review.UpdateReviewUseCase;
import s3_gps_ivanti.dto.response.ReplyDTO;
import s3_gps_ivanti.dto.review.CreateUpdateDeleteReplyDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ReplyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UpdateReviewUseCase updateReviewUseCase;

    @Test
    @WithMockUser(username = "Yellow", roles = {"Creator"})
    void addReplyToReview() throws Exception{
        mockMvc.perform(put("/reply")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "id": "1",
                                    "reply": {
                                        "title": "yolo",
                                        "description": "yolo"
                                    }
                                }
                                """))
                .andDo(print())
                .andExpect(status().isNoContent());

        CreateUpdateDeleteReplyDTO replyDTO = CreateUpdateDeleteReplyDTO.builder()
                .id("1")
                .reply(ReplyDTO.builder()
                        .title("yolo")
                        .description("yolo")
                        .build())
                .build();

        verify(updateReviewUseCase).replyAction(replyDTO);
    }
}