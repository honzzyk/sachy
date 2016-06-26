package cz.paka.sachy.model;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Třída reprezentující hráče dané partie
 * 
 * @author Jan Novotny
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Hrac {

	private String jmeno;

	private String prijmeni;

	/**
	 * najde hrace dle zadaneho jmena a prijmeni. Pokud neexistuje, vraci null
	 */
	public static Hrac findHrac(String jmeno, String prijmeni) {
		String query = "SELECT h FROM Hrac h WHERE LOWER(h.jmeno) = :jmeno AND LOWER(h.prijmeni) = :prijmeni";
		
		try {
			return entityManager().createQuery(query, Hrac.class)
					.setParameter("jmeno", jmeno.toLowerCase())
					.setParameter("prijmeni", prijmeni.toLowerCase())
					.setMaxResults(1)
					.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
