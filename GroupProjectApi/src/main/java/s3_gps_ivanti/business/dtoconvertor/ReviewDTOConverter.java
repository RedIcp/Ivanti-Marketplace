package s3_gps_ivanti.business.dtoconvertor;

import s3_gps_ivanti.dto.review.CreateReviewRequestDTO;
import s3_gps_ivanti.dto.review.ReviewDTO;
import s3_gps_ivanti.dto.review.UpdateReviewDTO;
import s3_gps_ivanti.repository.entity.Review;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReviewDTOConverter {
    private ReviewDTOConverter() {
    }

    public static List<ReviewDTO> convertToListOfDTO(List<Review> reviews) {
        List<ReviewDTO> result = new ArrayList<>();

        for (Review r : reviews) {
            result.add(ReviewDTOConverter.convertToDTOForView(r));
        }

        return result;
    }

    private static ReviewDTO convertToDTOForView(Review r) {
        String timePassed = timePassed(r.getDate(), LocalDateTime.now());

        return ReviewDTO.builder()
                .id(r.getId())
                .customerName(r.getCustomer().getUsername())
                .rating(r.getRating())
                .title(r.getTitle())
                .description(r.getDescription())
                .timePassed(timePassed)
                .reply(ReplyDTOConverter.convertToDTO(r.getReply()))
                .build();
    }

    public static UpdateReviewDTO convertToDTOForUpdate(Review r) {
        if (r != null) {
            return UpdateReviewDTO.builder()
                    .id(r.getId())
                    .rating(r.getRating())
                    .title(r.getTitle())
                    .description(r.getDescription())
                    .build();
        }
        return null;
    }

    public static Review convertToEntityCreate(CreateReviewRequestDTO review) {
        return Review.builder()
                .applicationName(review.getApplicationName())
                .rating(review.getRating())
                .title(review.getTitle())
                .description(review.getDescription())
                .reply(null)
                .build();
    }

    private static String timePassed(LocalDateTime start, LocalDateTime end) {
        long seconds = ChronoUnit.SECONDS.between(start, end);
        long minutes = ChronoUnit.MINUTES.between(start, end);
        long hours = ChronoUnit.HOURS.between(start, end);
        long days = ChronoUnit.DAYS.between(start, end);
        long months = ChronoUnit.MONTHS.between(start, end);
        long years = ChronoUnit.YEARS.between(start, end);

        if (seconds < 0) {
            return "just now";
        }
        if (seconds == 1) {
            return "one sec ago";
        }
        if (seconds > 1 && seconds < 60) {
            return seconds + "secs ago";
        }
        if (seconds > 60) {
            if (minutes == 1) {
                return "one min ago";
            }
            if (minutes > 1 && minutes < 60) {
                return minutes + "mins ago";
            }
            if (minutes > 60) {
                if (hours == 1) {
                    return "one hr ago";
                }
                if (hours > 1 && hours < 24) {
                    return hours + "hrs ago";
                }
                if (hours > 24) {
                    if (days == 1) {
                        return "one day ago";
                    }
                    if (days > 1 && days < 30) {
                        return days + "days ago";
                    }
                    if (days > 30) {
                        if (months == 1) {
                            return "one month ago";
                        }
                        if (months > 1 && months < 12) {
                            return months + "months ago";
                        }
                        if (months > 12) {
                            if (years == 1) {
                                return "one yr ago";
                            }
                            if (years > 1) {
                                return years + "yrs ago";
                            }
                        }
                    }
                }
            }
        }
        return "";
    }
}
