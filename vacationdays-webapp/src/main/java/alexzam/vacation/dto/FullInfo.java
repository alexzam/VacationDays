package alexzam.vacation.dto;

import alexzam.vacation.model.Vacation;
import org.joda.time.LocalDate;

import java.util.List;

public class FullInfo {
    private LocalDate lastKnownDate;
    private int lastKnownValue;

    private List<Vacation> vacations;

    public LocalDate getLastKnownDate() {
        return lastKnownDate;
    }

    public void setLastKnownDate(LocalDate lastKnownDate) {
        this.lastKnownDate = lastKnownDate;
    }

    public int getLastKnownValue() {
        return lastKnownValue;
    }

    public void setLastKnownValue(int lastKnownValue) {
        this.lastKnownValue = lastKnownValue;
    }

    public List<Vacation> getVacations() {
        return vacations;
    }

    public void setVacations(List<Vacation> vacations) {
        this.vacations = vacations;
    }
}
