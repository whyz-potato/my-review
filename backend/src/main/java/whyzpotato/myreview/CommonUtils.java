package whyzpotato.myreview;

import org.hibernate.proxy.HibernateProxy;
import whyzpotato.myreview.domain.ReviewStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.Math.min;

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

    public static int startToInt(String start) {
        try {
            Integer number = Integer.valueOf(start);
            return min(1000, number);
        } catch (NumberFormatException ex) {
            return 1;
        }
    }

    public static int displayToInt(String display) {
        try {
            Integer number = Integer.valueOf(display);
            return min(100, number);
        } catch (NumberFormatException ex) {
            return 10;
        }
    }

    public static <T> T unProxy(Object entity) {
        if(entity instanceof HibernateProxy){
            entity = ((HibernateProxy) entity)
                    .getHibernateLazyInitializer()
                    .getImplementation();
        }
        return (T) entity;
    }


}
