package cz.paka.sachy.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cz.paka.sachy.model.Novinka;
import cz.paka.sachy.model.Uzivatel;
import cz.paka.sachy.utils.Strankovani;

@Controller
public class NovinkaController {

	@RequestMapping(value = "/")
	public String index(Model model, Principal principal, HttpServletRequest request, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		List<Novinka> novinky = Novinka.findAllOrderedByDateByPage(page);
		model.addAttribute("novinky", novinky);

		// strankovani
		long pocetNovinek = Novinka.countNovinkas();
		long pocetStran = Strankovani.spocitejPocetStran(pocetNovinek, Strankovani.POCET_NOVINEK_NA_STRANKU);
		model.addAttribute("pocetStran", pocetStran);

		// seznam, ktery urcuje, ktera novinka je danym uzivatelem editovatelna
		List<Boolean> muzeEditovat = new ArrayList<Boolean>();
		for (Novinka n : novinky) {
			if (principal != null) {
				Uzivatel u = Uzivatel.findUzivatelByName(principal.getName());
				if (n.getAutor().equals(u) || request.isUserInRole("ROLE_ADMIN")) {
					muzeEditovat.add(true);
				} else {
					muzeEditovat.add(false);
				}
			} else {
				muzeEditovat.add(false);
			}
		}
		model.addAttribute("muzeEditovat", muzeEditovat);

		return "Main";
	}

}
