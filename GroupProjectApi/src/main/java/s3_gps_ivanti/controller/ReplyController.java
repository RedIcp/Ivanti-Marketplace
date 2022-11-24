package s3_gps_ivanti.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3_gps_ivanti.business.review.UpdateReviewUseCase;
import s3_gps_ivanti.configuration.security.isauthenticated.IsAuthenticated;
import s3_gps_ivanti.dto.review.CreateUpdateDeleteReplyDTO;

import javax.annotation.security.RolesAllowed;


@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class ReplyController {
    private final UpdateReviewUseCase updateReviewService;

    @PutMapping()
    public ResponseEntity<Object> replyAction(@RequestBody CreateUpdateDeleteReplyDTO request){
        updateReviewService.replyAction(request);
        return ResponseEntity.noContent().build();
    }
}
