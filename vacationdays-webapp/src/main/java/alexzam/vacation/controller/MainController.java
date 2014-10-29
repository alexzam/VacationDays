package alexzam.vacation.controller;

import alexzam.vacation.dto.DateState;
import alexzam.vacation.dto.FullInfo;
import alexzam.vacation.model.User;
import alexzam.vacation.model.Vacation;
import alexzam.vacation.service.GoogleAuthService;
import alexzam.vacation.service.StorageService;
import alexzam.vacation.service.VacDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    VacDaysService daysService;

    @Autowired
    User user;

    @Qualifier("storageService")
    @Autowired
    private StorageService storageService;

    @Qualifier("googleAuthService")
    @Autowired
    private GoogleAuthService googleAuthService;

    @Value("${auth.google.client_id}")
    private String googleAuthClientId;



    @RequestMapping("/")
    public ModelAndView main(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("main");
        modelAndView.addObject("haveData", user.getLastKnownDate() != null);

        String randomToken = googleAuthService.generateRandomToken();
        modelAndView.addObject("token", randomToken);
        session.setAttribute("token", randomToken);

        modelAndView.addObject("googleAuthClientId", googleAuthClientId);

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
