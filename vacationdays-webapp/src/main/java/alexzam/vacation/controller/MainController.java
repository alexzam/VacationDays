package alexzam.vacation.controller;

import alexzam.vacation.dto.DateState;
import alexzam.vacation.model.User;
import alexzam.vacation.service.VacDaysService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    VacDaysService daysService;

    @RequestMapping("/")
    public String main() {
        return "main";
    }

    @RequestMapping(value = "/json/setInitial", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public User setInitial(@RequestBody DateState state) {
        User user = daysService.setInitialDate(null, state);

        return user;
    }


}
