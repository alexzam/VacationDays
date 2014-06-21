package alexzam.vacation.controller;

import alexzam.vacation.dto.DateState;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/")
    public String main() {
        return "main";
    }

    @RequestMapping(value = "/json/setInitial", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public String setInitial(@RequestBody DateState state) {
        Logger.getLogger(MainController.class).debug("Okay! " + state.getDate());
        return "{\"st\":\"ok\"}";
    }


}
