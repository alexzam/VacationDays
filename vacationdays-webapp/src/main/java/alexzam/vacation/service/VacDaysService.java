package alexzam.vacation.service;

import alexzam.vacation.dto.DateState;
import alexzam.vacation.model.User;
import alexzam.vacation.model.UserImpl;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
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

        updateUserCurrentNum(user);

        return user;
    }

    private void updateUserCurrentNum(User user) {
        user.setCurrentNum(calculateNumber(user, new LocalDate()));
    }

    private int calculateNumber(User user, LocalDate date) {
        LocalDate start = user.getLastKnownDate();
        LocalDate toMonth = date.withDayOfMonth(start.getDayOfMonth());
        if (toMonth.isAfter(date)) toMonth = toMonth.minusMonths(1);

        int months = Months.monthsBetween(start, date).getMonths();
        int days = Days.daysBetween(toMonth, date).getDays();
        double monthVal = 28.0 / 12;

        return (int) Math.round(monthVal * months + monthVal * days / 30);
    }
}
