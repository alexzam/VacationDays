package alexzam.vacation.service;

import alexzam.vacation.dto.DateState;
import alexzam.vacation.model.User;
import org.springframework.stereotype.Service;

@Service
public class VacDaysService {
    public User setInitialDate(User user, DateState state) {
        if(user == null) user = new User();

        user.setLastKnownDate(state.getDate());
        user.setLastKnownValue(state.getNumber());

        return user;
    }
}
