package whyzpotato.myreview;

import whyzpotato.myreview.domain.ReviewStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CommonUtils {
    public static LocalDate toLocalDate(String date){
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }
    public static ReviewStatus toReviewStatus(String status){
        switch(status) {
            case "WATCHING" :
                return ReviewStatus.WATCHING;
            case "DONE" :
                return ReviewStatus.DONE;
            case "LIKE":
                return ReviewStatus.LIKE;
        }
        throw new IllegalStateException();
    }
}
