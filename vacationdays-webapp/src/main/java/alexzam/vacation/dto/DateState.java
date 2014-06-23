package alexzam.vacation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.LocalDate;

@SuppressWarnings("UnusedDeclaration")
public class DateState {
    @JsonProperty("dt")
    private LocalDate date;

    @JsonProperty("n")
    private Integer number;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
