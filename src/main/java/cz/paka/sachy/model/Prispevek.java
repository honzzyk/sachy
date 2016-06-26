package cz.paka.sachy.model;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import cz.paka.sachy.utils.Strankovani;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Size;

/**
 * Prispevek v diskuzi
 * 
 * @author Jan Novotny
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Prispevek {

	@Column(columnDefinition = "TEXT")
	@Size(min = 1, message = "Zadejte text!")
	private String text;

	@Size(min = 1, max = 25, message = "Délka musí být 1 až 25 písmen!")
	private String autor;

	/**
	 * IP adresa uzivatele
	 */
	private String ip;

	private Date datum;

	public static List<Prispevek> findAllOrderedByDate() {
		String query = "SELECT p FROM Prispevek p ORDER BY p.datum DESC";
		return entityManager().createQuery(query, Prispevek.class).getResultList();
	}

	public static List<Prispevek> findAllOrderedByDateByPage(int page) {
		int firstResult = Strankovani.POCET_PRISPEVKU_NA_STRANKU * page - Strankovani.POCET_PRISPEVKU_NA_STRANKU;
		String query = "SELECT p FROM Prispevek p ORDER BY p.datum DESC";
		return entityManager().createQuery(query, Prispevek.class).setFirstResult(firstResult).setMaxResults(Strankovani.POCET_PRISPEVKU_NA_STRANKU).getResultList();
	}

}
