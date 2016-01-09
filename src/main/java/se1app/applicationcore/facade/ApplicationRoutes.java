package se1app.applicationcore.facade;

/**
 * Created by nilo on 12.11.15.
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApplicationRoutes {

    @RequestMapping("/login")
    public String login(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        return "login";
    }

    @RequestMapping("/manageCustomers")
    public String hello(String name, Model model) {
        return "manageCustomers";
    }
}