package alexzam.vacation.service;

import alexzam.vacation.dto.DateState;
import alexzam.vacation.model.User;
import alexzam.vacation.model.UserImpl;
import alexzam.vacation.model.Vacation;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

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

    public int calculateNumber(User user, LocalDate date) {
        LocalDate start = user.getLastKnownDate();
        LocalDate toMonth = date.withDayOfMonth(start.getDayOfMonth());
        if (toMonth.isAfter(date)) toMonth = toMonth.minusMonths(1);

        int months = Months.monthsBetween(start, date).getMonths();
        int days = Days.daysBetween(toMonth, date).getDays();
        double monthVal = 28.0 / 12;

        int ret = (int) Math.round(monthVal * months + monthVal * days / 30);

        for (Vacation vacation : user.getVacations()) {
            ret -= vacation.getDuration();
        }

        if (ret < 0) ret = 0;

        return ret;
    }

    public void setVacation(User user, Vacation vacation) {
        int id = vacation.getId();
        boolean newVac = vacation.getId() == 0;

        List<Vacation> vacations = user.getVacations();

        int maxId = 0;
        for (Vacation vac : vacations) {
            int curId = vac.getId();

            if (newVac) {
                // Find out max ID
                if (curId > maxId) maxId = curId;
            } else {
                // Find vacation to update
                if (curId == id) {
                    vac.setComment(vacation.getComment());
                    vac.setStart(vacation.getStart());
                    vac.setEnd(vacation.getEnd());

                    break;
                }
            }
        }

        if (newVac) {
            // Add new vacation
            vacation.setId(maxId + 1);
            vacations.add(vacation);
        }

        updateUserCurrentNum(user);
    }

    public void deleteVacation(User user, int id) {
        Iterator<Vacation> iterator = user.getVacations().iterator();

        while (iterator.hasNext()) {
            Vacation vac = iterator.next();
            int curId = vac.getId();

            if (curId == id) {
                iterator.remove();
                break;
            }
        }

        updateUserCurrentNum(user);
    }
}
