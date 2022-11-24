package s3_gps_ivanti.business.dtoconvertor;

import s3_gps_ivanti.dto.response.ReplyDTO;
import s3_gps_ivanti.repository.entity.Reply;

public class ReplyDTOConverter {
    private ReplyDTOConverter(){}

    public static Reply convertToEntity(ReplyDTO dto){
        if(dto!=null){
            return Reply.builder()
                    .title(dto.getTitle())
                    .description(dto.getDescription())
                    .build();
        }
        else return null;
    }
    public static ReplyDTO convertToDTO(Reply reply){
        if(reply==null){
            return null;
        }
        return ReplyDTO.builder()
                .title(reply.getTitle())
                .description(reply.getDescription())
                .build();
    }
}
