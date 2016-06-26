package cz.paka.sachy.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cz.paka.sachy.model.Prispevek;
import cz.paka.sachy.utils.Strankovani;

@Controller
public class DiskuzeController {

	/**
	 * zobrazi vsechny prispevky v diskuzi
	 */
	@RequestMapping(value = "diskuze", method = RequestMethod.GET)
	public String diskuze(Model model, Principal principal, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		pridejPrispevkyDoModelu(model, page);

		// pokud je uzivatel prihlaseny, predvyplni se ve formulari jeho jmeno
		Prispevek novyPrispevek = new Prispevek();
		if (principal != null) {
			novyPrispevek.setAutor(principal.getName());
		}
		model.addAttribute("prispevek", novyPrispevek); 
		return "diskuze";
	}

	/**
	 * POST metoda pro pridani prispevku
	 */
	@RequestMapping(value = "prispevekAdd", method = RequestMethod.POST)
	public String prispevekAdd(@Valid @ModelAttribute Prispevek prispevek, BindingResult result, Model model, Principal principal, HttpServletRequest request) {
		if (result.hasErrors()) {
			pridejPrispevkyDoModelu(model, 1); // defaultne je chyba zobrazena prvni strana
			return "diskuze";
		}

		prispevek.setDatum(new Date());
		prispevek.setIp(request.getRemoteAddr());
		prispevek.persist();
		return "redirect:diskuze";
	}

	/**
	 * pomocna metoda pro pridani prispevku do modelu + strankovani
	 */
	private void pridejPrispevkyDoModelu(Model model, int page) {
		// zobrazeni prispevku na strance
		List<Prispevek> prispevky = Prispevek.findAllOrderedByDateByPage(page);

		for (Prispevek prispevek : prispevky) {
			prispevek.setText(StringEscapeUtils.escapeHtml(prispevek.getText()));
			prispevek.setText(prispevek.getText().replaceAll("\n", "<br />"));
		}

		// strankovani
		long pocetPrispevku = Prispevek.countPrispeveks();
		long pocetStran = Strankovani.spocitejPocetStran(pocetPrispevku, Strankovani.POCET_PRISPEVKU_NA_STRANKU);

		model.addAttribute("pocetStran", pocetStran);
		model.addAttribute("prispevky", prispevky);
	}

}
