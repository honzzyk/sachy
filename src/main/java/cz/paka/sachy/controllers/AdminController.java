package cz.paka.sachy.controllers;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cz.paka.sachy.model.Novinka;
import cz.paka.sachy.model.Partie;
import cz.paka.sachy.model.Prispevek;
import cz.paka.sachy.model.Uzivatel;
import cz.paka.sachy.utils.Utils;

/**
 * Controller pro administraci
 * 
 * @author Jan Novotny
 */
@Controller
@PreAuthorize("hasAnyRole('ROLE_DEFAULT','ROLE_ADMIN')")
public class AdminController {

	@RequestMapping(value = "admin")
	public String adminMain() {
		return "admin";
	}

	@RequestMapping(value = "admin/novinkaAdd", method = RequestMethod.GET)
	public String adminNovinkaAdd(Model model) {
		model.addAttribute("novinka", new Novinka());
		return "novinkaAdd";
	}

	/**
	 * pridani/upraveni novinky
	 */
	@RequestMapping(value = "admin/novinkaAdd", method = RequestMethod.POST)
	public String adminNovinkaAddPOST(@Valid @ModelAttribute Novinka novinka, BindingResult result, Model model, Principal principal, HttpServletRequest request) {
		if (result.hasErrors()) {
			return "novinkaAdd";
		}

		Novinka novinkaZDatabaze = Novinka.findNovinka(novinka.getId());
		if (novinkaZDatabaze == null) { // pokud se jedna o nove vytvorenou novinku
			novinka.setAutor(Uzivatel.findUzivatelByName(principal.getName()));
			novinka.setDatum(new Date());
			novinka.persist();
		} else { // pokud novinka uz existuje a jen se upravuje
			novinkaZDatabaze.setNadpis(novinka.getNadpis());
			novinkaZDatabaze.setText(novinka.getText());
			novinkaZDatabaze.persist();
		}
		return "redirect:/";
	}

	@RequestMapping(value = "admin/novinkaEdit/{id}", method = RequestMethod.GET)
	public String adminNovinkaEdit(@PathVariable("id") Long id, Model model) {
		model.addAttribute("novinka", Novinka.findNovinka(id));
		model.addAttribute("editace", true);
		return "novinkaAdd";
	}

	@RequestMapping(value = "admin/novinkaDelete/{id}", method = RequestMethod.GET)
	public String adminNovinkaDelete(@PathVariable("id") Long id, Model model) {
		Novinka n = Novinka.findNovinka(id);
		n.remove();
		return "redirect:/";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "admin/prispevekDelete/{id}", method = RequestMethod.GET)
	public String adminPrispevekDelete(@PathVariable("id") Long id, Model model) {
		Prispevek p = Prispevek.findPrispevek(id);
		p.remove();
		return "redirect:/diskuze";
	}

	@RequestMapping(value = "admin/partieDelete/{id}", method = RequestMethod.GET)
	public String adminPartieDeletee(@PathVariable("id") Long id, Model model, Principal principal, HttpServletRequest request) throws AccessDeniedException {
		Partie partie = Partie.findPartie(id);
		Uzivatel u = Uzivatel.findUzivatelByName(principal.getName());
		if (partie.getAutor().getId().equals(u.getId()) || request.isUserInRole("ROLE_ADMIN")) { // kontrola opravneni
			partie.remove();
			return "redirect:/partie";
		} else {
			throw new AccessDeniedException("pristup zamitnut");
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "admin/userAdd", method = RequestMethod.GET)
	public String adminUserAdd(Model model) {
		model.addAttribute("uzivatel", new Uzivatel());
		return "userAdd";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "admin/userAdd", method = RequestMethod.POST)
	public String adminUserAddPOST(@Valid @ModelAttribute Uzivatel uzivatel, BindingResult result, Model model, Principal principal, HttpServletRequest request) {
		if (result.hasErrors()) {
			return "userAdd";
		}
		if (Uzivatel.findUzivatelByName(uzivatel.getJmeno()) == null) { // pokud je jmeno volne
			try {
				Uzivatel.createUserRoleAndSave(uzivatel);
			} catch (Exception e) {
				model.addAttribute("msg", "Nastala chyba při vytváření uživatele.");
				return "userAdd";
			}
			return "redirect:/";
		} else {
			model.addAttribute("msg", "Jméno je již obsazeno! Zadejte jiné");
			return "userAdd";
		}

	}

	@RequestMapping(value = "admin/zmenaHesla", method = RequestMethod.GET)
	public String adminZmenaHesla(Model model) {
		return "zmenaHesla";
	}

	@RequestMapping(value = "admin/zmenaHesla", method = RequestMethod.POST)
	public String adminZmenaHeslaPOST(@RequestParam("aktualni") String aktualni, @RequestParam("novy") String novy, @RequestParam("novy2") String novy2, Model model, Principal principal) {
		Uzivatel u = Uzivatel.findUzivatelByName(principal.getName());
		String hesloZDB = u.getHeslo();
		String hesloZFormulare = Utils.sha256(aktualni);
		if (!hesloZDB.equals(hesloZFormulare)) {
			model.addAttribute("msg", "Aktuální heslo nebylo zadáno správně.");
			return "zmenaHesla";
		}
		if (novy.equals(novy2)) {
			u.setHeslo(Utils.sha256(novy));
			u.persist();
		} else {
			model.addAttribute("msg", "Hesla se neshodují.");
			return "zmenaHesla";
		}
		return "redirect:/admin";
	}
}
