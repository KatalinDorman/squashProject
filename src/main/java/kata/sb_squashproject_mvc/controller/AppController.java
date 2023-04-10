/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kata.sb_squashproject_mvc.controller;

import java.util.List;
import kata.sb_squashproject_mvc.db.Database;
import kata.sb_squashproject_mvc.model.Match;
import kata.sb_squashproject_mvc.model.User;
import kata.sb_squashproject_mvc.services.Services;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Katalin
 */
@Controller
public class AppController {

    @GetMapping("/")
    public String loadLoginPage() {

        return "login.html";
    }

    @PostMapping("/login")
    public String checkLogin(Model model,
            @RequestParam(name = "name") String uName,
            @RequestParam(name = "password") String password) {

        String returnPage = "";

        Database db = new Database();
        User user = db.userExists(uName, password);
        Services s = new Services();               

            if (user == null) {
                returnPage = "login.html";
                model.addAttribute("error", "User doesn't exist!");
            } else {
                if (user.isNewuser() == true) {
                    returnPage = "changepassword.html";
                    model.addAttribute("user", user);
                } else {
                    List<Match> matchList = db.getAllMatches();
                    
                    matchList = s.orderByDate(matchList);
                    
                    returnPage = "index.html";
                    model.addAttribute("matchList", matchList);
                }
            }
      
        db.closeDb();

        return returnPage;
    }

}
