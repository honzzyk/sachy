package cz.paka.sachy.controllers;

import org.springframework.security.access.AccessDeniedException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cz.paka.sachy.model.Partie;
import cz.paka.sachy.model.Uzivatel;
import cz.paka.sachy.service.PartieService;

@Controller
@PreAuthorize("hasAnyRole('ROLE_DEFAULT','ROLE_ADMIN')") // pouze prihlaseny uzivatel
public class SpravaPartiiController {
	@Autowired
	PartieService partieService;

	/**
	 * zobrazeni formulare pro pridani partie
	 */
	@RequestMapping(value = "/partieAdd", method = RequestMethod.GET)
	public String add() {
		return "partieAdd";
	}

	/**
	 * formular pro pridani partie v textovem poli
	 */
	@RequestMapping(value = "/partieAddString", method = RequestMethod.GET)
	public String partieAddString(Model model) {
		model.addAttribute("partie", new Partie());
		return "partieAddString";
	}

	/**
	 * POST metoda, zpracovani nahrane partie
	 */
	@RequestMapping(value = "/partieAdd", method = RequestMethod.POST)
	public String addPOST(@RequestParam(value = "verejna", required = false, defaultValue = "false") String verejna, @RequestParam("file") MultipartFile file, Model model, Principal principal) {
		if (!file.isEmpty()) {
			try {
				// nahrany soubor ulozit do stringu
				String pgnString = IOUtils.toString(file.getInputStream());
				Uzivatel autor = Uzivatel.findUzivatelByName(principal.getName());
				partieService.ulozPartie(pgnString, autor, Boolean.parseBoolean(verejna));
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("msg", "Došlo k chybě při nahrání partie!");
				return "partieAdd";
			}
		} else {
			model.addAttribute("msg", "Nebyl vybrán soubor!");
			return "partieAdd";
		}

		return "redirect:partie";
	}

	/**
	 * POST metoda, zpracovani nahrane partie ze stringu
	 */
	@RequestMapping(value = "/partieAddString", method = RequestMethod.POST)
	public String addPOSTString(@RequestParam(value = "verejna", required = false, defaultValue = "false") String verejna, @Valid @ModelAttribute Partie partie, Model model, Principal principal, BindingResult result) {
		if (result.hasErrors()) {
			return "partieAddString";
		}

		Uzivatel autor = Uzivatel.findUzivatelByName(principal.getName());
		try {
			partieService.ulozPartie(partie.getPgn(), autor, Boolean.parseBoolean(verejna));
			return "redirect:partie";
		} catch (Exception e) {
			model.addAttribute("msg", "Chyba při zpracování pgn. Je partie ve správném formátu?");
			return "partieAddString";
		}

	}

	/**
	 * editace partie; POST
	 */
	@RequestMapping(value = "partieEdit/{id}", method = RequestMethod.POST)
	public String partieEditPOST(@Valid @ModelAttribute Partie partie, BindingResult result, Principal principal, HttpServletRequest request, Model model, @PathVariable(value = "id") long id) throws AccessDeniedException {
		if (result.hasErrors()) {
			return "partieEdit";
		}

		try {
			partieService.upravPartii(partie.getPgn(), partie.getVerejna(), id);
		} catch (Exception e) {
			model.addAttribute("msg", "Chyba při nahrávání partie, je partie ve správném formátu?");
			return "partieEdit";
		}

		return "redirect:../partie/" + id;
	}

	/**
	 * editace partie
	 */
	@RequestMapping(value = "partieEdit/{id}", method = RequestMethod.GET)
	public String partieEdit(Model model, @PathVariable(value = "id") long id, Principal principal, HttpServletRequest request) throws AccessDeniedException {
		Partie partie = Partie.findPartie(id);
		Uzivatel u = Uzivatel.findUzivatelByName(principal.getName());
		if (partie.getAutor().equals(u) || request.isUserInRole("ROLE_ADMIN")) {
			model.addAttribute("partie", partie);
			return "partieEdit";
		} else {
			throw new AccessDeniedException("pristup zamitnut");
		}
	}
}
