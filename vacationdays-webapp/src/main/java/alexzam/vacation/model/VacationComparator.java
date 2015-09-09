package alexzam.vacation.model;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@Lazy
public class VacationComparator implements Comparator<Vacation> {
    @Override
    public int compare(Vacation o1, Vacation o2) {
        return o1.getStart().compareTo(o2.getStart());
    }
}
