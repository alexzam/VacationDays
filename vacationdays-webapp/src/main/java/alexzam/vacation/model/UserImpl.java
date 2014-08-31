package alexzam.vacation.model;

import alexzam.vacation.dto.FullInfo;
import org.joda.time.LocalDate;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class UserImpl implements User {
    private LocalDate lastKnownDate;
    private int lastKnownValue;
    private int currentNum;

    private List<Vacation> vacations = new ArrayList<>();

    public FullInfo generateDto() {
        FullInfo info = new FullInfo();

        info.setLastKnownDate(lastKnownDate);
        info.setLastKnownValue(lastKnownValue);
        info.setVacations(new ArrayList<>(vacations));
        info.setCurrentNum(currentNum);

        return info;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

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
