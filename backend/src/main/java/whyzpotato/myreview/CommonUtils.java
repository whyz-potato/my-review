package whyzpotato.myreview;

import org.hibernate.proxy.HibernateProxy;
import whyzpotato.myreview.domain.ReviewStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import static java.lang.Math.min;

public class CommonUtils {

    public static LocalDate toLocalDate(String date) {
        if (date.length() == 4)
            return LocalDate.parse(date, new DateTimeFormatterBuilder()
                    .appendPattern("yyyy")
                    .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                    .toFormatter());
        else if (date.length() == 8)
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static ReviewStatus toReviewStatus(String status) {
        switch (status) {
            case "WATCHING":
                return ReviewStatus.WATCHING;
            case "DONE":
                return ReviewStatus.DONE;
            case "LIKE":
                return ReviewStatus.LIKE;
        }
        throw new IllegalStateException();
    }

    public static int startToInt(String start, int defaultValue) {
        try {
            Integer number = Integer.valueOf(start);
            return min(1000, number);
        } catch (NumberFormatException ex) {
            return defaultValue;
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
        if (entity instanceof HibernateProxy) {
            entity = ((HibernateProxy) entity)
                    .getHibernateLazyInitializer()
                    .getImplementation();
        }
        return (T) entity;
    }


}
