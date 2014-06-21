package alexzam.vacation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.LocalDate;

@SuppressWarnings("UnusedDeclaration")
public class DateState {
    @JsonProperty("dt")
    private LocalDate date;

    @JsonProperty("n")
    private String number;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
