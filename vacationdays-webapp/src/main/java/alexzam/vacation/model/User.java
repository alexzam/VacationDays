package alexzam.vacation.model;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class User {
    private LocalDate lastKnownDate;
    private int lastKnownValue;

    private List<Vacation> vacations = new ArrayList<Vacation>();

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
}
