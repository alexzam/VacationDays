package alexzam.vacation.model;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.io.Serializable;

public class Vacation implements Serializable {
    private int id;
    private LocalDate start;
    private LocalDate end;
    private String comment;

    public Vacation() {
    }

    public Vacation(int id, LocalDate start, LocalDate end, String comment) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.comment = comment;
    }

    public int getDuration() {
        return Days.daysBetween(start, end).getDays() + 1;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
