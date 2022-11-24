package s3_gps_ivanti.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import s3_gps_ivanti.dto.Message;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate simplMessagingTemplate;

    @MessageMapping("/message") //pulls
    @SendTo("/notifications/messages") //push
    public String receiveMessage(@Payload final String message) {
        return message;
    }

    @MessageMapping("/private-message") // private function
    public Message recMessage(@Payload Message message) {
        simplMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        System.out.println(message.toString());
        return message;
    }
}