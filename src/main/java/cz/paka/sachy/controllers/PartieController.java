package cz.paka.sachy.controllers;

import org.springframework.security.access.AccessDeniedException;
import java.security.Principal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import cz.paka.sachy.model.*;
import cz.paka.sachy.utils.Strankovani;
import cz.paka.sachy.utils.Utils;

@Controller
public class PartieController {

	/**
	 * seznam vsech partii
	 */
	@RequestMapping(value = "/partie")
	public String seznam(Principal principal, Model model, HttpServletRequest request, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		// zobrazi bud vsechny partie pro prihlasene nebo jen verejne pro neprihlasene
		if (request.isUserInRole("ROLE_DEFAULT") || request.isUserInRole("ROLE_ADMIN")) {
			List<Partie> partie = Partie.findAllOrderedByDateByPage(page);
			long pocetCelkem = Partie.countParties();

			addPaging(model, partie, pocetCelkem);
			addEditableInfoToModel(principal, model, request, partie);
		} else {
			List<Partie> partie = Partie.findAllOrderedByDateByPagePublicOnly(page);
			long pocetCelkem = Partie.countPartiePublicOnly();

			addPaging(model, partie, pocetCelkem);
		}

		return "partie";
	}

	/**
	 * prida do modelu informace o tom, ktere partie muze dany uzivatel editovat
	 */
	private void addEditableInfoToModel(Principal principal, Model model, HttpServletRequest request, List<Partie> partie) {
		// seznam, ktery urcuje, ktera partie je danym uzivatelem editovatelna
		List<Boolean> muzeEditovat = new ArrayList<Boolean>();
		for (Partie p : partie) {
			if (principal != null) {
				Uzivatel u = Uzivatel.findUzivatelByName(principal.getName());
				if (p.canBeEditedBy(u)) { // muze editovat budto autor nebo admin
					muzeEditovat.add(true);
				} else {
					muzeEditovat.add(false);
				}
			} else {
				muzeEditovat.add(false);
			}

		}
		model.addAttribute("muzeEditovat", muzeEditovat);
	}

	/**
	 * prida do modelu informace ke strankovani
	 */
	private void addPaging(Model model, List<Partie> partie, long pocetCelkem) {
		model.addAttribute("partie", partie);

		// strankovani
		long pocetStran = Strankovani.spocitejPocetStran(pocetCelkem, Strankovani.POCET_PARTII_NA_STRANKU);
		model.addAttribute("pocetStran", pocetStran);

		model.addAttribute("zobrazitPravySloupec", true); // sloupec s vyhledavanim
	}

	/**
	 * zobrazeni detailu partie
	 */
	@RequestMapping(value = "partie/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Principal principal, Model model, HttpServletRequest request) throws AccessDeniedException {
		Partie partie = Partie.findPartie(id);
		addPartieToModel(partie, model);

		// pokud je uzivatel prihlaseny, predvyplni se ve formulari
		KomentarPartie novyKomentar = new KomentarPartie();
		if (principal != null) {
			novyKomentar.setAutor(principal.getName());
		}
		model.addAttribute("komentarPartie", novyKomentar);

		return returnViewName(partie, request);

	}

	/**
	 * pridani komentare k partii
	 */
	@RequestMapping(value = "partie/{id}", method = RequestMethod.POST)
	public String addKoment(@Valid @ModelAttribute KomentarPartie komentarPartie, BindingResult result, @PathVariable("id") Long id, Model model, HttpServletRequest request) throws AccessDeniedException {
		if (result.hasErrors()) {
			Partie p = Partie.findPartie(id);
			addPartieToModel(p, model);

			return returnViewName(p, request);
		}
		komentarPartie.setDatum(new Date());
		komentarPartie.setIp(request.getRemoteAddr());
		komentarPartie.setPartie(Partie.findPartie(id));
		komentarPartie.persist();

		return "redirect:/partie/" + id;
	}

	/**
	 * prida partii a jeji komentare do modelu
	 */
	private void addPartieToModel(Partie partie, Model model) {
		model.addAttribute("partie", partie);

		List<KomentarPartie> komentare = partie.getKomentareOrderedByDate();
		model.addAttribute("komentare", komentare);

		// prida se, zda muze uzivatel editovat partii
		String name = Utils.getUserName();
		if (name != null) {
			Uzivatel u = Uzivatel.findUzivatelByName(name);
			if (partie.canBeEditedBy(u)) { // editovat muze admin nebo vlastnik
				model.addAttribute("muzeEditovat", "true");
			}
		}
	}

	/**
	 * vraci nazev view (detailPartie), ktere se pouzije nebo vyhodi vyjimku v pripade, ze uzivatel nema pravo k pristupu
	 */
	private String returnViewName(Partie partie, HttpServletRequest request) throws AccessDeniedException {
		if (partie.getVerejna()) {
			return "partieDetail";
		} else if (request.isUserInRole("ROLE_DEFAULT") || request.isUserInRole("ROLE_ADMIN")) {
			return "partieDetail";
		} else {
			throw new AccessDeniedException("pristup zamitnut");
		}
	}

	/**
	 * zobrazi partie jen od daneho uzivatele
	 */
	@PreAuthorize("hasAnyRole('ROLE_DEFAULT','ROLE_ADMIN')")
	@RequestMapping(value = "moje-partie")
	public String mojePartie(@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model, Principal principal, HttpServletRequest request) {
		Uzivatel u = Uzivatel.findUzivatelByName(principal.getName());
		List<Partie> partie = Partie.findAllPartiesOfOwnerByPage(u, page);
		model.addAttribute("partie", partie);

		long pocetCelkem = Partie.countPartiesOfOwner(u);
		addPaging(model, partie, pocetCelkem);

		return "moje-partie";
	}

	@RequestMapping(value = "/hledat", method = RequestMethod.GET)
	public String hledat(@RequestParam("text") String hledanyVyraz, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model, Principal principal, HttpServletRequest request) {
		model.addAttribute("zobrazitPravySloupec", true);

		// kdyz neni zadano, nezobrazi se nic
		if (hledanyVyraz.isEmpty()) {
			model.addAttribute("partie", new ArrayList<Partie>());
			return "partie";
		}

		List<Partie> partie = Partie.searchGamesByPage(hledanyVyraz, (principal == null), page);

		model.addAttribute("partie", partie);

		Long pocetCelkem = Partie.countFoundGames(hledanyVyraz, (principal == null));

		addPaging(model, partie, pocetCelkem);
		addEditableInfoToModel(principal, model, request, partie);

		return "partie";
	}

}
