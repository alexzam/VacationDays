package alexzam.vacation.controller;

import alexzam.vacation.service.GoogleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("received")
    public String authReceived(HttpSession session,
                               @RequestParam("idtoken") String idToken,
                               @RequestParam("token") String token)
            throws GeneralSecurityException, IOException {

        if (token == null || !token.equals(session.getAttribute("token")))
            throw new GeneralSecurityException("Wrong own token");

        return authService.getIdFromToken(idToken);
    }
}
