package alexzam.vacation.controller;

import alexzam.vacation.dto.DateState;
import alexzam.vacation.dto.FullInfo;
import alexzam.vacation.model.User;
import alexzam.vacation.model.Vacation;
import alexzam.vacation.service.VacDaysService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @Autowired
    VacDaysService daysService;

    @Autowired
    User user;

    @RequestMapping("/")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView("main");
        modelAndView.addObject("haveData", user.getLastKnownDate() != null);
        return modelAndView;
    }

    @RequestMapping(value = "/json/setInitial", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public FullInfo setInitial(@RequestBody DateState state) {
        daysService.setInitialDate(state);

        FullInfo info = user.generateDto();
        info.getVacations().add(new Vacation(1, LocalDate.now().minusMonths(5), LocalDate.now(), "Commentos"));

        return info;
    }

    @RequestMapping(value = "/json/getData", method = RequestMethod.GET, consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public FullInfo getData() {
        return user.generateDto();
    }

}
