package alexzam.vacation.controller;

import alexzam.vacation.dto.FullInfo;
import alexzam.vacation.model.User;
import alexzam.vacation.service.GoogleAuthService;
import alexzam.vacation.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("gauth/")
public class GoogleAuthController {

    @Qualifier("googleAuthService")
    @Autowired
    private GoogleAuthService authService;

    @Autowired
    User user;

    @Autowired
    private StorageService storageService;

    @RequestMapping("received")
    @ResponseBody
    public FullInfo authReceived(HttpSession session,
                                 @RequestParam("idtoken") String idToken,
                                 @RequestParam("token") String token)
            throws GeneralSecurityException, IOException {

        if (token == null || !token.equals(session.getAttribute("token")))
            throw new GeneralSecurityException("Wrong own token");

        String id = authService.getIdFromToken(idToken);
        User storedUser = storageService.loadUser(id);

        if(storedUser == null) {
            user.setGoogleId(id);
            storageService.saveUser(user);
        } else {
            user.copyFrom(storedUser);
        }

        return user.generateDto();
    }
}
