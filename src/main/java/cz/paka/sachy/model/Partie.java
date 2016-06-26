package cz.paka.sachy.model;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import cz.paka.sachy.utils.Strankovani;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Trida reprezentujici sachovou partii
 * 
 * @author Jan Novotny
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Partie {

	/**
	 * výsledek partie <br>
	 * <br>
	 *
	 * <b>možné hodnoty:</b><br>
	 * <b>1</b> - výhra bílého<br>
	 * <b>0</b> - remíza<br>
	 * <b>-1</b> - výhra černého <br>
	 *
	 */
	private int vysledek;

	@NotNull
	@Column(columnDefinition = "BIT", length = 1)
	private Boolean verejna;

	@ManyToOne
	private Hrac bily;

	@ManyToOne
	private Hrac cerny;

	@ManyToOne
	private Uzivatel autor;

	/**
	 * partie ve formátu pgn (String)
	 */
	@Column(columnDefinition = "TEXT")
	@NotNull
	@Size(min = 1, message = "Minimálně 1 znak!")
	private String pgn;

	/**
	 * The result of the game can be:
	 * <ul>
	 * <li>1-0: White won the game</li>
	 * <li>0-1: Black won the game</li>
	 * <li>1/2-1/2: Draw game</li>
	 *
	 */
	public void setVysledek(String result) {
		// switch (result) {
		// case "1-0":
		// vysledek = 1;
		// break;
		// case "0-1":
		// vysledek = -1;
		// break;
		// case "1/2-1/2":
		// vysledek = 0;
		// break;
		// }
		if (result.equals("1-0")) {
			vysledek = 1;
		} else if (result.equals("0-1")) {
			vysledek = -1;
		} else if (result.equals("1/2-1/2")) {
			 vysledek = 0;
		}
	}

	@OneToMany(mappedBy = "partie", cascade = CascadeType.ALL)
	private Set<KomentarPartie> komentare = new HashSet<KomentarPartie>();

	private Date datumPridani;

	/**
	 * vraci seznam partii na x-te strance
	 */
	public static List<Partie> findAllOrderedByDateByPage(int page) {
		int firstResult = Strankovani.POCET_PARTII_NA_STRANKU * page - Strankovani.POCET_PARTII_NA_STRANKU;
		String query = "SELECT p FROM Partie p ORDER BY p.datumPridani DESC";
		return entityManager().createQuery(query, Partie.class).setFirstResult(firstResult)
				.setMaxResults(Strankovani.POCET_PARTII_NA_STRANKU).getResultList();
	}

	/**
	 * vraci seznam partii na x-te strance - pouze verejne partie
	 */
	public static List<Partie> findAllOrderedByDateByPagePublicOnly(int page) {
		int firstResult = Strankovani.POCET_PARTII_NA_STRANKU * page - Strankovani.POCET_PARTII_NA_STRANKU;
		String query = "SELECT p FROM Partie p WHERE (p.verejna = true) ORDER BY p.datumPridani DESC";
		return entityManager().createQuery(query, Partie.class).setFirstResult(firstResult)
				.setMaxResults(Strankovani.POCET_PARTII_NA_STRANKU).getResultList();
	}

	/**
	 * vraci pocet verejnych partii
	 */
	public static long countPartiePublicOnly() {
		return entityManager().createQuery("SELECT COUNT(p) FROM Partie p WHERE p.verejna = true", Long.class)
				.getSingleResult();
	}

	public boolean canBeEditedBy(Uzivatel u) {
		if (u == null) {
			return false;
		}
		if (autor.equals(u) || u.isAdmin()) { // editovat muze admin nebo
												// vlastnik
			return true;
		}
		return false;
	}

	/**
	 * vraci partie daneho uzivatele
	 */
	public static List<Partie> findAllPartiesOfOwnerByPage(Uzivatel u, int page) {
		int firstResult = Strankovani.POCET_PARTII_NA_STRANKU * page - Strankovani.POCET_PARTII_NA_STRANKU;
		String jpqlQuery = "SELECT p FROM Partie p WHERE p.autor = :u ORDER BY p.datumPridani DESC";
		return entityManager().createQuery(jpqlQuery, Partie.class).setParameter("u", u).setFirstResult(firstResult)
				.setMaxResults(Strankovani.POCET_PARTII_NA_STRANKU).getResultList();
	}

	/**
	 * vraci celkovy pocet partii daneho hrace
	 */
	public static long countPartiesOfOwner(Uzivatel u) {
		return entityManager().createQuery("SELECT COUNT(p) FROM Partie p WHERE p.autor = :u", Long.class)
				.setParameter("u", u).getSingleResult();
	}

	/**
	 * vraci seznam partii na x-te strance
	 */
	public List<KomentarPartie> getKomentareOrderedByDate() {
		List<KomentarPartie> k = new ArrayList<KomentarPartie>(komentare);
		Collections.sort(k, new Comparator<KomentarPartie>() {
			@Override
			public int compare(KomentarPartie o1, KomentarPartie o2) {
				return (o1.getDatum().before(o2.getDatum())) ? 1 : -1;
			}
		});
		return k;
	}

	/**
	 * vyhleda partie podle daneho vyrazu a dle toho, zda je uzivatel prihlasen
	 */
	public static List<Partie> searchGamesByPage(String hledanyVyraz, boolean zobrazitPouzeVerejne, int page) {
		int firstResult = Strankovani.POCET_PARTII_NA_STRANKU * page - Strankovani.POCET_PARTII_NA_STRANKU;
		String query = getSearchQueryAsString(zobrazitPouzeVerejne);

		return entityManager().createQuery(query, Partie.class).setParameter("vyraz", "%" + hledanyVyraz + "%")
				.setFirstResult(firstResult).setMaxResults(Strankovani.POCET_PARTII_NA_STRANKU).getResultList();
	}

	/**
	 * vraci pocet nalezenych partii
	 */
	public static Long countFoundGames(String hledanyVyraz, boolean zobrazitPouzeVerejne) {
		String query = getSearchQueryCount(zobrazitPouzeVerejne);
		return entityManager().createQuery(query, Long.class).setParameter("vyraz", "%" + hledanyVyraz + "%")
				.getSingleResult();
	}

	private static String getSearchQueryCount(boolean zobrazitPouzeVerejne) { // vraci
																				// dotaz
																				// pro
																				// vyhledavani
		String query = null;
		if (!zobrazitPouzeVerejne) {
			query = "SELECT COUNT(p) FROM Partie p " + "WHERE (" + "(p.bily.jmeno LIKE :vyraz) OR "
					+ "(p.bily.prijmeni LIKE :vyraz) OR " + "(p.cerny.jmeno LIKE :vyraz) OR "
					+ "(p.cerny.prijmeni LIKE :vyraz)" + ") ";
		} else {
			query = "SELECT COUNT(p) FROM Partie p " + "WHERE (" + "(" + "(p.bily.jmeno LIKE :vyraz) OR "
					+ "(p.bily.prijmeni LIKE :vyraz) OR " + "(p.cerny.jmeno LIKE :vyraz) OR "
					+ "(p.cerny.prijmeni LIKE :vyraz)" + ") " + "AND p.verejna = true" + ") ";
		}
		return query;
	}

	private static String getSearchQueryAsString(boolean zobrazitPouzeVerejne) { // vraci
																					// dotaz
																					// pro
																					// vyhledavani
		String query = null;
		if (!zobrazitPouzeVerejne) {
			query = "SELECT p FROM Partie p " + "WHERE (" + "(p.bily.jmeno LIKE :vyraz) OR "
					+ "(p.bily.prijmeni LIKE :vyraz) OR " + "(p.cerny.jmeno LIKE :vyraz) OR "
					+ "(p.cerny.prijmeni LIKE :vyraz)" + ") " + "ORDER BY p.datumPridani DESC";
		} else {
			query = "SELECT p FROM Partie p " + "WHERE (" + "(" + "(p.bily.jmeno LIKE :vyraz) OR "
					+ "(p.bily.prijmeni LIKE :vyraz) OR " + "(p.cerny.jmeno LIKE :vyraz) OR "
					+ "(p.cerny.prijmeni LIKE :vyraz)" + ") " + "AND p.verejna = true" + ") "
					+ "ORDER BY p.datumPridani DESC";
		}
		return query;
	}
}
