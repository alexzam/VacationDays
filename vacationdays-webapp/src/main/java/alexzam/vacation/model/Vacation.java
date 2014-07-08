package alexzam.vacation.model;

import com.google.appengine.repackaged.org.joda.time.Days;
import com.google.appengine.repackaged.org.joda.time.LocalDate;

public class Vacation {
    private LocalDate start, end;
    private String comment;

    public int getDuration() {
        return Days.daysBetween(start, end).getDays();
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
