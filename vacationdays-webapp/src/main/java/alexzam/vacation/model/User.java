package alexzam.vacation.model;

import alexzam.vacation.dto.FullInfo;
import com.google.appengine.api.datastore.Entity;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AlexZam on 24.06.2014.
 */
public interface User extends Serializable {
    LocalDate getLastKnownDate();

    void setLastKnownDate(LocalDate lastKnownDate);

    int getLastKnownValue();

    void setLastKnownValue(int lastKnownValue);

    List<Vacation> getVacations();

    FullInfo generateDto();

    void setCurrentNum(int i);

    void setGoogleId(String id);

    Entity toEntity();

    void copyFrom(User storedUser);
}
