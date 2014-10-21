package alexzam.vacation.controller;

import alexzam.vacation.dto.DateState;
import alexzam.vacation.dto.FullInfo;
import alexzam.vacation.model.User;
import alexzam.vacation.model.Vacation;
import alexzam.vacation.service.StorageService;
import alexzam.vacation.service.VacDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @Autowired
    VacDaysService daysService;

    @Autowired
    User user;

    @Qualifier("storageService")
    @Autowired
    private StorageService storageService;

    @RequestMapping("/")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView("main");
        modelAndView.addObject("haveData", user.getLastKnownDate() != null);
        return modelAndView;
    }

    @RequestMapping("/testPut")
    @ResponseBody
    public String put() {
        storageService.saveUser(user);
        return "OKKK";
    }

    @RequestMapping(value = "/json/setInitial", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public FullInfo setInitial(@RequestBody DateState state) {
        daysService.setInitialDate(state);

        return user.generateDto();
    }

    @RequestMapping(value = "/json/getData", method = RequestMethod.GET, consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public FullInfo getData() {
        return user.generateDto();
    }

    @RequestMapping(value = "/json/setVacation", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public FullInfo setVacation(@RequestBody Vacation vacation) {
        daysService.setVacation(user, vacation);

        return user.generateDto();
    }

    @RequestMapping(value = "/json/deleteVacation", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public FullInfo deleteVacation(@RequestParam("id") int id) {
        daysService.deleteVacation(user, id);

        return user.generateDto();
    }
}
