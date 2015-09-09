package alexzam.vacation.model;

import alexzam.vacation.dto.FullInfo;
import com.google.appengine.api.datastore.Entity;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class UserImpl implements User {
    private LocalDate lastKnownDate;
    private int lastKnownValue;
    private int currentNum;

    private List<Vacation> vacations = new ArrayList<>();
    private String googleId;

    @Autowired
    private VacationComparator vacationComparator;


    public UserImpl(Entity entity) {
        lastKnownDate = new LocalDate(entity.getProperty("lastDate"));
        lastKnownValue = (int) entity.getProperty("lastValue");
        googleId = (String) entity.getProperty("googleId");
    }

    public UserImpl() {
    }

    @Override
    public Entity toEntity() {
        Entity entity = new Entity("User");

        entity.setUnindexedProperty("lastDate", lastKnownDate.toDate());
        entity.setUnindexedProperty("lastValue", lastKnownValue);
        entity.setProperty("googleId", googleId);

        return entity;
    }

    @Override
    public void copyFrom(User otherUser) {
        lastKnownDate = otherUser.getLastKnownDate();
        lastKnownValue = otherUser.getLastKnownValue();
        for (Vacation vacation : otherUser.getVacations()) {
            if(!vacations.contains(vacation)) vacations.add(vacation);
        }
        vacations.sort(vacationComparator);
    }

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

    @Override
    public void setGoogleId(String id) {
        googleId = id;
    }

    public String getGoogleId() {
        return googleId;
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
