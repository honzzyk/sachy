package cz.paka.sachy.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.supareno.pgnparser_nejsem_autorem.PGNParser;
import com.supareno.pgnparser_nejsem_autorem.Parser;
import com.supareno.pgnparser_nejsem_autorem.jaxb.Game;
import com.supareno.pgnparser_nejsem_autorem.jaxb.Games;

import cz.paka.sachy.exception.NoGameFoundException;
import cz.paka.sachy.model.Hrac;
import cz.paka.sachy.model.Partie;
import cz.paka.sachy.model.Uzivatel;

@Service
public class PartieService {

	/**
	 * zpracuje vstupni string, ve kterem hleda partie ve formatu PGN a nasledne ulozi - vytvori novou nebo upravi uz existujici
	 * 
	 * @throws NoGameFoundException
	 */
	public void ulozPartie(String pgnString, Uzivatel autor, Boolean verejne) throws NoGameFoundException {
		List<Game> games = parseGamesFromString(pgnString);

		// ulozim vsechny partie
		for (Game game : games) {
			Partie p = new Partie();
			p.setDatumPridani(new Date());
			p.setPgn(game.rawPGN);
			pridejHraceKPartii(game, p);
			p.setVysledek(game.getResult());
			p.setAutor(autor);
			p.setVerejna(verejne);

			p.persist(); // ulozi do DB
		}
	}

	/**
	 * upravi partii dle zadaneho vstupniho stringu ve formatu PGN
	 * 
	 * @throws NoGameFoundException
	 */
	public void upravPartii(String pgn, Boolean verejna, long id) throws NoGameFoundException {
		List<Game> games = parseGamesFromString(pgn);

		// ulozim vsechny partie
		for (Game game : games) {
			Partie p = Partie.findPartie(id);
			p.setPgn(game.rawPGN);
			pridejHraceKPartii(game, p);
			p.setVysledek(game.getResult());
			p.setVerejna(verejna);

			p.persist(); // ulozi do DB
		}
	}

	/**
	 * naparsuje vstupni retezec a vraci seznam partii
	 * 
	 * @throws NoGameFoundException
	 */
	private List<Game> parseGamesFromString(String pgnString) throws NoGameFoundException {
		// parser naparsuje vstup na partie
		Parser parser = new PGNParser();
		Games g = parser.parseString(pgnString);
		List<Game> games = g.getGame();

		// pokud neni nalezena zadna partie
		if (games.size() == 0) {
			throw new NoGameFoundException();
		}
		return games;
	}

	/**
	 * @param game
	 *            - hra, ze ktere lze zjistit bileho i cerneho v partii
	 */
	private void pridejHraceKPartii(Game game, Partie p) {
		// projdou se vsichni hraci, jestlize uz v DB dany hrac je, tak se ulozi on, jinak se vytvori novy
		p.setBily(null);
		p.setCerny(null);

		Hrac bily = Hrac.findHrac(game.getWhiteFirstName(), game.getWhiteSurName());
		if (bily == null) { // pokud bily hrac jeste neni v DB, tak ho vytvorim
			Hrac novyHrac = new Hrac();
			novyHrac.setJmeno(game.getWhiteFirstName());
			novyHrac.setPrijmeni(game.getWhiteSurName());
			novyHrac.persist();
			p.setBily(novyHrac);			
		} else { // pokud uz existuje, tak jen upravim partii
			p.setBily(bily);
		}
		
		Hrac cerny = Hrac.findHrac(game.getBlackFirstName(), game.getBlackSurName());
		if (cerny == null) { // pokud cerny hrac jeste neni v DB, tak ho vytvorim
			Hrac novyHrac = new Hrac();
			novyHrac.setJmeno(game.getBlackFirstName());
			novyHrac.setPrijmeni(game.getBlackSurName());
			novyHrac.persist();
			p.setCerny(novyHrac);			
		} else { // pokud uz existuje, tak jen upravim partii
			p.setCerny(cerny);
		}
	}

}
