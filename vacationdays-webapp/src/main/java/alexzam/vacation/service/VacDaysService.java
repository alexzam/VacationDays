package alexzam.vacation.service;

import alexzam.vacation.dto.DateState;
import alexzam.vacation.model.User;
import alexzam.vacation.model.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacDaysService {
    @Autowired
    User user;

    public User setInitialDate(DateState state) {
        if (user == null) user = new UserImpl();

        user.setLastKnownDate(state.getDate());
        user.setLastKnownValue(state.getNumber());

        return user;
    }
}
