package s3_gps_ivanti.business.dtoconvertor;

import org.junit.jupiter.api.Test;
import s3_gps_ivanti.dto.response.ReplyDTO;
import s3_gps_ivanti.repository.entity.Reply;

import static org.junit.jupiter.api.Assertions.*;

class ReplyDTOConverterTest {

    @Test
    void convertToDTOReturnNull() {
        Reply reply = null;
        ReplyDTO actual = ReplyDTOConverter.convertToDTO(reply);

        assertEquals(actual, null);
    }

    @Test
    void convertToDTO() {
        Reply reply = Reply.builder()
                .title("title")
                .description("description")
                .build();
        ReplyDTO actual = ReplyDTOConverter.convertToDTO(reply);
        ReplyDTO expected = ReplyDTO.builder()
                .title("title")
                .description("description")
                .build();


        assertEquals(actual, expected);
    }
}