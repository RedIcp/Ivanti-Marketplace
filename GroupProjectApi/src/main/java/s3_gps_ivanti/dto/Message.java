package s3_gps_ivanti.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String senderName;
    private String receiverName;
    private String messageContent;
}
