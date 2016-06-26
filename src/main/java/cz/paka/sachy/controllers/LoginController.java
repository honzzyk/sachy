package cz.paka.sachy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@RequestMapping(value = "/login")
	public String login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			Model model) {
		if (error != null) {
			model.addAttribute("error", "Špatně zadané jméno nebo heslo!");
		}

		if (logout != null) {
			model.addAttribute("msg", "Byl jste úspěšně odhlášen.");
		}
		return "login";
	}
}
